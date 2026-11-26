inherit ros_distro_${ROS_DISTRO}
inherit ros_component
# The backends are plugins without a SONAME version: lib/camera/libcamera_backend_*.so
inherit ros_insane_dev_so
inherit pkgconfig cmake

DESCRIPTION = "QRB Camera: camera interface, CameraManager and the QMMF / QCarCam  \
backends, loaded at run time through plugin_discovery"
AUTHOR = "Dan Wang <danwa@qti.qualcomm.com>"
ROS_AUTHOR = "Dan Wang"
SECTION = "devel"
LICENSE = "BSD-3-Clause"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/${LICENSE};md5=550794465ba0ec5312d6919e203a55f9"

ROS_CN = "qrb_camera"
ROS_BPN = "qrb_camera"

# The vendor SDKs are added by the PACKAGECONFIG of the backend that needs them (below).
ROS_BUILD_DEPENDS = " \
    plugin-discovery \
    glib-2.0 \
"

ROS_BUILDTOOL_DEPENDS = " \
    ament-cmake-auto-native \
"

ROS_EXPORT_DEPENDS = " \
    plugin-discovery \
"

ROS_BUILDTOOL_EXPORT_DEPENDS = ""

ROS_EXEC_DEPENDS = " \
    plugin-discovery \
"

ROS_TEST_DEPENDS = " \
    ament-lint-auto \
    ament-lint-common \
"

DEPENDS = "${ROS_BUILD_DEPENDS} ${ROS_BUILDTOOL_DEPENDS}"
DEPENDS += "${ROS_EXPORT_DEPENDS} ${ROS_BUILDTOOL_EXPORT_DEPENDS} ${ROS_TEST_DEPENDS}"
DEPENDS += "pkgconfig-native"

RDEPENDS:${PN} += "${ROS_EXEC_DEPENDS}"

SRC_URI = "git://github.com/qualcomm-qrb-ros/qrb_ros_camera.git;protocol=https;branch=dev/robot-iq10;subpath=qrb_camera"
SRCREV = "${AUTOREV}"
S = "${UNPACKDIR}/qrb_camera"

ROS_BUILD_TYPE = "ament_cmake"

inherit ros_${ROS_BUILD_TYPE}

inherit robotics-package

# ---------------------------------------------------------------------------------------
# Backends: plugins of CameraManager. This recipe does not choose the backend used at run
# time: plugin_discovery does, from the platform configuration, a customer configuration in
# /etc/plugin_discovery/config.d/, or a manual choice (camera_backend:= /
# PLUGIN_DISCOVERY_BACKEND_CAMERA).
#
# What has to be decided at build time is only which backends are COMPILED, because each one
# links a vendor SDK. That is the PACKAGECONFIG below: ON for an enabled backend (a missing SDK
# fails the build), OFF otherwise (the SDK is not needed). The default is the backend of the
# platform this layer is for (QAM8797P: qcarcam); a platform or customer changes it without
# touching this recipe, e.g. in local.conf / distro / machine conf:
#   PACKAGECONFIG:pn-qrb-camera = "qcarcam qmmf"
# Adding a backend = one PACKAGECONFIG line + one QRB_CAMERA_BACKEND_RDEPENDS flag.
# ---------------------------------------------------------------------------------------
PACKAGECONFIG ??= "qcarcam"
PACKAGECONFIG[qmmf]    = "-DQRB_CAMERA_BACKEND_QMMF=ON,-DQRB_CAMERA_BACKEND_QMMF=OFF,camera-service"
PACKAGECONFIG[qcarcam] = "-DQRB_CAMERA_BACKEND_QCARCAM=ON,-DQRB_CAMERA_BACKEND_QCARCAM=OFF,camera-qcx"

# Runtime SDK of each backend.
QRB_CAMERA_BACKEND_RDEPENDS[qmmf]    = "camera-service"
QRB_CAMERA_BACKEND_RDEPENDS[qcarcam] = "camera-qcx"

# The unit test (fake backends) is for development hosts; not built here.
EXTRA_OECMAKE += "-DBUILD_TESTING=OFF"

# One package per backend, as on Ubuntu: ${PN}-backend-<name> holds the plugin
# (lib/camera/*.so) and its manifest (share/camera/plugins/*.plugin.yaml), both under the
# ROS prefix. robotics-package.bbclass resets PACKAGES to "${PN} ${PN}-dbg" in its anonymous
# function, so "PACKAGES =+" would be lost (and, with installed-vs-shipped skipped, the
# backend silently dropped); this anonymous function runs after it and adds them back.
# The enabled backends are recommended by ${PN}, so the image gets what was built; a
# backend that is not built gives an empty package, which is not created.
python __anonymous() {
    pn = d.getVar("PN")
    pkg_dest = d.getVar("pkg_dest").strip()
    # ament installs into the ROS prefix (/usr/ros/<distro>), not ${prefix}.
    libdir = d.getVar("ros_libdir")
    datadir = d.getVar("ros_datadir")
    enabled = (d.getVar("PACKAGECONFIG") or "").split()
    for b in sorted((d.getVarFlags("QRB_CAMERA_BACKEND_RDEPENDS") or {}).keys()):
        pkg = "%s-backend-%s" % (pn, b)
        d.prependVar("PACKAGES", pkg + " ")
        files = ["%s/camera/libcamera_backend_%s.so" % (libdir, b),
                 "%s/camera/plugins/camera_backend_%s.plugin.yaml" % (datadir, b)]
        if pkg_dest:
            files += [pkg_dest + f for f in files]
        d.setVar("FILES:" + pkg, " ".join(files))
        d.setVar("RDEPENDS:" + pkg, "%s %s" % (pn, d.getVarFlag("QRB_CAMERA_BACKEND_RDEPENDS", b)))
        # A dlopen()ed plugin without a SONAME version (dev-so), under the ROS prefix rather
        # than ${libdir} (libdir; ros_opt_prefix.bbclass skips the same for ${PN}).
        d.appendVar("INSANE_SKIP:" + pkg, " dev-so libdir")
        if b in enabled:
            d.appendVar("RRECOMMENDS:" + pn, " " + pkg)
}

# Which backends load comes from configuration. The platform one
# (/etc/plugin_discovery/platform.d/camera.config.yaml) is recommended, not required: a
# customer may leave it out (BAD_RECOMMENDATIONS) and use its own configuration or a manual
# choice instead.
RRECOMMENDS:${PN} += "plugin-discovery-platform-config"
