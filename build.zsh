#!/bin/zsh
mvn clean package
scp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar desktoppi:./Development/SharpCommunicator.jar
scp SharpCommunicator/examples/*.bas desktoppi:./Development/
