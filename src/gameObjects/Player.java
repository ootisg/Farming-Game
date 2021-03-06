package gameObjects;

import java.util.ArrayList;

import crops.GrowingPotato;
import gui.InteractBubble;
import json.JSONObject;
import main.GameObject;
import main.MainLoop;
import projectiles.Projectile;
import resources.Sprite;
import util.Vector2D;
import visualEffects.Bubble;

public class Player extends GameObject implements Damageable {
	
	private double health = 0;
	private double maxHealth = 0;
	private int direction = 0;
	private double speed = 3;
	private int invulTime = 0;
	private int invulLength = 10;
	private InteractBubble bubble;
	
	private boolean noclip = false;
	private Sprite armSprite;
	
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_LEFT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_RIGHT = 3;
	
	public static final double[] RADIAN_DIRECTION_MAP = new double[] {Projectile.DIRECTION_UP, Projectile.DIRECTION_LEFT, Projectile.DIRECTION_DOWN, Projectile.DIRECTION_RIGHT};
	public static final Vector2D[] VECTOR_DIRECTION_MAP = new Vector2D[] {
			Projectile.VEC_DIRECTION_UP,
			Projectile.VEC_DIRECTION_LEFT,
			Projectile.VEC_DIRECTION_DOWN,
			Projectile.VEC_DIRECTION_RIGHT
	};
	public Player () {
		this.setSprite (getSprites ().playerIdle);
		this.armSprite = getSprites ().playerArmsIdle;
		this.getAnimationHandler ().setAnimationSpeed (0);
		this.createHitbox (4, 1, 8, 14);
		this.setVariantAttribute ("layer", "0");
		bubble = new InteractBubble (32);
		setMaxHealth (50);
		setHealth (50);
		setPriority (-1);
		setPersistent (true);
	}
	@Override
	public void frameEvent () {
		if (mouseClicked ()) {
			int clickX = (int)(getMouseX () / 16);
			int clickY = (int)(getMouseY () / 16);
			short tile = getRoom ().getTileId (clickX, clickY, 0);
			System.out.println ("TILE ID: " + tile);
		}
		doGraphicsTick ();
		bubble.setCenter (getCenterX (), getCenterY ());
		if (invulTime != 0) {
			invulTime --;
		}
		if (keyCheck ('W')) {
			direction = DIRECTION_UP;
			setY (getY () - speed);
			if (!noclip && collidingWithBarrier (this)) {
				this.backstepY ();
			}
		}
		if (keyCheck ('A')) {
			direction = DIRECTION_LEFT;
			setX (getX () - speed);
			if (!noclip && collidingWithBarrier (this)) {
				this.backstepX ();
			}
		}
		if (keyCheck ('S')) {
			direction = DIRECTION_DOWN;
			setY (getY () + speed);
			if (!noclip && collidingWithBarrier (this)) {
				this.backstepY ();
			}
		}
		if (keyCheck ('D')) {
			direction = DIRECTION_RIGHT;
			setX (getX () + speed);
			if (!noclip && collidingWithBarrier (this)) {
				this.backstepX ();
			}
		}
		if (keyPressed (';')) {
			getSave ().writeToFile ();
		}
		if (keyCheck ('W') || keyCheck ('A') || keyCheck ('S') || keyCheck ('D')) {
			if (this.getSprite () != getSprites ().playerWalkSprites [direction]) {
				this.setSprite (getSprites ().playerWalkSprites [direction]);
				this.armSprite = getSprites ().playerArmSprites [direction];
			}
			this.getAnimationHandler ().setAnimationSpeed (.25);
		} else {
			this.getAnimationHandler ().setAnimationSpeed (0);
			this.getAnimationHandler ().setFrame (1);
		}
		double x = this.getX ();
		double y = this.getY ();
		int viewX = getRoom ().getViewX ();
		int viewY = getRoom ().getViewY ();
		int viewportWidth = MainLoop.getWindow ().getResolution ()[0];
		int viewportHeight = MainLoop.getWindow ().getResolution ()[1];
		double scrollSection = .25;
		int leftBound = (int)(viewportWidth * scrollSection);
		int rightBound = viewportWidth - leftBound;
		int topBound = (int)(viewportHeight * scrollSection);
		int bottomBound = viewportHeight - topBound;
		if (y - viewY >= bottomBound && y - bottomBound < getRoom ().getHeight () * 16 - viewportHeight) {
			viewY = (int) y - bottomBound;
			getRoom ().setView (getRoom ().getViewX (), viewY);
		}
		if (y - viewY <= topBound && y - topBound > 0) {
			viewY = (int) y - topBound;
			getRoom ().setView (getRoom ().getViewX (), viewY);
		}
		if (x - viewX >= rightBound && x - rightBound < getRoom ().getWidth () * 16 - viewportWidth) {
			viewX = (int) x - rightBound;
			getRoom ().setView (viewX, getRoom ().getViewY ());
		}
		if (x - viewX <= leftBound && x - leftBound > 0) {
			viewX = (int) x - leftBound;
			getRoom ().setView (viewX, getRoom ().getViewY ());
		}
		if (health <= 0) {
			health = maxHealth;
			MainLoop.getConsole ().enable ("You died oof");
		}
	}
	@Override
	public void onDeclare () {
		//Use the startpos if present
		ArrayList<GameObject> startpos = MainLoop.getObjectMatrix ().getObjects ("gameObjects.Startpos");
		if (startpos != null && startpos.size () != 0) {
			this.setX (startpos.get (0).getX ());
			this.setY (startpos.get (0).getY ());
		}
	}
	@Override
	public void draw () {
		super.draw ();
		armSprite.draw ((int)getX () - getRoom ().getViewX(), (int)getY () - getRoom ().getViewY (), getAnimationHandler ().getFrame ());
	}
	
	public static boolean collidingWithBarrier (GameObject obj) {
		if (getRoom ().isColliding (obj.getHitbox ())) {
			return true;
		}
		ArrayList<GameObject> solids = MainLoop.getObjectMatrix ().getAll (Debris.class);
		solids.addAll (MainLoop.getObjectMatrix ().getAll (Building.class));
		for (int i = 0; i < solids.size (); i++) {
			if (obj.isColliding (solids.get (i))) {
				return true;
			}
		}
		if (obj instanceof Player) {
			if (obj.isColliding ("gameObjects.OverpassBarrier")) {
				ArrayList<GameObject> overpasses = obj.getCollidingObjects ("gameObjects.Overpass");
				for (int i = 0; i < overpasses.size (); i++) {
					if (((Overpass)overpasses.get (i)).isUnderPlayer ()) {
						return true;
					}
				}
			}
			if (obj.isColliding ("gameObjects.LayerCollider")) {
				ArrayList<GameObject> layerColliders = obj.getCollidingObjects ("gameObjects.LayerCollider");
				int playerLayer = Integer.parseInt (obj.getVariantAttribute ("layer"));
				for (int i = 0; i < layerColliders.size (); i++) {
					if (playerLayer == ((LayerCollider)layerColliders.get (i)).getLayer ()) {
						return true;
					}
				}
			}
		}
		return false; 
	}
	
	public void focusView () {
		int windowWidth = MainLoop.getWindow ().getResolution () [0];
		int windowHeight = MainLoop.getWindow ().getResolution () [1];
		int newViewX = (int)(getX () - windowWidth / 2);
		int newViewY = (int)(getY () - windowHeight / 2);
		if (newViewX + windowWidth > getRoom ().getWidth () * 16) {
			newViewX = getRoom ().getWidth () * 16 - windowWidth;
		}
		if (newViewY + windowHeight > getRoom ().getHeight () * 16) {
			newViewY = getRoom ().getHeight () * 16 - windowHeight;
		}
		if (newViewX < 0) {
			newViewX = 0;
		}
		if (newViewY < 0) {
			newViewY = 0;
		}
		getRoom ().setView (newViewX, newViewY);
	}
	
	public void setMaxHealth (double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public double getMaxHealth () {
		return maxHealth;
	}
	
	@Override
	public void damage (double amount) {
		setHealth (health - amount);
	}
	
	@Override
	public void damageEvent (DamageSource source) {
		if (invulTime == 0) {
			damage (source.getBaseDamage ());
			invulTime = invulLength;
		}
	}
	
	@Override
	public double getHealth () {
		return health;
	}
	
	@Override
	public void setHealth (double health) {
		if (health > maxHealth) {
			health = maxHealth;
		} else {
			this.health = health;
		}
	}
	
	public int getDirection () {
		return direction;
	}
	
	public void doGraphicsTick () {
		
		//Generate the coords for the tick
		int startX = getRoom ().getViewX () / 16;
		int startY = getRoom ().getViewY () / 16;
		int width = MainLoop.getWindow ().getResolution () [0] / 16 + 1;
		int height = MainLoop.getWindow ().getResolution () [1] / 16 + 1;
		int randOffsX = (int)(Math.random () * width);
		int randOffsY = (int)(Math.random () * height);
		int finalX = startX + randOffsX;
		int finalY = startY + randOffsY;
		
		//Do the tick
		short tileId = getRoom ().getTileId (finalX, finalY, 0);
		if (tileId == 247) {
			if (Math.random () < .2) {
				new Bubble ().declare (finalX * 16, finalY * 16);
			}
		}
		
	}
	
	public Vector2D getFacingDirection () {
		switch (direction) {
			case DIRECTION_UP:
				return new Vector2D (0, -1);
			case DIRECTION_LEFT:
				return new Vector2D (-1, 0);
			case DIRECTION_DOWN:
				return new Vector2D (0, 1);
			case DIRECTION_RIGHT:
				return new Vector2D (1, 0);
			default:
				return new Vector2D (0, 0);
		}
	}
	
	public InteractBubble getInteractBubble () {
		return bubble;
	}
	
}