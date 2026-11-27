#!/bin/sh
set -e

bundle install
printf 1 | bundle exec cap android:create -s path=p42app -s name=p42app
cp project/app/google-services.json p42app/app/google-services.json
cd p42app
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > local.properties
