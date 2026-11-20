inherit ros_distro_${ROS_DISTRO}
inherit ros_component robotics-package
inherit pkgconfig cmake

LICENSE  = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

SRC_URI = "git://github.com/qualcomm-qrb-ros/qrb_ros_camera.git;protocol=https;branch=dev/robot-iq10;subpath=qrb_ros_camera"
SRCREV = "${AUTOREV}"
S = "${UNPACKDIR}/qrb_ros_camera"

# Dependencies. No camera backend: the backends are plugins of qrb-camera, found at run time
# through plugin_discovery; the image installs the one of its machine.
CAMERA_ROS2_NODE_DEPENDS = " \
    qrb-ros-transport-image-type \
    qrb-camera \
    plugin-discovery \
"

ROS_BUILD_DEPENDS = " \
    rclcpp \
    rclcpp-components \
    sensor-msgs \
    yaml-cpp \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-auto-native \
"

ROS_EXEC_DEPENDS = " \
    rclcpp \
    rclcpp-components \
    sensor-msgs \
    qrb-ros-transport-image-type \
    qrb-camera \
"
ROS_TEST_DEPENDS = " \
    ament-lint-auto \
    ament-lint-common \
"
ROS_EXPORT_DEPENDS = " \
    rclcpp \
"
ROS_BUILDTOOL_EXPORT_DEPENDS = ""

DEPENDS = "${CAMERA_ROS2_NODE_DEPENDS} ${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS} ${ROS_TEST_DEPENDS}"
DEPENDS += "pkgconfig-native"

RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

ROS_BUILD_TYPE = "ament_cmake"
inherit ros_${ROS_BUILD_TYPE}

FILES:${PN} += " \
    ${pkg_dest}${includedir} "

# Builds TestNode: the launch file loads it by default (enable_test_node:=true).
EXTRA_OECMAKE:append = " -DBUILD_TESTING=ON"