package cz.roke.android.gorillaz;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Koule extends GameObject {

	public final int WIDTH = 16;
	public final int HEIGHT = 16;

	public static final int RYCHLOST = 5;
	
	private Paint paint;
	private int move;
	
	private Bitmap obrazek;

	public Koule(int x, int y, int move, Resources resources) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);

		obrazek = BitmapFactory.decodeResource(resources, R.drawable.orech);
		this.move = move;
	}

	public Koule(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public void update() {
		switch (move) {
			case GorillazActivity.UP:
				y-= RYCHLOST;
				break;
			case GorillazActivity.DOWN:
				y+= RYCHLOST;
				break;
			case GorillazActivity.LEFT:
				x-= RYCHLOST;
				break;
			case GorillazActivity.RIGHT:
				x+= RYCHLOST;
				break;
		}
	}

	public void draw(Canvas canvas, Paint paint) {

		if (paint == null) {
			paint = new Paint();
		}

		canvas.drawBitmap(obrazek, x, y, paint);

	}
}
