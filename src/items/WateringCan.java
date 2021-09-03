package items;

import java.util.HashMap;

import gui.Environment;
import main.GameAPI;
import main.GameCode;
import map.TileAttributesMap;
import map.TileData;

public class WateringCan extends Tool {
	
	@Override
	public boolean use () {
		int clickX = (int)((GameCode.getMouseX () + GameAPI.getRoom ().getViewX ()) / 16);
		int clickY = (int)((GameCode.getMouseY () + GameAPI.getRoom ().getViewY ()) / 16);
		GameCode.getGui ().getEnvironment ().waterTile (clickX, clickY);
		return true;
	}
	
}
