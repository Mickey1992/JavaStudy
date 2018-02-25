package test;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.stage.Stage;

public class Test extends Application {
private MainScene _mainScene = new MainScene();
	@Override
	public void start(Stage primaryStage) throws Exception {
		Platform.setImplicitExit(false);
		primaryStage.setScene(_mainScene);
		primaryStage.setTitle("Test Application");
		primaryStage.show();
		//primaryStage.toBack();
		
		primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

		    @Override
		    public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
		    	primaryStage.hide();
		    	primaryStage.show();
		    	primaryStage.setIconified(false);
		    	primaryStage.toBack();
		    }
		});
		
		primaryStage.xProperty().addListener((obs, oldVal, newVal) -> System.out.println("X: " + newVal));
		primaryStage.yProperty().addListener((obs, oldVal, newVal) -> System.out.println("Y: " + newVal));

	}

	public static void main(String[] args) {
		launch(args);
	}

}
