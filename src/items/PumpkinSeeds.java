package items;

import crops.GrowingCorn;
import crops.GrowingPumpkin;

public class PumpkinSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingPumpkin ().declare (x * 16, y * 16);
	}
	
}
