package gameObjects;

import gui.Interactable;
import items.Hay;
import main.GameObject;

public class Bush extends SolidStatic implements Interactable {
	
	public Bush () {
		createHitbox (0, 0, 16, 16);
		setSprite (getSprites ().bushSprite);
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
		getGui ().getInventory ().addItem (new Hay ());
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
