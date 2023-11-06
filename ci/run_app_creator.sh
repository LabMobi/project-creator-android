#!/bin/bash

bundle install
bundle exec cap android:create p42app p42app
cp google-services.json p42app/app
cp ci/debug.keystore ci/signing.keystore p42app/app/signing
cd p42app
chmod +x gradlew
echo "sdk.dir=$ANDROID_SDK_ROOT" > "$FCI_BUILD_DIR/local.properties"
cd app
sed -i "/signingConfigs {/,/buildTypes/c\ \
    signingConfigs {\n \
        releaseSigning {\n \
            storeFile file(\"signing/signing.keystore\")\n \
            storePassword \"FQQLjhEOVAsgBlWvaFjrOOQVrGtPdYAnqitjgJxSopQpgimQhv\"\n \
            keyAlias \"p42app\"\n \
            keyPassword \"GDvTLAgjOntFFnECJoAmgGzhPpwTgpQjiSeDNjbCYxNEkVhKzH\"\n \
        }\n \
        debugSigning {\n \
            storeFile file(\"signing/debug.keystore\")\n \
            storePassword \"tHDueHvbqoqajlqYSmEvfTGcaYoFUCnsKCUymURFcunhNYysgb\"\n \
            keyAlias \"p42app\"\n \
            keyPassword \"VomPDReTqFcwqjdeInPHEbbgvudUHvOczOrlRCxBlneaLpWBWi\"\n \
        }\n \
    }\n \
\n \
    buildTypes {" build.gradle
