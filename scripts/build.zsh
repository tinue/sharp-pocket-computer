#!/bin/zsh
mvn clean package
scp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar desktoppi:./Development/SharpCommunicator.jar
scp SharpCommunicator/examples/*.bas desktoppi:./Development/
cp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar ~/Applications/PocketPc/SharpCommunicator.jar
