module Flario {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;
	requires javafx.media;

	opens main to javafx.graphics, javafx.fxml;
	opens flario to javafx.graphics, javafx.fxml;
}