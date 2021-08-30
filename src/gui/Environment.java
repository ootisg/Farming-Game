package gui;

import main.GameObject;

public class Environment extends GameObject {
	
	private TimeOverlay timeDisplay;
	private int weekDay;
	private int monthDay = 1;
	
	private long dayDurationMs;
	
	public Environment () {
		
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
	
	public void setTimeDisplay (TimeOverlay display) {
		timeDisplay = display;
	}
	
	public void startDay () {
		dayDurationMs = 0;
		monthDay++;
		if (monthDay > 28) {
			monthDay = 1;
		}
	}
	
	@Override
	public void frameEvent () {
		timeDisplay.setTime (getGameTime ());
		timeDisplay.setWeekDay (getWeekDay ());
		timeDisplay.setMonthDay (getMonthDay ());
		dayDurationMs += 33;
		if (dayDurationMs > 1440 * 1000) { //1440000 ms is midnight
			startDay ();
		}
	}

}
