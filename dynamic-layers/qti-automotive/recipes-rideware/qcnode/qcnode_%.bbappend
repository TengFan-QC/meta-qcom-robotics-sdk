# Build the sensor nodes from qrb_qcnodes as part of qcnode, so that they are
# linked into libQCNode.so and registered with QCNodeFactory.
#
# The header-only qrb_contracts (extra buffer descriptors) is fetched here too
# instead of being a separate recipe: its headers include qcnode headers, so a
# qrb-contracts recipe depending on qcnode while qcnode depends on it would be a
# circular dependency. Its protobuf part (QRB_CONTRACTS_BUILD_PROTO, OFF by default)
# is not built here, it is the separate recipe qrb-contracts-proto.
#
# The HCM IPC Adapter Node of qrb_ros_adapter is a QCNode as well, so it is
# built into libQCNode.so the same way, and its SampleIpcAdapter into the
# QCNode sample application (qcrun QCNodeSampleApp -t IpcAdapter). The rest of
# qrb_ros_adapter (IPC runtime, ROS IPC Bridge) stays in separate recipes.
#
# qcnode itself is not patched: qrb_qcnodes.cmake is injected through
# CMAKE_PROJECT_qcnode_INCLUDE and adds the node directories from the
# qrb_qcnodes and qrb_ros_adapter checkouts to the qcnode build.

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "\
    git://github.com/qualcomm-qrb-ros/qrb_qcnodes.git;protocol=https;branch=main;name=qrb-qcnodes;destsuffix=qrb_qcnodes \
    git://github.com/qualcomm-qrb-ros/qrb_contracts.git;protocol=https;branch=main;name=qrb-contracts;destsuffix=qrb_contracts \
    git://github.com/qualcomm-qrb-ros/qrb_ros_adapter.git;protocol=https;branch=main;name=qrb-ros-adapter;destsuffix=qrb_ros_adapter \
    file://qrb_qcnodes.cmake \
"
# qcnode already fetches one git repo (the unnamed one, whose name is "default"),
# so with the repos below there are several SCMs and bitbake requires
# SRCREV_FORMAT to say how their revisions are combined.
SRCREV_FORMAT = "default_qrb-qcnodes_qrb-contracts_qrb-ros-adapter"

# TODO: pin SRCREV_qrb-qcnodes, SRCREV_qrb-contracts and SRCREV_qrb-ros-adapter
# to fixed commits (or tags) once the repos are stable. AUTOREV follows the head
# of the branch, which is convenient during development but makes the build
# non-reproducible.
SRCREV_qrb-qcnodes = "${AUTOREV}"
SRCREV_qrb-contracts = "${AUTOREV}"
SRCREV_qrb-ros-adapter = "${AUTOREV}"

# The IPC Adapter Node links the IPC runtime of qrb_ros_adapter (and through it
# protobuf and qrb_contracts_proto) into libQCNode.so.
DEPENDS += "qrb-ros-adapter-runtime protobuf nlohmann-json"

EXTRA_OECMAKE += "\
    -DCMAKE_PROJECT_qcnode_INCLUDE=${UNPACKDIR}/qrb_qcnodes.cmake \
    -DQRB_QCNODES_SOURCE_DIR=${UNPACKDIR}/qrb_qcnodes \
    -DQRB_CONTRACTS_SOURCE_DIR=${UNPACKDIR}/qrb_contracts \
    -DQRB_ROS_ADAPTER_SOURCE_DIR=${UNPACKDIR}/qrb_ros_adapter \
"

# Re-run configure when the injected hook or the node sources change.
do_configure[file-checksums] += "${THISDIR}/files/qrb_qcnodes.cmake:True"
