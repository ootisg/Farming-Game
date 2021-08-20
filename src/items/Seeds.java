package items;

public abstract class Seeds extends GameItem {

	public Seeds () {
		super (ItemType.CONSUMABLE);
		setMaxStack (99);
	}
	
}
