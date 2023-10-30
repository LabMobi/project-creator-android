# ProjectCodeName library

TODO a short description of what the library does
TODO an overview how to use the library

## Adding the library to a project

To add the library we need the `.aar` artefact. Either build it locally or take it from Codemagic's `ProjectCodeName` project.

##### Gradle

Add the following to your main application module's `build.gradle` file:

```
repositories {
   flatDir {
        dirs 'libs'
    }
}

dependencies {
    implementation(name: 'ProjectCodeName-1.0.0', ext: 'aar')
}
```

This assumes 2 things:
* The version is `1.0.0`
* The main application module has a `libs/` directory that contains the `ProjectCodeName-1.0.0.aar` artefact.

