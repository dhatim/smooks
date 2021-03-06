#!/usr/bin/env bash

if [ "${TRAVIS_EVENT_TYPE}" == push ] &&
       echo "${TRAVIS_TAG}" | egrep '^[0-9]+\.[0-9]+\.[0-9]+$'
then
    # the build is triggered by a tag push, and the tag looks like
    # a version number: proceed with release
    echo ${GPG_SECRET_KEY} | base64 --decode | gpg --import
    echo ${GPG_OWNERTRUST} | base64 --decode | gpg --import-ownertrust
    cd smooks-parent
    mvn versions:set -DnewVersion=${TRAVIS_TAG}
    cd ..
    mvn --batch-mode --quiet -s .travis/settings.xml -Prelease deploy
else
    # this is a regular build
    mvn --batch-mode --quiet install
fi
