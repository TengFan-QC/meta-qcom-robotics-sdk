# Copyright (c) Qualcomm Technologies, Inc. and/or its subsidiaries.
# SPDX-License-Identifier: BSD-3-Clause-Clear

DESCRIPTION = "Collection of robotics QCNode-based components, including contracts, qcnodes and ROS adapters."
SUMMARY = "QCNode-based robotics packages"
LICENSE = "BSD-3-Clause-Clear"

PACKAGE_ARCH = "${MACHINE_ARCH}"
inherit packagegroup
inherit rdepends-collector

RDEPENDS:${PN} = " \
    qrb-contracts-proto \
    qcnode \
    qrb-ros-adapter \
    qrb-ros-adapter-stream-probe \
"
