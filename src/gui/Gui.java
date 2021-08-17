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
	public Gui () {
		this.declare (0, 0);
	}
	public void frameEvent () {

	}
	public void pauseEvent () {
		
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