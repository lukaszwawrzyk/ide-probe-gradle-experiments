#!/usr/bin/env bash
set -x

# Disable IntelliJ Data Sharing
mkdir -p ~/.local/share/JetBrains/consentOptions/
echo -n rsch.send.usage.stat:1.1:0:1574939222872 > ~/.local/share/JetBrains/consentOptions/accepted

export IDEPROBE_DISPLAY=xvfb

sbt run
