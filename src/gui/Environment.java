package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import gameObjects.Saveable;
import main.GameCode;
import main.MainLoop;
import map.TileAttributesMap;
import map.TileData;

public class Environment extends Saveable {
	
	private TimeOverlay timeDisplay;
	private int weekDay;
	private int monthDay = 1;
	private int monthCount = 0;
	
	private long dayDurationMs;
	
	private ColorMap todColor;
	
	private static HashMap<String, String> wetTileMap;
	private static HashMap<String, String> dryTileMap;
	
	private boolean firstFrame = true;
	private boolean isLoaded = false;
	
	public Environment () {
		
		todColor = new ColorMap ();
		todColor.addColor (new Color (0, 0, 10, 170), .15);
		todColor.addColor (new Color (0, 0, 40, 20), .33);
		todColor.addColor (new Color (0, 0, 0, 0), .4);
		todColor.addColor (new Color (0, 0, 0, 0), .75);
		todColor.addColor (new Color (0, 0, 40, 20), .8);
		todColor.addColor (new Color (0, 0, 10, 170), .85);
		todColor.addColor (new Color (0, 0, 10, 170), 1);
		
		dayDurationMs = 360000;
		
	}
	
	public int getGameTime () {
		return (int)((dayDurationMs) / 1000);
	}
	
	public int getWeekDay () {
		return (monthDay - 1) % 7;
	}
	
	public int getMonthDay () {
		return monthDay;
	}
	
	public int getElapsedDays () {
		return monthCount * 28 + monthDay - 1;
	}
	
	public int getWaterCycle () {
		return getElapsedDays () * 2 + (dayDurationMs > 720000 ? 1 : 0);
	}
	
	public int getElapsedMonths () {
		return monthCount;
	}
	
	public void setTimeDisplay (TimeOverlay display) {
		timeDisplay = display;
	}
	
	public void skipDay () {
		
		//TODO hacky
		dayDurationMs = 1440001;
		
	}
	
	public void startDay () {
		
		//Handle environment-related events
		dryMap ();
		
	}
	
	public static String getWetVersion (String tileId) {
		
		//Init wet tile map if null
		if (wetTileMap == null) {
			wetTileMap = new HashMap<String, String> ();
		}
		
		//Check the map for a cached result
		if (wetTileMap.containsKey (tileId)) {
			return wetTileMap.get (tileId);
		}
		
		//Find the proper result if not cached
		TileAttributesMap attributes = GameCode.getRoom ().getTileAttributesList ();
		TileData td = attributes.getTile (tileId);
		Object wateredTile = td.getProperties ().get ("watered");
		if (wateredTile != null) {
			String finalTile = "farming_tiles.png:" + wateredTile;
			wetTileMap.put (tileId, finalTile); //Cache the result
			return finalTile;
		}
		
		//Return null if the tile has no watered variant
		return null;
		
	}
	
	public static String getDryVersion (String tileId) {
		
		//Init dry tile map if null
		if (dryTileMap == null) {
			dryTileMap = new HashMap<String, String> ();
		}
		
		//Check the map for a cached result
		if (dryTileMap.containsKey (tileId)) {
			return dryTileMap.get (tileId);
		}
		
		//Find the proper result if not cached
		TileAttributesMap attributes = GameCode.getRoom ().getTileAttributesList ();
		TileData td = attributes.getTile (tileId);
		Object driedTile = td.getProperties ().get ("dried");
		if (driedTile != null) {
			String finalTile = "farming_tiles.png:" + driedTile;
			dryTileMap.put (tileId, finalTile); //Cache the result
			return finalTile;
		}
		
		//Return null if the tile has no watered variant
		return null;
		
	}
	
	public void dryMap () {
		int width = getRoom ().getWidth ();
		int height = getRoom ().getHeight ();
		for (int wy = 0; wy < height; wy++) {
			for (int wx = 0; wx < width; wx++) {
				dryTile (wx, wy);
			}
		}
	}
	
	public void wetMap () {
		int width = getRoom ().getWidth ();
		int height = getRoom ().getHeight ();
		for (int wy = 0; wy < height; wy++) {
			for (int wx = 0; wx < width; wx++) {
				waterTile (wx, wy);
			}
		}
	}
	
	public void waterTile (int tileX, int tileY) {
		
		//Update the tile
		for (int i = 0; i < 3; i++) {
			String strId = GameCode.getRoom ().getTileIdString (tileX, tileY, i);
			String wateredTile = Environment.getWetVersion (strId);
			if (wateredTile != null) {
				GameCode.getRoom ().setTile (i, tileX, tileY, wateredTile);
			}
		}
		
		//Update the crop at the tile
		GameCode.getCropHandler ().water (tileX, tileY);
		
	}
	
	public void dryTile (int tileX, int tileY) {
		for (int i = 0; i < 3; i++) {
			String strId = GameCode.getRoom ().getTileIdString (tileX, tileY, i);
			String driedTile = Environment.getDryVersion (strId);
			if (driedTile != null) {
				GameCode.getRoom ().setTile (i, tileX, tileY, driedTile);
			}
		}
	}
	
	@Override
	public void frameEvent () {
		
		//Load save data
		if (firstFrame) {
			load ();
			firstFrame = false;
			isLoaded = true;
		}
		
		//Other stuffs
		timeDisplay.setTime (getGameTime ());
		timeDisplay.setWeekDay (getWeekDay ());
		timeDisplay.setMonthDay (getMonthDay ());
		GameCode.getCropHandler ().frameEvent ();
		dayDurationMs += 33;
		if (dayDurationMs > 1440000) { //1440000 ms is midnight
			dayDurationMs = 360000;
			monthDay++;
			if (monthDay > 28) {
				monthDay = 1;
				monthCount++;
			}
			startDay ();
		}
		GameCode.getDebrisHandler ().frameEvent ();
		
		this.save ("" + monthCount + "," + monthDay);
	}

	public boolean isLoaded () {
		return isLoaded;
	}
	
	@Override
	public void draw () {
		Color c = todColor.getColor ((double)dayDurationMs / 1440000);
		Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		g.setColor (c);
		g.fillRect (0, 0, MainLoop.getWindow ().getResolution () [0], MainLoop.getWindow ().getResolution () [1]);
	}

	@Override
	public void load() {
		if (getSaveData () != null) {
			String[] parsed = getSaveData ().split (",");
			monthCount = Integer.parseInt (parsed [0]);
			monthDay = Integer.parseInt (parsed [1]);
		}
	}
	
}
