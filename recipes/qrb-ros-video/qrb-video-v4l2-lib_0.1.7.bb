inherit pkgconfig cmake

DESCRIPTION = "A hardware accelerated library implementing video encoding and decoding based on V4L2."
AUTHOR = "Jean Xiao <quic_jianxiao@quicinc.com>"
ROS_AUTHOR = "Jean Xiao"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=86fcc2294062130b497ba0ffff9f82fc"

PV = "0.2.0"

DEPENDS  = "glog gflags"
DEPENDS += "gstreamer1.0-plugins-base"

SRC_URI = "git://github.com/quic-qrb-ros/qrb_ros_video.git;protocol=https;branch=dev/robot-iq10"

SRCREV = "b12df71c6235f2e316d7d405b5e19cdd98e5d3ea"
S = "${UNPACKDIR}/${BP}/qrb_video_v4l2_lib"

EXTRA_OECMAKE:append = " -DBUILD_TESTING=OFF"

FILES:${PN}-dev += "${datadir}"
