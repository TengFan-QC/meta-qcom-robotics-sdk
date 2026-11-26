# Copyright (c) 2026 Qualcomm Innovation Center, Inc. All rights reserved.
# SPDX-License-Identifier: BSD-3-Clause-Clear

# Robotics image for the automotive stack: auto machine-image plus robotics
# open-source and proprietary content. qcom-robotics-image and
# qcom-robotics-proprietary-image stay untouched for qcom-distro.
#
# Packages not ready on the auto stack are dropped from the packagegroups in
# conf/distro/qcom-robotics-distro.conf (RDEPENDS:<packagegroup>:remove).

require recipes-products/images/machine-image.bb
inherit rootfs-symlink
inherit psdk-image

DESCRIPTION = "Robotics image built on top of the automotive machine-image (machine-image.bb), \
customized for the automotive platform. Adds ROS2 framework, open-source robotics packages, \
open-source robotics packages with Qualcomm proprietary dependencies and Qualcomm \
proprietary robotics packages."

SUMMARY = "Robotics image on auto machine-image with open-source and proprietary content"

LICENSE = "BSD-3-Clause-Clear"

ROBOTICS_IMAGE_INSTALL = " \
    packagegroup-qcom-ros2 \
    packagegroup-robotics-opensource \
    packagegroup-oss-with-prop-deps \
    packagegroup-robotics-proprietary \
    packagegroup-robotics-qcnode \
    qirp-sdk \
"

IMAGE_INSTALL:append = " ${ROBOTICS_IMAGE_INSTALL}"

# Runtime packages of these packagegroups are packed into the QIRP SDK
# (bitbake robotics-machine-image -c generate_qirp_sdk). Not-ready packages are
# already removed from the packagegroups by the distro conf.
QIRP_SDK_PACKAGEGROUPS = " \
    packagegroup-robotics-opensource \
    packagegroup-oss-with-prop-deps \
    packagegroup-robotics-proprietary \
    packagegroup-robotics-qcnode \
"

ROOTFS_SYMLINK_PAIRS = "/usr/ros:/opt/ros"
