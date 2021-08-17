package gui;

import main.MainLoop;
import resources.Sprite;
import resources.Spritesheet;

public class MappedUi extends GuiComponent {
	private int[][] map;
	private Spritesheet tileSheet;
	private Sprite tiles;
	public MappedUi (Spritesheet tileSheet, int[][] map) {
		super ();
		this.map = map;
		this.tileSheet = tileSheet;
		this.tiles = new Sprite (tileSheet, 16, 16);
	}
	public void setMap (int[][] map) {
		this.map = map;
	}
	public int[][] getMap () {
		return map;
	}
	public void setTileSheet (Spritesheet tileSheet) {
		this.tileSheet = tileSheet;
		this.tiles = new Sprite (tileSheet, 16, 16);
	}
	public Spritesheet getTileSheet () {
		return tileSheet;
	}
	@Override
	public void draw () {
		if (map != null) {
			if (map.length > 0) {
				for (int i = 0; i < map.length; i ++) {
					for (int j = 0; j < map[0].length; j ++) {
						tiles.draw ((int)this.getX () + j * 16, (int)this.getY () + i * 16, map [i][j]);
					}
				}
			}
			renderGui ();
		}
	}
	public void renderGui () {
		
	}
	public int getWidth () {
		return map [0].length;
	}
	public int getHeight () {
		return map.length;
	}
	public static int[][] buildTileMap (int width, int height) {
		if (width < 2 || height < 2) {
			return null;
		}
		int[][] map = new int[height][width];
		map [0][0] = 5;
		map [0][width - 1] = 8;
		map [height - 1][0] = 6;
		map [height - 1][width - 1] = 7;
		for (int i = 1; i < width - 1; i ++) {
			map [0][i] = 1;
			map [height - 1][i] = 3;
		}
		for (int i = 1; i < height - 1; i ++) {
			map [i][0] = 2;
			map [i][width - 1] = 4;
			for (int j = 1; j < width - 1; j ++) {
				map [i][j] = 0;
			}
		}
		return map;
	}
}