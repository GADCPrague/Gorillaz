package cz.roke.android.gorillaz;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class GameView extends View {

	private static final String TAG = "GameView";
	private Activity ga;
	
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
		ga = (Activity) context;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.RED);

		Gorilka g2 = new Gorilka(90, 90);
		Gorilka g1 = new Gorilka(88, 85, 100, 100);
			
		p.setColor(Color.RED);
		canvas.drawRect(g1.getX(), g1.getY(), g1.getRight(), g1.getBottom(), p);
		
		p.setColor(Color.GREEN);
		canvas.drawRect(g2.getX(), g2.getY(), g2.getRight(), g2.getBottom(), p);
		
		p.setColor(Color.YELLOW);
		if ( g1.isCollision(g2) == true ) {
			canvas.drawText("KOLIZE !!!", 20, 20, p);
		}

		
	}

}
