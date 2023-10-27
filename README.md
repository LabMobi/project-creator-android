# Android Project Creator

This is a script to simplify Android project creation. 

## Changelog

| Date       |  Developer  | Change                                                       |
| ---------- | :---------: | :----------------------------------------------------------- |
| 04.04.2018 | Harri Kirik | Moved the project creation script to Mobi Lab's Git Lab. Created this file. |
| 07.09.2018 | Harri Kirik | Updated to a Kotlin version. Moved the Java one to "java" branch |
| 07.09.2018 | Harri Kirik | Added examples for tests and instrumentation tests. Added the "lib_" prefix for the MVP library. |
| 07.09.2018 | Harri Kirik | Updated this readme to make it clearer where the project is created. |
| 19.11.0218 | Harri Kirik | INFRA-43: Added in the newer MVP library. Update tools.      |
| 19.11.2018 | Harri Kirik | INFRA-43: Added in example usage of the MVP + UseCases architecture. |
| 20.11.2018 | Harri Kirik | INFRA-43: Added an example tests for a Presenter and a UseCase. |
| 06.08.2019 | Lauris Kruusamäe | INFRA-72: Added a more opinionated base project that leverages Clean Architecture ideas and MVVM on the UI side |
| 12.09.2019 | Lauris Kruusamäe | P42-12: Added a Splash + Login + Main screen base implementation |
| 01.10.2019 | Lauris Kruusamäe | P42-17: Added support for creating a new Android library project |
| 16.12.2019 | Lauris Kruusamäe | P42: Updated project and dependency versioning logic |
| 21.04.2020 | Harri Kirik | P42: Updated wording. Moved script dev section to the end of the readme. |
| 07.10.2020 | Harri Kirik | Updated the section about the CI. We use Codemagic now. |
| 11.11.2020 | Lauris Kruusamäe | Updated ProgressButton library. |
| 18.12.2020 | Lauris Kruusamäe | Replace Gson with Moshi + Kotshi. |
| 04.03.2021 | Harri Kirik | Update the minSDK to API 23 for both templates. |
| 30.04.2021 | Lauris Kruusamäe | Updated dependencies. Removed generic Jcenter repository. Move to java.time.* for time handling. |
| 21.07.2021 | Tarvi Liivak | Added tasks to build bundles for release. Updated dependencies |
| 19.08.2021 | Kristofer Käosaar | Update build tooling and dependency versions. |
| 19.08.2021 | Kristofer Käosaar | Update to API 31. |
| 13.09.2021 | Tarvi Liivak | Created debug activity with options to see logs and create crash. Moved debug content outside release build |
| 28.10.2021 | Lauris Kruusamäe | Dependency updates. Disabled jetifier, enabled non transitive R class |
| 17.01.2022 | Sammy Calle | Update of dependencies , gradle version , gradle wrapper. Task dependencyUpdate added to library template |
| 18.01.2022 | Lauris Kruusamäe | Cleaned up debug menu usage and code organization. Fixed Scrolls log posting dialog not appearing issue |
| 20.01.2022 | Sammy Calle | Added the checkCode gradle task that will run ktlint and detekt |
| 10.02.2022 | Lauris Kruusamäe | Changed how .aar dependencies are included to hide gradle warnings |
| 21.02.2022 | Lauris Kruusamäe | Make Mvvm LiveData observables depend on Fragment's View lifecycle instead of Fragment's lifecycle |
| 16.03.2022 | Lauris Kruusamäe | Cleanup BaseViewModel. Remove generic progress support and replace finish MutableLiveData with finish() function |
| 28.04.2022 | Lauris Kruusamäe | Project template README improvements |
| 19.05.2022 | Lauris Kruusamäe | Update project template module structure |
| 26.05.2022 | Lauris Kruusamäe | Add checkCode as the next step after setup in CI |
| 06.06.2022 | Lauris Kruusamäe | Integrate Gradle Version Catalogs feature into the template. Add a local Maven repo for local .aar dependencies |
| 07.06.2022 | Lauris Kruusamäe | Replace RxSchedulers file with SchedulerProvider interface to provide RxJava Schedulers |
| 06.07.2022 | Lauris Kruusamäe | Remove unnecessary pre android 23 NetworkUtil implementations |
| 13.07.2022 | Lauris Kruusamäe | Update Timber ScrollsTree debug tag creation |
| 20.09.2022 | Lauris Kruusamäe | Update dependencies. Change target to 33 and added support for the new splash screen |
| 21.09.2022 | Lauris Kruusamäe | Add Espresso and Dagger support for instrumentation tests with examples |
| 26.09.2022 | Lauris Kruusamäe | Remove local .aar dependency support and move MVVM lib into the project and replace ProgressButton with a ProgressDialog implementation |
| 04.10.2022 | Lauris Kruusamäe | Remove BaseViewModel and and move BaseMvvmActivity(Fragment) functionality to BaseActivity and BaseFragment. Rename Text.P16 to Text.Body16 as Body is a common text style used in Material design, too.  |
| 11.11.2022 | Harri Kirik | Fixed an issue with the MyErrorTransformer and NetworkUtil where the lack of network connection was not correctly detected. Added an additional check for UnknownHostException. |
| 15.11.2022 | Lauris Kruusamäe | Instrumentation test improvements. Removed IdlingResource reusage and improved ConditionIdlingResource to check Conditions in the proper order. |
| 17.04.2023 | Lauris Kruusamäe | Improve DI comments. Migrate to gradle plugin dsl. Various improvements. |
| 22.05.2023 | Lauris Kruusamäe | Update Gradle and AGP to version 8+. Additional rules for R8 full mode (AGP 8). Addeded KSP instead of kapt for all depedencies except for Dagger. |
| 31.09.2023 | Lauris Kruusamäe | Change MVVM to a local maven artefact instead of a submodule. Update dependencies |
| 04.10.2023 | Lauris Kruusamäe | Removed unnecessary util and extension functions. Updated dependencies |

## Idea

"Batteries-included" project bootstrapper that configures all the infrastructure necessary for starting a new Android project with Kotlin. Let's not waste time configuring build scripts when we could be delivering features instead.

There are 2 project templates available: 

- a regular Android project 
- an Android library project


### Android project
The Android project template uses MVVM for UI and Use Cases for business logic. 

View models are powered by Google's ViewModel and LiveData classes. Communication between Use Cases and models is done with RxJava. An event bus is used for communication originating from dialogs.

Dependencies are injected with Dagger. Constructor injection (@Inject annotated constructors) is preferred where possible. See `di` packages in modules and Injector class in the application module.

The bootstrapper also includes updated Scrolls library that allows posting to email and chats (without the need of the backend), Gradle tasks for Nevercode, and pre-configured unit and instrumentation tests.

See the example in the new project created by the bootstrapper for more information. 

#### Features packaged with the project

**App**
- Splash implementation with existing session check and routing to Main or Login screens
- Login screen implementation with an example login use case
- Main application screen with button to open app prototype(Figma)
- Debug screen with buttons for viewing  logs, creating crash and logging user out. Accessible from toolbar in non release build variant.
- MVVM library
- Scrolls logging library
- Firebase Analytics integration
- Firebase Crashlytics integration
- Dagger2 setup completed
- Base activities and Fragments for MVVM and general usage
- BaseFragmentActivity for an easier showing of fragments
- MyNotificationManager to init Notification channels and show notifications
- Session handling. See LogoutMonitor, Session entity, SessionStorage
- Timber for logging to Scrolls and Crashlytics
- EventBus implementation with base Event and DataEvent classes
- ConfirmationDialogFragment implementation with DialogEvent as callback. DialogUtil object for showing/hiding DialogFragments.

**Infrastructure**
- Retrofit, OkHttp client setup
- Moshi + Kotshi for json response parsing
- Api Dto classes for incoming objects. Mappers to map them into entities
- Db Dto classes for storing entity objects into a database
- ApiErrorResponse for error response mapping. It also includes RxErrorCallAdapterFactory for mapping errors. See also DomainException and ErrorCode classes.
- ImageLoader for image loading. Uses Glide underneath as the actual image loading library.

**Domain**
- Sample Use Cases for Session handling + sample screens.
- Business logic implemented in Use Cases in the domain module.
- Domain module also contains entities used within the application and Gateway, Storage interface definitions
- The infrastructure module implements said Gateway and Storage interfaces. 

### Android library project

The library project contains a module for main library implementation and a `sample` module for implementation samples.


## Notices

Notice 1: The project does not create a git repository automatically.

Notice 2: The template includes a README.md file for your new project. Please take some time and fill it in.

Notice 3: The template includes a RELEASE_GUIDE.md for your new project. Please take some time to review and update it.

Notice 4: The template includes a `codemagic.yaml` fail to configure your build at Codemagic CI. Please take some time to review it.

Notice 5: The template uses Android Gradle Plugin 7.0, for which the minimum java version is 11.

## Usage

### Creating a new project

#### Prerequisites

 * An installation of Ruby and an Android SDK.

 * Bundler Ruby gem installed. Use "gem install bundler" if not.

 * Access to Mobi Lab's Git Lab installation, to clone this project.

 * Either a UNIX operating system (Linux, OS X) or Windows 10 v1607 or newer with "Ubuntu on Windows" installed.

#### Getting and updating the creation script

 * Getting: 

   ```bash
   git clone git@git.lab.mobi:mobilab/project-creator-android.git
   ```

 * Updating: 

   ```bash
   git checkout master
   git pull origin master
   ```

#### Running the script to create a new project

 * Check the prerequisite list above

 * If you have a previous clone of the script then **please please please** update it, see above

 * Run 

   ```bash
   bundle install
   ```

   in the script's working directory to grab updated dependencies.

* For a new Android project run: 
  
   ```bash
   bundle exec cap android:create
   ```
   
* For a new Android library project run:
   ```bash
   bundle exec cap android:create_library
   ```
   

The script will ask you for a project relative path (like *../my-new-project-dir*). Next up the script will ask some more parameters, answer those to the best of your ability. After that, your new project will be created. To get it to run see Configuring Firebase section below.

#### Configuring Firebase

The created project has Firebase Crashlytics and Analytics enabled by default. But, the project is missing the required `google-services.json` file. Firebase needs to be configured before the project can be run. 

If you do not know where the Firebase project is set up then contact your Project Manager for more information and access.

If you already have access to the Firebase Console for this application then add or update the application (both debug and release key hash values, these are different) at the Firebase Console project (https://firebase.google.com/docs/android/setup#register-app) and download the `google-services.json` configuration file to the main module directly. 

#### Updating dependency versions of your new application

Run 
```
./gradlew dependencyUpdates
```

This will show a list of dependencies that can be updated. Update the dependency versions from the root `build.gradle`.

More information about the plugin:
https://github.com/ben-manes/gradle-versions-plugin

#### Creating a build job in CI

By default we use Codemagic. 

The build configuration file for it is already included, see `codemagic.yaml` and https://confluence.lab.mobi/display/DEV/Codemagic

NOTE: Please replace the email [android@lab.mobi](mailto:android@lab.mobi) in `codemagic.yaml` with your email and with any other stakeholder emails which should receive build success and failure notifications.

1. Check if you have access to the Mobi Lab team in Codemagic. 
   1. See https://confluence.lab.mobi/display/DEV/List+of+Codemagic+CI+teams
   2. Ask the team owners for advice.
2. Add a build to the Mobi Lab team at Codemagic. Name the build as "AppName Android"
   1. If you do not have access then ask the team owners
3. Add a Git build hook for this build so that any pushes to develop branch automatically start the build.
   1. See https://docs.codemagic.io/building/webhooks/
4. Test your build setup
   1. Make sure the hooks triggers when you push to the develop branch.
   2. Make sure the artifacts show up.

PS: You need a **ssh key** to add the build to Codemagic. See https://confluence.lab.mobi/display/DEV/Access+accounts. **Avoid using your own ssh key at all costs!**

PPS: For the base library project there is only a single `buildAll` task.

As of August 2021, Google Play requires new apps to be published with the Android App Bundle. This will replace the APK as the standard publishing format.
The `codemagic.yaml` file includes both the script to attach bundleRelease to Play Store release build and artifacts.
To build the Android App Bundle(aab) file use the `bundleRelease` command.
The bundle must also be signed. To sign using jarsigner, execute the following command `jarsigner -keystore $pathToKeystore app-release.aab $keyAlias`.
Replace the variables with actual values.


WARNING: Current approach creates the bundle using Google's default command.
If a release was built with some other way that wouldn't involve Codemagic (or even do it locally) and would use the .\gradlew bundleRelease, then this would skip all the linters and setup for .\gradlew buildAllRelease.
If that command has already been run then this is fine. Otherwise this can cause a problem.


### Developing and updating the template itself

Make sure you are in the `develop` branch. 

When your merge request is approved and is merged into `develop`, feel free to merge `develop` into `master`

#### Set up the Google services for development

Copy google-services.json from project root to project/app/ dir
Edit project/app/build.gradle and add

```
debugSigning {
    storeFile file('../../ProjectCodeName-debug.keystore')
    storePassword 'androiddebug'
    keyAlias 'androiddebug'
    keyPassword 'androiddebug'
}
```

**NB! Remove said changes from project/app before committing your changes**

#### Open the project template in Android Studio

Open the template from `project` folder with Android Studio. Now you can directly edit and run the project.

Note 1: If you make changes to the code of the project make sure the project still compiles with `buildAllRelease`. 

Note 2: If you make changes to the project creation script them make sure that the script still runs.
