#!/bin/bash

cp google-services.json project/app
cd project
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > "$FCI_BUILD_DIR/local.properties"
cd app
sed -i "s|signing/signing.keystore|$CM_KEYSTORE_PATH|g" build.gradle
sed -i "s|<%= storepass_release %>|$CM_KEYSTORE_PASSWORD|g" build.gradle
sed -i "s|<%= sanitized_name %>|$CM_KEY_ALIAS|g" build.gradle
sed -i "s|<%= keypass_release %>|$CM_KEY_PASSWORD|g" build.gradle
sed -i "s|signing/debug.keystore|$CM_KEYSTORE_PATH|g" build.gradle
sed -i "s|<%= storepass_debug %>|$CM_KEYSTORE_PASSWORD|g" build.gradle
sed -i "s|<%= sanitized_name %>|$CM_KEY_ALIAS|g" build.gradle
sed -i "s|<%= keypass_debug %>|$CM_KEY_PASSWORD|g" build.gradle