package crops;

import items.Carrot;

public class GrowingCarrot extends GrowingCrop {
	
	public GrowingCarrot () { 
		super ();
		setSprite (getSprites ().cropSpriteCarrot);
		setGrowthTime (2);
	}

	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Carrot ());
		super.harvest ();
	}
	
}
