package gameObjects;

import java.lang.reflect.InvocationTargetException;

import main.ObjectMatrix;

public class Debris extends Saveable {

	private boolean isRandomSpawn = false;
	private boolean firstFrame = true;
	
	public void setRandomSpawn () {
		isRandomSpawn = true;
	}
	
	public boolean isRandomSpawn () {
		return isRandomSpawn;
	}
	
	public void onHarvest () {
		if (!isRandomSpawn) {
			save ("" + getGui ().getEnvironment ().getElapsedMonths ());
		}
	}
	
	public static Debris fromString (String s) {
		String[] splitStr = s.split (",");
		try {
			Debris d = (Debris)ObjectMatrix.makeInstance (splitStr [0]);
			d.declare (Integer.parseInt (splitStr [1]), Integer.parseInt (splitStr [2]));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString () {
		return getClass ().getSimpleName () + "," + ((int)getX ()) + "," + ((int)getY ());
	}
	
	@Override
	public void frameEvent () {
		if (firstFrame && !isRandomSpawn && getGui ().getEnvironment ().isLoaded ()) {
			load ();
			firstFrame = false;
		}
	}
	
	@Override
	public void load() {
		if (getSaveData () != null) {
			int currMonth = getGui ().getEnvironment ().getElapsedMonths ();
			System.out.println (getSaveData () + ", " + currMonth);
			if (Integer.parseInt (getSaveData()) == currMonth) {
				forget ();
			}
		}
	}

}
