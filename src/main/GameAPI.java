package main;

import gameObjects.Player;
import gui.Gui;
import map.Room;
import resources.GlobalSprites;

public abstract class GameAPI {
	private static final Room room = new Room (); //Makes the static instance of the room object
	private static final GlobalSprites sprites = new GlobalSprites (); //Makes the static instance of the sprite container object
	private static final Gui gui = new Gui ();
	private static final Player player = new Player ();
	private static final SaveFile activeSave = new SaveFile ();
	public static boolean keyCheck (int keyCode) {
		//Returns true if the key with an ASCII code of keyCode is pressed down
		return MainLoop.getWindow ().keyCheck (keyCode);
	}
	public static boolean keyPressed (int keyCode) {
		//Returns true if the key with an ASCII code of keyCode was pressed down this frame
		return MainLoop.getWindow ().keyPressed (keyCode);
	}
	public static boolean keyReleased (int keyCode) {
		//Returns true if the key with an ASCII code of keyCode was released this frame
		return MainLoop.getWindow ().keyReleased (keyCode);
	}
	public static boolean keyCheck (char key) {
		//Returns true if the key with an ASCII code of keyCode is pressed down
		return MainLoop.getWindow ().keyCheck ((int)key);
	}
	public static boolean keyPressed (char key) {
		//Returns true if the key with an ASCII code of keyCode was pressed down this frame
		return MainLoop.getWindow ().keyPressed ((int)key);
	}
	public static boolean keyReleased (char key) {
		//Returns true if the key with an ASCII code of keyCode was released this frame
		return MainLoop.getWindow ().keyReleased ((int)key);
	}
	public static GameObject getObject (int x, int y) {
		//Gets the object from the object matrix with the position (x, y)
		return MainLoop.getObjectMatrix ().get (x, y);
	}
	public static int getTypeId (String objectName) {
		//Gets the x index for the object matrix corresponding to a specific object name
		return MainLoop.getObjectMatrix ().getTypeId (objectName);
	}
	public static int getMouseX () {
		//Get the current mouse x-coordinate on the screen
		return MainLoop.getWindow ().getMouseX ();
	}
	public static int getMouseY () {
		//Get the current mouse y-coordinate on the screen
		return MainLoop.getWindow ().getMouseY ();
	}
	public static int[] getClick () {
		//Returns the coordinates of the most recent click in the format [x, y]. Returns null if there was not a click this frame.
		return MainLoop.getWindow ().getClick ();
	}
	public static boolean mouseClicked () {
		//Returns true if the mouse was clicked this frame
		if (MainLoop.getWindow ().getClick () == null) {
			return false;
		}
		return true;
	}
	public static Room getRoom () {
		return room;
	}
	public static GlobalSprites getSprites () {
		return sprites;
	}
	public static Gui getGui () {
		return gui;
	}
	public static Player getPlayer () {
		return player;
	}
	public static GameWindow getWindow () {
		return MainLoop.getWindow ();
	}
	public static SaveFile getSave () {
		return activeSave;
	}
}