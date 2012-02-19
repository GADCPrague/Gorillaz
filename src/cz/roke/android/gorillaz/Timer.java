package cz.roke.android.gorillaz;

import android.util.Log;

public class Timer implements Runnable {

	private int milisecond;
	private boolean alive = true;
	private Thread th;
	private TimerUpdatable animator = null;

	public Timer(int milisecond) {
		super();

		this.milisecond = milisecond;
	}

	@Override
	public void run() {

		try {
			while (alive) {
				
				if (animator != null) 
					animator.timerUpdate();
				
				Thread.sleep(milisecond);
			}
		} catch (InterruptedException e) {

		}
	}

	public void start() {
		alive = true;
		th = new Thread(this);
		th.start();
	}

	public void halt() {
		alive = false;
	}

	public void setAnimator(TimerUpdatable animator) {
		this.animator = animator;
	}
}
