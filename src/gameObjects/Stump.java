package gameObjects;

import gui.Interactable;
import items.Wood;
import main.GameObject;

public class Stump extends Debris implements Interactable {
	
	public Stump () {
		createHitbox (0, 0, 16, 16);
		setSprite (getSprites ().stumpSprite);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		String type = getVariantAttribute ("type");
		if (type == null) {
			type = "" + (int)(Math.random () * getSprite ().getFrameCount());
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
		getGui ().getInventory ().addItem (new Wood ());
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
