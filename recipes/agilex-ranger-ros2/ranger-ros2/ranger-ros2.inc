# Common settings for the packages of agilexrobotics/ranger_ros2.
#
# The upstream repository is a multi-package ROS 2 workspace (no top-level
# CMakeLists.txt):
#     ranger_msgs/    message definitions
#     ranger_base/    ranger_base_node, depends on ugv-sdk and ranger_msgs
#     ranger_bringup/ launch files
# so each package gets its own recipe which builds a sub-directory of the
# same checkout through OECMAKE_SOURCEPATH.

inherit ros_distro_${ROS_DISTRO}
inherit ros_component

AUTHOR = "AgileX Robotics"
HOMEPAGE = "https://github.com/agilexrobotics/ranger_ros2"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://LICENSE;md5=510e1827455e44bc25a01f81c90591f0"

ROS_CN = "ranger_ros2"

# The only upstream branch is "humble".
ROS_BRANCH ?= "branch=humble"
SRC_URI = "git://github.com/agilexrobotics/ranger_ros2.git;${ROS_BRANCH};protocol=https"
# humble @ "update readme"
SRCREV = "b6ea21a275ca5e7168130cc6470e61474681d679"

S = "${UNPACKDIR}/${BP}"
OECMAKE_SOURCEPATH = "${S}/${ROS_BPN}"

EXTRA_OECMAKE:append = " -DCMAKE_SKIP_RPATH=ON"

# ---- Avoid TMPDIR leaking into binaries/debug info (QA buildpaths) ----
CFLAGS:append   = " ${DEBUG_PREFIX_MAP}"
CXXFLAGS:append = " ${DEBUG_PREFIX_MAP}"

DEPENDS = "${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS}"
RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

ROS_BUILD_TYPE = "ament_cmake"
inherit ros_${ROS_BUILD_TYPE} robotics-package
