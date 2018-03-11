#!/bin/bash


ROOT_DIR=$PWD
WORK_DIR=$ROOT_DIR/work
MODULE_DIR=$ROOT_DIR/module
#MODULE_DIR="/c/Users/Mickey/Desktop/modules"
mkdir -p $MODULE_DIR
mkdir $WORK_DIR

PROJECT_ROOT_PATH="/d/workspace"

function update_jar()
{
	echo "update $1 start"
	jar_file=$1
	depend_modules=""

	file_folder_dir=$(dirname "${jar_file}")
	echo "classpath: $file_folder_dir/*"
	dependency=$(jdeps --class-path "$file_folder_dir/" -summary $jar_file | grep "^.*jar$" | grep -v "$MODULE_DIR")
	echo "dependency: $dependency"
	for depend_file_dir in $dependency
	do
		echo "$jar_file depends on $depend_file"
		update_jar $depend_file &
		wait
		depend_modules=$depend_modules$(depend_file_dir | sed 's/^.*\\//');
	done

	

	work_dir=$file_folder_dir/work
	class_dir=$work_dir/classes

	

	echo "generating module-info.jave"
	if [ "$depend_modules" != "" ] 
	then 
		depend_modules=${depend_modules::-1}
		echo "$jar_file depend on modules: $depend_modules"
		jdeps --module-path $MODULE_DIR \
			--add-modules $depend_modules \
			--generate-module-info $work_dir $jar_file
	else 
		echo "$jar_file do not depend on any module"
		jdeps --generate-module-info $work_dir $jar_file
	fi

	module_name=$(find $work_dir/ -maxdepth 1 -mindepth 1 -type d -exec basename {} \;)
	echo "module name: $module_name"

	echo "extracting $jar_file"
	mkdir -p $class_dir
	cd $class_dir
	jar xf $jar_file
	cd $ROOT_DIR

	echo "compile module-info.java"
	echo "$work_dir/$module_name/module-info.java"
	
	javac --module-path $MODULE_DIR \
		-d $class_dir \
		$work_dir/$module_name/module-info.java

	echo "injecting module-info.java to $jar_file"
	jar -uf $jar_file -C $class_dir module-info.class
	jar -d --file=$jar_file

	mv $jar_file $MODULE_DIR/$(basename $jar_file)

	rm -rf $work_dir
	echo "update $1 end"
}

function create_jar()
{
	project_name=$1
	project_dir=$PROJECT_ROOT_PATH/${project_name/-//}
	echo "Project dir: $project_dir"
	project_work_dir=$WORK_DIR/$project_name
	echo "$project_work_dir"
	echo "Going to create folder $project_work_dir"
	mkdir $project_work_dir

	find $project_dir/lib/ -type f -exec cp {} $project_work_dir \; 
	for jar_file in $project_work_dir/*
	do
		#generate_depend_module_list
		update_jar $jar_file
	done
}

create_jar JavaFXStudy
