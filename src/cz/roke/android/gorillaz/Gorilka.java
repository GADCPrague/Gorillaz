package cz.roke.android.gorillaz;

public class Gorilka extends GameObject {
	
	public static final int WIDTH = 40;
	
	public static final int HEIGHT = 40;
	
	public static final int MOVE = 2;
	
	public int due;
	
	public Gorilka(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		// TODO Na zacatku vpravu
		due = GorillazActivity.RIGHT;
	}
	
	public Gorilka(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}
	

	public void moveUp() {
		setY(getY() - MOVE);
		due = GorillazActivity.UP;
	}
	
	public void moveDown() {
		setY(getY() + MOVE);
		due = GorillazActivity.DOWN;
	}
	
	public void moveLeft() {
		setX(getX() - MOVE);
		due = GorillazActivity.LEFT;
	}
	
	public void moveRight() {
		setX(getX() + MOVE);
		due = GorillazActivity.RIGHT;
	}
	
	public void fire() {
	
	}
	
}
