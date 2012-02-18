package cz.roke.android.gorillaz;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Mapa {

	public Bitmap collisionMap;
	public Bitmap pozadi;
	
	public Mapa(Resources resources, int collisionMapId, int pozadiId) {
		collisionMap = BitmapFactory.decodeResource(resources, collisionMapId);
		pozadi = BitmapFactory.decodeResource(resources, pozadiId);
		
	}
	
	public void draw(Canvas canvas, Paint paint) {
		canvas.drawBitmap(collisionMap, 0, 0, paint);
	}
	
}
