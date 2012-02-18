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

	private Gorilka vlastnik;
	
	public boolean vystrelena;
	public boolean nabita;

	public Koule(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		
		this.vlastnik = vlastnik;

		obrazek = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.orech);

		nabita = false;
		vystrelena = false;
	}
	
	public void strel(Gorilka vlastnik) {
		this.vlastnik = vlastnik;
		vystrelena = true;
		move = vlastnik.due;
	}

	public Koule(int x, int y, int width, int height) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
	}

	public void update() {
		if (vystrelena == true) {

			switch (move) {
			case GorillazActivity.UP: {
				int px = (getRight() - x) / 2 + x;
				int py = y - 3;
				if (GameView.mapa.collisionMap.getPixel(px, py) < -5) {

				} else
					y -= RYCHLOST;
				break;
			}
			case GorillazActivity.DOWN: {
				int px = (getRight() - x) / 2 + x;
				int py = getBottom() + 3;
				if (GameView.mapa.collisionMap.getPixel(px, py) < -5) {

				} else
					y += RYCHLOST;
				break;
			}
			case GorillazActivity.LEFT: {
				int py = getBottom() - y / 2 + y;
				int px = x - 3;

				if (GameView.mapa.collisionMap.getPixel(px, py) < -5) {

				} else
					x -= RYCHLOST;
				break;
			}
			case GorillazActivity.RIGHT: {
				int py = (getBottom() - y) / 2 + y;
				int px = getRight() + 3;

				if (GameView.mapa.collisionMap.getPixel(px, py) < -5) {
				} else
					x += RYCHLOST;
				break;
			}
			}
		}
		
		Gorilka gorilky[] = GameView.gorilkaArray;
		
		for (int i = 0; i < GameView.gorilkaArray.length; i++) {
			if (this.vystrelena == true && this.isCollision(GameView.gorilkaArray[i]) && gorilky[i] != vlastnik ) {
				gorilky[i].respawn();
				vystrelena = true;
			}
			
			if (this.vystrelena == false && this.nabita == false && this.isCollision(gorilky[i]) && gorilky[i].nabitaKoule == null) {
				gorilky[i].vemKouli(this);
			}
		}
	}

	public void draw(Canvas canvas, Paint paint) {

		if (paint == null) {
			paint = new Paint();
		}

		if (nabita == false)
			canvas.drawBitmap(obrazek, x, y, paint);

	}
}
