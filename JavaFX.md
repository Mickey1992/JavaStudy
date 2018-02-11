### How to setup Eclipse to be able to use JavaFX

For reasons unknown to me, Eclipse does not automatically do this for the JavaFX classes (from the package javafx ), though. 
But because like the rest of the JRE, the FX sources are also bundled with the JDK, few things are easier than to attach them.

In Eclipse:

+ open preferences (Window ~> Preferences)
+ edit the used JRE (Java ~> Installed JREs ~> Select JDK on the right ~> Edit)
+ start to attach FX sources (select jfxrt.jar ~> Source Attachement)
+ configure the sources (select External location ~> External File)
+ select the sources bundled with the JDK (go to JDK root folder ~> select javafx-src.zip)
+ almost done, just OK/Finish your way back…

refer to: [JavaFX Sources in Eclipse](https://blog.codefx.org/tools/javafx-sources-in-eclipse/)
