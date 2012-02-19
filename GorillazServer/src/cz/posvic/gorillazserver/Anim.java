package cz.posvic.gorillazserver;

public class Anim {

	private int animCount = 3, animDelay = 5;
	private int animType, animFrame, animProgress;

	public Anim(int count, int delay) {
		animCount = count;
		animDelay = delay;

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

}
