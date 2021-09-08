package gameObjects;

import java.io.FileNotFoundException;

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
		getGui ().getEnvironment ().skipDay ();
		try {
			getRoom ().loadRoom ("resources/maps/farm.rmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}
	
}
