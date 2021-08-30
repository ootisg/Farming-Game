package gui;

import java.awt.Point;

import main.GameObject;

public class TimeOverlay extends GameObject {

	public static final Point UI_HOUR_POS = new Point (6, 7);
	public static final Point UI_MINUTE_POS = new Point (22, 7);
	public static final Point UI_AM_PM_POS = new Point (38, 7);
	public static final Point UI_WEEKDAY_POS = new Point (4, 21);
	public static final Point UI_MONTH_DAY_POS = new Point (30, 21);
	
	private int dayTime = 0;
	private int weekDay = 1;
	private int monthDay = 1;
	
	public void TimeOverlay () {
		
	}
	
	public void drawNumber (String num, int x, int y) {
		for (int i = 0; i < num.length (); i++) {
			int digit = num.charAt (i) - '0';
			getSprites ().uiNumSprite.draw (x + i * 6, y, digit);
		}
	}
	
	public String getTwoDigitStringRep (int val) {
		return val < 10 ? "0" + val : "" + val;
	}
	
	public String getHour () {
		int hourInt = (dayTime / 60) % 12;
		if (hourInt == 0) {
			hourInt = 12;
		}
		return getTwoDigitStringRep (hourInt);
	}
	
	public String getMinute () {
		return getTwoDigitStringRep (dayTime % 60);
	}
	
	public boolean isPM () {
		return dayTime >= 720;
	}
	
	public void setTime (int time) {
		dayTime = time;
	}
	
	public void setWeekDay (int day) {
		weekDay = day;
	}
	
	public void setMonthDay (int day) {
		monthDay = day;
	}
	
	@Override
	public void draw () {
		//Draw the bg
		getSprites ().uiTimeSprite.draw ((int)getX (), (int)getY ());
		//Draw the hour and minute
		drawNumber (getHour (), (int)getX () + UI_HOUR_POS.x, (int)getY () + UI_HOUR_POS.y);
		drawNumber (getMinute (), (int)getX () + UI_MINUTE_POS.x, (int)getY () + UI_HOUR_POS.y);
		//Draw the AM/PM
		if (isPM ()) {
			getSprites ().uiPmAmSprite.draw ((int)getX () + UI_AM_PM_POS.x, (int)getY () + UI_AM_PM_POS.y, 0);
		} else {
			getSprites ().uiPmAmSprite.draw ((int)getX () + UI_AM_PM_POS.x, (int)getY () + UI_AM_PM_POS.y, 1);
		}
		//Draw the weekday
		getSprites ().uiWeekSprite.draw ((int)getX () + UI_WEEKDAY_POS.x, (int)getY () + UI_WEEKDAY_POS.y, weekDay);
		//Draw the day of the month
		drawNumber (getTwoDigitStringRep (monthDay), (int)getX () + UI_MONTH_DAY_POS.x, (int)getY () + UI_MONTH_DAY_POS.y);
	}
	
}
