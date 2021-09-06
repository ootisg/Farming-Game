package gameObjects;

import main.GameObject;

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
