package items;

import java.util.HashMap;

import gui.Environment;
import main.GameCode;
import map.TileAttributesMap;
import map.TileData;

public class WateringCan extends Tool {
	
	@Override
	public boolean use () {
		int clickX = (int)(GameCode.getMouseX () / 16);
		int clickY = (int)(GameCode.getMouseY () / 16);
		GameCode.getGui ().getEnvironment ().waterTile (clickX, clickY);
		return true;
	}
	
}
