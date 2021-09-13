package gameObjects;

import gui.GuiComponent;
import main.GameObject;

public class Textbox extends GuiComponent {

	//NOTE: not intended to be declared
	
	String text;
	int framePos;
	int cursorPos;
	
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
	
	public void getDisplayText (int startPos, int endPos) {
		
	}
	
	@Override
	public void frameEvent () {
		super.frameEvent ();
	}
	
	@Override
	public void draw () {
		getSprite ().draw ((int)getX (), (int)getY ());
	}
	
}
