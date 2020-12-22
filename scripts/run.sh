#!/usr/bin/env bash

UNAME=$( command -v uname)
ROOT_DIR=".."
TARGET_DIR="target/"
RESOURCES="/"

java_reg=^14\.[0-9]\.[0-9]$

# TEST JAVA
if type -p java; then
    echo found java executable in PATH
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo found java executable in JAVA_HOME     
    _java="$JAVA_HOME/bin/java"
else
    echo "no java"
fi

if [[ "$_java" ]]; then
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo version "$version"
    if [[ "$version" =~ $java_reg ]]; then
        echo java version $version is correct
    else
        echo ERROR java version $version is incorrect
        exit 1
    fi
fi

run_unix (){
	cd $ROOT_DIR
	cd $TARGET_DIR
	echo $@
	java --enable-preview com.labsky.timotej.BigShop "$@"	
}

case $( "${UNAME}" | tr '[:upper:]' '[:lower:]') in
  linux* | darwin*)
    printf 'linux\n' 
    run_unix "$@"
    ;;
  msys* | cygwin* | mingw* | nt|win*)
    # or possible 'bash on windows'
    printf 'windows\n'
    ;;
  *)
    printf 'unkonwn'
    exit 1
    ;;
esac



