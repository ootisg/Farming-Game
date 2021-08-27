package items;

import crops.GrowingBeet;
import crops.GrowingCorn;

public class BeetSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingBeet ().declare (x * 16, y * 16);
	}
	
}
