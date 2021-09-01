package gameObjects;

import main.GameObject;

public class Building extends GameObject {
	
	Door door;
	
	public Building () {
		
	}
	
	@Override
	public void onDeclare () {
		door = new Door ();
		door.declare (getX () + 22, getY () + 64);
	}

}