FROM openjdk:11

RUN export DEBIAN_FRONTEND=noninteractive \
    && apt-get -qq update \
    && apt-get -qq -o=Dpkg::Use-Pty=0 install libxtst6 libx11-6 libxrender1 xvfb openssh-server python3 python3-pip \
        python3-venv libssl-dev pkg-config x11-apps vim imagemagick zip openjdk-11-jre openjdk-11-jdk \
    && mkdir -p ~/.local/share/JetBrains/consentOptions/ \
    && echo -n rsch.send.usage.stat:1.1:0:1574939222872 > ~/.local/share/JetBrains/consentOptions/accepted


ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64

ADD . /workspace
WORKDIR /workspace
