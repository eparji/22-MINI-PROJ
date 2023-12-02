package main;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class newStage {
	private Scene scene;
	private Scene scene2;
	private Scene options;

	private VBox root;
	private VBox root2;
	private VBox optionrt;

	public newStage() {

		this.root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.setStyle("-fx-background-color: gray;");

		this.root2 = new VBox();
		root2.setAlignment(Pos.CENTER);
		root2.setStyle("-fx-background-color: black;");

		this.optionrt = new VBox();
		optionrt.setAlignment(Pos.CENTER);
		optionrt.setStyle("-fx-background-color: blue;");

		this.scene = new Scene(root, 500, 500);
		this.scene2 = new Scene(root2, 500, 500);
		this.options = new Scene(optionrt, 500, 500);

	}

	public void setStage(Stage stage) {
		Label l1 = new Label("Main Menu");
		l1.setFont(new Font("Arial", 24));
		l1.setTextFill(Color.WHITE);

		Label l2 = new Label("Game");
		l2.setFont(new Font("Arial", 24));
		l2.setTextFill(Color.WHITE);

		Label l3 = new Label("Options");
		l3.setFont(new Font("Arial", 24));
		l3.setTextFill(Color.WHITE);

		Button play = new Button("Play!");
		Button opt = new Button("Options");
		Button exit = new Button("Exit!");

		Button ret = new Button("Back to Main");
		Button clicked = new Button("Clicked!");

		Button save = new Button("Saved!");

		scene.setFill(Color.WHITE);
		scene2.setFill(Color.BLACK);

		play.setOnAction(e -> {
			stage.setScene(scene2);
		});

		opt.setOnAction(e -> {
			stage.setScene(options);
		});

		ret.setOnAction(e -> {
			stage.setScene(scene);
		});

		save.setOnAction(e -> {
			stage.setScene(scene);
		});

		clicked.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				handleButtonClick("clicked");
			}

		});
//		
		exit.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				handleButtonClick("exit");
			}

		});

		root.getChildren().addAll(l1, play, opt, exit);
		root2.getChildren().addAll(l2, clicked, ret);
		optionrt.getChildren().addAll(l3, save);

		stage.setTitle("Trial");
		stage.setScene(scene);
		stage.show();
	}

	private void handleButtonClick(String btnName) {
		if (btnName.contentEquals("clicked")) {
			System.out.println("Hello World!");
		} else {
			System.out.println("Exiting...");
			System.exit(0);
		}
	}

}
