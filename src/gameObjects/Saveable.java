package gameObjects;

import main.GameObject;

public abstract class Saveable extends GameObject {
	
	protected Saveable () {
		super ();
	}
	
	@Override
	public void onDeclare () {
		load ();
	}
	
	public void save (String data, String roomName) {
		getSave ().save (getSaveId (), data);
	}
	
	public void save (String data) {
		save (data, getRoom ().getRoomName ());
	}
	
	protected String getSaveData (String objId) {
		return getSave ().getSaveData (getSaveId ());
	}
	
	protected String getSaveData () {
		return getSaveData (getRoom ().getRoomName ());
	}
	
	public String getSaveId () {
		return getSaveRoom () + "," + getClass ().getSimpleName () + "," + (int)getStartPos ()[0] + "," + (int)getStartPos ()[1];
	}
	
	public String getSaveRoom () {
		return getRoom ().getRoomName ();
	}
	
	public abstract void load ();
}
