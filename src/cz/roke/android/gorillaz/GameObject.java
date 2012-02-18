package cz.roke.android.gorillaz;


public class GameObject {
	
	public final int OX = 0;
	
	public final int OY = 1;
	
	public final int LH = 0;
	
	public final int PH = 1;
	
	public final int LD = 2;
	
	public final int PD = 3;
	
	private int x;
	
	private int y;
	
	private int width;
	
	private int height;
	
	public int kolizniBody[][];
	
	
	public int getRight() {
		return x + width;
	}
	
	public int getBottom() {
		return y + height;
	}
	
	public boolean isCollision(GameObject o) {
		if ( isInObject(o) == true )
			return true;
		
		if ( o.isInObject(this) == true) 
			return true;
		
		return false;
	}
	
	public boolean isInObject(GameObject o) {
		
		int bod[][] = new int[4][2];
		
		bod[LH][OX] = x;
		bod[LH][OY] = y;
	
		bod[PH][OX] = getRight();
		bod[PH][OY] = y;
		
		bod[LD][OX] = x;
		bod[LD][OY] = getBottom();
		
		bod[PD][OX] = getRight();
		bod[PD][OY] = getBottom();
		
		for (int i = 0; i < 4; i ++) {
			if ( bod[i][OX] >= o.x && bod[i][OX] < o.getRight() && bod[i][OY] >= o.y && bod[i][OY] <= o.getBottom() )
				return true;
		}
		
		return false;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
