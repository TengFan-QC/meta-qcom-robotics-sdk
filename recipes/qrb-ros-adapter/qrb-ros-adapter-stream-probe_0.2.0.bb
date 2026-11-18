inherit ros_distro_${ROS_DISTRO}
inherit ros_component
inherit ros_insane_dev_so

ROS_BUILD_TYPE = "ament_cmake"

inherit ros_${ROS_BUILD_TYPE}
inherit robotics-package

SUMMARY = "QRB ROS Adapter: end-to-end stream probe"
DESCRIPTION = "The ROS side of the end-to-end test of qrb_ros_adapter: a probe component that sends or receives and checks frames through the ROS IPC Bridge, and a launch file that runs it in the Bridge container. The HCM side, HcmStreamProbeNode and its QCNode sample, is built into qcnode with the HCM IPC Adapter Node."
HOMEPAGE = "https://github.com/qualcomm-qrb-ros/qrb_ros_adapter"
SECTION = "devel"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=223037c4be0bfc6cf757035432adf983"

ROS_CN = "qrb_ros_adapter_stream_probe"
ROS_BPN = "qrb_ros_adapter_stream_probe"

ROS_BUILD_DEPENDS = " \
    ament-index-cpp \
    rclcpp \
    rclcpp-components \
    geometry-msgs \
    sensor-msgs \
    qrb-ros-transport-point-cloud2-type \
    qrb-ros-adapter \
    nlohmann-json \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-native \
"

ROS_EXPORT_DEPENDS = ""

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = " \
    ament-index-cpp \
    rclcpp \
    rclcpp-components \
    geometry-msgs \
    sensor-msgs \
    qrb-ros-transport-point-cloud2-type \
    qrb-ros-adapter \
    launch \
    launch-ros \
    ament-index-python \
"

DEPENDS = "${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS}"

RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

SRC_URI = "git://github.com/qualcomm-qrb-ros/qrb_ros_adapter.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
S = "${UNPACKDIR}/${PN}-${PV}/test"
