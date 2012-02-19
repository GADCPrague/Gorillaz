package cz.roke.android.gorillaz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Anim {

	private int ANIM_COUNT = 3, ANIM_DELAY = 5, ANIM_SIZE = 40;
	private int animType, animFrame, animProgress;
	private Bitmap animBitmap;

	public Anim() {
		animType = 0;
		animFrame = 0;
		animProgress = 0;
		animBitmap = BitmapFactory.decodeResource(GameView.context.getResources(), R.drawable.gorillaz_animation);

	}

	public void animate(int type) {
		animType = type;
		animProgress++;
		if (animProgress >= ANIM_DELAY) {
			animProgress = 0;
			animFrame++;
			if (animFrame >= ANIM_COUNT) {
				animFrame = 0;
			}
		}
	}

	public void draw(Canvas canvas, int x, int y, Paint paint) {
		int gX = animFrame * ANIM_SIZE;
		int gY = animType * ANIM_SIZE;
		Rect src = new Rect(gX, gY, gX + ANIM_SIZE, gY + ANIM_SIZE);
		Rect dst = new Rect(x, y, x + ANIM_SIZE, y + ANIM_SIZE);
		canvas.drawBitmap(animBitmap, src, dst, paint);
	}

}
