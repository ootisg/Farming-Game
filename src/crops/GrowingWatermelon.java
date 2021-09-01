package crops;

import items.Watermelon;

public class GrowingWatermelon extends GrowingCrop {

	public GrowingWatermelon () {
		super ();
		setSprite (getSprites ().cropSpriteWatermelon);
		setGrowthTime (6);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Watermelon ());
		super.harvest ();
	}
	
}
