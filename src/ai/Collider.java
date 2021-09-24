package ai;

import gameObjects.Player;
import main.GameObject;
import main.Hitbox;

public class Collider extends GameObject {

	public Collider (int width, int height) {
		declare (0, 0);
		createHitbox (0, 0, width, height);
	}
	
	public boolean checkCollision (int x, int y) {
		setX (x);
		setY (y);
		return collidingWithBarrier ();
	}
	
	protected boolean collidingWithBarrier () {
		return Player.collidingWithBarrier (this);
	}
}
