SUMMARY = "UGV SDK - SDK for AgileX Robotics"
DESCRIPTION = "UGV SDK provides the communication library (CAN / serial) used by \
ranger_ros2 to control AgileX robots such as Ranger, Scout, Hunter, Bunker, \
Tracer and Titan."
HOMEPAGE = "https://github.com/agilexrobotics/ugv_sdk"
BUGTRACKER = "https://github.com/agilexrobotics/ugv_sdk/issues"
SECTION = "libs"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

# Upstream project(ugv_sdk VERSION 0.8.0); no release tags are published.

SRC_URI = "git://github.com/agilexrobotics/ugv_sdk.git;protocol=https;branch=main \
           file://0001-Fix-build-with-newer-asio.patch \
          "
# main @ "Fixed bugs in the Bunker series."
SRCREV = "f2704eacdc90357078cd93ec60aae08bb4baab35"

S = "${UNPACKDIR}/${BP}"

# asio is header-only and is included by the public headers (asio.hpp),
# so consumers (e.g. ranger-ros2) also need it in their sysroot.
DEPENDS = "asio"

inherit cmake

# Upstream builds a static library by default (add_library without SHARED).
# Make it PIC so it can be linked into shared objects of dependent packages.
EXTRA_OECMAKE = " \
    -DBUILD_TESTING=OFF \
    -DSTATIC_CHECK=OFF \
    -DCMAKE_POSITION_INDEPENDENT_CODE=ON \
"

# Avoid TMPDIR leaking into binaries/debug info (QA buildpaths)
CFLAGS:append   = " ${DEBUG_PREFIX_MAP}"
CXXFLAGS:append = " ${DEBUG_PREFIX_MAP}"

# Installed content: libugv_sdk.a, headers (include/ugv_sdk) and
# lib/cmake/ugv_sdk/*.cmake (find_package(ugv_sdk)), all of which are
# development files. Provide an empty main package so that -dev/-staticdev
# can be pulled in by dependents without QA noise.
ALLOW_EMPTY:${PN} = "1"
FILES:${PN}-dev += "${libdir}/cmake/ugv_sdk"
