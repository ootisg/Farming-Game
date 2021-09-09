package crops;

import items.Corn;
import main.GameCode;

public class GrowingCorn extends GrowingCrop {

	public GrowingCorn () {
		super ();
		setGrowthTime (3);
		setSprite (getSprites ().cropSpriteCorn);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Corn ());
		GameCode.getCropHandler ().setGrowthStage (this, 3);
	}
	
}
