SUMMARY = "Livox SDK2 - SDK for Livox 3D LiDARs (HAP, Mid-360, Mid-360s/l, Avia2)"
DESCRIPTION = "Livox SDK2 provides the communication library used by livox_ros_driver2 \
to configure Livox LiDARs and receive point cloud / IMU data over Ethernet."
HOMEPAGE = "https://github.com/Livox-SDK/Livox-SDK2"
SECTION = "libs"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=433d1f618cfa59e85ac2a2e9f1af3969"

SRC_URI = "git://github.com/Livox-SDK/Livox-SDK2.git;protocol=https;branch=master"
SRCREV = "c0796f04c143143899c87a773d9f6b7136453c0b"

inherit cmake

# Upstream uses cmake_minimum_required(VERSION 3.0), which CMake >= 4 rejects.
EXTRA_OECMAKE += " \
    -DCMAKE_POLICY_VERSION_MINIMUM=3.5 \
    -DCMAKE_BUILD_TYPE=Release \
"

# Samples are not installed by upstream; do not build them.
do_configure:prepend() {
    sed -i '/add_subdirectory(samples)/d' ${S}/CMakeLists.txt
}

do_install:append() {
    # Upstream hardcodes "lib"; relocate if libdir differs (e.g. multilib).
    if [ "${D}${prefix}/lib" != "${D}${libdir}" ] && [ -d ${D}${prefix}/lib ]; then
        install -d ${D}${libdir}
        mv ${D}${prefix}/lib/* ${D}${libdir}/
        rmdir ${D}${prefix}/lib
    fi
}

# The shared library is unversioned (no SONAME), so it must ship in the
# runtime package instead of -dev, otherwise livox-ros-driver2 can't load it.
FILES_SOLIBSDEV = ""
FILES:${PN} += "${libdir}/liblivox_lidar_sdk_shared.so"
INSANE_SKIP:${PN} += "dev-so"
