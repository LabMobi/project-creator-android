![Mobi Lab](docs/assets/mobilab-header-logo.png)
# Mobi Lab Android Template

This is a script to simplify Android project creation.

## Idea

"Batteries-included" project bootstrapper that configures all the infrastructure necessary for starting a new Android project with Kotlin. Let's not waste time configuring build scripts when we could be delivering features instead.

There are 2 project templates available: 

- a regular Android project 
- an Android library project


### Android project
The Android project template uses MVVM for UI and Use Cases for business logic. 

View models are powered by Google's ViewModel and LiveData classes. Communication between Use Cases and models is done with RxJava. An event bus is used for communication originating from dialogs.

Dependencies are injected with Hilt. Constructor injection (@Inject annotated constructors) is preferred where possible. See `di` packages in modules.

The bootstrapper also includes updated Scrolls library that allows posting to email and chats (without the need of the backend), Gradle tasks for Codemagic, and pre-configured unit and instrumentation tests.

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
- Hilt setup completed
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

Notice 2: The template includes a `README.md` file for your new project. Please take some time and fill it in.

Notice 3: The template includes a `RELEASE_GUIDE.md` for your new project. Please take some time to review and update it.

Notice 4: The template includes a `codemagic.yaml` fail to configure your build at Codemagic CI. Please take some time to review it.

Notice 5: The template uses Android Gradle Plugin 8.1.2, for which the minimum Java version is 17.

## Usage

### Creating a new project

#### Prerequisites

 * An installation of Ruby and an Android SDK.

 * Bundler Ruby gem installed. Use "gem install bundler" if not.

 * Either a UNIX operating system (Linux, OS X) or Windows 10 v1607 or newer with "Ubuntu on Windows" installed.

#### Getting and updating the creation script

 * Getting: 

   ```bash
   git clone git@github.com:LabMobi/project-creator-android.git
   ```

 * Updating: 

   ```bash
   git checkout main
   git pull origin main
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

The necessary arguments can be passed as command line arguments as well:

   ```bash
   bundle exec cap android:create -s path=<relative_path> -s name=<project_name> -s sanitized=<sanitized_name> -s package=<package_name>
   ```

   ```bash
   bundle exec cap android:create_library -s path=<relative_path> -s name=<project_name> -s sanitized=<sanitized_name> -s package=<package_name>
   ```

The `sanitized` and `package` arguments are optional, if not passed then the script will sanitize the name and default package name `mobi.lab` will be used.

#### Configuring Firebase

The created project has Firebase Crashlytics and Analytics enabled by default. But, the project is missing the required `google-services.json` file. Firebase needs to be configured before the project can be run. 

For Mobi Lab employees, if you do not know where the Firebase project is set up then contact your Project Manager for more information and access.

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

Disclaimer: This section assumes you are an employee of Mobi Lab and have access to the company's account in Codemagic CI.

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

1. Clone the repository
2. Create a new branch using Mobi Lab's [branching strategy](https://labmobi.atlassian.net/wiki/spaces/DEV/pages/15991186/Git+branching+strategy)
3. Make changes to the project
4. Document the changes in `CHANGELOG.md` in the project's root directory
5. Create a pull request targeting the `develop` branch
   - A code review and approval from at least one other person from the community
   - Tag `Lauris Kruusamäe` and `Harri Kirik` in the request
   - Codemagic builds will be triggered automatically when creating a pull request, make sure those succeed
     - If Mobi Lab employee then you can trigger the builds manually in Codemagic before creating a pull request if you want to be sure, just choose the branch you're working on and the appropriate workflow
6. If the review is approved, then merge the pull request to `develop` and then feel free to merge `develop` into `main`
   - If you do not have the rights to merge then contact `Lauris Kruusamäe` or `Harri Kirik`
7. If Mobi Lab employee, then let the Android community `#community-android` in Slack know about the changes you made

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
