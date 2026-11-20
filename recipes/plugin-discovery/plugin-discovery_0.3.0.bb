inherit cmake pkgconfig

SUMMARY = "plugin_discovery: configuration-driven backend loading for the Robotics SDK"
DESCRIPTION = "C++ library that loads the backends listed by configuration (platform \
config package, customer config or manual choice), lets each backend report through \
probe() whether it works on this machine and which devices it serves, checks interface \
version and ABI, and keeps several backends of one interface in one process. Two \
libraries: plugin_loader (dlopen layer aligned with ROS class_loader) and plugin_discovery."
AUTHOR = "Chaochen Liang <chalia@qti.qualcomm.com>"
HOMEPAGE = "https://github.com/qualcomm-qrb-ros/plugin_discovery"
SECTION = "devel"
LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://LICENSE;md5=27bed425d409914cf1a2a322089c7558"

SRC_URI = "git://github.com/qualcomm-qrb-ros/plugin_discovery.git;protocol=https;branch=main"
SRCREV = "877c0f321501f01127d3f8955f17594963e1170a"

DEPENDS = "yaml-cpp"

# The exported CMake targets reference pluginlib-detect / platform-detect in ${bindir};
# stage it so that dependent projects (plugin-discovery-examples) can find_package() us.
SYSROOT_DIRS += "${bindir}"

EXTRA_OECMAKE += " \
    -DPLUGIN_LOADER_BUILD_TESTS=OFF \
    -DCMAKE_INSTALL_RPATH= \
"

# The plugin_loader / plugin_discovery shared libraries have no SONAME version, so the
# unversioned .so is the runtime library and belongs in the main package.
FILES_SOLIBSDEV = ""
FILES:${PN} += " \
    ${libdir}/libplugin_loader.so \
    ${libdir}/libplugin_discovery.so \
    ${datadir}/plugin_discovery \
    ${nonarch_base_libdir}/systemd/system \
"
FILES:${PN}-dev += "${libdir}/cmake/plugin_discovery"
INSANE_SKIP:${PN} += "dev-so"
