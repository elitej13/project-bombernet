package spawn.entity.tile;

import graphic.Sprite;

public class WallTile extends Tile{

	public WallTile(int x, int y) {
		super(Sprite.WALL, true, x, y);
	}

}
