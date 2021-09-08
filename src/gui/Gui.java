package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import crops.GrowingPotato;
import gameObjects.GlobalSave;
import items.GameItem;
import json.JSONObject;
import main.GameAPI;
import main.GameObject;
import main.GameWindow;
import main.MainLoop;
import main.TextInterface;
import resources.Sprite;
import resources.Spritesheet;

public class Gui extends GameObject {
	public TextInterface textInterface;
	private boolean guiOpen;
	private Inventory inventory;
	private Hotbar hotbar;
	private TimeOverlay timeOverlay;
	private Environment environment;
	public Gui () {
		this.declare (0, 0);
		this.setPersistent (true);
		this.setPriority (-420);
		inventory = new Inventory ();
		hotbar = new Hotbar ();
		timeOverlay = new TimeOverlay ();
		environment = new Environment ();
		environment.setTimeDisplay (timeOverlay);
	}
	@Override
	public void frameEvent () {
		if (keyPressed('E')) {
			guiOpen = true;
			MainLoop.pause ();
		}
		if (!guiOpen) {
			environment.frameEvent (); //Environment state only advances when not paused
			hotbar.frameEvent ();
		}
		
		if (GameAPI.mouseClicked () && !GameAPI.getGui ().getHotbar ().wasClicked ()) {
			GameItem it = hotbar.getSelectedItem ();
			if (it != null) {
				it.use ();
			}
		}
		
	}
	@Override
	public void pauseEvent () {
		if (guiOpen && (keyPressed ('E') || keyPressed (KeyEvent.VK_ESCAPE))) {
			guiOpen = false;
			GlobalSave.saveInv (getInventory ().toString ());
			MainLoop.resume ();
		}
		if (guiOpen) {
			inventory.frameEvent ();
		}
	}
	@Override
	public void draw () {
		if (guiOpen) {
			inventory.draw ();
		} else {
			hotbar.draw ();
		}
		timeOverlay.draw ();
		
		//Draw the environment overlay effects after everything else
		environment.draw ();
	}
	public void drawText (String text, int x, int y) {
		for (int i = 0; i < text.length (); i ++) {
			textInterface.drawChar (text.charAt (i), x + i * 8, y);
		}
	}
	public void openGui () {
		guiOpen = true;
	}
	public void closeGui () {
		guiOpen = false;
	}
	public boolean guiOpen () {
		return guiOpen;
	}
	public ItemContainer getInventory () {
		return inventory;
	}
	public Environment getEnvironment () {
		return environment;
	}
	public Hotbar getHotbar () {
		return hotbar;
	}
}