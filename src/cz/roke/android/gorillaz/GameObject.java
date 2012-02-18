package cz.roke.android.gorillaz;


public class GameObject {
	
	public static final int OX = 0;
	
	public static final int OY = 1;
	
	public static final int LH = 0;
	
	public static final int PH = 1;
	
	public static final int LD = 2;
	
	public static final int PD = 3;
	
	public static final int UP = 0;
	
	public static final int DOWN = 1;
	
	public static final int LEFT = 2;
	
	public static final int RIGHT = 3;
	
	protected int x;
	
	protected int y;
	
	protected int width;
	
	protected int height;
	
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
