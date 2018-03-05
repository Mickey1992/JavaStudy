## How to setup Eclipse to be able to use JavaFX
### 1. Add Accessible Rule Pattern
go to project> properties> java build path> libraries> then expand the libraries and double click on> Access rules there you set the permission Resolution : Accessible Rule Pattern : javafx/**

refer to: https://stackoverflow.com/a/46513651/2334320

### 2. Tell Eclipse to attach the JavaFX sources
+ open preferences (Window ~> Preferences)
+ edit the used JRE (Java ~> Installed JREs ~> Select JDK on the right ~> Edit)
+ start to attach FX sources (select jfxrt.jar ~> Source Attachement)
+ configure the sources (select External location ~> External File)
+ select the sources bundled with the JDK (go to JDK root folder ~> select javafx-src.zip)
+ almost done, just OK/Finish your way back…

refer to: [JavaFX Sources in Eclipse](https://blog.codefx.org/tools/javafx-sources-in-eclipse/)

## The basic structure of a JavaFX Application
- `Stage` class - the top-level JavaFX container
- `Scene` class - the container for all content

## Some layout pane
- StackPane
   lay out its children in a back-to-front stack
- GridPane
   lay out its children within a flexible grid of rows and columns
- HBox
   lay out its children in a single horizontal row.
   
## Style Sheets
### Selector `.root` in JavaFX is simlar with `body` in HTML.

### I did an expirement.
the style defined in `SiginBtn.css` only applys on `SigninBtn` class but not `Button` class.
**`Login.java`**
```java
public class Login extends Application {

	@Override
	public void start(final Stage primaryStage) throws Exception {
		primaryStage.setTitle("JavaFX Welcome");
		final GridPane grid = new GridPane();

		final SigninBtn btn = new SigninBtn("Sign in");
		final HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(btn);
		grid.add(hbBtn, 1, 4);

		final Button btn2 = new Button("test");
		final HBox hbBtn2 = new HBox(10);
		hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn2.getChildren().add(btn2);
		grid.add(hbBtn2, 1, 8);

		final Scene scene = new Scene(grid, 300, 275);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(Login.class.getResource("Login.css").toExternalForm());
		scenetitle.setId("welcome-text");
		actiontarget.setId("actiontarget");
		primaryStage.show();

	}

	public static void main(final String[] args) {
		launch(args);
	}
}
```
**`SigninBtn.java`**
```java
public class SigninBtn extends Button {
	public SigninBtn(final String text) {
		super(text);
		this.getStylesheets().add(SigninBtn.class.getResource("SigninBtn.css").toExternalForm());
	}
}
```
**`SigninBtn.css`**
```css
.button {
    -fx-text-fill: white;
    -fx-font-family: "Arial Narrow";
    -fx-font-weight: bold;
    -fx-background-color: linear-gradient(#61a2b1, #2A5058);
    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );
}

.button:hover {
    -fx-background-color: linear-gradient(#2A5058, #61a2b1);
}
```

### The `.root` style class includes properties that can be used by other styles to provide consistency in a UI

```css
// the property -fx-focused-base is defined in the .root style. 
.check-box:focused {
    -fx-color: -fx-focused-base;
}
```

TODO:
+ [Using JavaFX UI Controls](https://docs.oracle.com/javafx/2/ui_controls/jfxpub-ui_controls.htm)
+ [Styling Layout Panes with CSS](https://docs.oracle.com/javafx/2/layout/style_css.htm)
+ [Styling Charts with CSS](https://docs.oracle.com/javafx/2/charts/css-styles.htm) **can check this later**
+ [Using FXML to Create a User Interface](https://docs.oracle.com/javafx/2/get_started/fxml_tutorial.htm#CIHHGHJJ)
+ [Working With Layouts in JavaFX](https://docs.oracle.com/javafx/2/layout/jfxpub-layout.htm)
+ [Deploying Your First JavaFX Application](https://docs.oracle.com/javafx/2/get_started/basic_deployment.htm#BABIDHGA)

https://stackoverflow.com/questions/9861178/javafx-primarystage-remove-windows-borders
https://stackoverflow.com/questions/46712293/how-can-i-remove-my-javafx-program-from-the-taskbar?rq=1

##Command used to package

1.javapackager -createjar -nocss2bin -appclass [package.mainClass] -srcdir bin -outdir [output folder] -outfile [output file name] -v

2.javapackager -deploy -native image -outdir [output folder] -outfile [output file name] -srcdir [output folder in step1] -srcfiles [output file in step1] -appclass [Main class name] -name "MyApplication" -title "MyApplication" -BappVersion=1.0 -Bwin.menuGroup="MyApplication"

3.jdeps -s [jar file in step one]

javac --module-source-path src -d mod -m gui

java --module-path mod -m gui/gui.test.Test

jlink --module-path mod "C:\Program Files\Java\jdk-9.0.4\jmods" -add-modules gui output myjre

jlink --module-path $JAVA_HOME/jmods:mod --add-modules gui --output greetingsapp


javapackager -createjar -nocss2bin -appclass gui.test.Test -srcdir bin -outdir out -outfile myApplication -v
jdeps -s out/myApplication.jar
javapackager -deploy -v -outdir output -name Test -native -BsignBundle=false -BappVersion=1.0 -Bmac.dmg.simple=true --module-path JAVA_HOME/jmods --module gui/gui.test.Test



jlink --compress=2 --module-path JAVA_MODS:modules --add-modules mod --output dist/my-app --launcher myapp=gui/gui.test.Test 
jlink --compress=2 --module-path JAVA_HOME/jmods:modules --add-modules mod --output dist/my-app --launcher myapp=gui/gui.test.Test

jlink --module-path libs:jmods --add-modules gui --launcher gui=gui/gui.test.Test --output dist --strip-debug --compress 2 --no-header-files --no-man-pages


jlink -output mydemo-runtime --module-path c:/Program Files/Java/jdk-9.0.4/mods --limit-modules gui --add-modules gui -launcher demorun=gui --compress=2 --no-header-files --strip-debug


java --module-path out/myApplication.jar -m org.abondar.experimental.intro/org.abondar.experimental.intro.Welcome

refer to :http://www.torutk.com/projects/swe/wiki/Jigsaw%E8%A9%A6%E8%A1%8C%E9%8C%AF%E8%AA%A4

and https://www.logicbig.com/tutorials/core-java-tutorial/modules/automatic-modules.html

then, 

javapackager -deploy -v -outdir output -name Test -native -BsignBundle=false -BappVersion=1.0 -Bmac.dmg.simple=true --module-path module;"%JAVA_HOME%\jmods" --add-modules javafx.study --module javafx.study/test.Test

https://qiita.com/opengl-8080/items/1007c2b2543c2fe0d7d5#named-module---automatic-module
