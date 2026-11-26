SUMMARY = "AgileX Ranger ROS 2 packages"
DESCRIPTION = "Meta recipe pulling in all ROS 2 packages of agilexrobotics/ranger_ros2 \
(ranger-msgs, ranger-base, ranger-bringup)."
HOMEPAGE = "https://github.com/agilexrobotics/ranger_ros2"
LICENSE = "BSD-3-Clause"

inherit packagegroup

RDEPENDS:${PN} = " \
    ranger-msgs \
    ranger-base \
    ranger-bringup \
"
