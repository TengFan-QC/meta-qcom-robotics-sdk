inherit ros_distro_${ROS_DISTRO}
inherit ros_component
inherit ros_insane_dev_so

ROS_BUILD_TYPE = "ament_cmake"

inherit ros_${ROS_BUILD_TYPE}
inherit robotics-package

SUMMARY = "QRB ROS Adapter: ROS IPC Bridge"
DESCRIPTION = "ROS IPC Bridge of qrb_ros_adapter: a Manager that creates one typed ROS 2 node per registered HCM Flow. Depends on the IPC Runtime only, not on QCNode or the HCM IPC Adapter."
HOMEPAGE = "https://github.com/qualcomm-qrb-ros/qrb_ros_adapter"
SECTION = "devel"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=223037c4be0bfc6cf757035432adf983"

ROS_CN = "qrb_ros_adapter"
ROS_BPN = "qrb_ros_adapter"

ROS_BUILD_DEPENDS = " \
    rclcpp \
    rclcpp-components \
    builtin-interfaces \
    std-msgs \
    geometry-msgs \
    nav-msgs \
    sensor-msgs \
    tf2-msgs \
    vision-msgs \
    qrb-ros-transport-point-cloud2-type \
    qrb-ros-transport-image-type \
    qrb-ros-adapter-runtime \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-native \
"

ROS_EXPORT_DEPENDS = " \
    rclcpp \
    rclcpp-components \
    builtin-interfaces \
    std-msgs \
    geometry-msgs \
    nav-msgs \
    sensor-msgs \
    tf2-msgs \
    vision-msgs \
    qrb-ros-transport-point-cloud2-type \
    qrb-ros-transport-image-type \
    qrb-ros-adapter-runtime \
"

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = " \
    rclcpp \
    rclcpp-components \
    builtin-interfaces \
    std-msgs \
    geometry-msgs \
    nav-msgs \
    sensor-msgs \
    tf2-msgs \
    vision-msgs \
    qrb-ros-transport-point-cloud2-type \
    qrb-ros-transport-image-type \
    qrb-ros-adapter-runtime \
    launch \
    launch-ros \
    ament-index-python \
"

DEPENDS = "${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS}"

RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

SRC_URI = "git://github.com/qualcomm-qrb-ros/qrb_ros_adapter.git;protocol=https;branch=main"
SRCREV = "${AUTOREV}"
S = "${UNPACKDIR}/${PN}-${PV}/ros_ipc_bridge"
