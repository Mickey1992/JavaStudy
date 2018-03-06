## Command will be used

- Compile

```bash
javac --module-path [module path] -d [output folder] [java file path]

javac --source-path [source path] -d [output folder]  [java file path]
```

- Create jar file

```bash
jar --create --file [output jar file path] -C [compiled class path] .
```

- Check module dependency

```bash
jar -d --file=[jar file path]
```

- generate module from a automatic module

```bash
jdeps --generate-module-info [output folder] [jar file path]

jdeps --module-path [module path] --add-modules [module name] --generate-module-info [output folder] [jar file path]
```

- update jar file(automatic module) with generated `module-info.java` file

```bash
jar uf [jar file path] -C [compiled class path of jar file] module-info.class
```

- package

```bash
javapackager -deploy -v -outdir [output folder] -name [application name] -native -BsignBundle=false -BappVersion=1.0 -Bmac.dmg.simple=true --module-path [module path];"%JAVA_HOME%\jmods" --add-modules [root module name] --module [module name]/[package].[main class]
```

###Other usage
https://qiita.com/opengl-8080/items/1007c2b2543c2fe0d7d5#unnamed-module---named-module

http://www.torutk.com/projects/swe/wiki/Jigsaw%E8%A9%A6%E8%A1%8C%E9%8C%AF%E8%AA%A4

https://www.logicbig.com/tutorials/core-java-tutorial/modules/automatic-modules.html

https://github.com/codetojoy/easter_eggs_for_java_9/blob/master/egg_34_stack_overflow_47727869
