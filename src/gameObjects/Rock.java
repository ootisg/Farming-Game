package gameObjects;

import java.util.Random;

import gui.Interactable;
import items.Stone;
import main.GameObject;

public class Rock extends Debris implements Interactable {
	
	public Rock () {
		createHitbox (0, 0, 16, 16);
		setSprite (getSprites ().rockSprite);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		String type = getVariantAttribute ("type");
		if (type == null) {
			Random r = new Random ((long)(getX () * 476710937 + getY () * 98960489) + 13931213);
			type = "" + (int)(r.nextDouble () * getSprite ().getFrameCount());
			setVariantAttribute ("type", type);
		}
		getAnimationHandler ().setFrame (Integer.parseInt (type));
	}

	@Override
	public void hover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unhover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		onHarvest ();
		getGui ().getInventory ().addItem (new Stone ());
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
