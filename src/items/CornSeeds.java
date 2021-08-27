package items;

import crops.GrowingCorn;
import json.JSONObject;
import main.GameAPI;

public class CornSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingCorn ().declare (x * 16, y * 16);
	}
	
}
