package spawn.entity.mob;

import java.util.ArrayList;
import java.util.List;

import graphic.Sprite;
import spawn.entity.Entity;
import spawn.entity.tile.Tile;
import util.Action;
import util.Vector2i;

public class Mob extends Entity {
	
	protected Bomb bomb;
	protected boolean isDead;
	protected int speed, radius;
	
	public Mob(Sprite sprite, int x, int y) {
		super(sprite, true, x, y);
		isDead = false;
		speed = 1;
		radius = 10;
	}
	public void update(List<Mob> mobs, Tile[][] tiles) {
		anim();
		resolve(mobs, behavior(), tiles);
	}
	public void anim() {
	}
	public List<Action> behavior() {
		List<Action> actions = new ArrayList<>();
		//Do stuff if single player
		return actions;
	}
	public void resolve(List<Mob> mobs, List<Action> actions, Tile[][] tiles) {
		boolean up = false, down = false, left = false, right = false, attack = false;
		for(Action a : actions) {
			if(a == Action.UP) up = true;
			else if(a == Action.DOWN) down = true;
			else if(a == Action.LEFT) left = true;
			else if(a == Action.RIGHT) right = true;
			else if(a == Action.ATTACK) attack = true;
		}
		if(!(up && down)) {
			if(up) for(int i = 0; i < speed; i++) moveY(tiles, false);
			if(down) for(int i = 0; i < speed; i++) moveY(tiles,true);
		}
		if(!(left && right)) {
			if(left) for(int i = 0; i < speed; i++) moveX(tiles,false);
			if(right) for(int i = 0; i < speed; i++) moveX(tiles,true);
		}
		if(attack) spawnBomb();
		if(bomb != null) if(bomb.update(tiles, mobs)) bomb = null;
	}
	public void spawnBomb() {
		if(bomb != null) return;
		int centerX = pos.getX() + (sprite.getSize().getX() / 2);
		int centerY = pos.getY() + (sprite.getSize().getY() / 2);
		int x = centerX - (centerX % Tile.SIZE);
		int y = centerY - (centerY % Tile.SIZE);
		bomb = new Bomb(x, y, 1.0, radius);

	}
	public Entity moveX(Tile[][] tiles, boolean dir) {
		Vector2i moved = new Vector2i(pos);
		moved.moveX(dir);
		Tile t = checkTileCollision(tiles, moved, getSprite().getSize());
		if(t != null) return t;
		getPosition().moveX(dir);
		return null;
	}
	public Entity moveY(Tile[][] tiles, boolean dir) {
		Vector2i moved = new Vector2i(pos);
		moved.moveY(dir);
		Tile t = checkTileCollision(tiles, moved, getSprite().getSize());
		if(t != null) return t;	
		getPosition().moveY(dir);
		return null;
	}
	public List<Mob> checkMobCollision(List<Mob> mobs, Vector2i reference, Vector2i size) {
		List<Mob> collided = new ArrayList<>();
		int w = size.getX();
		int h = size.getY();
		int rx = reference.getX();
		int ry = reference.getY();
		for(Mob m : mobs) {
			int sx = m.getSprite().getSize().getX();
			int sy = m.getSprite().getSize().getY();
			int mx = m.getPosition().getX();
			int my = m.getPosition().getY();
			if(rx < mx + sx && rx + w > mx)
				if(ry < my + sy && ry + h > my)
					collided.add(m);
		}
		return collided;
	}
	public Tile checkTileCollision(Tile[][] tiles, Vector2i reference, Vector2i size) {
		int ts = Tile.SIZE;
		int w = size.getX();
		int h = size.getY();
		int px = reference.getX();
		int py = reference.getY();
		for(int i = 0; i < tiles[0].length; i++) {
			for(int j = 0; j < tiles.length; j++) { 
				if(tiles[j][i].isSolid()) {
					int tx = tiles[j][i].getPosition().getX();
					int ty = tiles[j][i].getPosition().getY();
					if(px < tx + ts && px + w > tx)
						if(py < ty + ts && py + h > ty)
							return tiles[j][i];
				}				
			}
		}
		return null;
	}
	public void kill() {
		isDead = true;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setSolid(boolean solid) {
		this.solid = solid;
	}
}
