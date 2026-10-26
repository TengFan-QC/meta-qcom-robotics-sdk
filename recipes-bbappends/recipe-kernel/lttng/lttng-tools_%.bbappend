# Create "tracing" as a system group (GID < 1000). As a regular group it gets
# the first free GID (1000), which collides with the static "system" group
# from the auto distro's qti_group table.
GROUPADD_PARAM:${PN} = "-r tracing"
