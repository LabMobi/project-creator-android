#!/bin/bash

./gradlew connectedAndroidTest
result=$?
adb logcat -d > emulator.log
if [ $result -eq 0 ]
then
  echo "Tests executed successfully"
else
  echo "Tests failed"
  exit 1
fi