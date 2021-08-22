package crops;

import main.GameObject;

public class GrowingCrop extends GameObject {

	private int growthStage;
	private int subGrowth;
	
	private int growthTime;
	
	public GrowingCrop () {
		growthStage = 0;
		subGrowth = 0;
		growthTime = 0;
		getAnimationHandler ().setAnimationSpeed (0);
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
	
	public void harvest () {
		forget ();
	}
	
	@Override
	public void frameEvent () {
		grow ();
	}
	
	@Override
	public void draw () {
		getAnimationHandler ().setFrame (growthStage);
		super.draw ();
	}
	
}
