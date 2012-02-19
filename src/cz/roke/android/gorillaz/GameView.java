package cz.roke.android.gorillaz;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements TimerUpdatable {

	public static Mapa mapa;

	private static final String TAG = "GameView";
	private GorillazActivity ga;

	private Bitmap logo;

	private int gameTime = 0;

	private Bitmap popredi;

	public static Context context;

	public boolean up, down, left, right, fire;
	public boolean upB, downB, leftB, rightB, fireB;

	public static LinkedList<Koule> balls = new LinkedList<Koule>();

	public Gorilka gorilka1, gorilka2;
	public static Gorilka gorilkaArray[];

	// TODO Zjistit okraj obrazovky a pocitat od nej
	private GameObject buttonUpA;
	private GameObject buttonDownA;
	private GameObject buttonLeftA;
	private GameObject buttonRightA;
	private GameObject buttonFireA;

	private GameObject buttonUpB;
	private GameObject buttonDownB;
	private GameObject buttonLeftB;
	private GameObject buttonRightB;
	private GameObject buttonFireB;

	public Client client = new Client();

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ga = (GorillazActivity) context;
		this.context = context;
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ga = (GorillazActivity) context;
		this.context = context;
	}

	private int width2, height2;

	@Override
	public void onSizeChanged(int w, int h, int oldW, int oldH) {
		if (((oldW == 0 && oldH == 0) && height2 == 0)) {
			width2 = w;
			height2 = h;
			init();
		}
	}

	/*
	 * Inicialize game.
	 */
	private void init() {
		Log.d(TAG, "init");

		float density = GameView.context.getResources().getDisplayMetrics().density;
		int SI = (int) (40 * density);

		// Create areas for joystick
		buttonUpA = new GameObject(SI, height2 - SI * 3, SI, SI);
		buttonDownA = new GameObject(SI, height2 - SI, SI, SI);
		buttonLeftA = new GameObject(0, height2 - SI * 2, SI, SI);
		buttonRightA = new GameObject(SI * 2, height2 - SI * 2, SI, SI);
		buttonFireA = new GameObject(width2 - SI, height2 - SI, SI, SI);
		buttonUpB = new GameObject(width2 - SI * 2, 0, SI, SI);
		buttonDownB = new GameObject(width2 - SI * 2, SI * 2, SI, SI);
		buttonLeftB = new GameObject(width2 - SI * 3, SI, SI, SI);
		buttonRightB = new GameObject(width2 - SI, SI, SI, SI);
		buttonFireB = new GameObject(0, 0, SI, SI);

		mapa = new Mapa(context.getResources(), R.drawable.kolize,
				R.drawable.pozadi);
		popredi = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.popredi);
		logo = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.logo);

		gorilka1 = new Gorilka(100, 100);
		gorilka2 = new Gorilka(200, 200);

		gorilkaArray = new Gorilka[2];
		gorilkaArray[0] = gorilka1;
		gorilkaArray[1] = gorilka2;

		for (int i = 0; i < 10; i++) {
			GameView.balls.add(new Koule((int) (Math.random() * 400 + 40),
					(int) (Math.random() * 250 + 40)));
		}

		setFocusable(true);

		if (ga.timer == null) {
			ga.timer = new Timer(30);
		}
		ga.timer.setAnimator(this);

		// TODO Zakomentovat, pokud nechci server
		// client.connect();
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// Log.d(TAG, getWidth() + "x" + getHeight());
		if (isInEditMode()) {
			return;
		}

		Paint p = new Paint();

		mapa.draw(canvas, p);

		for (Koule ball : balls) {
			ball.draw(canvas, p);
		}

		gorilka1.draw(canvas, p);
		gorilka2.draw(canvas, p);

		canvas.drawBitmap(popredi, 0, 0, p);

		// TODO
		buttonUpA.draw(canvas, p);
		buttonDownA.draw(canvas, p);
		buttonLeftA.draw(canvas, p);
		buttonRightA.draw(canvas, p);
		buttonFireA.draw(canvas, p);

		buttonUpB.draw(canvas, p);
		buttonDownB.draw(canvas, p);
		buttonLeftB.draw(canvas, p);
		buttonRightB.draw(canvas, p);
		buttonFireB.draw(canvas, p);

		if (gameTime < 100) {
			// TODO canvas.drawBitmap(logo, 0, 0, p);
		}

	}

	@Override
	public void timerUpdate() {

		gameTime++;

		if (client != null && client.isConnect() == true) {
			client.netSend.up = up;
			client.netSend.down = down;
			client.netSend.left = left;
			client.netSend.right = right;
			client.netSend.fire = fire;

			client.send();
		}

		// Player 1
		if (up == true) {
			gorilka1.moveUp();
		} else if (down == true) {
			gorilka1.moveDown();
		} else if (left == true) {
			gorilka1.moveLeft();
		} else if (right == true) {
			gorilka1.moveRight();
		}

		if (fire == true) {
			gorilka1.fire();
			client.netSend.fire = true;
		}

		// Player 2
		if (upB == true)
			gorilka2.moveUp();
		else if (downB == true)
			gorilka2.moveDown();
		else if (leftB == true)
			gorilka2.moveLeft();
		else if (rightB == true)
			gorilka2.moveRight();

		if (fireB == true)
			gorilka2.fire();

		for (Koule ball : balls) {
			ball.update();
		}

		if (gameTime % 50 == 0 && client.isConnect() == false) {
			client.connect();
		}

		this.postInvalidate();
	}

	// ------------------------------------------------------------------------

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;

		up = false;
		down = false;
		left = false;
		right = false;
		upB = false;
		downB = false;
		leftB = false;
		rightB = false;

		for (int i = 0; i < event.getPointerCount(); i++) {
			int x = (int) event.getX(i);
			int y = (int) event.getY(i);

			// Pri zvednuti lib. kurzoru se vse zastavi
			if (actionCode == MotionEvent.ACTION_UP
					|| actionCode == MotionEvent.ACTION_POINTER_UP) {
				if (buttonUpA.isInside(x, y)) {
					up = false;
					continue;
				} else

				if (buttonDownA.isInside(x, y)) {
					down = false;
					continue;
				} else

				if (buttonLeftA.isInside(x, y)) {
					left = false;
					continue;
				} else

				if (buttonRightA.isInside(x, y)) {
					right = false;
					continue;
				}

				if (buttonUpB.isInside(x, y)) {
					upB = false;
					continue;
				}

				if (buttonDownB.isInside(x, y)) {
					downB = false;
					continue;
				}

				if (buttonLeftB.isInside(x, y)) {
					leftB = false;
					continue;
				}

				if (buttonRightB.isInside(x, y)) {
					rightB = false;
					continue;
				}
			}

			if (buttonUpA.isInside(x, y)) {
				up = true;
			} else

			if (buttonDownA.isInside(x, y)) {
				down = true;
			} else

			if (buttonLeftA.isInside(x, y)) {
				left = true;
			} else

			if (buttonRightA.isInside(x, y)) {
				right = true;
			} else

			if (buttonFireA.isInside(x, y)) {
				gorilka1.fire();
			}

			if (buttonUpB.isInside(x, y)) {
				upB = true;
			}

			if (buttonDownB.isInside(x, y)) {
				downB = true;
			}

			if (buttonLeftB.isInside(x, y)) {
				leftB = true;
			}

			if (buttonRightB.isInside(x, y)) {
				rightB = true;
			} else

			if (buttonFireB.isInside(x, y)) {
				gorilka2.fire();
			}
		}

		return true;
	}
}
