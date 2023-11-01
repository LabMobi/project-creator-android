# ProjectCodeName library

TODO a short description of what the library does
TODO an overview how to use the library

## Adding the library to a project

`.aar` file dependencies are added via a local Maven repository under `maven/local`. If dependencies need to be updated, the repository structure, metadata and pom files need to be updated.

Maven itself can be used to install a file in the default local repository (`~/.m2/`).

1. Example command for installing `progressbutton.aar` dependency in the default local repo:

    ```
    mvn install:install-file -Dfile=ProgressButton-1.0.1-release.aar -DgroupId=mobi.lab.progressbutton -DartifactId=progressbutton -Dversion=1.0.1 -Dpackaging=aar -DgeneraterPom=true
    ```

    If you do not have Maven installed then you can install it via:

    ```
    sudo apt install maven
    ```
    If you are using Windows then you can easily install and use Maven via WSL. Just be mindful that the artifact will be installed to the ".m2" folder in the WSL home directory (not the Windows one).

2. Copy paste the resulting `mobi` directory into your project's repository's `maven/local` (if the directory doesn't exist, create it).
**NB! Be aware of the package structure and don't copy paste unwanted dependencies. mobi/lab/ directory can contain other dependencies and/or versions, too!**

3. Define the local maven repository for your application. Preferably using the is "includeGroup" so that only your artifact can be taken from it. In case of a Mobi Lab template-based project, be sure to check the `settings.gradle` `dependencyResolutionManagement` in case the new dependency has a different package name. Only listed group ids are allowed:

    ```
    dependencyResolutionManagement {
        repositories {
            maven {
                url "$rootDir/maven/local"
                content {
                    includeGroup "mobi.lab"
                }
            }
        }
    }
    ```
    In case of other setup, just define the repository for the correct module(s)
    ```

    repositories {
        ...
        maven {
            url "$rootDir/maven/local"
            content {
                includeGroup "mobi.lab"
            }
        }
        ...
    }
    ```

4. Declare the dependency as you normally would in `libs.versions.toml`

 


