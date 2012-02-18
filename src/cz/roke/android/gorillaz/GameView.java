package cz.roke.android.gorillaz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View implements TimerUpdatable {

	private static final String TAG = "GameView";
	private GorillazActivity ga;
	
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
		p.setColor(Color.RED);

		Gorilka g1 = new Gorilka(88, 85, 100, 100);
			
		p.setColor(Color.RED);
		canvas.drawRect(g1.getX(), g1.getY(), g1.getRight(), g1.getBottom(), p);
		
		p.setColor(Color.GREEN);
		canvas.drawRect(ga.gorilka1.getX(), ga.gorilka1.getY(), ga.gorilka1.getRight(), ga.gorilka1.getBottom(), p);
		
		p.setColor(Color.YELLOW);
		if ( g1.isCollision(ga.gorilka1) == true ) {
			canvas.drawText("KOLIZE !!!", 20, 20, p);
		}

		
	}

	@Override
	public void timerUpdate() {

		this.postInvalidate();
	}

}
