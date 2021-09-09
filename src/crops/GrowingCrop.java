package crops;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import gui.Interactable;
import main.GameCode;
import main.GameObject;
import main.MainLoop;
import main.ObjectMatrix;

public class GrowingCrop extends GameObject implements Interactable {

	private int growthStage;
	private int subGrowth;
	private int lastWatered = -69;
	private int roomHash;
	
	private int growthTime;
	
	public GrowingCrop () {
		growthStage = 0;
		subGrowth = 0;
		growthTime = 0;
		roomHash = getRoom ().getRoomName ().hashCode ();
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
	
	public void harvest () {
		GameCode.getCropHandler ().harvest (this);
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
	
	public String getCropId () {
		return roomHash + "," + (int)getX () + "," + (int)getY ();
	}
	
	public int getGrowthTime () {
		return growthTime;
	}
	
	public int getMaxGrowth () {
		return getSprite ().getFrameCount () - 1;
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
		if (isFullyGrown ()) {
			harvest ();
		}
	}

	@Override
	public boolean useDefaultHover () {
		return isFullyGrown ();
	}
	
	@Override
	public String toString () {
		return getClass ().getSimpleName () + "," + ((int)getX ()) + "," + ((int)getY ()) + "," + getRoom ().getRoomName ().hashCode () + "," + lastWatered + "," + growthStage + "," + subGrowth;
	}
	
	public void updateCropState (String str) {
		String[] parsed = str.split (",");
		this.roomHash = Integer.parseInt (parsed [3]);
		this.lastWatered = (Integer.parseInt (parsed [4]));
		this.growthStage = (Integer.parseInt (parsed [5]));
		this.subGrowth = (Integer.parseInt (parsed [6]));
	}
	
	public static GrowingCrop fromString (String str, boolean matchRoom) {
		String[] parsed = str.split (",");
		if (!matchRoom || parsed [3] == "" + getRoom ().getRoomName ().hashCode ()) {
			//ONLY returns a crop if the room hash is correct when matchRoom is true
			try {
				GrowingCrop crop = (GrowingCrop)ObjectMatrix.makeInstance (parsed [0]);
				crop.declare (Integer.parseInt (parsed [1]), Integer.parseInt (parsed [2]));
				crop.roomHash = Integer.parseInt (parsed [3]);
				crop.lastWatered = (Integer.parseInt (parsed [4]));
				crop.growthStage = (Integer.parseInt (parsed [5]));
				crop.subGrowth = (Integer.parseInt (parsed [6]));
				return crop;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
