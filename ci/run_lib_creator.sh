#!/bin/bash

bundle install
bundle exec cap android:create_library -s path=p42lib -s name=p42lib
cd p42lib
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > "$FCI_BUILD_DIR/local.properties"
