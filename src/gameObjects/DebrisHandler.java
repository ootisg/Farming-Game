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
	
	public static double BUSH_MIN = 0;
	public static double BUSH_MAX = .2;
	public static double ROCK_MIN = .2;
	public static double ROCK_MAX = .4;
	public static double STUMP_MIN = .4;
	public static double STUMP_MAX = .5;
	public static double TREE_MIN = .5;
	public static double TREE_MAX = .55;
	
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
			
			//Choose the spawn coordinates
			int rx = (int)(Math.random () * getRoom ().getWidth () - 2) * 16;
			int ry = (int)(Math.random () * getRoom ().getHeight () - 2) * 16; //TODO revert OOB crash fix
			
			//Choose the debris type
			double r = Math.random ();
			Debris toSpawn;
			if (r > BUSH_MIN && r <= BUSH_MAX) {
				toSpawn = new Bush ();
			} else if (r > ROCK_MIN && r <= ROCK_MAX) {
				toSpawn = new Rock ();
			} else if (r > STUMP_MIN && r <= STUMP_MAX) {
				toSpawn = new Stump ();
			} else if (r > TREE_MIN && r <= TREE_MAX) {
				toSpawn = new Tree ();
			} else {
				toSpawn = null;
			}
			
			//TODO allow other types of debris
			if (toSpawn != null) {
				
				//Declare the new debris
				toSpawn.declare (rx, ry);
				
				//Check to see if the debris can spawn in the chosen location
				if (getRoom ().isColliding (toSpawn.getHitbox())) {
					//Colliding with the tilemap
					toSpawn.forget ();
				} else {
					//Colliding with other debris
					ArrayList<GameObject> allDebris = MainLoop.getObjectMatrix ().getAll (Debris.class);
					for (int j = 0; j < allDebris.size (); j++) {
						Debris d = (Debris)allDebris.get (j);
						if (toSpawn != d && toSpawn.isColliding (d)) {
							toSpawn.forget ();
						} else {
							this.addDebris (toSpawn);
						}
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
		while (globalDay < getGui ().getEnvironment ().getElapsedDays ()) {
			globalDay++;
			spawnDebris ();
		}
	}

	@Override
	public void load () {
		if (getSaveData () != null) {
			String[] split = getSaveData ().split (";");
			globalDay = Integer.parseInt (split [0]);
			for (int i = 1; i < split.length; i++) {
				Debris d = Debris.fromString (split [i]);
				if (d != null) {
					addDebris (d);
				}
			}
		}
	}
	
}
