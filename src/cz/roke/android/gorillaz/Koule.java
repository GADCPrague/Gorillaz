package cz.roke.android.gorillaz;

public class Koule extends GameObject {

	public final int WIDTH = 16;
	
	public final int HEIGHT = 16;
	
	public Koule(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);
	}
	
	public Koule(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
}
