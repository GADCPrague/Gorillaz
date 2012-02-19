package cz.posvic.gorillazserver;

import java.awt.Canvas;
import java.awt.Paint;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.IOException;

import javax.annotation.Resources;
import javax.imageio.ImageIO;

public class Mapa {

	
	public BufferedImage collisionMap;
	public Raster raster;
	
	
	public Mapa() throws IOException {
		collisionMap = ImageIO.read( Mapa.class.getResourceAsStream("/kolize.png"));
		raster = collisionMap.getData();
	}
	
	
	public boolean isKolizeAtPos(int x, int y) {
	
		int barva[] = new int[3];
		raster.getPixel(x, y, barva);
		
		if (barva[0] == 1)
			return false;
		
		return true;
	}
	
}
