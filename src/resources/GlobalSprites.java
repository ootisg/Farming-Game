//A container class for sprites

package resources;

public class GlobalSprites {
	//Please alphebitize spritesheets and sprites
	//Spritesheets
	public Spritesheet playerSheet = new Spritesheet ("resources/sprites/walk_sheet.png");
	public Spritesheet playerArmSheet = new Spritesheet ("resources/sprites/arms_sheet.png");
	public Spritesheet swordArmSheet = new Spritesheet ("resources/sprites/swordarmsheet.png");
	public Spritesheet textSheet = new Spritesheet ("resources/sprites/text.png");
	//Sprites
	public Sprite playerIdle = new Sprite (playerSheet, new int[] {0, 0, 0, 0}, new int[] {0, 16, 32, 48}, 16, 16);
	public Sprite playerArmsIdle = new Sprite (playerArmSheet, new int[] {0, 0, 0, 0}, new int[] {0, 16, 32, 48}, 16, 16);
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
