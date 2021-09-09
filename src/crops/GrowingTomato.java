package crops;

import items.Tomato;
import main.GameCode;

public class GrowingTomato extends GrowingCrop {
	
	public GrowingTomato () {
		super ();
		setSprite (getSprites ().cropSpriteTomato);
		setGrowthTime (3);
	}
	
	@Override
	public void harvest () {
		getGui ().getInventory ().addItem (new Tomato ());
		GameCode.getCropHandler ().setGrowthStage (this, 3);
	}

}
