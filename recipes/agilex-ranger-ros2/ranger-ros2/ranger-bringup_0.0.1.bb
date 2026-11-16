SUMMARY = "Bringup launch files for AgileX Ranger robots"
DESCRIPTION = "Launch files (ranger, ranger_mini_v1/v2/v3) that start ranger_base_node."

ROS_BPN = "ranger_bringup"

ROS_BUILD_DEPENDS = ""

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-native \
"

ROS_EXPORT_DEPENDS = ""

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = " \
    ament-index-python \
    launch \
    launch-ros \
    ranger-base \
"

require ranger-ros2.inc
