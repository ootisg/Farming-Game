package main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import crops.CropHandler;
import gameObjects.DebrisHandler;
import gameObjects.GlobalSave;
import music.MusicPlayer;

public class GameCode extends GameAPI {
	
	private static CropHandler cropHandler;
	private static DebrisHandler debrisHandler;
	
	public void initialize () {
		//Set the save file path
		getSave ().setFile ("saves/save.txt");
		//Create the global save data
		new GlobalSave ().declare (0, 0);
		//Make the music player so music can be played
		new MusicPlayer ();
		//Load the starting room
		try {
			getRoom ().loadRMF ("resources/maps/farm.rmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Declare the player
		getPlayer ().declare (32, 32);
		MainLoop.getWindow ().setResolution (512, 238);
		MainLoop.getWindow ().setSize (1024, 576);
		//Make the crop handler
		cropHandler = new CropHandler ();
		debrisHandler = new DebrisHandler ();
	}
	
	public void gameLoop () {
		//Runs once per frame
	}
	
	public static CropHandler getCropHandler () {
		return cropHandler;
	}
	
	public static DebrisHandler getDebrisHandler () {
		return debrisHandler;
	}
	
}