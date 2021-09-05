package gameObjects;

import gui.Interactable;
import items.GameItem;
import items.Stone;
import items.Wood;
import main.GameObject;

public class Tree extends SolidStatic implements Interactable {
	
	public Tree () {
		createHitbox (0, 11, 32, 32);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		String type = getVariantAttribute ("type");
		if (type == null) {
			type = "" + (int)(Math.random () * 2);
		}
		switch (type) {
			case "0":
				setSprite (getSprites ().treeSprite);
				break;
			case "1":
				setSprite (getSprites ().pineTreeSprite);
				break;
		}
		getAnimationHandler ().setFrame (0);
	}
	
	@Override
	public void draw () {
		super.draw ();
		getSprite ().setFrame (1);
		getSprite ().draw ((int)getX () - getRoom ().getViewX (), (int)getY () - getRoom ().getViewY ());
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
		GameItem wood = new Wood ();
		wood.setCount (6);
		getGui ().getInventory ().addItem (wood);
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
