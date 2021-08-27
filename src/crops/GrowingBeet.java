package crops;

import items.Beet;

public class GrowingBeet extends GrowingCrop {

	public GrowingBeet () {
		super ();
		setSprite (getSprites ().cropSpriteBeet);
		setGrowthTime (40);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Beet ());
		super.harvest ();
	}
	
}
