package cz.roke.android.gorillaz;

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

	private static final String TAG = "GameView";
	private GorillazActivity ga;
	private Bitmap b;
	
	public boolean up, down, left, right, fire;
	
	public Mapa mapa;
	
	public Gorilka gorilka1;
	
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
		
		
		mapa = new Mapa(context.getResources(), R.drawable.kolize);
		
		gorilka1 = new Gorilka(100, 100, context.getResources());
		
		setFocusable(true);
	
		if (ga.timer == null) {
			ga.timer = new Timer(30);
		}
		ga.timer.setAnimator(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		if (isInEditMode()) {
			return;
		}
		
		Paint p = new Paint();
		
		mapa.draw(canvas, p);
		gorilka1.draw(canvas, p);
		
		

	}

	@Override
	public void timerUpdate() {

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
		
		
		this.postInvalidate();
	}
	

}
