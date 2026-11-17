# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: BSD-3-Clause-Clear
#
# Hook that builds the nodes from qrb_qcnodes as part of the qcnode build and
# links them into libQCNode.so. qrb_contracts (buffer descriptors and protobuf
# schemas) is built and installed from source as well.
#
# It is injected by qcnode_%.bbappend through
#   -DCMAKE_PROJECT_qcnode_INCLUDE=<this file>
# so that the qcnode sources do not have to be patched. CMake runs this file
# right after project(qcnode). The node directories are added immediately
# (add_subdirectory() cannot be deferred), while linking them into QCNode is
# deferred to the end of the top-level CMakeLists.txt, when that target exists.

if( NOT QRB_QCNODES_SOURCE_DIR OR NOT EXISTS ${QRB_QCNODES_SOURCE_DIR}/source/Node )
    message( FATAL_ERROR "QRB_QCNODES_SOURCE_DIR is not set or invalid: '${QRB_QCNODES_SOURCE_DIR}'" )
endif()

# qrb_contracts is added as a subdirectory so that its own install rules apply:
# the buffer descriptor headers (QRB/...), the generated protobuf headers
# (qrb/contract/...pb.h), the .proto schemas, libqrb_contracts_proto and the
# qrb_contracts CMake package are all installed together with qcnode.
#
# It is not a separate recipe: its descriptor headers include qcnode headers, so
# a qrb-contracts recipe depending on qcnode while qcnode depends on it would be
# a circular dependency.
#
# The protobuf part is deliberately NOT linked into libQCNode.so (QCNode gets no
# link dependency on qrb_contracts_proto); the nodes only see the header-only
# descriptors. Downstream consumers link qrb_contracts::proto themselves.
if( QRB_CONTRACTS_SOURCE_DIR )
    if( NOT EXISTS ${QRB_CONTRACTS_SOURCE_DIR}/CMakeLists.txt )
        message( FATAL_ERROR "QRB_CONTRACTS_SOURCE_DIR is invalid: '${QRB_CONTRACTS_SOURCE_DIR}'" )
    endif()
    message( STATUS "qrb_qcnodes: adding qrb_contracts from ${QRB_CONTRACTS_SOURCE_DIR}" )
    add_subdirectory( ${QRB_CONTRACTS_SOURCE_DIR} ${CMAKE_BINARY_DIR}/qrb_contracts )
    # Make the descriptor headers visible to the node directories added below,
    # without linking the proto library into them.
    include_directories( ${QRB_CONTRACTS_SOURCE_DIR}/include )
endif()

# One entry per node directory under qrb_qcnodes/source/Node, as
# <directory>:<OBJECT library target>. Add new nodes (Imu, Lidar, ...) here once
# they are wired into the upstream tree.
set( QRB_QCNODES_NODES demo:QCNodeDemo )

foreach( entry IN LISTS QRB_QCNODES_NODES )
    string( REPLACE ":" ";" entry ${entry} )
    list( GET entry 0 dir )
    list( GET entry 1 target )
    message( STATUS "qrb_qcnodes: adding node '${dir}' (${target})" )
    add_subdirectory( ${QRB_QCNODES_SOURCE_DIR}/source/Node/${dir} ${CMAKE_BINARY_DIR}/qrb_qcnodes/${dir} )
    cmake_language( EVAL CODE "
        cmake_language( DEFER DIRECTORY ${CMAKE_SOURCE_DIR} CALL target_link_libraries
            QCNode PUBLIC ${target} )
    " )
endforeach()
