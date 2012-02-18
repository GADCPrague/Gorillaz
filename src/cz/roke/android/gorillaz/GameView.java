package cz.roke.android.gorillaz;

import java.util.LinkedList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GameView extends View implements TimerUpdatable {

	public static Mapa mapa;

	private static final String TAG = "GameView";
	private GorillazActivity ga;
	private Bitmap palma;

	private Context context;

	public boolean up, down, left, right, fire;
	public boolean upB, downB, leftB, rightB, fireB;

	private LinkedList<Koule> balls = new LinkedList<Koule>();

	public Gorilka gorilka1, gorilka2;
	public static Gorilka gorilkaArray[];

	// Oblasti pro joystick
	private final int SI = 40;
	private final int OKRAJ = 320;
	
	// TODO Zjistit okraj obrazovky a pocitat od nej
	private GameObject buttonUpA = new GameObject(SI, OKRAJ - SI * 2, SI, SI);
	private GameObject buttonDownA = new GameObject(SI, OKRAJ - SI, SI, SI);
	private GameObject buttonLeftA = new GameObject(0, OKRAJ - SI, SI, SI);
	private GameObject buttonRightA = new GameObject(SI * 2, OKRAJ - SI, SI, SI);
	
	private GameObject buttonUpB = new GameObject(SI, 0, SI, SI);
	private GameObject buttonDownB = new GameObject(SI, SI, SI, SI);
	private GameObject buttonLeftB = new GameObject(0, 0, SI, SI);
	private GameObject buttonRightB = new GameObject(SI * 2, 0, SI, SI);

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	/*
	 * Inicialize game.
	 */
	private void init(Context context) {
		Log.d(TAG, "init");
		ga = (GorillazActivity) context;
		this.context = context;

		mapa = new Mapa(context.getResources(), R.drawable.kolize, R.drawable.pozadi);
		palma = BitmapFactory.decodeResource(context.getResources(), R.drawable.palma);

		gorilka1 = new Gorilka(100, 100, context.getResources());
		gorilka2 = new Gorilka(200, 200, context.getResources());

		gorilkaArray = new Gorilka[2];
		gorilkaArray[0] = gorilka1;
		gorilkaArray[1] = gorilka2;

		setFocusable(true);

		if (ga.timer == null) {
			ga.timer = new Timer(30);
		}
		ga.timer.setAnimator(this);
	}

	public void fire() {
		balls.add(new Koule(gorilka1.x, gorilka1.y, gorilka1.due, context.getResources(), gorilka1));
	}

	public void fireB() {
		balls.add(new Koule(gorilka2.x, gorilka2.y, gorilka2.due, context.getResources(), gorilka2));
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// Log.d(TAG, getWidth() + "x" + getHeight());
		if (isInEditMode()) {
			return;
		}

		Paint p = new Paint();

		mapa.draw(canvas, p);
		gorilka1.draw(canvas, p);
		gorilka2.draw(canvas, p);

		for (Koule ball : balls) {
			ball.draw(canvas, p);
		}

		canvas.drawBitmap(palma, -50, 150, p);
		
		p.setColor(Color.RED);
		buttonUpB.draw(canvas, p);
		//buttonDownB.draw(canvas, p);
	}

	@Override
	public void timerUpdate() {

		// Player 1
		if (up == true)
			gorilka1.moveUp();
		else if (down == true)
			gorilka1.moveDown();
		else if (left == true)
			gorilka1.moveLeft();
		else if (right == true)
			gorilka1.moveRight();

		if (fire == true)
			gorilka1.fire();

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

		this.postInvalidate();
	}

	// ------------------------------------------------------------------------

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE", "POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
		StringBuilder sb = new StringBuilder();
		int action = event.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		sb.append("event ACTION_").append(names[actionCode]);

		if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP) {
			sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
			sb.append(")");
		}

		up = false;
		down = false;
		left = false;
		right = false;
		upB = false;
		downB = false;
		leftB = false;
		rightB = false;

		sb.append("[");
		for (int i = 0; i < event.getPointerCount(); i++) {
			int x = (int) event.getX(i);
			int y = (int) event.getY(i);

			sb.append("#").append(i);
			sb.append("(pid ").append(event.getPointerId(i));
			sb.append(")=").append(x);
			sb.append(",").append(y);
			if (i + 1 < event.getPointerCount())
				sb.append(";");

			// Pri zvednuti lib. kurzoru se vse zastavi
			if (actionCode == MotionEvent.ACTION_UP || actionCode == MotionEvent.ACTION_POINTER_UP) {
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
						}
		}

		sb.append("]");
		Log.d(TAG, sb.toString());
		return true;
	}
}
