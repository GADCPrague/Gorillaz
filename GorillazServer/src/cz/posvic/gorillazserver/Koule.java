package cz.posvic.gorillazserver;


public class Koule extends GameObject {

	public final int WIDTH = 16;
	public final int HEIGHT = 16;

	public static final int RYCHLOST = 5;

	private int move;

	private Gorilka vlastnik;

	public boolean vystrelena;
	public boolean nabita;

	private Anim anim;

	public Koule(int x, int y) {
		setX(x);
		setY(y);
		setWidth(WIDTH);
		setHeight(HEIGHT);

		anim = new Anim(6, 1);
		nabita = false;
		vystrelena = false;
	}

	public void strel(Gorilka vlastnik) {
		this.vlastnik = vlastnik;
		x = (vlastnik.getRight() - vlastnik.x) / 2 + vlastnik.x;
		y = (vlastnik.getBottom() - vlastnik.y) / 2 + vlastnik.y;
		vystrelena = true;
		nabita = false;
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
			anim.animate(0);

			switch (move) {
				case GorillazActivity.UP: {
					int px = (getRight() - x) / 2 + x;
					int py = y - 1;

					if ( ServerSide.mapa.isKolizeAtPos(px, py) == true ) {
						zastavSe();
					} else
						y -= RYCHLOST;
					break;
				}
				
				case GorillazActivity.DOWN: {
					int px = (getRight() - x) / 2 + x;
					int py = getBottom() + 1;

					if ( ServerSide.mapa.isKolizeAtPos(px, py) == true ) {
						zastavSe();
					} else
						y += RYCHLOST;
					break;
				}
				
				case GorillazActivity.LEFT: {
					int py = (getBottom() - y) / 2 + y;
					int px = x - 1;

					if ( ServerSide.mapa.isKolizeAtPos(px, py) == true ) {
						zastavSe();
					} else
						x -= RYCHLOST;
					break;
				}
				
				case GorillazActivity.RIGHT: {
					int py = (getBottom() - y) / 2 + y;
					int px = getRight() + 1;

					if ( ServerSide.mapa.isKolizeAtPos(px, py) == true ) {
						zastavSe();
					} else
						x += RYCHLOST;
					break;
				}
			}
		}

		for (int i = 0; i < ServerSide.gorilkaArray.length; i++) {
			if (this.vystrelena == true && this.isCollision(ServerSide.gorilkaArray[i]) && ServerSide.gorilkaArray[i] != vlastnik) {
				zastavSe();
				ServerSide.gorilkaArray[i].respawn();
			}

			if (this.vystrelena == false && this.nabita == false && this.isCollision(ServerSide.gorilkaArray[i]) && ServerSide.gorilkaArray[i].nabitaKoule == null) {
				ServerSide.gorilkaArray[i].vemKouli(this);
			}
		}

	}

	public void zastavSe() {
		vystrelena = false;
		nabita = false;
		vlastnik = null;
	}

	// TODO
	public void preparedToSendToClient() {

		if (nabita == false) {
			// anim.draw(canvas, obrazek, x, y, paint);
		}

	}
}
