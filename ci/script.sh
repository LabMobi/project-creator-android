#!/bin/bash

bundle install
bundle exec cap android:create creatorapp creatorapp
cp ci/google-services.json creatorapp/app
cp ci/debug.keystore ci/signing.keystore creatorapp/app/signing
cd creatorapp
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > "$FCI_BUILD_DIR/local.properties"
cd app
sed -i '/signingConfigs/,/buildTypes/c\    signingConfigs { \
        releaseSigning { \
            storeFile file("signing/signing.keystore") \
            storePassword $CM_KEYSTORE_PASSWORD_RELEASE \
            keyAlias $CM_KEY_ALIAS \
            keyPassword $CM_KEY_PASSWORD_RELEASE \
        } \
        debugSigning { \
            storeFile file("signing/release.keystore") \
            storePassword $CM_KEYSTORE_PASSWORD_DEBUG \
            keyAlias $CM_KEY_ALIAS \
            keyPassword $CM_KEY_PASSWORD_DEBUG \
        } \
    } \
 \
    buildTypes {' build.gradle
cat build.gradle
