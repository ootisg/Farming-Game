package gui;

import java.awt.Rectangle;

import items.GameItem;
import json.JSONException;
import json.JSONUtil;

public class Hotbar extends GuiComponent {

	private Layout barLayout;
	
	private int selection = -1;
	
	private boolean clicked;
	
	public Hotbar () {
		
		//Set the bg sprite
		setSprite (getSprites ().hotbarSprite);
		
		//Init the layout
		try {
			barLayout = new Layout (JSONUtil.loadJSONFile ("resources/config/hotbar_layout.json").getJSONArray ("layout"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setX (0);
		setY (203);
		
	}
	
	public boolean wasClicked () {
		return clicked;
	}
	
	public GameItem getSelectedItem () {
		return selection == -1 ? null : getGui ().getInventory ().getItem (selection);
	}
	
	@Override
	public void frameEvent () {
		clicked = false;
		if (mouseClicked ()) {
			if (getBounds ().contains (getMouseX (), getMouseY ())) {
				clicked = true;
			}
			int cell = barLayout.getCellContainingPoint (getMouseX () - (int)getX (), getMouseY () - (int)getY ());
			if (cell != -1) {
				selection = cell;
			}
			System.out.println (cell);
		}
	}
	
	public void draw () {
		
		//Draw the inventory background
		getSprites ().hotbarSprite.draw ((int)getX (), (int)getY ());
		
		//Draw the inventory items
		for (int i = 0; i < 33; i++) {
			GameItem item = getPlayer ().getGui ().getInventory ().getItem (i);
			if (item != null) {
				int cellX = (int)(getX () + barLayout.getCells().get (i).getX ());
				int cellY = (int)(getY () + barLayout.getCells().get (i).getY ());
				item.draw (cellX, cellY);
			}
		}
		
		//Draw the highlight
		if (selection != -1) {
			Rectangle r = barLayout.getCells ().get (selection);
			getSprites ().hotbarHighlight.draw ((int)getX () + r.x - 1, (int)getY () + r.y - 1);
		}
		
	}
	
}
