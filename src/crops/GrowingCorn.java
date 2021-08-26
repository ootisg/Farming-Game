package crops;

import items.Corn;

public class GrowingCorn extends GrowingCrop {

	public GrowingCorn () {
		super ();
		setGrowthTime (30);
		setSprite (getSprites ().cropSpriteCorn);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Corn ());
		this.setGrowthStage (3);
	}
	
}
