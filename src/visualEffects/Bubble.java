package visualEffects;

import main.GameObject;

public class Bubble extends GameObject {

	public Bubble () {
		setSprite (getSprites ().bubbleSprite);
		getAnimationHandler ().setAnimationSpeed (.2);
		getAnimationHandler ().setRepeat (false);
	}
	
	@Override
	public void frameEvent () {
		if (getAnimationHandler ().isDone ()) {
			forget ();
		}
	}
	
}
