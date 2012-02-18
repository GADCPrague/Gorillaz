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

		canvas.drawRect(0, 0, 20, 20, p);
	}

}
