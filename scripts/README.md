# Scripts

This folder contains helper scripts. They are not part of the software itself, and only help in building or releasing
it.

- build.zsh: Builds the software, and copies the resulting jar file to convenient locations (needs to be adapter for
  each user)
- makerelease: Uses Github CLI to create a release or pre-release.

Note: All scripts must run from the project base directory, or they will fail. There is no check for this in the
scripts.