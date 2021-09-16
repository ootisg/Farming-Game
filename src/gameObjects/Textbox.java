package gameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Scanner;

import gui.GuiComponent;
import main.GameObject;
import main.MainLoop;

public class Textbox extends GuiComponent {

	//NOTE: not intended to be declared
	
	public static final int TBOX_SCROLLING = 0;
	public static final int TBOX_WAITING = 1;
	
	private String text;
	private int framePos;
	private int cursorPos;
	private int cursorTime;
	
	private int state = TBOX_SCROLLING;
	
	public static final int PADDING_TOP = 8;
	public static final int PADDING_LEFT = 8;
	public static final int TEXT_AREA_WIDTH = 444;
	
	public Textbox () {
		setX (24);
		setY (132);
		framePos = 0;
		cursorPos = 0;
		setSprite (getSprites ().textboxSprite);
	}
	
	public void openBox (String text) {
		this.text = text;
		framePos = 0;
		cursorPos = 0;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public String getDisplayText (int startPos, int endPos) {
		return text.substring (startPos, endPos);
	}
	
	@Override
	public void frameEvent () {
		super.frameEvent ();
		if (state == TBOX_SCROLLING) {
			cursorTime++;
			if (cursorTime > 5) {
				cursorPos++;
			}
			if (cursorPos >= text.length ()) {
				state = TBOX_WAITING;
			}
		} else if (state == TBOX_WAITING) {
			//Do nothing TODO make responsive
		}
	}
	
	@Override
	public void draw () {
		
		//Draw the background
		getSprite ().draw ((int)getX (), (int)getY ());
		
		//Setup for text
		Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		Font f = new Font ("Arial", 12, 12);
		g.setFont (f);
		FontMetrics fm = g.getFontMetrics();
		
		//Calculate text to draw
		ArrayList<String> lines = new ArrayList<String> ();
		Scanner s = new Scanner (getDisplayText (framePos, cursorPos)); //TODO make this use displayText
		String currLine = "";
		while (s.hasNext ()) {
			String word = s.next ();
			String withWord = currLine + word;
			if (fm.stringWidth (withWord) > TEXT_AREA_WIDTH) {
				lines.add (currLine);
				currLine = word + " ";
			} else {
				currLine = withWord + " ";
			}
		}
		if (!currLine.isEmpty ()) {
			lines.add (currLine);
		}
		s.close ();
		
		//Draw text
		g.setColor (Color.BLACK);
		for (int i = 0; i < lines.size (); i++) {
			g.drawString (lines.get (i), (int)getX () + PADDING_LEFT, (int)getY () + fm.getAscent () + PADDING_TOP + i * fm.getHeight ());
		}
		
	}
	
}
