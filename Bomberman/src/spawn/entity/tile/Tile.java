package spawn.entity.tile;

import graphic.Sprite;
import spawn.entity.Entity;


public class Tile extends Entity {
	
	public static int SIZE = 32;
	
	public Tile(Sprite sprite, boolean solid, int x, int y) {
		super(sprite, solid, x, y);
	}
	
}
