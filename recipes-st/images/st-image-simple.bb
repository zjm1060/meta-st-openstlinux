SUMMARY = "OpenSTLinux weston image with basic Wayland support (if enable in distro)."
LICENSE = "Proprietary"

include recipes-st/images/st-image.inc

inherit core-image distro_features_check

# let's make sure we have a good image...
REQUIRED_DISTRO_FEATURES = "wayland"

IMAGE_LINGUAS = "en-us"

IMAGE_FEATURES += "\
    splash \
    package-management  \
    ssh-server-openssh \
    "

IMAGE_INSTALL_append = "gdbserver openssh-sftp-server dhcp-client udev-extraconf zlog sqlite3 systemd-analyze ppp wget libfftw "

IMAGE_DISPLAY_PART = " \
    ${@bb.utils.contains('DISTRO_FEATURES', 'opengl', 'kmscube', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wayland', 'weston weston-init', '', d)} \
    libdrm          \
"

#
# INSTALL addons
#
CORE_IMAGE_EXTRA_INSTALL += " \
    coreutils       \
    mtd-utils       \
    i2c-tools       \
    ethtool         \
    iproute2        \
    \
    ${@bb.utils.contains('DISTRO_FEATURES', 'usbgadget', 'usbotg-gadget-config', '', d)} \
    ${@bb.utils.contains('COMBINED_FEATURES', 'optee', 'packagegroup-optee-core', '', d)} \
    ${@bb.utils.contains('COMBINED_FEATURES', 'optee', 'packagegroup-optee-test', '', d)} \
    \
    ${IMAGE_DISPLAY_PART} \
    "

#PACKAGE_EXCLUDE += "avahi"
#PACKAGE_EXCLUDE += "avahi-daemon"
#PACKAGE_EXCLUDE += "weston-examples weston-xwayland"
IMAGE_FEATURES_remove += "avahi"