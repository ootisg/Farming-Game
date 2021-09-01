package crops;

import items.Tomato;

public class GrowingTomato extends GrowingCrop {
	
	public GrowingTomato () {
		super ();
		setSprite (getSprites ().cropSpriteTomato);
		setGrowthTime (3);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Tomato ());
		this.setGrowthStage (3);
	}

}
