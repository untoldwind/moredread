#!/bin/sh

if [ "$1" == "" ]; then
	echo "Usage: eclipse-to-maven.sh <eclipse installation directory>
fi

OWN_DIR=$(dirname $0)

(cd $OWN_DIR/../..; mvn -DstripQualifier=true -DeclipseDir=$1  eclipse:to-maven