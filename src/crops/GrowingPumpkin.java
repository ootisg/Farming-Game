package crops;

import items.Pumpkin;

public class GrowingPumpkin extends GrowingCrop {
	
	public GrowingPumpkin () {
		super ();
		setSprite (getSprites ().cropSpritePumpkin);
		setGrowthTime (50);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Pumpkin ());
		super.harvest ();
	}

}
