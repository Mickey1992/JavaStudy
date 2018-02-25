package test;

import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class MainScreen extends VBox {
	private MessageLabel _messageLabel = new MessageLabel();
	private TableListPane _tableListPane = new TableListPane();
	private Button _btn1 = new Button("Button 1");
	private Button _btn2 = new Button("Button 2");
	private Button _btn3 = new Button("Button 3");
	public MainScreen()
	{
		this.setId("main-screen");
		_addMesasgeLabel();
		_addTable();
		_addButtons();
		getStylesheets().add(MainScreen.class.getResource("MainScreen.css").toExternalForm());
	}
	
	private void _addMesasgeLabel(){
		this.getChildren().add(_messageLabel);
	}
	
	private void _addButtons()
	{
		this.getChildren().add(new ButtonPane(_btn1, _btn2, _btn3));
	}
	
	private void _addTable()
	{
		this.getChildren().add(_tableListPane);
	}
}
