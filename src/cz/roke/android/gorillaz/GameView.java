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
	private Bitmap popredi;
	
	public static Context context;

	public boolean up, down, left, right, fire;
	public boolean upB, downB, leftB, rightB, fireB;
	
	public static LinkedList<Koule> balls = new LinkedList<Koule>();

	public Gorilka gorilka1, gorilka2;
	public static Gorilka gorilkaArray[];
	
	
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
		popredi = BitmapFactory.decodeResource(context.getResources(), R.drawable.popredi);
		
		
		gorilka1 = new Gorilka(100, 100);
		gorilka2 = new Gorilka(200, 200);
		
		gorilkaArray = new Gorilka[2];
		gorilkaArray[0] = gorilka1;
		gorilkaArray[1] = gorilka2;
		
		
		for (int i = 0; i < 15; i ++) {
			GameView.balls.add(new Koule((int)(Math.random() * 400 + 40), (int)(Math.random() * 250 + 40)));
		}
		
		
		setFocusable(true);
		
		if (ga.timer == null) {
			ga.timer = new Timer(30);
		}
		ga.timer.setAnimator(this);
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
		
		canvas.drawBitmap(popredi, 0, 0, p);
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
