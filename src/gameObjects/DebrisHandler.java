package gameObjects;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import crops.GrowingCrop;
import main.GameObject;
import main.MainLoop;

public class DebrisHandler extends Saveable {

	public static int DEBRIS_PER_DAY = 5;
	
	private HashMap<Point, Debris> savedDebris;
	
	private boolean firstFrame = true;
	private int globalDay;
	
	public DebrisHandler () {
		globalDay = 0;
	}
	
	public void addDebris (Debris debris) {
		debris.setRandomSpawn ();
		getRandomDebris ().put (new Point ((int)debris.getX (), (int)debris.getY ()), debris);
	}
	
	public void spawnDebris () {
		
		//Spawn in the new debris
		for (int i = 0; i < DEBRIS_PER_DAY; i++) {
			int rx = (int)(Math.random () * getRoom ().getWidth () - 1) * 16;
			int ry = (int)(Math.random () * getRoom ().getHeight () - 1) * 16;
			//TODO allow other types of debris
			Rock rock = new Rock ();
			rock.declare (rx, ry);
			if (getRoom ().isColliding (rock.getHitbox())) {
				rock.forget ();
			} else {
				ArrayList<GameObject> allDebris = MainLoop.getObjectMatrix ().getAll (Debris.class);
				for (int j = 0; j < allDebris.size (); j++) {
					Debris d = (Debris)allDebris.get (j);
					if (rock != d && rock.isColliding (d)) {
						rock.forget ();
					} else {
						this.addDebris (rock);
					}
				}
			}
		}
		
		//Save all the debris
		ArrayList<GameObject> allDebris = MainLoop.getObjectMatrix ().getAll (Debris.class);
		String saved = getGui ().getEnvironment ().getElapsedDays () + ";";
		for (int i = 0; i < allDebris.size (); i++) {
			Debris d = (Debris)allDebris.get (i);
			if (d.isRandomSpawn ()) {
				saved += d;
				if (i != allDebris.size () - 1) {
					saved += ";";
				} //TODO remove semicolon at end of debris (maybe)
			}
		}
		save (saved);
		
	}
	
	public HashMap<Point, Debris> getRandomDebris () {
		if (savedDebris == null) {
			savedDebris = new HashMap<Point, Debris> ();
		}
		return savedDebris;
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
		if (firstFrame) {
			load ();
			firstFrame = false;
		}
		if (globalDay != getGui ().getEnvironment ().getElapsedDays ()) {
			globalDay = getGui ().getEnvironment ().getElapsedDays ();
			spawnDebris ();
		}
	}

	@Override
	public void load () {
		if (getSaveData () != null) {
			String[] split = getSaveData ().split (";");
			globalDay = Integer.parseInt (split [0]);
			for (int i = 1; i < split.length; i++) {
				Debris.fromString (split [i]);
			}
		}
	}
	
}
