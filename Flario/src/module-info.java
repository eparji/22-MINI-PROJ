module Flario {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.base;

	opens main to javafx.graphics, javafx.fxml;
	opens flario to javafx.graphics, javafx.fxml;
}