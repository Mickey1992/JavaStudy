package test;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MessageLabel extends Label {
	public MessageLabel()
	{
		Image image = new Image(getClass().getResourceAsStream("attention.png"));
		ImageView imageView = new ImageView(image);
		imageView.setId("attention-icon");
		imageView.setFitWidth(50);
		imageView.setFitHeight(50);
		this.setGraphic(imageView);
		this.setText("Some Message");
		this.setId("message-label");
	}

}
