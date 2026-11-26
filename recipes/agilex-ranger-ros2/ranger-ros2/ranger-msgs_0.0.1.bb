SUMMARY = "Messages for AgileX Ranger robots"
DESCRIPTION = "Message definitions (ActuatorState, DriverState, MotionState, ...) used by ranger_base."

ROS_BPN = "ranger_msgs"

ROS_BUILD_DEPENDS = " \
    builtin-interfaces \
    rosidl-default-runtime \
    std-msgs \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-native \
    rosidl-default-generators-native \
"

ROS_EXPORT_DEPENDS = " \
    builtin-interfaces \
    std-msgs \
"

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = " \
    builtin-interfaces \
    rosidl-default-runtime \
    std-msgs \
"

require ranger-ros2.inc
