package gameObjects;

import gui.Interactable;
import main.GameObject;

public class Rock extends SolidStatic implements Interactable {
	
	public Rock () {
		createHitbox (0, 0, 16, 16);
		setSprite (getSprites ().rockSprite);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		String rockType = getVariantAttribute ("type");
		if (rockType == null) {
			rockType = Math.random () > .5 ? "0" : "1";
		}
		if (rockType.equals ("0")) {
			getAnimationHandler ().setFrame (0);
		}
		if (rockType.equals ("1")) {
			getAnimationHandler ().setFrame (1);
		}
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
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
