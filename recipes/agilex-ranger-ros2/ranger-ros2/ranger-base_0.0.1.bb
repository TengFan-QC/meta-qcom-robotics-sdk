SUMMARY = "Control node for AgileX Ranger robots"
DESCRIPTION = "ranger_base_node: ROS 2 base driver for AgileX Ranger / Ranger Mini, \
talks to the robot over CAN through ugv-sdk."

ROS_BPN = "ranger_base"

ROS_BUILD_DEPENDS = " \
    geometry-msgs \
    nav-msgs \
    rclcpp \
    sensor-msgs \
    std-msgs \
    tf2 \
    tf2-geometry-msgs \
    tf2-ros \
    ranger-msgs \
    rosidl-default-runtime \
    ugv-sdk \
    boost \
    libeigen \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-native \
"

ROS_EXPORT_DEPENDS = ""

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

# launch files use launch, launch_ros, launch_xml and ament_index_python
ROS_EXEC_DEPENDS = " \
    ament-index-python \
    geometry-msgs \
    launch \
    launch-ros \
    launch-xml \
    nav-msgs \
    rclcpp \
    sensor-msgs \
    std-msgs \
    tf2 \
    tf2-geometry-msgs \
    tf2-ros \
    ranger-msgs \
"

require ranger-ros2.inc
