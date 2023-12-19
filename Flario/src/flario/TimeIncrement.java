package flario;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TimeIncrement extends Button {
	private ImageView imgView;
	private int type;
	
	private final static int SINGKO = 5;
	private final static int TRES = 10;
	private final static int  DOS = 15;
	private final static int  UNO = 20;
	private final  Image UNO_IMAGE = new Image("1_time.png");
	private final  Image DOS_IMAGE = new Image("2_time.png");
	private final  Image TRES_IMAGE = new Image("3_time.png");
	private final  Image SINGKO_IMAGE = new Image("5_time.png");
	private final ArrayList<Image> images = new ArrayList<Image>(Arrays.asList(UNO_IMAGE,DOS_IMAGE,TRES_IMAGE,SINGKO_IMAGE));
	final static private ArrayList<Integer> increment = new ArrayList<Integer> (Arrays.asList(UNO,DOS,TRES,SINGKO));

	// TimeIncrement button object constructor
	public TimeIncrement(double x,double y, int type) {
		this.type = type;
		this.imgView = new ImageView(this.images.get(type));
		this.setGraphic(this.imgView);
		this.setPos(x, y);
		this.setPadding(Insets.EMPTY); // no margins
	}

	public int getIncrement() {
		return TimeIncrement.increment.get(this.type);
	}

	private void setPos(double x, double y) {
		this.setTranslateX(x);
		this.setTranslateY(y);
	}
	
	public ImageView getImageView() {
		return this.imgView;
	}
}
