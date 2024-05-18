#!/bin/zsh
mvn clean package
scp target/sharp-jar-with-dependencies.jar desktoppi:./Development/SharpCommunicator.jar
scp examples/*.bas desktoppi:./Development/
