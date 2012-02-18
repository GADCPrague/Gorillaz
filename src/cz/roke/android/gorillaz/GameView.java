package cz.roke.android.gorillaz;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class GameView extends View implements TimerUpdatable {

	private static final String TAG = "GameView";
	private GorillazActivity ga;
	private Bitmap b;
	
	public boolean up, down, left, right, fire;
	
	public Gorilka gorilka1 = new Gorilka(100, 100);
	
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
		
		b = BitmapFactory.decodeResource(context.getResources(), R.drawable.donk);
		
		setFocusable(true);
	
		if (ga.timer == null) {
			ga.timer = new Timer(30);
		}
		ga.timer.setAnimator(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint p = new Paint();
		p.setColor(Color.RED);

		Gorilka g1 = new Gorilka(88, 85, 100, 100);
			
		p.setColor(Color.RED);
		canvas.drawRect(g1.getX(), g1.getY(), g1.getRight(), g1.getBottom(), p);
		
		p.setColor(Color.GREEN);
		canvas.drawRect(gorilka1.getX(), gorilka1.getY(), gorilka1.getRight(), gorilka1.getBottom(), p);
		
		p.setColor(Color.YELLOW);
		if ( g1.isCollision(gorilka1) == true ) {
			canvas.drawText("KOLIZE !!!", 20, 20, p);
		}

		
		canvas.drawBitmap(b, 10, 10, p);
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
