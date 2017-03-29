package spawn.entity.mob;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import audio.Audio;
import graphic.Sprite;
import spawn.entity.tile.FlameTile;
import spawn.entity.tile.FloorTile;
import spawn.entity.tile.RockTile;
import spawn.entity.tile.Tile;
import spawn.entity.tile.WallTile;
import util.Vector2i;

public class Bomb extends Mob{

	private static double ns = 1000000000.0;
	private List<FlameTile> flames;
	private Audio explosion;
	private boolean isExploded;
	private double fuse, death;
	private long start;
	private int radius;
	
	public Bomb(int x, int y, double fuse, int radius) {
		super(Sprite.BOMB, x, y);
		this.radius = radius;
		this.fuse = fuse;
		explosion = new Audio("explosion");
		flames = new ArrayList<FlameTile>();
		start = System.nanoTime();
		death = 0.25;
		isExploded = false;
		setSolid(false);
	}
	
	public boolean update(Tile[][] tiles, List<Mob> mobs) {
		if(isExploded) {
			checkVictims(mobs);
			if((System.nanoTime() - start) / ns >= death) return true;
		}else {
			if((System.nanoTime() - start) / ns >= fuse) {
				explode(tiles);
				start = System.nanoTime();
			}
		}
		return false;
	}
	public void checkVictims(List<Mob> mobs) {
		for(FlameTile ft : flames) {
			List<Mob> collided = checkMobCollision(mobs, ft.getPosition(), ft.getSprite().getSize());
			for(Mob m : collided) m.kill();
		}
	}
	public void explode(Tile[][] tiles) {
		addFlame(pos);
		boolean goingUp, goingDown, goingLeft, goingRight;
		goingUp = goingDown = goingLeft = goingRight = true;
		Vector2i up = new Vector2i(pos);
		Vector2i down = new Vector2i(pos);
		Vector2i left = new Vector2i(pos);
		Vector2i right = new Vector2i(pos);
		for(int i = 0; i < radius; i++) {
			if(goingUp) {
				up.addY(-Tile.SIZE);
				goingUp = checkTile(tiles, up);
			}
			if(goingDown) {
				down.addY(Tile.SIZE);
				goingDown = checkTile(tiles, down);				
			}
			if(goingLeft) {
				left.addX(-Tile.SIZE);
				goingLeft = checkTile(tiles, left);				
			}
			if(goingRight) {
				right.addX(Tile.SIZE);
				goingRight = checkTile(tiles, right);				
			}
		}
		explosion.playSound(false);
		isExploded = true;
	}
	public boolean checkTile(Tile[][] tiles, Vector2i ref) {
		Tile tile = checkTileCollision(tiles, ref, sprite.getSize());
		if(tile == null) addFlame(ref);
		else if(tile instanceof RockTile) {
			deleteTile(tiles, ref);
			addFlame(ref);
		}else if(tile instanceof WallTile) return false;
		return true;
	}
	public void addFlame(Vector2i pos) {
		flames.add(new FlameTile(pos.getX(), pos.getY()));
	}
	public void deleteTile(Tile[][] tiles, Vector2i pos) {
		int x = pos.getX() / Tile.SIZE;
		int y = pos.getY() / Tile.SIZE;
		tiles[x][y] = new FloorTile(x * Tile.SIZE, y * Tile.SIZE);
	}
	@Override
	public void render(Graphics g, Vector2i scroll, Vector2i offset) {
		int x = getPosition().getX() - scroll.getX() + offset.getX();
		int y = getPosition().getY() - scroll.getY() + offset.getY();
		g.drawImage(sprite.getImage(), x, y, null);
		for(FlameTile ft : flames) ft.render(g, scroll, offset);
	}
	

}
