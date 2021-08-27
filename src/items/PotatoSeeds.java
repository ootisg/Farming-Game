package items;

import crops.GrowingPotato;
import json.JSONObject;
import main.GameAPI;

public class PotatoSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingPotato ().declare (x * 16, y * 16);
	}
	
}
