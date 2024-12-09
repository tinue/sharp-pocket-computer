#!/bin/bash
mvn clean package -Dbuild.time="$(date '+%Y-%m-%d %H:%M:%S')"
scp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar desktoppi:./Development/SharpCommunicator.jar
scp SharpCommunicator/testsuite/* desktoppi:./Development/
cp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar ~/Applications/PocketPc/SharpCommunicator.jar
