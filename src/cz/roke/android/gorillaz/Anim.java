package cz.roke.android.gorillaz;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Anim {

	private int animCount = 3, animDelay = 5, animSize = 40;
	private int animType, animFrame, animProgress;

	public Anim(int count, int delay, int size) {
		animCount = count;
		animDelay = delay;
		animSize = size;

		animType = 0;
		animFrame = 0;
		animProgress = 0;
	}

	public void animate(int type) {
		animType = type;
		animProgress++;
		if (animProgress >= animDelay) {
			animProgress = 0;
			animFrame++;
			if (animFrame >= animCount) {
				animFrame = 0;
			}
		}
	}

	public void draw(Canvas canvas, Bitmap bitmap, int x, int y, Paint paint) {
		int gX = animFrame * animSize;
		int gY = animType * animSize;
		Rect src = new Rect(gX, gY, gX + animSize, gY + animSize);
		Rect dst = new Rect(x, y, x + animSize, y + animSize);
		canvas.drawBitmap(bitmap, src, dst, paint);
	}

}
