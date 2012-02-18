package cz.roke.android.gorillaz;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class GorillazActivity extends Activity {

	private static final String TAG = GorillazActivity.class.getName();

	public static final int UP = 19;
	public static final int DOWN = 20;
	public static final int LEFT = 21;
	public static final int RIGHT = 22;
	public static final int FIRE = 62;

	public Timer timer = null;

	public Gorilka gorilka1 = new Gorilka(100, 100);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);

		debugStartGame();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		Log.i("", "key :" + keyCode);

		if (keyCode == UP)
			gorilka1.moveUp();
		else if (keyCode == DOWN)
			gorilka1.moveDown();
		else if (keyCode == LEFT)
			gorilka1.moveLeft();
		else if (keyCode == RIGHT)
			gorilka1.moveRight();

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		return super.onKeyUp(keyCode, event);
	}

	private void debugStartGame() {

		// TODO Rovnou do hry
		setContentView(R.layout.game);

		// timer = new Timer(50);

		// this.set

		Button butUp = (Button) findViewById(R.id.butUp);
		butUp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					Toast.makeText(v.getContext(), "Pressed up", Toast.LENGTH_SHORT).show();
				} else

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					
				}
				return false;
			}
		});

		Button butDown = (Button) findViewById(R.id.butDown);
		butDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(v.getContext(), "Down", Toast.LENGTH_SHORT).show();
			}

		});
	}

	@Override
	protected void onStart() {
		if (timer == null) {
			timer = new Timer(30);
		}
		super.onStart();

		timer.start();
	}

	@Override
	protected void onStop() {
		timer.halt();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		timer.halt();
		super.onDestroy();
	}
}
