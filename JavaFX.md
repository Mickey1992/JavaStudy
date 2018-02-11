## How to setup Eclipse to be able to use JavaFX
### Add Accessible Rule Pattern
go to project> properties> java build path> libraries> then expand the libraries and double click on> Access rules there you set the permission Resolution : Accessible Rule Pattern : javafx/**

refer to: https://stackoverflow.com/a/46513651/2334320

### tell Eclipse to attach the JavaFX sources
+ open preferences (Window ~> Preferences)
+ edit the used JRE (Java ~> Installed JREs ~> Select JDK on the right ~> Edit)
+ start to attach FX sources (select jfxrt.jar ~> Source Attachement)
+ configure the sources (select External location ~> External File)
+ select the sources bundled with the JDK (go to JDK root folder ~> select javafx-src.zip)
+ almost done, just OK/Finish your way back…

refer to: [JavaFX Sources in Eclipse](https://blog.codefx.org/tools/javafx-sources-in-eclipse/)
