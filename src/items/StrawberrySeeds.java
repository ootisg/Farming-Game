package items;

import crops.GrowingCorn;
import crops.GrowingStrawberry;

public class StrawberrySeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingStrawberry ().declare (x * 16, y * 16);
	}
	
}
