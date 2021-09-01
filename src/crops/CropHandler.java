package crops;

import java.awt.Point;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import main.GameObject;

public class CropHandler extends GameObject {

	private HashMap<Point, GrowingCrop> crops;
	private int globalGrowth;
	
	public CropHandler () {
		globalGrowth = 0;
	}
	
	@Override
	public void onDeclare () {
		setPersistent (true);
	}
	
	public void addCrop (GrowingCrop crop) {
		getCrops ().put (new Point ((int)crop.getX (), (int)crop.getY ()), crop);
	}
	
	public HashMap<Point, GrowingCrop> getCrops () {
		if (crops == null) {
			crops = new HashMap<Point, GrowingCrop> ();
		}
		return crops;
	}
	
	public void growAll () {
		Set<Entry<Point, GrowingCrop>> cropsSet = getCrops ().entrySet ();
		Iterator<Entry<Point, GrowingCrop>> iter = cropsSet.iterator ();
		while (iter.hasNext ()) {
			Entry<Point, GrowingCrop> currCrop = iter.next ();
			currCrop.getValue ().attemptGrow ();
		}
	}
	
	public static int getGlobalGrowthStage () {
		int growthDay = getGui ().getEnvironment ().getMonthDay () + getGui ().getEnvironment ().getElapsedMonths () * 28;
		int growthPhase = growthDay * 2;
		if (getGui ().getEnvironment ().getGameTime () > 720000) {
			growthPhase++;
		}
		return growthPhase;
	}
	
	@Override
	public void frameEvent () {
		while (globalGrowth < getGlobalGrowthStage ()) {
			//Grow crops until growth stage is matched
			globalGrowth++;
			growAll ();
		}
	}
	
}
