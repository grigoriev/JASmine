#!/usr/bin/env bash

pushd "`dirname $0`/../dist" > /dev/null
rm -rf *
popd > /dev/null

pushd "`dirname $0`/../sources/jasmine" > /dev/null
mvn clean package
popd > /dev/null

pushd "`dirname $0`/.." > /dev/null
cp -rf sources/jasmine/jasmine-server/target/*.war dist/
popd > /dev/null
