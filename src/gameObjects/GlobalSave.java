package gameObjects;

import gui.Inventory;

public class GlobalSave extends Saveable {

	public static long gameTime;
	private static String invString = "";
	
	public GlobalSave () {
		setPersistent (true);
	}
	
	@Override
	public void onDeclare () {
		load ();
	}
	
	@Override
	public void frameEvent () {
		gameTime ++;
		save (String.valueOf (gameTime) + ";" + invString);
	}
	
	@Override
	public void load () {
		if (getSaveData ("global") != null) {
			String[] split = getSaveData ("global").split (";");
			String time = split [0];
			if (time != null) {
				gameTime = Long.parseLong (time);
			} else {
				gameTime = 0;
			}
			if (split.length > 1) {
				((Inventory)getGui ().getInventory ()).loadFromString (split [1]);
			}
		}
	}
	
	@Override
	public String getSaveId () {
		return "_GLOBAL,GlobalSave";
	}
	
	public static long getGameTime () {
		return gameTime;
	}
	
	public static void saveInv (String s) {
		invString = s;
	}
	
	public boolean hasSavedInventory () {
		return !invString.isEmpty ();
	}
	
}
