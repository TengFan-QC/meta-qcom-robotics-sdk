inherit ros_distro_${ROS_DISTRO}
inherit ros_component

DESCRIPTION = "The ROS device driver for Livox 3D LiDARs (HAP, Mid-360, Mid-360s/l, Avia2), for ROS2"
AUTHOR = "feng <dev@livoxtech.com>"
HOMEPAGE = "https://github.com/Livox-SDK/livox_ros_driver2"
SECTION = "devel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=51d991140ae82e35d22cb3aeaced1fb3"

ROS_CN = "livox_ros_driver2"
ROS_BPN = "livox_ros_driver2"

ROS_BUILD_DEPENDS = " \
    livox-sdk2 \
    rclcpp \
    rclcpp-components \
    std-msgs \
    sensor-msgs \
    builtin-interfaces \
    rcutils \
    rcl-interfaces \
    pcl-conversions \
    pcl \
    apr \
    rosidl-default-runtime \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-auto-native \
    rosidl-default-generators-native \
"

ROS_EXPORT_DEPENDS = ""

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = " \
    livox-sdk2 \
    rclcpp \
    rclcpp-components \
    std-msgs \
    sensor-msgs \
    builtin-interfaces \
    rcutils \
    rcl-interfaces \
    pcl-conversions \
    apr \
    rosidl-default-runtime \
"

# Currently informational only -- see http://www.ros.org/reps/rep-0149.html#dependency-tags.
ROS_TEST_DEPENDS = " \
    ament-lint-auto \
    ament-lint-common \
"

DEPENDS = "${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS}"

RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

ROS_BRANCH ?= "branch=master"
SRC_URI = "git://github.com/Livox-SDK/livox_ros_driver2.git;${ROS_BRANCH};protocol=https"
# master @ "Support Mid360L Lidar" (driver version 1.2.8)
SRCREV = "21445540f0d100dc86a7e6df312dd70bbdb4afdf"
S = "${UNPACKDIR}/${BP}"

# Upstream build.sh normally does these steps before colcon build:
#  - select package_ROS2.xml as package.xml
#  - pass -DROS_EDITION=ROS2 -DDISTRO_ROS=<distro>
# DISTRO_ROS only selects the humble/jazzy (rosidl_get_typesupport_target) code path,
# which is the correct one for all ROS2 distros >= humble.
do_configure:prepend() {
    cp -f ${S}/package_ROS2.xml ${S}/package.xml
}

EXTRA_OECMAKE:append = " \
    -DROS_EDITION=ROS2 \
    -DDISTRO_ROS=jazzy \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DCMAKE_SKIP_RPATH=ON \
"

# ---- Avoid TMPDIR leaking into binaries/debug info (QA buildpaths) ----
CFLAGS:append   = " ${DEBUG_PREFIX_MAP}"
CXXFLAGS:append = " ${DEBUG_PREFIX_MAP}"

do_install:append() {
    # Sanitize exported cmake files that may embed sysroot paths of PCL/livox-sdk2
    for f in $(find ${D} -name "*.cmake" 2>/dev/null); do
        sed -i -e "s|${RECIPE_SYSROOT}||g" -e "s|${TMPDIR}||g" "$f"
    done
}

FILES:${PN} += "${ros_datadir}/${ROS_BPN}"

ROS_BUILD_TYPE = "ament_cmake"

inherit ros_${ROS_BUILD_TYPE} robotics-package
