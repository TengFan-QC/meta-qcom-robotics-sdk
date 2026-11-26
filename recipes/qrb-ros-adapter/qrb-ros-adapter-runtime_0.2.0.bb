SUMMARY = "QRB ROS Adapter: IPC Runtime"
DESCRIPTION = "Flow model, IPC transport and Contract validation shared by the HCM IPC Adapter and the ROS IPC Bridge, over the qrb_contracts_proto messages. Depends on neither ROS nor QCNode."
HOMEPAGE = "https://github.com/qualcomm-qrb-ros/qrb_ros_adapter"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://LICENSE;md5=223037c4be0bfc6cf757035432adf983"

SRC_URI = "git://github.com/qualcomm-qrb-ros/qrb_ros_adapter.git;protocol=https;branch=main"

SRCREV = "${AUTOREV}"

OECMAKE_SOURCEPATH = "${S}/ipc_runtime"

DEPENDS += "qrb-contracts-proto protobuf protobuf-native"

inherit cmake

EXTRA_OECMAKE += "\
    -DCMAKE_INSTALL_PREFIX=/usr \
    -DBUILD_TESTING=OFF \
    -DProtobuf_PROTOC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/protoc \
"

FILES:${PN}-dev += "\
    ${datadir}/qrb_ros_adapter_runtime \
"
