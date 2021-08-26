package crops;

import items.Potato;

public class GrowingPotato extends GrowingCrop {

	public GrowingPotato () {
		super ();
		setGrowthTime (30);
		setSprite (getSprites ().cropSpritePotato);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Potato ());
		super.harvest ();
	}
	
}
