#
# Dockerfile for Linux-based images with Podman Container Runtime.
# Version: latest stable version for Ubuntu 20.04 is taken - 3.4.2. ...
# ... See: https://download.opensuse.org/repositories/devel:/kubic:/libcontainers:/stable/xUbuntu_20.04/amd64/)
# Docstrings annotation with "[Rootless]" prefix are required for rootless execution only and can be ...
# ... omitted if only rootful execution is required.
#
ARG teamCityAgentImage

FROM ${teamCityAgentImage}
USER root

# Install Podman 3.* - latest stable release
RUN mkdir -p /etc/apt/keyrings && \
    curl -fsSL "https://download.opensuse.org/repositories/devel:kubic:libcontainers:stable/xUbuntu_$(lsb_release -rs)/Release.key" \
      | gpg --dearmor \
      | tee /etc/apt/keyrings/devel_kubic_libcontainers_unstable.gpg > /dev/null && \
    echo \
      "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/devel_kubic_libcontainers_unstable.gpg]\
                https://download.opensuse.org/repositories/devel:kubic:libcontainers:stable/xUbuntu_$(lsb_release -rs)/ /" \
              | tee /etc/apt/sources.list.d/devel:kubic:libcontainers:unstable.list > /dev/null && \
    apt-get update -qq && \
    apt install conmon containernetworking-plugins && \
    apt-get -qq -y install podman;

# Giving access to additional groups as per https://github.com/containers/podman/blob/main/docs/tutorials/rootless_tutorial.md#enable-unprivileged-ping
RUN echo buildagent:10000:5000 > /etc/subuid; \
    echo buildagent:10000:5000 > /etc/subgid; \
    usermod -aG docker buildagent;

# [Rootless] Enable unprivileged ping as per https://github.com/containers/podman/blob/main/docs/tutorials/rootless_tutorial.md#enable-unprivileged-ping
RUN sysctl -w "net.ipv4.ping_group_range=0 2000000"

# Create directories required for Podman
RUN mkdir -p /var/lib/shared/overlay-images  \
    /var/lib/shared/overlay-layers  \
    /var/lib/shared/vfs-images  \
    /var/lib/shared/vfs-layers  \
    /home/buildagent/.local/share/containers; \
    touch /var/lib/shared/overlay-images/images.lock;  \
    touch /var/lib/shared/overlay-layers/layers.lock;  \
    touch /var/lib/shared/vfs-images/images.lock;  \
    touch /var/lib/shared/vfs-layers/layers.lock;

# Add configuration files to use configure overlayFS / FUSE properly
COPY linux/agent/configs/podman/rootful.containers.conf /etc/containers/containers.conf
COPY linux/agent/configs/podman/rootless.containers.conf /home/buildagent/.config/containers/containers.conf

# Update access policy for configuration files (containers.conf, storage.conf), update storage configuration ...
# ... to enable FUSE storage.
RUN chmod 644 /etc/containers/containers.conf;  \
    sed -i -e 's|^#mount_program|mount_program|g'  \
    -e '/additionalimage.*/a "/var/lib/shared",'  \
    -e 's|^mountopt[[:space:]]*=.*$|mountopt = "nodev,fsync=0"|g'  \
    /etc/containers/storage.conf

# Adjust container storage ownership to have the ability to execute containers via `buildagent`
RUN chown -R buildagent:buildagent /home/buildagent/ \
    /home/buildagent/.local/share/containers/storage \
    /home/buildagent/.config/containers/containers.conf; \
    chmod -R 755 /home/buildagent /home/buildagent/.config/containers/containers.conf;

# [Rootless] Enable user namespace to prevent "cannot clone: Invalid argument" @ podman.
RUN echo 'kernel.unprivileged_userns_clone=1' > /etc/sysctl.d/userns.conf

USER buildagent

#
# Creation of volumes for Podman containers. Please, note that Docker sets "root:root" ownership by default.
#
# -- Rootful containers
VOLUME /var/lib/containers
# -- Rootless containers.
VOLUME /home/buildagent/.local/share/containers

ENV _CONTAINERS_USERNS_CONFIGURED=""
