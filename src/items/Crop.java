package items;

public abstract class Crop extends GameItem {
	
	public Crop () {
		super (ItemType.CONSUMABLE);
		setMaxStack (99);
	}

}
