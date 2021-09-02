package crops;

import gui.Interactable;
import main.GameCode;
import main.GameObject;

public class GrowingCrop extends GameObject implements Interactable {

	private int growthStage;
	private int subGrowth;
	
	private int growthTime;
	
	public GrowingCrop () {
		growthStage = 0;
		subGrowth = 0;
		growthTime = 0;
		createHitbox (0, 0, 16, 16);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		GameCode.getCropHandler ().addCrop (this);
	}
	
	public int getGrowthStage () {
		return growthStage;
	}
	
	protected void setGrowthTime (int growthTime) {
		this.growthTime = growthTime;
	}
	
	public boolean isFullyGrown () {
		return getSprite ().getFrameCount () - 1 == growthStage;
	}
	
	public void attemptGrow () {
		if (isWatered ()) {
			grow ();
		}
	}
	
	public void grow () {
		if (growthStage == 0) {
			nextStage ();
		} else {
			if (subGrowth == growthTime) {
				subGrowth = 0;
				nextStage ();
			} else {
				subGrowth++;
			}
		}
	}
	
	public void nextStage () {
		if (!isFullyGrown ()) {
			growthStage++;
		}
	}
	
	public void setGrowthStage (int stage) {
		growthStage = stage;
	}
	
	public void harvest () {
		forget ();
	}
	
	public boolean isWatered () {
		int tileX = (int)getX () / 16;
		int tileY = (int)getY () / 16;
		for (int i = 0; i < 3; i++) {
			String tileId = getRoom ().getTileIdString (tileX, tileY, i);
			Boolean isWet = (Boolean)getRoom ().getTileAttributesList ().getTile (tileId).getProperties ().get ("wet");
			if (isWet == null) {
				//Do nothing
			} else if (isWet) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void frameEvent () {
		
	}
	
	@Override
	public void draw () {
		getAnimationHandler ().setFrame (growthStage);
		super.draw ();
	}

	@Override
	public void hover () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unhover () {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click () {
		harvest ();
	}

	@Override
	public boolean useDefaultHover () {
		return isFullyGrown ();
	}
	
}
