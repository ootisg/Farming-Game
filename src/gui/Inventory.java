package gui;

import java.util.ArrayList;

import items.Avocado;
import items.BeetSeeds;
import items.CarrotSeeds;
import items.CornSeeds;
import items.GameItem;
import items.Peach;
import items.PotatoSeeds;
import items.PumpkinSeeds;
import items.Strawberry;
import items.StrawberrySeeds;
import items.TomatoSeeds;
import items.WateringCan;
import items.WatermelonSeeds;
import json.JSONException;
import json.JSONUtil;

public class Inventory extends GuiComponent implements ItemContainer {

	private ArrayList<GameItem> items;
	private Layout invLayout;
	
	private boolean clicked;
	
	private GameItem heldItem;
	
	public Inventory () {
		
		//Set the bg sprite
		setSprite (getSprites ().inventory);
		setY (9);
		
		//Initialize the items list
		items = new ArrayList<GameItem> ();
		for (int i = 0; i < 33; i++) {
			items.add (null);
		}
		
		//Init the layout
		try {
			invLayout = new Layout (JSONUtil.loadJSONFile ("resources/config/inventory_layout.json").getJSONArray ("layout"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		addItem (new CarrotSeeds ());
		addItem (new TomatoSeeds ());
		addItem (new StrawberrySeeds ());
		addItem (new PumpkinSeeds ());
		addItem (new CornSeeds ());
		addItem (new PotatoSeeds ());
		addItem (new WatermelonSeeds ());
		addItem (new BeetSeeds ());
		addItem (new WateringCan ());
		
	}
	
	public boolean wasClicked () {
		return clicked;
	}
	
	@Override
	public String toString () {
		StringBuilder invBuilder = new StringBuilder ();
		for (int i = 0; i < items.size (); i++) {
			GameItem curr = items.get (i);
			if (curr == null) {
				invBuilder.append ("null");
			} else {
				invBuilder.append (curr.getClass ().getSimpleName ());
				invBuilder.append (" ");
				invBuilder.append (curr.getCount ());
			}
			if (i != items.size () - 1) {
				invBuilder.append (",");
			}
		}
		return invBuilder.toString ();
	}
	
	public void loadFromString (String s) {
		if (s != null && !s.isEmpty ()) {
			String[] split = s.split (",");
			for (int i = 0; i < items.size (); i++) {
				if (split [i].equals ("null")) {
					items.set (i, null);
				} else {
					String[] params = split [i].split (" ");
					GameItem it = GameItem.makeGameItem ("items." + params [0]);
					it.setCount (Integer.parseInt (params [1]));
					items.set (i, it);
				}
			}
		}
	}
	
	@Override
	public void onDeclare () {

	}
	
	@Override
	public void frameEvent () {
		clicked = false;
		if (mouseClicked ()) {
			if (getBounds ().contains (getMouseX (), getMouseY ())) {
				clicked = true;
			}
			int cell = invLayout.getCellContainingPoint (getMouseX () - (int)getX (), getMouseY () - (int)getY ());
			if (cell != -1) {
				//TODO improve inventory UI functionality, it is very primitive
				if (heldItem == null) {
					if (getItem (cell) != null) {
						heldItem = getItem (cell);
						setItem (cell, null);
					}
				} else {
					if (getItem (cell) == null) {
						setItem (cell, heldItem);
						heldItem = null;
					} else {
						GameItem temp = heldItem;
						heldItem = getItem (cell);
						setItem (cell, temp);
					}
				}
			}
			System.out.println (cell);
		}
	}
	
	@Override
	public boolean hasItem(GameItem item) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) == item) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean hasSimilar(GameItem item) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) != null && items.get (i).getClass ().equals (item.getClass ())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int numItems(GameItem item) {
		int count = 0;
		for (int i = 0; i < 27; i++) {
			if (items.get (i) == item) {
				count += items.get (i).getCount ();
			}
		}
		return count;
	}

	@Override
	public int numSimilar(GameItem item) {
		int count = 0;
		for (int i = 0; i < 27; i++) {
			if (items.get (i) != null && items.get (i).getClass ().equals (item.getClass ())) {
				count += items.get (i).getCount ();
			}
		}
		return count;
	}

	@Override
	public boolean addItem(GameItem item) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) != null && items.get (i).getClass ().equals (item.getClass ())) {
				while (item.getCount () != 0) {
					if (!items.get (i).addOne ()) {
						break;
					}
					item.removeOne ();
				}
			}
		}
		if (item.getCount () == 0) {
			return true;
		}
		for (int i = 0; i < 27; i++) {
			if (items.get (i) == null) {
				items.set (i, item);
				return true;
			}
		}
		return item.getCount () == 0;
	}

	@Override
	public boolean removeItem(GameItem item) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) == item) {
				items.set (i, null);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean removeSimilar(GameItem item) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) != null && items.get (i).getClass ().equals (item.getClass ())) {
				while (item.getCount () != 0) {
					if (!items.get (i).removeOne ()) {
						break;
					}
				}
			}
		}
		return item.getCount () == 0;
	}

	@Override
	public boolean replace(GameItem oldItem, GameItem newItem) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) == oldItem) {
				items.set (i, newItem);
				return true;
			}
		}
		return false;
	}

	@Override
	public void setItem(int index, GameItem item) {
		items.set (index, item);
	}

	@Override
	public GameItem getSimilar(GameItem item) {
		for (int i = 0; i < 27; i++) {
			if (items.get (i) != null && items.get (i).getClass ().equals (item.getClass ())) {
				return items.get (i);
			}
		}
		return null;
	}

	@Override
	public GameItem getItem(int index) {
		return items.get (index);
	}
	
	@Override
	public void draw () {
		
		//Draw the inventory background
		getSprites ().inventory.draw ((int)getX (), (int)getY ());
		
		//Draw the inventory items
		for (int i = 0; i < 33; i++) {
			GameItem item = items.get (i);
			if (item != null) {
				int cellX = (int)(getX () + invLayout.getCells().get (i).getX ());
				int cellY = (int)(getY () + invLayout.getCells().get (i).getY ());
				item.draw (cellX, cellY);
			}
		}
		
		if (heldItem != null) {
			heldItem.draw (getMouseX(), getMouseY());
		}
		
	}

}
