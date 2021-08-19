package gui;

import java.util.ArrayList;

import items.GameItem;
import json.JSONException;
import json.JSONUtil;

public class Inventory extends GuiComponent implements ItemContainer {

	private ArrayList<GameItem> items;
	private Layout invLayout;
	
	public Inventory () {
		
		//Yeet
		items = new ArrayList<GameItem> ();
		for (int i = 0; i < 27; i++) {
			items.add (null);
		}
		
		//Init the layout
		try {
			invLayout = new Layout (JSONUtil.loadJSONFile ("resources/config/inventory_layout.json").getJSONArray ("layout"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void frameEvent () {
		if (mouseClicked ()) {
			int cell = invLayout.getCellContainingPoint (getMouseX (), getMouseY ());
			System.out.println (cell);
		}
	}
	
	@Override
	public boolean hasItem(GameItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasSimilar(GameItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int numItems(GameItem item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int numSimilar(GameItem item) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean addItem(GameItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeItem(GameItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeSimilar(GameItem item) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean replace(GameItem oldItem, GameItem newItem) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setItem(int index, GameItem item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public GameItem getSimilar(GameItem item) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameItem getItem(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void draw () {
		
		//Draw the inventory background
		getSprites ().inventory.draw ((int)getX (), (int)getY ());
		
		//Draw the inventory items
		for (int i = 0; i < 27; i++) {
			GameItem item = items.get (i);
			if (item != null) {
				int cellX = (int)(getX () + invLayout.getCells().get (i).getX ());
				int cellY = (int)(getY () + invLayout.getCells().get (i).getY ());
				item.getIcon ().draw (cellX, cellY);
			}
		}
		
	}

}
