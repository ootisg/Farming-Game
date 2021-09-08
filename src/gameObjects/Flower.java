package gameObjects;

import gui.Interactable;
import items.BlueFlower;
import items.GameItem;
import items.Hay;
import items.PurpleFlower;
import items.RedFlower;

public class Flower extends Debris implements Interactable {
	
	public Flower () {
		createHitbox (0, 0, 16, 16);
		setSprite (getSprites ().flowerSprite);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		String type = getVariantAttribute ("type");
		if (type == null) {
			type = "" + (int)(Math.random () * getSprite ().getFrameCount());
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
		GameItem it = null;
		switch (getVariantAttribute ("type")) {
			case "0":
				it = new RedFlower ();
				break;
			case "1":
				it = new BlueFlower ();
				break;
			case "2":
				it = new PurpleFlower ();
				break;
		}
		getGui ().getInventory ().addItem (it);
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
