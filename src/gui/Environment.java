package gui;

import java.awt.Color;
import java.awt.Graphics;

import main.GameObject;
import main.MainLoop;

public class Environment extends GameObject {
	
	private TimeOverlay timeDisplay;
	private int weekDay;
	private int monthDay = 1;
	private int monthCount = 0;
	
	private long dayDurationMs;
	
	private ColorMap todColor;
	
	public Environment () {
		
		todColor = new ColorMap ();
		todColor.addColor (new Color (0, 0, 10, 170), .15);
		todColor.addColor (new Color (0, 0, 40, 20), .33);
		todColor.addColor (new Color (0, 0, 0, 0), .4);
		todColor.addColor (new Color (0, 0, 0, 0), .75);
		todColor.addColor (new Color (0, 0, 40, 20), .8);
		todColor.addColor (new Color (0, 0, 10, 170), .85);
		todColor.addColor (new Color (0, 0, 10, 170), 1);
		
		dayDurationMs = 600000;
		
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
	
	public int getElapsedMonths () {
		return monthCount;
	}
	
	public void setTimeDisplay (TimeOverlay display) {
		timeDisplay = display;
	}
	
	public void startDay () {
		dayDurationMs = 360000;
		monthDay++;
		if (monthDay > 28) {
			monthDay = 1;
			monthCount++;
		}
	}
	
	@Override
	public void frameEvent () {
		timeDisplay.setTime (getGameTime ());
		timeDisplay.setWeekDay (getWeekDay ());
		timeDisplay.setMonthDay (getMonthDay ());
		dayDurationMs += 33;
		if (dayDurationMs > 1440000) { //1440000 ms is midnight
			startDay ();
		}
	}

	@Override
	public void draw () {
		Color c = todColor.getColor ((double)dayDurationMs / 1440000);
		Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		g.setColor (c);
		g.fillRect (0, 0, MainLoop.getWindow ().getResolution () [0], MainLoop.getWindow ().getResolution () [1]);
	}
	
}
