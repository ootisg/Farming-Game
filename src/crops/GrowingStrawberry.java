package crops;

import items.Strawberry;
import main.GameCode;

public class GrowingStrawberry extends GrowingCrop {

	public GrowingStrawberry () {
		super ();
		setSprite (getSprites ().cropSpriteStrawberry);
		setGrowthTime (2);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Strawberry ());
		GameCode.getCropHandler ().setGrowthStage (this, 4);
	}
	
}
