package gameObjects;

import java.util.Random;

import gui.Interactable;
import items.BeetSeeds;
import items.CarrotSeeds;
import items.CornSeeds;
import items.Hay;
import items.PotatoSeeds;
import items.PumpkinSeeds;
import items.StrawberrySeeds;
import items.TomatoSeeds;
import items.WatermelonSeeds;
import main.GameObject;

public class Bush extends Debris implements Interactable {
	
	public Bush () {
		createHitbox (0, 0, 16, 16);
		setSprite (getSprites ().bushSprite);
		getAnimationHandler ().setAnimationSpeed (0);
	}
	
	@Override
	public void onDeclare () {
		String type = getVariantAttribute ("type");
		if (type == null) {
			Random r = new Random ((long)(getX () * 476710937 + getY () * 98960489) + 13931213);
			type = "" + (int)(r.nextDouble () * getSprite ().getFrameCount());
			setVariantAttribute ("type", type);
		}
		getAnimationHandler ().setFrame (Integer.parseInt (type));
	}

	@Override
	public void hover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unhover() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		onHarvest ();
		getGui ().getInventory ().addItem (new Hay ());
		if (Math.random () < .1) {
			//Drop rolled for seeds (10%)
			int season = (int)(Math.random () * 3); //TODO swap based on actual season
			if (season == 0) {
				//Spring seeds
				int rollVal = (int)(Math.random () * 3);
				switch (rollVal) {
					case 0:
						getGui ().getInventory ().addItem (new CarrotSeeds ());
						break;
					case 1:
						getGui ().getInventory ().addItem (new PotatoSeeds ());
						break;
					case 2:
						getGui ().getInventory ().addItem (new StrawberrySeeds ());
						break;
				}
			}
			if (season == 1) {
				//Summer seeds
				int rollVal = (int)(Math.random () * 3);
				switch (rollVal) {
					case 0:
						getGui ().getInventory ().addItem (new TomatoSeeds ());
						break;
					case 1:
						getGui ().getInventory ().addItem (new WatermelonSeeds ());
						break;
					case 2:
						getGui ().getInventory ().addItem (new CornSeeds ());
						break;
				}
			}
			if (season == 2) {
				//Fall seeds
				int rollVal = (int)(Math.random () * 2);
				switch (rollVal) {
					case 0:
						getGui ().getInventory ().addItem (new BeetSeeds ());
						break;
					case 1:
						getGui ().getInventory ().addItem (new PumpkinSeeds ());
						break;
				}
			}
		}
		forget ();
	}

	@Override
	public boolean useDefaultHover() {
		return true;
	}

}
