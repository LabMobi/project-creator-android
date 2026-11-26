# Mobi Lab Android Project Creator — Repository Guide

This repository contains the Ruby-based project bootstrapper and Android project templates used by Mobi Lab.

## Repository Layout

- `project/` — Main Android application template (4-module Clean Architecture, single-activity Jetpack Compose + Jetpack Navigation 3, Hilt, RxJava, Firebase, Scrolls). See `project/AGENTS.md` for detailed app architecture guidelines.
- `projectlib/` — Android library project template (library module + sample app).
- `script.rb` & `lib/android.rb` — Capistrano/Ruby generator script that copies a template and substitutes placeholders.
- `ci/` — CI helper scripts (`run_app_creator.sh`, `run_lib_creator.sh`, `run_tests.sh`) and CI keystores.
- `README.md` & `CHANGELOG.md` — Template usage documentation and change history.

## Template Substitution Rules (`lib/android.rb`)

When `bundle exec cap android:create` runs, `lib/android.rb` processes every file whose extension matches:
`xml | properties | java | kt | gradle | txt | md | yaml | pro`

For those files it:
1. Evaluates ERB templates (`<%= ... %>`) — e.g. `sanitized_name`, `storepass_release`, `keypass_release`.
2. Replaces `ProjectCodeName` with the new project's sanitized name (in both file contents and file paths).
3. Replaces `mobi.lab.sample` (and `mobi/lab/sample` in paths) with the new project's package name.

**Guidelines when editing files under `project/` or `projectlib/`:**
- Always use `ProjectCodeName` and `mobi.lab.sample` as the placeholder project name and base package so generator substitution continues to work.
- Avoid stray `<%` sequences in `.md`, `.kt`, `.gradle`, or `.xml` files unless they are intentional ERB tags.
- Preserve the ERB signing config placeholders in `project/app/build.gradle` (and do not commit local `project/app/google-services.json` or temporary `debugSigning` overrides used for local runs).

## Making Template Changes

1. Open `project/` (or `projectlib/`) in Android Studio to develop and test template changes directly.
2. Keep `project/AGENTS.md`, `project/README.md`, root `README.md`, and `CHANGELOG.md` updated whenever the template architecture, dependencies, or conventions change.
3. Verify template builds from `project/`:
   - `./gradlew checkCode` (ktlint + detekt across all modules)
   - `./gradlew :app:testDebugUnitTest :app-domain:testDebugUnitTest :app-infrastructure:testDebugUnitTest`
   - `./gradlew :app:assembleDebug :app:minifyReleaseWithR8 :app:lint`
4. Keep `compileSdk` and AGP compatibility in mind when bumping AndroidX dependencies in `project/gradle/libs.versions.toml` (`checkDebugAarMetadata` enforces `compileSdk` requirements strictly).
