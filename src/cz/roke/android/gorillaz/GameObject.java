package cz.roke.android.gorillaz;

import android.graphics.Canvas;
import android.graphics.Paint;

public class GameObject {

	public static final int OX = 0;

	public static final int OY = 1;

	public static final int LH = 0;

	public static final int PH = 1;

	public static final int LD = 2;

	public static final int PD = 3;

	public static final int UP = 0;

	public static final int DOWN = 1;

	public static final int LEFT = 2;

	public static final int RIGHT = 3;

	protected int x;

	protected int y;

	protected int width;

	protected int height;

	
	private int bod[][] = new int[4][2];
	


	public GameObject() {

	}

	public GameObject(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public int getRight() {
		return x + width;
	}

	public int getBottom() {
		return y + height;
	}

	public boolean isCollision(GameObject o) {
		if (isInObject(o) == true)
			return true;

		if (o.isInObject(this) == true)
			return true;

		return false;
	}

	public boolean isInObject(GameObject o) {

		bod[LH][OX] = x;
		bod[LH][OY] = y;

		bod[PH][OX] = getRight();
		bod[PH][OY] = y;

		bod[LD][OX] = x;
		bod[LD][OY] = getBottom();

		bod[PD][OX] = getRight();
		bod[PD][OY] = getBottom();

		for (int i = 0; i < 4; i++) {
			if (bod[i][OX] >= o.x && bod[i][OX] < o.getRight() && bod[i][OY] >= o.y && bod[i][OY] <= o.getBottom())
				return true;
		}

		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public boolean canUp() {
		
		int px = (getRight() - x) / 2  + x;
		int py = y - 3;
		
		if ( GameView.mapa.collisionMap.getPixel(px, py) < -2 )
			return false;

		return true;
	}

	public boolean canDown() {
		int px = (getRight() - x) /2  + x;
		int py = getBottom() + 3;
		if ( GameView.mapa.collisionMap.getPixel(px, py) < -2 )
			return false;

		return true;
	}

	public boolean canLeft() {
		int py = (getBottom() - y) /2  + y;
		int px = x - 3;
		
		if ( GameView.mapa.collisionMap.getPixel(px, py) < -2 )
			return false;

		return true;
	}

	public boolean canRight() {
		int py = (getBottom() - y) / 2 + y;
		int px = getRight() + 3;
		
		if ( GameView.mapa.collisionMap.getPixel(px, py) < -2 )
			return false;

		return true;
	}

	/*
	 * Return true if point is inside game object.
	 */
	public boolean isInside(int x, int y) {

		if (x < this.x) {
			return false;
		}

		if (x > this.x + this.width) {
			return false;
		}

		if (y < this.y) {
			return false;
		}

		if (y > this.y + this.height) {
			return false;
		}

		return true;
	}

	public void draw(Canvas c, Paint p) {
		p.setAlpha(200);
		c.drawRect(x, y, x + width, y + height, p);
	}
}
