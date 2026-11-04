SUMMARY = "Library for Intel Realsense D405/D455"
HOMEPAGE = "https://github.com/IntelRealSense/librealsense"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=89aea4e17d99a7cacdbeed46a0096b10"

inherit cmake pkgconfig
SRC_URI = "git://github.com/IntelRealSense/librealsense.git;protocol=https;branch=r/2.58.4;rev=34d6c778e1134d8505adcd56bb57acbe7598a459"

EXTRA_OECMAKE += " \
    -DBUILD_SHARED_LIBS=ON \
    -DBUILD_WITH_TM2=OFF \
    -DBUILD_EXAMPLES=OFF \
    -DBUILD_TOOLS=ON \
    -DFORCE_RSUSB_BACKEND=ON \
    -DBUILD_GRAPHICAL_EXAMPLES=OFF \
    -DBUILD_GLSL_EXTENSIONS=OFF \
    -DBUILD_WITH_OPENMP=true \
    -DCMAKE_BUILD_TYPE=Release"

DEPENDS = "udev libusb1"

SKIP_RECIPE[librealsense2] = ""

DEPENDS:remove = "glfw mesa libglu virtual/libgl"

RDEPENDS:${PN}-tests += "${PN}"

PACKAGES += "${PN}-tests"

FILES:${PN} = "${pkg_dest}${libdir}/${PN}.so.* \
       ${pkg_dest}${sysconfdir}/udev/rules.d/* "

FILES:${PN}-tests = "${pkg_dest}${bindir}/rs-enumerate-devices \
       ${pkg_dest}${bindir}/rs-fw-logger \
       ${pkg_dest}${bindir}/rs-fw-update "

do_install:append() {
    install -d "${D}/${sysconfdir}/udev/rules.d"
    install -m 0644 ${S}/config/99-realsense-libusb.rules ${D}/${sysconfdir}/udev/rules.d/99-${PN}-libusb.rules
}

INSANE_SKIP:${PN} += "installed-vs-shipped"

inherit robotics-package

do_configure[network] = "1"

