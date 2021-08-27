package items;

import crops.GrowingPotato;
import json.JSONObject;
import main.GameAPI;

public abstract class Seeds extends GameItem {

	public Seeds () {
		super (ItemType.CONSUMABLE);
		setMaxStack (99);
	}
	
	public boolean isPlantable (int tileX, int tileY) {
		//Check the top 3 layers for stuff
		for (int i = 0; i < 3; i++) {
			String tileId = GameAPI.getRoom ().getTileIdString (tileX, tileY, i);
			//System.out.println (tileId + " " + getRoom ().getTileAttributesList ().getTile (tileId).getProperties ());
			JSONObject tileProperties = GameAPI.getRoom ().getTileAttributesList ().getTile (tileId).getProperties ();
			if (tileProperties.get ("farmable") != null) {
				return true;
			}
		}
		return false;
	}
	
	public boolean canPlantAtCursor () {
		int tileX = (int)((GameAPI.getMouseX () + GameAPI.getRoom ().getViewX ()) / 16);
		int tileY = (int)((GameAPI.getMouseY () + GameAPI.getRoom ().getViewY ()) / 16);
		return isPlantable (tileX, tileY);
	}
	
	@Override
	public boolean use () {
		int tileX = (int)((GameAPI.getMouseX () + GameAPI.getRoom ().getViewX ()) / 16);
		int tileY = (int)((GameAPI.getMouseY () + GameAPI.getRoom ().getViewY ()) / 16);
		if (isPlantable (tileX, tileY)) {
			doPlant (tileX, tileY);
			super.use ();
			return true;
		}
		return false;
	}
	
	public void doPlant (int x, int y) {
		
	}
	
}
