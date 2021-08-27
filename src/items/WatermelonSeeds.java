package items;

import crops.GrowingCorn;
import crops.GrowingWatermelon;

public class WatermelonSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingWatermelon ().declare (x * 16, y * 16);
	}
	
}
