#!/bin/bash

function clean_dir {
	if [ "$(ls ./$1)" ]; then
		for path in ./${1}/*; do
			if [ -d "${path}" ]; then
		        	rm -r "${path}"
				echo "[DIR-DELETED]: ${path}"
			else
		        	rm "${path}"
				echo "[FILE-DELETED]: ${path}"
			fi
		done
	else
		echo "The Directory is Empty"
	fi
}

# Cleaning <build> Directory
echo "Cleaning Build Files..."
clean_dir "build";

#Cleaning <dep> Directory
echo "Cleaning Dependency Files..."
clean_dir "dep";
