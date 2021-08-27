package items;

import crops.GrowingCarrot;

public class CarrotSeeds extends Seeds {

	@Override
	public void doPlant (int x, int y) {
		new GrowingCarrot ().declare (x * 16, y * 16);
	}
	
}
