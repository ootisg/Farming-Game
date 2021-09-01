package gameObjects;

import gui.Interactable;
import main.GameObject;

public class Door extends GameObject implements Interactable {

	public Door () {
		setSprite (getSprites ().doorStdSprite);
		getAnimationHandler ().setAnimationSpeed (0);
		createHitbox (0, 0, 12, 16);
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
		getGui ().getEnvironment ().startDay ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}
	
}
