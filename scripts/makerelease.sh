#!/bin/bash
# Create a Github release or pre-release using the Github CLI (Mac: 'brew install gh')
#
# Preconditions:
# - Login first with 'gh auth login'
# - For a release: main branch must be clean
#
# The script will then:
# - Draft the (pre-)release
# - For a release: Create the tag
# - Attach the JAR file to the (pre-)release

# Function to check for a clean work tree
require_clean_work_tree () {
    # Update the index
    git update-index -q --ignore-submodules --refresh
    err=0
    # Check if there are unpushed changes
    UNPUSHED=$(git log origin/main..main)
    if [ ! -z "${UNPUSHED}" ];
    then
        echo >&2 "cannot build a release: you have unpushed changes."
        echo $UNPUSHED >&2
        err=1
    fi
    # Disallow unstaged changes in the working tree
    if ! git diff-files --quiet --ignore-submodules --
    then
        echo >&2 "cannot build a release: you have unstaged changes."
        git diff-files --name-status -r --ignore-submodules -- >&2
        err=1
    fi
    # Disallow uncommitted changes in the index
    if ! git diff-index --cached --quiet HEAD --ignore-submodules --
    then
        echo >&2 "cannot build a release: your index contains uncommitted changes."
        git diff-index --cached --name-status -r --ignore-submodules HEAD -- >&2
        err=1
    fi
    if [ $err = 1 ]
    then
        echo >&2 "Please commit or stash them."
        exit 2
    fi
}

# Get build information
VERSION=$(mvn -f SharpCommunicator/pom.xml help:evaluate -Dexpression=project.version -q -DforceStdout)
BRANCH=$(git rev-parse --abbrev-ref HEAD)
HASH=$(git rev-parse --short=7 HEAD)

# Check branch. Develop will create a pre-release, and main a release
if [[ "$BRANCH" == "develop" ]]; then
  read -p "Creating a pre-release, continue? " -n 1 -r
  if [[ $REPLY =~ ^[Yy]$ ]]
  then
    mvn clean package
    cp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar SharpCommunicator.jar
    gh release create v$VERSION-$HASH SharpCommunicator.jar -d --notes-file SharpCommunicator/ReleaseNotes.md --title $VERSION-prerelease
    rm SharpCommunicator.jar
    echo Done
    exit 0
  fi
fi

if [[ "$BRANCH" == "main" ]]; then
  require_clean_work_tree
  echo "Please check that the main branch has been merged with develop!"
  read -p "Creating a release, continue? " -n 1 -r
  if [[ $REPLY =~ ^[Yy]$ ]]
  then
    mvn clean package
    cp SharpCommunicator/target/SharpCommunicator-jar-with-dependencies.jar SharpCommunicator.jar
    gh release create v$VERSION SharpCommunicator.jar --notes-file SharpCommunicator/ReleaseNotes.md --title $VERSION
    rm SharpCommunicator.jar
    echo Done
    exit 0
  fi
fi

echo Muat be in main or develop branch for this script to work.
exit 1
