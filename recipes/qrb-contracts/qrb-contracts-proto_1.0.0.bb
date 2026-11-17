SUMMARY = "QRB contracts: protobuf schemas"
DESCRIPTION = "Protocol Buffer schemas for robotics, aligned with ROS 2 messages"
HOMEPAGE = "https://github.com/qualcomm-qrb-ros/qrb_contracts"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=7e50fb71aa022627cbbc82d17793a8c9"

SRC_URI = "git://github.com/qualcomm-qrb-ros/qrb_contracts.git;protocol=https;branch=main"

SRCREV = "${AUTOREV}"

OECMAKE_SOURCEPATH = "${S}/proto"

DEPENDS += "protobuf protobuf-native"

inherit cmake

EXTRA_OECMAKE += "\
    -DCMAKE_INSTALL_PREFIX=/usr \
    -DBUILD_SHARED_LIBS=ON \
"

FILES:${PN}-dev += "\
    ${datadir}/qrb_contracts_proto \
"
