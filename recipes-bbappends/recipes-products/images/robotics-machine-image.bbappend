# robotics-machine-image requires machine-image.bb, but bbappends match by
# recipe name, so the auto machine-image.bbappends do not apply to it. Pull
# in the same content here so robotics-machine-image matches the auto
# machine-image. bitbake can only require .bb/.bbclass/.inc/.conf files, so
# the .inc files used by those bbappends are required directly and the
# remaining lines are copied; keep them in sync with:
#   meta-qcom-auto-extras/recipes-products/images/machine-image.bbappend
#   meta-qti-automotive-internal/recipes/images/machine-image.bbappend
# Paths are relative to each layer root (resolved via BBPATH).

# meta-qcom-auto-extras: proprietary packagegroups, verityboot signing
require recipes-products/images/machine-image-prop.inc

IMAGE_INSTALL += "\
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-vmm', 'packagegroup-qti-vmm-prop', '', d)} \
    ${@bb.utils.contains('MACHINE_FEATURES', 'qti-vmm', 'packagegroup-qti-lvgvm-prop', '', d)} \
"

# meta-qti-automotive-internal: internal test packages, prebuilt population.
# include (not require): skipped when the internal layer is not present.
include recipes/images/include/internal-image.inc
include recipes/images/common-auto-image.inc

# gplv3 packages introduced due to ltp
# Use :append:pn-... because the distro conf sets the :pn-robotics-machine-image
# override, which would otherwise discard a plain += here.
INCOMPATIBLE_LICENSE_EXCEPTIONS:append:pn-robotics-machine-image = " cpio:GPL-3.0-only gawk:GPL-3.0-only gawk:GPL-3.0-or-later tar:GPL-3.0-only bc:GPL-3.0-or-later gzip:GPL-3.0-or-later which:GPL-3.0-or-later coreutils:GPL-3.0-or-later coreutils-stdbuf:GPL-3.0-or-later findutils:GPL-3.0-or-later readline:GPL-3.0-or-later bash:GPL-3.0-or-later"
