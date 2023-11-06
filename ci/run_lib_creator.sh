#!/bin/bash

bundle install
bundle exec cap android:create_library templatelib templatelib
cd templatelib
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > "$FCI_BUILD_DIR/local.properties"
