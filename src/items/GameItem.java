package items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.HashMap;

import gameObjects.DamageSource;
import gameObjects.Damageable;
import main.GameAPI;
import main.MainLoop;
import resources.AnimationHandler;
import resources.Sprite;
import resources.Spritesheet;

public abstract class GameItem implements Damageable {
	private String name;
	private Sprite icon;
	private HashMap<String, String> properties;
	protected AnimationHandler animationHandler;
	private ItemType type;
	private static boolean usePerciseCompare = false;
	public static enum ItemType {
		WEAPON, EQUIPMENT, CONSUMABLE, MATERIAL, TOOL, OTHER;
	}
	private GameItem () {
		animationHandler = new AnimationHandler (null);
		animationHandler.setIgnorePause (true);
	}
	protected GameItem (ItemType type) {
		this ();
		this.name = this.getClass ().getSimpleName ();
		setIcon (new Sprite ("resources/sprites/items/" + this.name + ".png"));
		this.type = type;
		this.properties = new HashMap<String, String> ();
	}
	protected GameItem (Sprite icon, ItemType type) {
		this ();
		this.name = this.getClass ().getSimpleName ();
		setIcon (icon);
		this.type = type;
		this.properties = new HashMap<String, String> ();
	}
	protected GameItem (String name, Sprite icon, ItemType type) {
		this ();
		this.name = name;
		setIcon (icon);
		this.type = type;
		this.properties = new HashMap<String, String> ();
	}
	protected GameItem (GameItem item) {
		this ();
		this.name = item.name;
		setIcon (item.icon);
		this.type = item.type;
		this.properties = item.properties;
	}
	public String getProperty (String propertyName) {
		return properties.get (propertyName);
	}
	public void setProperty (String propertyName, String value) {
		properties.put (propertyName, value);
	}
	public String getName () {
		return name;
	}
	public Sprite getIcon () {
		return icon;
	}
	public HashMap<String, String> getProperties () {
		return properties;
	}
	public ItemType getType () {
		return type;
	}
	public void setProperties (HashMap<String, String> properties) {
		this.properties = properties;
	}
	public static int getValue (ItemType type) {
		switch (type) {
			case WEAPON:
				return 0;
			case EQUIPMENT:
				return 1;
			case CONSUMABLE:
				return 2;
			case MATERIAL:
				return 3;
			case TOOL:
				return 4;
			default:
				return 0;
		}
			
	}
	public static GameItem getItemByName (String name, GameItem[] items) {
		for (int i = 0; i < items.length; i ++) {
			if (items [i].getName ().equals (name)) {
				return items [i];
			}
		}
		return null;
	}
	protected void setName (String name) {
		this.name = name;
	}
	protected void setIcon (Sprite icon) {
		if (icon.getWidth () > 16 || icon.getHeight () > 16) {
			Spritesheet sheet = new Spritesheet (icon.getImageArray ()[0]);
			icon = new Sprite (sheet, 16, 16);
		}
		this.icon = icon;
		animationHandler.setSprite (icon);
	}
	protected void setAnimationSpeed (double speed) {
		animationHandler.setAnimationSpeed (speed);
	}
	public boolean use () {
		if (getCount () == 1) {
			GameAPI.getGui ().getInventory ().removeItem (this);
		} else {
			this.removeOne ();
		}
		return true;
	}
	@Override
	public boolean equals (Object o) {
		if (o.getClass ().getName ().equals (this.getClass ().getName ())) {
			//TODO better solution to durability problem please!
			if (this.type == ((GameItem)o).type && this.name.equals (((GameItem)o).getName ())) {
				if (usePerciseCompare) {
					if (this.properties.equals (((GameItem)o).getProperties ())) {
						return true;
					} else {
						return false;
					}
				} else {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void damageEvent (DamageSource source) {
		//Damage source could be the crafting menu used?
		if (getHealth () <= 0) {
			GameAPI.getGui ().getInventory ().removeItem (this);
		}
	}
	
	public int getIntProperty (String name) {
		String property = getProperty (name);
		return property == null ? 1 : Integer.parseInt (property);
	}
	
	public int getMaxStack () {
		return getIntProperty ("maxStack");
	}
	
	public int getCount () {
		return getIntProperty ("count");
	}
	
	public void setMaxStack (int amt) {
		properties.put ("maxStack", "" + amt);
	}
	
	public void setCount (int amt) {
		properties.put ("count", "" + amt);
	}
	
	public boolean removeOne () {
		int newAmt = getCount () - 1;
		if (newAmt < 0) {
			return false;
		}
		setCount (newAmt);
		return true;
	}
	
	public boolean addOne () {
		int newAmt = getCount () + 1;
		if (newAmt > getMaxStack ()) {
			return false;
		}
		setCount (newAmt);
		return true;
	}
	
	@Override
	public void damage (double amount) {
		if (!getProperty ("health").equals ("")) {
			setProperty ("health", String.valueOf (Double.parseDouble (getProperty ("health")) - amount));
		}
		//TODO damage amount
		damageEvent (null);
	}
	
	@Override
	public double getHealth () {
		if (!getProperty ("health").equals ("")) {
			return Double.parseDouble (getProperty ("health"));
		} else {
			return Double.NaN;
		}
	}
	
	@Override
	public double getMaxHealth () {
		if (!getProperty ("maxHealth").equals ("")) {
			return Double.parseDouble (getProperty ("maxHealth"));
		} else {
			return Double.NaN;
		}
	}
	
	@Override
	public void setHealth (double health) {
		setProperty ("health", String.valueOf (health));
	}
	
	@Override
	public void setMaxHealth (double maxHealth) {
		setProperty ("maxHealth", String.valueOf (maxHealth));
	}
	
	public void draw (int x, int y) {
		getIcon ().draw (x, y);
		if (getCount () > 1) {
			Graphics g = MainLoop.getWindow ().getBufferGraphics ();
			Font f = new Font ("Arial", 10, 8);
			g.setFont (f);
			g.setColor (Color.WHITE);
			g.drawString (getProperty ("count"), x + 10, y + 15);
		}
	}
	
}