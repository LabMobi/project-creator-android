# <%= sanitized_name %> release guide

Release guide for <%= sanitized_name %> Android.

## Purpose

This document contains a guide for the release process. 

## Release prerequisites

1) Access to the project's repository
2) Access to the project's JIRA
3) Access to the project's CI
4) Access to the project's Firebase Crashlytics 
5) Access to the app project at Google Play Store
6) The `develop` branch build is set up in CI and builds pass testing.
7) The JIRA tasks that will go into this release have been tagged with the correct `fix version`.

**Note:** See the README.md for additional project info.

## Release steps

### 1. Verify and merge `develop` branch

1.1) Verify the state of the `develop` branch. Make sure all changes that need to go into the release are merged to `develop` branch and `develop` branch is successfully built and tested in CI.

1.2) Verify the version code. Make sure the version numbers in the main module's `build.gradle` file are correct for the release. These can be changed by updating their respective constants:

```groovy
/* Versioning information:
 * The versions are in the format x.y.z
 * x - Major version
 * y - Minor version
 * z - Patch version
 * w - Build version
 *
 * versionName will be x.y.z
 * versionCode will be a number in the format xxyyzzw.
 * This means that every version (except build version) has up to 2 digits until it starts affecting other numbers.
 * w (versionBuild) is for making a release with the same version name, but different version code.
 */
versionMajor = 0 <-- UPDATE HERE
versionMinor = 0 <-- UPDATE HERE
versionPatch = 1 <-- UPDATE HERE
versionBuild = 0 // Use this when builds with the same version are needed. Change to 0 once done
```

If you need to update the version code then do not forget to commit and push the changes to the `develop` branch.

1.3) Merge the verified `develop` branch state to `main` branch. 

### 2. Create a release from `main` branch in the CI

Please note that APKs should be used for testing. For the Play Store App Bundles (aab) should be used.

2.1) Build a new release build in the CI. Make use the build succeeds and correct artifacts are created.

2.2) Test the new release as appropriate

Try out:

-  a clean install
- an upgrade install
- any specific features that were added for this feature

2.3) Upload the binaries to Play Store.

2.4) Also add changelogs and upload the minifier mappings for all specified versions

2.5) Release the binaries for all specified versions

2.5.1) Use a staged rollout. It is often useful to not use a 100% rollout right away. Event if you use a 95% staged rollout then you still will retain the option to halt the rollout if a fatal bug is found. NOTE: If you do a staged rollout then put a reminder for yourself to increase it to a 100% at some later date.

### 3. Update the `develop` branch to the next version

3.1) Update the `develop` branch to a new version after a finishing release. If no specific version is specified, then increment the `VERSION_PATCH` number by 1.

```groovy
/* Versioning information:
 * The versions are in the format x.y.z
 * x - Major version
 * y - Minor version
 * z - Patch version
 * w - Build version
 *
 * versionName will be x.y.z
 * versionCode will be a number in the format xxyyzzww.
 * This means that every version has up to 2 digits until it starts affecting other numbers.
 * w (versionBuild) is for making a release with the same version name, but different version code.
 */
versionMajor = 0 <-- UPDATE HERE
versionMinor = 0 <-- UPDATE HERE
versionPatch = 2 <-- UPDATE HERE
versionBuild = 0 // Use this when builds with the same version are needed. Change to once done
```

 3.2) Commit and push the changes to `develop` branch.

### 4. Tag the release

1) Create a Git tag for the release in the form of `release-x.y.z`

For example, for release `1.0.0` a Git tag "release-1.0.0" should be created. This can be done as shown below:

```bash
git checkout main
git pull
git tag release-X.Y.Z # <- Substitute the current version here
git push origin release-X.Y.Z # <- Substitute the current version here
```

## Additional checklist

Also, consider to following:

1) Are all the JIRA tasks that did not make into this release re-tagged with the next release's `fix version`?

2) If you used a staged rollout then did you put a reminder for yourself to check the release telemetry and increment the rollout percentage in 1-2 days?

