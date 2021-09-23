package animals;

import java.awt.Point;
import java.util.LinkedList;

import ai.Pathfinder;
import gameObjects.Player;
import main.GameObject;
import resources.Sprite;

public class FarmAnimal extends GameObject {

	private int facingDirection = 0;
	private int walkSpeed = 2;
	private double walkCycleSpeed = 0.15;
	
	private int walkDirection;
	private int walkDist;
	
	private LinkedList<Point> walkPts;
	
	protected Sprite walkUpSpr = getSprites ().animalSpriteChickenUp;
	protected Sprite walkDownSpr = getSprites ().animalSpriteChickenDown;
	protected Sprite walkLeftSpr = getSprites ().animalSpriteChickenLeft;
	protected Sprite walkRightSpr = getSprites ().animalSpriteChickenRight;
	protected Sprite sleepSpr = getSprites ().animalSpriteChickenSleep;
	protected Sprite loveSpr = getSprites ().animalSpriteChickenLove;
	
	private int minMoveTime = 100;
	private int maxMoveTime = 700;
	private int moveTimer = 200;
	
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
	
	public void walkTo (int x, int y) {
		//TODO
	}
	
	@Override
	public void frameEvent () {
		
		//Walk (if walking)
		if (walkDist > 0) {
			
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
			
		} else {
			
			//Start walking randomly if applicable
			if (moveTimer == 0) {
				
				//Reset the move timer
				moveTimer = minMoveTime + (int)(Math.random () * (maxMoveTime - minMoveTime));
				
				//Pick a random direction and distance
				int dist = (int)(Math.random () * 2) + 1;
				int startX = (int)(getX () / 16);
				int startY = (int)(getY () / 16);
				int endX = startX;
				int endY = startY;
				if (Math.random () < .5) {
					if (Math.random () < .5) {
						endX -= dist;
					} else {
						endX += dist;
					}
				} else {
					if (Math.random () < .5) {
						endY -= dist;
					} else {
						endY += dist;
					}
				}
				
				//Check to see if the path is walkable; travel if it is
				LinkedList<Point> path = Pathfinder.findPath (startX - 4, startY - 4, 9, 9, startX, startY, endX, endY);
				if (path != null && path.size () == 2) {
					if (endY - startY != 0) {
						if (endY - startY < 0) {
							walk (Player.DIRECTION_UP, (startY - endY) * 16);
						} else {
							walk (Player.DIRECTION_DOWN, (endY - startY) * 16);
						}
					} else {
					if (endX - startX < 0) {
							walk (Player.DIRECTION_LEFT, (startX - endX) * 16);
						} else {
							walk (Player.DIRECTION_RIGHT, (endX - startX) * 16);
						}
					}
				}
			} else {
				moveTimer--;
			}
			
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
