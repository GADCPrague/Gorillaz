package cz.posvic.gorillazserver;


public class Gorilka extends GameObject {

	private static final String TAG = "Gorilka";

	public static final int WIDTH = 40;

	public static final int HEIGHT = 40;

	public static final int MOVE = 2;

	public static final int RELOAD_TIME = 10;

	public int due;

	public int fireTime = 0;

	public Koule nabitaKoule = null;

	private Anim anim;

	public Gorilka(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);

		nabitaKoule = null;

		// TODO Na zacatku vpravo
		due = GorillazActivity.RIGHT;

		anim = new Anim(3, 5);
	}

	public void moveUp() {
		if (super.canUp() == false)
			return;

		anim.animate(2);

		setY(getY() - MOVE);
		due = GorillazActivity.UP;
	}

	public void moveDown() {
		if (super.canDown() == false)
			return;

		anim.animate(3);

		setY(getY() + MOVE);
		due = GorillazActivity.DOWN;
	}

	public void moveLeft() {
		if (super.canLeft() == false)
			return;

		anim.animate(1);

		setX(getX() - MOVE);
		due = GorillazActivity.LEFT;
	}

	public void moveRight() {
		if (super.canRight() == false)
			return;

		anim.animate(0);

		setX(getX() + MOVE);
		due = GorillazActivity.RIGHT;
	}

	public void fire() {
		if (nabitaKoule != null) {

			nabitaKoule.strel(this);
			nabitaKoule = null;
			fireTime = 0;
		}
	}

	// TODO
	public void preparedToSendToClient() {
		if (nabitaKoule == null) {
			// anim.draw(canvas, animBitmap, x, y, paint);
		} else {
			// anim.draw(canvas, animBitmapFull, x, y, paint);
		}

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
