#!/bin/bash

# Taken from: https://github.com/IBM/etcd-java/blob/906f89dc63705e0b0ba7019daed7fc82e4fca25a/build-env/install-etcd.sh

# Fail on any error
#set -e

ETCD_VERSION=v3.3.22

INSTALL_DIR="${1:-$HOME/etcd}"

# Download and install etcd
echo "Downloading etcd server ${ETCD_VERSION}"
cd /tmp
wget -nv https://github.com/etcd-io/etcd/releases/download/${ETCD_VERSION}/etcd-${ETCD_VERSION}-linux-amd64.tar.gz
echo "Create Etcd dir: ${INSTALL_DIR}"
mkdir -p "${INSTALL_DIR}"

if [ ! -f etcd-${ETCD_VERSION}-linux-amd64.tar.gz ] ; then
  echo "File cannot be found: etcd-${ETCD_VERSION}-linux-amd64.tar.gz"
  exit 1
fi
if [ ! -d $INSTALL_DIR ] ; then
  echo "Etcd target folder not found: $INSTALL_DIR"
  exit 2
fi
echo "Extract Etcd archive etcd-${ETCD_VERSION}-linux-amd64.tar.gz into ${INSTALL_DIR}"
tar xzf etcd-${ETCD_VERSION}-linux-amd64.tar.gz -C "${INSTALL_DIR}" --strip-components=1
${INSTALL_DIR}/etcdctl
if [ $? -ne 0 ] ; then
  echo "Something went wrong during the Etcd setup"
  exit 3
fi
rm -rf etcd*.gz
