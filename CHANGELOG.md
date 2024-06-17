# Changelog for Mobi Lab Android Template

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/). This project does not use semantic versioning.

## 03.06.2024

### Changed

- Removed deprecated material dialogs lib and migrated to use material dialogs from Google's material lib.
- Updated the project to Kotlin 2.0


## 18.04.2024

### Changed

- Updated ktlint and detekt to get along with Compose. Updated SchedulerProvider to get along with the version in example/ui-tests branch. This makes adding instrumentation tests easier.

## 05.02.2024

### Changed

- Updated dependencies.

## 10.12.2023

### Changed

- Updated dependencies.
- Migrated from Dagger to Hilt.
- Added new assisted injection extension functions to `package mobi.lab.sample.common.assistedViewModels()`.

## 22.11.2023

### Changed

- Updated dependencies.
- Change `LiveData.onEachEvent` usages to return Boolean values.
- Removed Codemagic workflows that directly build the template projects.

## 06.11.2023

### Added

Added CI builds.

## 29.10.2023

### Changed

Moved the repository to Mobi Lab's GitHub.

## 04.10.2023

### Changed

- Updated dependencies.

### Removed

- Removed unnecessary util and extension functions.

## 31.09.2023

### Changed

- Changed MVVM to a local maven artefact instead of a submodule.
- Updated dependencies.

## 22.05.2023

### Added

- Added additional rules for R8 full mode (AGP 8).
- Added KSP instead of kapt for all depedencies except for Dagger.

### Changed

- Updated Gradle and AGP to version 8+.

## 17.04.2023

### Added

- Various improvements.
- Improved DI comments.

### Changed

- Migrated to Gradle plugin DSL.

## 15.11.2022

### Added

- Added instrumentation test improvements.
- Improved ConditionIdlingResource to check Conditions in the proper order.

### Removed

- Removed IdlingResource reusage.

## 11.11.2022

### Added

- Added an additional check for UnknownHostException.

### Fixed

- Fixed an issue with the MyErrorTransformer and NetworkUtil where the lack of network connection was not correctly detected.

## 04.10.2022

### Changed

- Moved BaseMvvmActivity(Fragment) functionality to BaseActivity and BaseFragment.
- Renamed Text.P16 to Text.Body16 as Body is a common text style used in Material design, too.

### Removed

- Removed BaseViewModel.

## 26.09.2022

### Changed

- Moved MVVM lib into the project.
- Replaced ProgressButton with a ProgressDialog implementation.

### Removed

- Removed local .aar dependency support.

## 21.09.2022

### Added

- Added Espresso and Dagger support for instrumentation tests with examples.

## 20.09.2022

### Added

- Added support for the new splash screen.

### Changed

- Updated dependencies.
- Changed target API to 33.

## 13.07.2022

### Changed

- Updated Timber ScrollsTree debug tag creation.

## 06.07.2022

### Removed

- Removed unnecessary pre android 23 NetworkUtil implementations.

## 07.06.2022

### Changed

- Replaced RxSchedulers file with SchedulerProvider interface to provide RxJava Schedulers.

## 06.06.2022

### Added

- Integrated Gradle Version Catalogs feature into the template.
- Added a local Maven repo for local .aar dependencies.

## 26.05.2022

### Added

- Added checkCode as the next step after setup in CI.

## 19.05.2022

### Changed

- Updated project template module structure.

## 28.04.2022

### Added

- Added project template README improvements.

## 16.03.2022

### Changed

- Replaced finish MutableLiveData with finish() function.

### Removed

- Cleaned up BaseViewModel.
- Removed generic progress support.

## 21.02.2022

### Changed

- Made Mvvm LiveData observables depend on Fragment's View lifecycle instead of Fragment's lifecycle.

## 10.02.2022

### Changed

- Changed how .aar dependencies are included to hide gradle warnings.

## 20.01.2022

### Added

- Added checkCode gradle task that will run ktlint and detekt.

## 18.01.2022

### Removed

- Cleaned up debug menu usage and code organization.

### Fixed

- Fixed Scrolls log posting dialog not appearing issue.

## 17.01.2022

### Added

- Added dependencyUpdate task to library template.

### Changed

- Updated dependencies.
- Updated Gradle version and Gradle wrapper.

## 28.10.2021

### Added

- Enabled non transitive R class.

### Changed

- Updated dependencies. 

### Removed

- Disabled jetifier.

## 13.09.2021

### Added

- Added debug activity with options to see logs and create crash.

### Changed

- Moved debug content outside release build.

## 19.08.2021

### Changed

- Changed target API to 31.

## 19.08.2021

### Changed

- Updated dependencies.
- Updated build tooling.

## 21.07.2021

### Added

- Added tasks to build bundles for release.

### Changed

- Updated dependencies.

## 30.04.2021

### Changed

- Updated dependencies.
- Moved to java.time.* for time handling.

### Removed

- Removed generic Jcenter repository.

## 04.03.2021

### Changed

- Updated the minSDK to API 23 for both templates.

## 18.12.2020

### Changed

- Replaced Gson with Moshi + Kotshi.

## 11.11.2020

### Changed

- Updated ProgressButton library.

## 07.10.2020

### Changed

- Updated the section about the CI. We use Codemagic now.

## 21.04.2020

### Changed

- Updated README wording.
- Moved script dev section to the end of the README.

## 16.12.2019

### Changed

- Updated project and dependency versioning logic.

## 01.10.2019

### Added

- Added support for creating a new Android library project.

## 12.09.2019

### Added

- Added a Splash + Login + Main screen base implementation.

## 06.08.2019

### Added

- Added a more opinionated base project that leverages Clean Architecture ideas and MVVM on the UI side.

## 20.11.2018

### Added

- Added example tests for a Presenter and a UseCase.

## 19.11.2018

### Added

- Added in example usage of the MVP + UseCases architecture.

## 19.11.2018

### Added

- Added in the newer MVP library.

### Changed

- Update tools.

## 07.09.2018

### Changed

- Updated this README to make it clearer where the project is created.

## 07.09.2018

### Added

- Added examples for tests and instrumentation tests.
- Added the "lib_" prefix for the MVP library.

## 07.09.2018

### Changed

- Updated to a Kotlin version.
- Moved the Java one to "java" branch.

## 04.04.2018

### Added

- Created README file.

### Changed

- Moved the project creation script to Mobi Lab's GitLab.
