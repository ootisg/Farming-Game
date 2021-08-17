package main;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.IOException;

import gameObjects.GlobalSave;
import music.MusicPlayer;

public class GameCode extends GameAPI {
	
	public void initialize () {
		//Set the save file path
		getSave ().setFile ("saves/save.txt");
		//Create the global save data
		new GlobalSave ().declare (0, 0);
		//Make the music player so music can be played
		new MusicPlayer ();
		//Load the starting room
		try {
			getRoom ().loadRMF ("resources/maps/start.rmf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Declare the player
		getPlayer ().declare (32, 32);
		MainLoop.getWindow ().setResolution (480, 480);
		MainLoop.getWindow ().setSize (480, 480);
	}
	
	public void gameLoop () {
		//Runs once per frame
	}
	
}