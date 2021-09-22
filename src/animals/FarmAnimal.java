package animals;

import gameObjects.Player;
import main.GameObject;
import resources.Sprite;

public class FarmAnimal extends GameObject {

	private int facingDirection = 0;
	private int walkSpeed = 2;
	private double walkCycleSpeed = 0.15;
	
	private int walkDirection;
	private int walkDist;
	
	protected Sprite walkUpSpr = getSprites ().animalSpriteChickenUp;
	protected Sprite walkDownSpr = getSprites ().animalSpriteChickenDown;
	protected Sprite walkLeftSpr = getSprites ().animalSpriteChickenLeft;
	protected Sprite walkRightSpr = getSprites ().animalSpriteChickenRight;
	protected Sprite sleepSpr = getSprites ().animalSpriteChickenSleep;
	protected Sprite loveSpr = getSprites ().animalSpriteChickenLove;
	
	public FarmAnimal () {
		getAnimationHandler ().setAnimationSpeed (0);
		setSprite (walkDownSpr);
	}
	
	public boolean walk (int direction, int dist) {
		
		if (walkDist == 0) {
			facingDirection = direction;
			walkDirection = direction;
			walkDist = dist;
			//Set sprite accordingly
			switch (direction) {
				case Player.DIRECTION_UP:
					setSprite (walkUpSpr);
					break;
				case Player.DIRECTION_DOWN:
					setSprite (walkDownSpr);
					break;
				case Player.DIRECTION_LEFT:
					setSprite (walkLeftSpr);
					break;
				case Player.DIRECTION_RIGHT:
					setSprite (walkRightSpr);
					break;
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void frameEvent () {
		
		//Walk (if walking)
		if (walkDist >= 0) {
			
			//Prevent skipping over destination
			int travelDist = walkSpeed;
			if (walkDist - walkSpeed < 0) {
				travelDist = walkDist;
			}
			walkDist -= travelDist;
			
			//Walk the specified distance
			switch (walkDirection) {
				case Player.DIRECTION_UP:
					setY (getY () - travelDist);
					break;
				case Player.DIRECTION_DOWN:
					setY (getY () + travelDist);
					break;
				case Player.DIRECTION_LEFT:
					setX (getX () - travelDist);
					break;
				case Player.DIRECTION_RIGHT:
					setX (getX () + travelDist);
					break;
			}
			
			//TODO stop the animal if it hits a wall
			
		}
		if (Math.random () < .002) {
			walk ((int)(Math.random () * 4), 48);
		}
	}
	
	@Override
	public void draw () {
		
		//Handle walking animation
		if (walkDist > 0 && this.getAnimationHandler ().getAnimationSpeed () == 0) {
			this.getAnimationHandler ().setAnimationSpeed (walkCycleSpeed);
		} else if (walkDist == 0 && this.getAnimationHandler ().getAnimationSpeed () != 0) {
			this.getAnimationHandler ().setAnimationSpeed (0);
			this.getAnimationHandler ().setFrame (0);
		}
		
		//Parent draw
		super.draw ();
			
	}
	
}
