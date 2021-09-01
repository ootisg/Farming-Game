package crops;

import items.Strawberry;

public class GrowingStrawberry extends GrowingCrop {

	public GrowingStrawberry () {
		super ();
		setSprite (getSprites ().cropSpriteStrawberry);
		setGrowthTime (2);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Strawberry ());
		this.setGrowthStage (4);
	}
	
}
