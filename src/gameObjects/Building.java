package gameObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import main.GameObject;
import resources.Sprite;

public class Building extends GameObject {
	
	private String type;
	
	private Door door;
	private int doorXOffset;
	private int doorYOffset;
	
	public Building (String type) {
		
		this.type = type;
		Sprite buildingSpr = new Sprite ("resources/sprites/buildings/" + type + "/building.png");
		setSprite (buildingSpr);
		
	}
	
	@Override
	public void onDeclare () {
		/*File doorOffset = new File ("resources/sprites/buildings/" + type + "/offset.txt");
		Scanner s;
		int doorXOffset = 0, doorYOffset = 0;
		try {
			s = new Scanner (doorOffset);
			doorXOffset = s.nextInt ();
			doorYOffset = s.nextInt ();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		door = new Door ();
		//door.setSprite (new Sprite ("resources/sprites/buildings/" + type + "/door.png"));
		door.declare (getX () + 22, getY () + 64);
	}

}