package gui;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gameObjects.GlobalSave;
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
	public Gui () {
		this.declare (0, 0);
		this.setPersistent (true);
		inventory = new Inventory ();
	}
	@Override
	public void frameEvent () {
		if (keyPressed('E')) {
			guiOpen = true;
			MainLoop.pause ();
		}
	}
	@Override
	public void pauseEvent () {
		if (guiOpen && (keyPressed ('E') || keyPressed (KeyEvent.VK_ESCAPE))) {
			guiOpen = false;
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
		}
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
		return null; //TODO
	}
}