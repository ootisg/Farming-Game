//A container class for sprites

package resources;

public class GlobalSprites {
	//Please alphebitize spritesheets and sprites
	//Spritesheets
	public Spritesheet bubbleSheet = new Spritesheet ("resources/sprites/bubbles.png");
	public Spritesheet bushSheet = new Spritesheet ("resources/sprites/bush.png");
	public Spritesheet cropSheetCarrot = new Spritesheet ("resources/sprites/crops/carrot.png");
	public Spritesheet cropSheetTomato = new Spritesheet ("resources/sprites/crops/tomato.png");
	public Spritesheet cropSheetStrawberry = new Spritesheet ("resources/sprites/crops/strawberry.png");
	public Spritesheet cropSheetPumpkin = new Spritesheet ("resources/sprites/crops/pumpkin.png");
	public Spritesheet cropSheetCorn = new Spritesheet ("resources/sprites/crops/corn.png");
	public Spritesheet cropSheetPotato = new Spritesheet ("resources/sprites/crops/potato.png");
	public Spritesheet cropSheetWatermelon = new Spritesheet ("resources/sprites/crops/watermelon.png");
	public Spritesheet cropSheetBeet = new Spritesheet ("resources/sprites/crops/beet.png");
	public Spritesheet pineTreeSheet = new Spritesheet ("resources/sprites/pine_tree.png");
	public Spritesheet playerSheet = new Spritesheet ("resources/sprites/walk_sheet.png");
	public Spritesheet playerArmSheet = new Spritesheet ("resources/sprites/arms_sheet.png");
	public Spritesheet rockSheet = new Spritesheet ("resources/sprites/rock.png");
	public Spritesheet stumpSheet = new Spritesheet ("resources/sprites/stump.png");
	public Spritesheet swordArmSheet = new Spritesheet ("resources/sprites/swordarmsheet.png");
	public Spritesheet textSheet = new Spritesheet ("resources/sprites/text.png");
	public Spritesheet treeSheet = new Spritesheet ("resources/sprites/regular_tree.png");
	public Spritesheet uiSheet = new Spritesheet ("resources/sprites/ui.png");
	//Sprites
	public Sprite bubbleSprite = new Sprite (bubbleSheet, 16, 16);
	public Sprite bushSprite = new Sprite (bushSheet, 16, 16);
	public Sprite cropSpriteCarrot = new Sprite (cropSheetCarrot, 16, 16);
	public Sprite cropSpriteTomato = new Sprite (cropSheetTomato, 16, 16);
	public Sprite cropSpriteStrawberry = new Sprite (cropSheetStrawberry, 16, 16);
	public Sprite cropSpritePumpkin = new Sprite (cropSheetPumpkin, 16, 16);
	public Sprite cropSpriteCorn = new Sprite (cropSheetCorn, 16, 16);
	public Sprite cropSpritePotato = new Sprite (cropSheetPotato, 16, 16);
	public Sprite cropSpriteWatermelon = new Sprite (cropSheetWatermelon, 16, 16);
	public Sprite cropSpriteBeet = new Sprite (cropSheetBeet, 16, 16);
	public Sprite hotbarHighlight = new Sprite ("resources/sprites/hotbar_highlight.png");
	public Sprite hotbarSprite = new Sprite ("resources/sprites/hotbar.png");
	public Sprite inventory = new Sprite (uiSheet, 0, 192, 293, 118);
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
