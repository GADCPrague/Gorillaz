package cz.roke.android.gorillaz;

public class Gorilka extends GameObject {
	
	public final int WIDTH = 32;
	
	public final int HEIGHT = 32;
	
	public Gorilka(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);
	}
	
	public Gorilka(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	
	
}
