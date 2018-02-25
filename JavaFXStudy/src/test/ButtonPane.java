package test;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class ButtonPane extends BorderPane {
	public ButtonPane(Button btn1, Button btn2, Button btn3)
	{
		setId("button-pane");
		_doLayout(btn1, btn2, btn3);
	}
	
	private void _doLayout(Button btn1, Button btn2, Button btn3)
	{
		HBox left = new HBox();
		left.getChildren().add(btn1);
		HBox right = new HBox();
		right.getChildren().addAll(btn2, btn3);
		this.setLeft(left);
		this.setRight(right);
	}
}
