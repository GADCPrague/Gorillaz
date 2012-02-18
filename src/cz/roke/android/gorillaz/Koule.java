package cz.roke.android.gorillaz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Koule extends GameObject {

	public final int WIDTH = 16;
	public final int HEIGHT = 16;

	private Paint paint;
	private int move;

	public Koule(int x, int y, int move) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);

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
				y--;
				break;
			case GorillazActivity.DOWN:
				y++;
				break;
			case GorillazActivity.LEFT:
				x--;
				break;
			case GorillazActivity.RIGHT:
				x++;
				break;
		}
	}

	public void draw(Canvas c) {

		if (paint == null) {
			paint = new Paint();
		}

		paint.setColor(Color.GREEN);
		c.drawRect(x, y, x + width, y + height, paint);
	}
}
