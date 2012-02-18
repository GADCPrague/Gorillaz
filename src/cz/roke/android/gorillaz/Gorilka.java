package cz.roke.android.gorillaz;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Gorilka extends GameObject {
	
	public static final int WIDTH = 40;
	
	public static final int HEIGHT = 40;
	
	public static final int MOVE = 2;
	
	public int due;
	
	public Bitmap picUp;
	public Bitmap picDown;
	public Bitmap picLeft;
	public Bitmap picRight;

	public Bitmap actualPic;
	
	public Gorilka(int x, int y, Resources resources) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		// TODO Na zacatku vpravu
		due = GorillazActivity.RIGHT;

		picUp = BitmapFactory.decodeResource(resources, R.drawable.up);
		picDown = BitmapFactory.decodeResource(resources, R.drawable.down);
		picLeft = BitmapFactory.decodeResource(resources, R.drawable.left);
		picRight = BitmapFactory.decodeResource(resources, R.drawable.right);
		
		actualPic = picRight;
		
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
		actualPic = picUp;
	}
	
	public void moveDown() {
		setY(getY() + MOVE);
		due = GorillazActivity.DOWN;
		actualPic = picDown;
	}
	
	public void moveLeft() {
		setX(getX() - MOVE);
		due = GorillazActivity.LEFT;
		actualPic = picLeft;
	}
	
	public void moveRight() {
		setX(getX() + MOVE);
		due = GorillazActivity.RIGHT;
		actualPic = picRight;
	}
	
	public void fire() {
	
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(actualPic, getX(), getY(), paint);
	}
	
}
