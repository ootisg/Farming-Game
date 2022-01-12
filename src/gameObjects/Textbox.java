package gameObjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;

import gui.GuiComponent;
import main.GameObject;
import main.MainLoop;

public class Textbox extends GuiComponent {

	//TODO crash if textbox text starts with instruction
	//NOTE: not intended to be declared
	
	public static final int TBOX_SCROLLING = 0;
	public static final int TBOX_WAITING_ADVANCE = 1;
	public static final int TBOX_WAITING_CLOSE = 2;
	public static final int TBOX_WAITING_TIMER = 3;
	public static final int TBOX_INSTANT_SCROLL = 4;
	public static final int TBOX_SELECTION_MODE = 5;
	public static final int TBOX_FORCE_CLOSED = 6;
	
	private String text;
	private int framePos;
	private int cursorPos;
	private int cursorTime;
	private int waitTime;
	
	private int state = TBOX_SCROLLING;
	
	private ArrayList<Integer> selectableLines = new ArrayList<Integer> ();
	private int selectedLine = -1;
	private int lastSelection = -1;
	
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
		state = TBOX_SCROLLING;
	}
	
	public void setText (String text) {
		this.text = text;
	}
	
	public String getDisplayText (int startPos, int endPos) {
		String frameText = text.substring (startPos, endPos);
		StringBuilder retText = new StringBuilder ();
		boolean isCommand = false;
		for (int i = 0; i < frameText.length (); i++) {
			if (frameText.charAt (i) == '&') {
				isCommand = !isCommand;
			} else if (!isCommand) {
				retText.append (frameText.charAt (i));
			}
		}
		return retText.toString ();
	}
	
	public int getLineOf (int y) {
		Graphics g = MainLoop.getWindow ().getBufferGraphics ();
		Font f = new Font ("Arial", 12, 12);
		g.setFont (f);
		FontMetrics fm = g.getFontMetrics();
		int startY = PADDING_TOP;
		int lineHeight = fm.getHeight ();
		int adjY = y - startY;
		return adjY / lineHeight;
	}
	
	public int getSelection () {
		return lastSelection;
	}
	
	@Override
	public void frameEvent () {
		
		if (state == TBOX_SELECTION_MODE) {
			int lineIdx = getLineOf (getMouseY () - (int)getY ());
			if (selectableLines.contains (lineIdx)) {
				selectedLine = lineIdx;
			}
			if (mouseClicked () && selectableLines.contains (lineIdx)) {
				lastSelection = lineIdx;
				selectedLine = -1;
				state = TBOX_SCROLLING;
			}
		}
		
		super.frameEvent ();
		if (state == TBOX_SCROLLING) {
			
			//Increase the frame count
			cursorTime++;
			
			//Increment the cursor every 5 frames
			if (cursorTime > 5) {
				
				//Increment the cursor position
				cursorPos++;
				
				//Instantly run and scroll over any special instructions, if needed
				if (cursorPos < text.length () && text.charAt (cursorPos) == '&') {
					cursorPos++;
					StringBuilder instruction = new StringBuilder ();
					while (text.charAt (cursorPos) != '&') {
						instruction.append (text.charAt (cursorPos));
						cursorPos++;
					}
					boolean nextChar = runInstruction (instruction.toString ());
					if (nextChar) {
						cursorPos++;
					}
					if (state == TBOX_INSTANT_SCROLL) {
						while (cursorPos < text.length ()) {
							cursorPos++;
							if (text.substring (cursorPos - 5, cursorPos + 1).equals ("&skip&")) {
								System.out.println ("CHAR: " + text.charAt (cursorPos));
								state = TBOX_SCROLLING;
								break;
							}
						}
					}
					
				}
				
			}
			
			//Enter waiting mode if text length is too long
			if (cursorPos >= text.length ()) {
				state = TBOX_WAITING_CLOSE;
			}
			
		} else if (state == TBOX_WAITING_ADVANCE) {
			//Wait for enter key
			if (keyPressed (KeyEvent.VK_ENTER)) {
				cursorPos++;
				framePos = cursorPos;
				state = TBOX_SCROLLING;
			}
		} else if (state == TBOX_WAITING_CLOSE) {
			//Do nothing (handled by parent)
		} else if (state == TBOX_WAITING_TIMER) {
			//Wait for timer
			if (waitTime == 0) {
				state = TBOX_SCROLLING;
			} else {
				waitTime--;
			}
		}
	}
	
	public int getState () {
		return state;
	}
	
	//Returns true if the textbox should advance a character
	private boolean runInstruction (String instruction) {
		if (instruction.equals ("fEnd")) {
			//fEnd marks the end of a text frame, textbox will wait for input before continuing.
			state = TBOX_WAITING_ADVANCE;
			return false;
		} else if (instruction.length () >= 4 && instruction.substring (0, 4).equals ("wait")) {
			//wait <fc> pauses the text scrolling for fc frames 
			String[] params = instruction.split (" ");
			int waitTime = Integer.parseInt (params [1]);
			this.waitTime = waitTime;
			state = TBOX_WAITING_TIMER;
			return false;
		} else if (instruction.length () >= 4 && instruction.substring (0, 4).equals ("skip")) {
			state = TBOX_INSTANT_SCROLL;
			return false;
		} else if (instruction.length () >= 3 && instruction.substring (0, 3).equals ("sel")) {
			state = TBOX_SELECTION_MODE;
			selectableLines = new ArrayList<Integer> ();
			for (int i = 3; i < instruction.length (); i++) {
				int lineIdx = instruction.charAt (i) - '0';
				selectableLines.add (lineIdx);
			}
			return false;
		} else if (instruction.length () >= 5 && instruction.substring (0, 5).equals ("close")) {
			state = TBOX_FORCE_CLOSED;
			return false;
		}
		return true; //Default case
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
		Scanner s = new Scanner (getDisplayText (framePos, cursorPos));
		Scanner lineScanner = new Scanner (getDisplayText (framePos, cursorPos));
		String currLine = "";
		while (lineScanner.hasNextLine ()) {
			s = new Scanner (lineScanner.nextLine ());
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
			currLine = "";
		}
		s.close ();
		
		//Draw text
		g.setColor (Color.BLACK);
		for (int i = 0; i < lines.size (); i++) {
			if (i == selectedLine) {
				g.setColor (new Color (0x00, 0x00, 0x00, 0x40));
				g.fillRect ((int)getX () + PADDING_LEFT, (int)getY () + PADDING_TOP + i * fm.getHeight (), TEXT_AREA_WIDTH, fm.getHeight ());
			}
			g.setColor (Color.BLACK);
			g.drawString (lines.get (i), (int)getX () + PADDING_LEFT, (int)getY () + fm.getAscent () + PADDING_TOP + i * fm.getHeight ());
		}
		
	}
	
}
