package items;

import crops.GrowingCorn;
import crops.GrowingTomato;

public class TomatoSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingTomato ().declare (x * 16, y * 16);
	}
	
}
