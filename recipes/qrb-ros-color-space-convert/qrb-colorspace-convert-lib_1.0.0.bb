inherit ros_distro_${ROS_DISTRO}
inherit ros_component
inherit ros_insane_dev_so

ROS_BUILD_TYPE = "ament_cmake"

inherit ros_${ROS_BUILD_TYPE}
inherit robotics-package

DESCRIPTION = "The library to convert color space based on OpenGL ES"
AUTHOR = "Vito Wang <wantao@qti.qualcomm.com>"
ROS_AUTHOR = "Vito Wang"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=86fcc2294062130b497ba0ffff9f82fc"

ROS_CN = "qrb_colorspace_convert_lib"
ROS_BPN = "qrb_colorspace_convert_lib"

ROS_BUILD_DEPENDS = " \
    virtual/egl \
    virtual/libgles2 \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-auto-native \
"

ROS_EXPORT_DEPENDS = ""

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = ""

PACKAGECONFIG ??= "${@bb.utils.contains('DISTRO_FEATURES', 'glvnd', 'qcom-adreno-direct', '', d)}"
PACKAGECONFIG[qcom-adreno-direct] = "-DQRB_EGL_LIBRARY=${STAGING_LIBDIR}/libEGL_adreno.so.1 -DQRB_GLESV2_LIBRARY=${STAGING_LIBDIR}/libGLESv2_adreno.so.2,,qcom-adreno,qcom-adreno"

DEPENDS = "${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS}"

RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

SRC_URI = "git://github.com/quic-qrb-ros/qrb_ros_color_space_convert.git;protocol=https;branch=dev/robot-iq10"
SRCREV = "${AUTOREV}"
S = "${UNPACKDIR}/${PN}-${PV}/qrb_colorspace_convert_lib"
