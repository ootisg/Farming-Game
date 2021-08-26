package items;

import crops.GrowingCorn;
import json.JSONObject;
import main.GameAPI;

public class CornSeeds extends Seeds {

	@Override
	public boolean use () {
		//Check the top 3 layers for stuff
		boolean farmable = false;
		int tileX = (int)((GameAPI.getMouseX () + GameAPI.getRoom ().getViewX ()) / 16);
		int tileY = (int)((GameAPI.getMouseY () + GameAPI.getRoom ().getViewY ()) / 16);
		for (int i = 0; i < 3; i++) {
			String tileId = GameAPI.getRoom ().getTileIdString (tileX, tileY, i);
			//System.out.println (tileId + " " + getRoom ().getTileAttributesList ().getTile (tileId).getProperties ());
			JSONObject tileProperties = GameAPI.getRoom ().getTileAttributesList ().getTile (tileId).getProperties ();
			if (tileProperties.get ("farmable") != null) {
				farmable = true;
			}
		}
		if (farmable) {
			new GrowingCorn ().declare (tileX * 16, tileY * 16);
			return super.use ();
		}
		return false;
	}
	
}
