//A container class for sprites

package resources;

public class GlobalSprites {
	//Please alphebitize spritesheets and sprites
	//Spritesheets
	public Spritesheet bubbleSheet = new Spritesheet ("resources/sprites/bubbles.png");
	public Spritesheet bushSheet = new Spritesheet ("resources/sprites/bush.png");
	public Spritesheet pineTreeSheet = new Spritesheet ("resources/sprites/pine_tree.png");
	public Spritesheet playerSheet = new Spritesheet ("resources/sprites/walk_sheet.png");
	public Spritesheet playerArmSheet = new Spritesheet ("resources/sprites/arms_sheet.png");
	public Spritesheet rockSheet = new Spritesheet ("resources/sprites/rock.png");
	public Spritesheet stumpSheet = new Spritesheet ("resources/sprites/stump.png");
	public Spritesheet swordArmSheet = new Spritesheet ("resources/sprites/swordarmsheet.png");
	public Spritesheet textSheet = new Spritesheet ("resources/sprites/text.png");
	public Spritesheet treeSheet = new Spritesheet ("resources/sprites/regular_tree.png");
	//Sprites
	public Sprite bubbleSprite = new Sprite (bubbleSheet, 16, 16);
	public Sprite bushSprite = new Sprite (bushSheet, 16, 16);
	public Sprite pineTreeSprite = new Sprite (pineTreeSheet, 32, 48);
	public Sprite playerIdle = new Sprite (playerSheet, new int[] {0, 0, 0, 0}, new int[] {0, 16, 32, 48}, 16, 16);
	public Sprite playerArmsIdle = new Sprite (playerArmSheet, new int[] {0, 0, 0, 0}, new int[] {0, 16, 32, 48}, 16, 16);
	public Sprite rockSprite = new Sprite (rockSheet, 16, 16);
	public Sprite stumpSprite = new Sprite (stumpSheet, 16, 16);
	public Sprite treeSprite = new Sprite (treeSheet, 32, 48);
	public Sprite[] playerWalkSprites = new Sprite[] {
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {0, 0, 0, 0}, 16, 16),
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {16, 16, 16, 16}, 16, 16),
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {32, 32, 32, 32}, 16, 16),
			new Sprite (playerSheet, new int[] {16, 0, 32, 0}, new int[] {48, 48, 48, 48}, 16, 16)
	};
	public Sprite[] playerArmSprites = new Sprite[] {
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {0, 0, 0, 0}, 16, 16),
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {16, 16, 16, 16}, 16, 16),
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {32, 32, 32, 32}, 16, 16),
			new Sprite (playerArmSheet, new int[] {16, 0, 32, 0}, new int[] {48, 48, 48, 48}, 16, 16)
	};
	public Sprite[] swordArmSprites = new Sprite[] {
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {0, 0, 0}, 16, 16),
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {16, 16, 16}, 16, 16),
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {32, 32, 32}, 16, 16),
			new Sprite (swordArmSheet, new int[] {0, 16, 32}, new int[] {48, 48, 48}, 16, 16)
	};
	public GlobalSprites () {
		
	}
}
