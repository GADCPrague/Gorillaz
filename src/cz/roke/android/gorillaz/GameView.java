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
import android.view.View;

public class GameView extends View implements TimerUpdatable {

	public static Mapa mapa;
	
	private static final String TAG = "GameView";
	private GorillazActivity ga;
	private Bitmap palma;

	public boolean up, down, left, right, fire;
	public boolean upB, downB, leftB, rightB, fireB;
	
	private LinkedList<Koule> balls = new LinkedList<Koule>();
	public Gorilka gorilka1, gorilka2;
	
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
		
		
		mapa = new Mapa(context.getResources(), R.drawable.kolize, R.drawable.pozadi);
		palma = BitmapFactory.decodeResource(context.getResources(), R.drawable.palma);
		
		
		gorilka1 = new Gorilka(100, 100, context.getResources());
		gorilka2 = new Gorilka(200, 200, context.getResources());
		
		balls.add(new Koule(20, 20, GorillazActivity.RIGHT));
		
		setFocusable(true);
		
		if (ga.timer == null) {
			ga.timer = new Timer(30);
		}
		ga.timer.setAnimator(this);
	}

	public void fire() {
		balls.add(new Koule(gorilka1.x, gorilka1.y, gorilka1.due));
	}

	public void fireB() {
		balls.add(new Koule(gorilka2.x, gorilka2.y, gorilka2.due));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		
		if (isInEditMode()) {
			return;
		}
		
		Paint p = new Paint();
		
		mapa.draw(canvas, p);
		gorilka1.draw(canvas, p);
		gorilka2.draw(canvas, p);
		
		canvas.drawBitmap(palma, -50, 100, p);
		
		for (Koule ball : balls) {
			ball.draw(canvas);
		}
	}

	@Override
	public void timerUpdate() {

		// Player 1
		if (up == true )
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
		if (upB == true )
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
	

}
