package crops;

import java.awt.Point;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import gameObjects.Saveable;
import main.GameObject;
import main.MainLoop;
import main.ObjectMatrix;

public class CropHandler extends Saveable {
	
	private HashMap<String, SavedCrop> crops;
	private int lastWaterCycle;
	
	private boolean firstFrame = true;
	
	public CropHandler () {
		lastWaterCycle = getGui ().getEnvironment ().getWaterCycle ();
		setPersistent (true);
	}
	
	public void addCrop (GrowingCrop crop) {
		if (!getAllSavedCrops ().containsKey (crop.getCropId ())) {
			SavedCrop sc = new SavedCrop (crop.toString ());
			sc.cropObject = crop;
			getAllSavedCrops ().put (sc.getCropId (), sc);
		}
	}
	
	public void saveAll () {
		Iterator<Entry<String, SavedCrop>> iter = getAllSavedCrops ().entrySet ().iterator ();
		StringBuilder sb = new StringBuilder ();
		while (iter.hasNext ()) {
			sb.append (iter.next ().getValue ());
			if (iter.hasNext ()) {
				sb.append (";");
			}
		}
		save (sb.toString ());
	}
	
	private HashMap<String, SavedCrop> getAllSavedCrops () {
		if (crops == null) {
			crops = new HashMap<String, SavedCrop> ();
		}
		return crops;
	}
	
	public void growAll () {
		Iterator<Entry<String, SavedCrop>> iter = getAllSavedCrops ().entrySet ().iterator ();
		while (iter.hasNext ()) {
			SavedCrop crop = iter.next ().getValue ();
			growCrop (crop);
		}
		saveAll ();
	}
	
	public void growCrop (GrowingCrop crop) {
		growCrop (crops.get (crop.getCropId ()));
	}

	private void growCrop (SavedCrop crop) {
		if (getGui ().getEnvironment ().getWaterCycle () - crop.lastWatered <= 2) {
			
			//Get the crop growth parameters
			//TODO this is slow
			int growthTime = -1;
			int maxGrowth = -1;
			try {
				GrowingCrop gc = (GrowingCrop)ObjectMatrix.makeInstance (crop.cropType);
				growthTime = gc.getGrowthTime ();
				maxGrowth = gc.getMaxGrowth ();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				//Do nothing
			}
			
			//Growth logic
			if (growthTime != -1) {
				if (crop.growthStage == 0) {
					crop.growthStage = 1; //Grow immediately if seeds
				} else {
					crop.subGrowth++; //Grow slowly once sprouted
					if (crop.subGrowth >= growthTime) {
						crop.subGrowth = 0;
						crop.growthStage++;
					}
					if (crop.growthStage > maxGrowth) {
						crop.growthStage = maxGrowth;
					}
				}
			}
			
			//Update the crop's object representation
			if (crop.cropObject != null) {
				crop.cropObject.updateCropState (crop.toString ());
			}
		}
	}
			
	
	public void harvest (GrowingCrop crop) {
		crops.remove (crop.getCropId ());
	}
	
	public void water (GrowingCrop crop) {
		crops.get (crop.getCropId ()).lastWatered = getGui ().getEnvironment ().getWaterCycle ();
	}
	
	public void setGrowthStage (GrowingCrop crop, int stage) {
		
		//Grow the crop to the desired stage
		SavedCrop sc = crops.get (crop.getCropId ());
		sc.growthStage = stage;
		sc.subGrowth = 0;
		
		//Update the crop's object representation
		if (sc.cropObject != null) {
			sc.cropObject.updateCropState (sc.toString ());
		}
		
	}
	
	public void water (int tileX, int tileY) {
		String cropId = getRoom ().getRoomName ().hashCode () + "," + tileX * 16 + "," + tileY * 16;
		if (crops.containsKey (cropId)) {
			crops.get (cropId).lastWatered = getGui ().getEnvironment ().getWaterCycle ();
		}
	}
	
	@Override
	public void frameEvent () {
		if (firstFrame) {
			load ();
			firstFrame = false;
		}
		while (lastWaterCycle < getGui ().getEnvironment ().getWaterCycle ()) {
			growAll ();
			saveAll ();
			lastWaterCycle++;
		}
	}
	
	@Override
	public String getSaveId () {
		return "_GLOBAL,CropHandler";
	}

	@Override
	public void load() {
		if (getSaveData () != null) {
			String[] split = getSaveData ().split (";");
			for (int i = 0; i < split.length; i++) {
				String curr = split [i];
				SavedCrop crop = new SavedCrop (curr);
				getAllSavedCrops ().put (crop.getCropId (), crop);
				if (crop.roomHash == getRoom ().getRoomName().hashCode ()) {
					GrowingCrop gc = GrowingCrop.fromString (curr, false);
					crop.setCropObject (gc);
				}
			}
		}
	}
	
	private class SavedCrop {
		
		public String cropType;
		public int x;
		public int y;
		public int roomHash;
		public int lastWatered;
		public int growthStage;
		public int subGrowth;
		
		public GrowingCrop cropObject;
		
		public SavedCrop (String cropString) {
			if (!cropString.isEmpty ()) {
				String[] cropSplit = cropString.split (",");
				cropType = cropSplit [0];
				x = Integer.parseInt (cropSplit [1]);
				y = Integer.parseInt (cropSplit [2]);
				roomHash = Integer.parseInt (cropSplit [3]);
				lastWatered = Integer.parseInt (cropSplit [4]);
				growthStage = Integer.parseInt (cropSplit [5]);
				subGrowth = Integer.parseInt (cropSplit [6]);
			}
		}
		
		public void setCropObject (GrowingCrop crop) {
			cropObject = crop;
		}
		
		public String getCropId () {
			return roomHash + "," + x + "," + y;
		}
		
		@Override
		public String toString () {
			return cropType + "," + x + "," + y + "," + roomHash + "," + lastWatered + "," + growthStage + "," + subGrowth;
		}
		
	}
	
}
