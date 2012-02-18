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

public class GorillazActivity extends Activity {

	private static final String TAG = "GorillazActivity";
	
	public static final int UP = 19;
	public static final int DOWN = 20;
	public static final int LEFT = 21;
	public static final int RIGHT = 22;
	public static final int FIRE = 62;

	public Timer timer = null;

	GameView gameView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.main);

		debugStartGame();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("", "key down :" + keyCode);

		if (keyCode == UP)
			gameView.up = true;
		else if (keyCode == DOWN)
			gameView.down = true;
		else if (keyCode == LEFT)
			gameView.left = true;
		else if (keyCode == RIGHT)
			gameView.right = true;
		else if (keyCode == FIRE)
			gameView.fire = true;

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.i("", "key up :" + keyCode);

		if (keyCode == UP)
			gameView.up = false;
		else if (keyCode == DOWN)
			gameView.down = false;
		else if (keyCode == LEFT)
			gameView.left = false;
		else if (keyCode == RIGHT)
			gameView.right = false;
		else if (keyCode == FIRE)
			gameView.fire = false;

		return super.onKeyUp(keyCode, event);
	}

	private void debugStartGame() {

		// TODO Rovnou do hry
		setContentView(R.layout.game);

		// timer = new Timer(50);

		// this.set

		setJoystick();
		setJoystick2();
	}

	private void setJoystick() {
		Log.d(TAG, "Setting joystick...");

		Button butUp = (Button) findViewById(R.id.butUp);
		butUp.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.up = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.up = false;
				}

				return false;
			}
		});

		Button butDown = (Button) findViewById(R.id.butDown);
		butDown.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.down = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.down = false;
				}

				return false;
			}
		});

		Button butLeft = (Button) findViewById(R.id.butLeft);
		butLeft.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.left = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.left = false;
				}

				return false;
			}
		});

		Button butRight = (Button) findViewById(R.id.butRight);
		butRight.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.right = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.right = false;
				}

				return false;
			}
		});

		Button butFire = (Button) findViewById(R.id.butFire);
		butFire.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameView.fire();
			}
		});

		Log.d(TAG, "Joystick is ready");
	}
	
	private void setJoystick2() {
		Log.d(TAG, "Setting joystick #2...");

		Button butUpB = (Button) findViewById(R.id.butUpB);
		butUpB.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.upB = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.upB = false;
				}

				return false;
			}
		});

		Button butDownB = (Button) findViewById(R.id.butDownB);
		butDownB.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.downB = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.downB = false;
				}

				return false;
			}
		});

		Button butLeftB = (Button) findViewById(R.id.butLeftB);
		butLeftB.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.leftB = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.leftB = false;
				}

				return false;
			}
		});

		Button butRightB = (Button) findViewById(R.id.butRightB);
		butRightB.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					gameView.rightB = true;
				} else

				if (event.getAction() == MotionEvent.ACTION_UP) {
					gameView.rightB = false;
				}

				return false;
			}
		});

		Button butFireB = (Button) findViewById(R.id.butFireB);
		butFireB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				gameView.fireB();
			}
		});

		Log.d(TAG, "Joystick #2 is ready");
	}

	@Override
	protected void onStart() {
		super.onStart();
		gameView = (GameView) findViewById(R.id.GameView);

		if (timer == null) {
			timer = new Timer(30);
		}
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
