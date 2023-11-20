#!/bin/bash

cp google-services.json project/app
cd project
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > "$FCI_BUILD_DIR/local.properties"
cd app
sed -i "s|signing/signing.keystore|../../ProjectCodeName-debug.keystore|g" build.gradle
sed -i "s|<%= storepass_release %>|androiddebug|g" build.gradle
sed -i "s|<%= sanitized_name %>|androiddebug|g" build.gradle
sed -i "s|<%= keypass_release %>|androiddebug|g" build.gradle
sed -i "s|signing/debug.keystore|../../ProjectCodeName-debug.keystore|g" build.gradle
sed -i "s|<%= storepass_debug %>|androiddebug|g" build.gradle
sed -i "s|<%= sanitized_name %>|androiddebug|g" build.gradle
sed -i "s|<%= keypass_debug %>|androiddebug|g" build.gradle