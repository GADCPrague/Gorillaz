package cz.roke.android.gorillaz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;

public class Gorilka extends GameObject {

	private static final String TAG = "Gorilka";

	public static final int WIDTH = 40;

	public static final int HEIGHT = 40;

	public static final int MOVE = 2;

	public static final int RELOAD_TIME = 10;

	public int due;

	public static Bitmap picUp = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.up);
	public static Bitmap picDown = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.down);
	public static Bitmap picLeft = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.left);
	public static Bitmap picRight = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.right);

	public static Bitmap picUpFull = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.up_full);
	public static Bitmap picDownFull = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.down_full);
	public static Bitmap picLeftFull = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.left_full);
	public static Bitmap picRightFull = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.right_full);

	public Bitmap actualPic;

	public int fireTime = 0;

	public Koule nabitaKoule = null;

	private Anim anim;
	private static Bitmap animBitmap = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.gorillaz_animation);
	private static Bitmap animBitmapFull = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.gorillaz_straight_animation);

	public Gorilka(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);

		nabitaKoule = null;

		// TODO Na zacatku vpravo
		due = GorillazActivity.RIGHT;

		float density = GameView.context.getResources().getDisplayMetrics().density;
		anim = new Anim(3, 5, (int) (40 * density));

		

		actualPic = picRight;
	}

	public void moveUp() {
		if (super.canUp() == false)
			return;

		anim.animate(2);

		setY(getY() - MOVE);
		due = GorillazActivity.UP;
		if (nabitaKoule == null)
			actualPic = picUp;
		else
			actualPic = picUpFull;
	}

	public void moveDown() {
		if (super.canDown() == false)
			return;

		anim.animate(3);

		setY(getY() + MOVE);
		due = GorillazActivity.DOWN;
		if (nabitaKoule == null)
			actualPic = picDown;
		else
			actualPic = picDownFull;
	}

	public void moveLeft() {
		if (super.canLeft() == false)
			return;

		anim.animate(1);

		setX(getX() - MOVE);
		due = GorillazActivity.LEFT;
		if (nabitaKoule == null)
			actualPic = picLeft;
		else
			actualPic = picLeftFull;
	}

	public void moveRight() {
		if (super.canRight() == false)
			return;

		anim.animate(0);

		setX(getX() + MOVE);
		due = GorillazActivity.RIGHT;
		if (nabitaKoule == null)
			actualPic = picRight;
		else
			actualPic = picRightFull;
	}

	public void fire() {
		Log.d(TAG, "Fire!");

		if (nabitaKoule != null) {

			nabitaKoule.strel(this);
			nabitaKoule = null;
			fireTime = 0;

			if (actualPic == picUpFull)
				actualPic = picUp;
			if (actualPic == picDownFull)
				actualPic = picDown;
			if (actualPic == picLeftFull)
				actualPic = picLeft;
			if (actualPic == picRightFull)
				actualPic = picRight;

		}
	}

	public void draw(Canvas canvas, Paint paint) {
		if (nabitaKoule == null) {
			anim.draw(canvas, animBitmap, x, y, paint);
		} else {
			anim.draw(canvas, animBitmapFull, x, y, paint);
		}
		
		Log.i("", "anim frame: " + anim);

		fireTime++;
		if (fireTime >= RELOAD_TIME) {
			fireTime = 0;
		}
	}

	public void vemKouli(Koule koule) {
		nabitaKoule = koule;
		koule.nabita = true;
		koule.vystrelena = false;
	}

	// TODO Dodelat
	public void respawn() {
		x = (int) (Math.random() * 430 + 40);
		y = (int) (Math.random() * 270 + 40);
	}

}
