#!/bin/bash

if [ ! -d ./build/build_files ]; then
	mkdir ./build/build_files
fi
if [ "$(ls ./dep)" ]; then
	javac -cp ./dep/*.jar ./src/* -d ./build/build_files/
	cd dep
	if [ ! -d output ]; then
		mkdir output
	fi
	cp ./*.jar ./output
	cd output
	for lib in ./*.jar; do
		jar xf "${lib}"
	done
	rm ./*.jar
	for path in ./*; do
		if [[ -d "${path}" && "${path}" !=  "./META-INF" ]]; then
			cp -r "${path}" ../../build/build_files
		fi
	done
	cd ../../
else
	javac ./src/* -d ./build/build_files/
fi
ls -al ./src
cd ./build/build_files
ls -al
jar -cvf ../ZSNetwork-1.0.0.0.jar ./*
