package cz.roke.android.gorillaz;

public class Gorilka extends GameObject {
	
	public static final int WIDTH = 32;
	
	public static final int HEIGHT = 32;
	
	public static final int MOVE = 2;
	
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
	

	public void moveUp() {
		setY(getY() - MOVE);
	}
	
	public void moveDown() {
		setY(getY() + MOVE);
	}
	
	public void moveLeft() {
		setX(getX() - MOVE);
	}
	
	public void moveRight() {
		setX(getX() + MOVE);
	}
	
	
}
