package framework;

import java.util.ArrayList;
import java.util.List;

import input.Input;
import spawn.entity.mob.Mob;
import spawn.entity.mob.Player;
import spawn.entity.tile.FloorTile;
import spawn.entity.tile.RockTile;
import spawn.entity.tile.Tile;
import spawn.entity.tile.WallTile;
import util.Noise;
import util.Settings;
import util.Vector2i;

public class Level {
	
	private List<Mob> mobs;
	private Tile[][] tiles;
	private Player player;
	private Vector2i scroll, size;
	
	public Level(int cols, int rows, Input input) {
		mobs = new ArrayList<>();
		player = new Player(Tile.SIZE, Tile.SIZE, input);
		scroll = new Vector2i(0, 0);
		mobs.add(player);
		initTiles(cols, rows);
	}
	private void initTiles(int cols, int rows) {
		if(cols % 2 == 0) cols++;
		if(rows % 2 == 0) rows++;
		size = new Vector2i(cols * Tile.SIZE, rows * Tile.SIZE);
		tiles = new Tile[cols][rows];
		float[][] noise = Noise.GeneratePerlinNoise(
				Noise.GenerateSmoothNoise(Noise.GenerateWhiteNoise(cols, rows), 1), 2);
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) { 
//				System.out.println(noise[j][i]);
				tiles[j][i] = new WallTile(j * Tile.SIZE, i * Tile.SIZE);
			}
		}
		for(int i = 1; i < rows - 1; i++) {
			for(int j = 1; j < cols - 1; j++) {
				if((i % 2 == 1) || (j % 2 == 1)) {
					if(noise[j][i] > 0.7 || noise[j][i] < 0.3) tiles[j][i] = new FloorTile(j * Tile.SIZE, i * Tile.SIZE);
					else tiles[j][i] = new RockTile(j * Tile.SIZE, i * Tile.SIZE);					
				}
			}			
		}
		//Player spawn location and space for first bomb;
		tiles[1][1] = new FloorTile(1 * Tile.SIZE, 1 * Tile.SIZE);
		tiles[2][1] = new FloorTile(2 * Tile.SIZE, 1 * Tile.SIZE);
		tiles[1][2] = new FloorTile(1 * Tile.SIZE, 2 * Tile.SIZE);
	}
	
	public void update() {
		for(Mob m : mobs) m.update(mobs, tiles);
		player.update(mobs, tiles);
		checkDeath();
		updateScroll();
	}
	public void checkDeath() {
		for(int i = 0; i < mobs.size(); i++) {
			if(mobs.get(i).isDead()) {
				mobs.remove(i);
				if(i == 0) Game.STATE = 3;
				i--;
			}
		}
	}
	public void updateScroll() {
		int sw = Settings.getDimension().width;
		int sh = Settings.getDimension().height - Settings.TOP_INSET;
		int lw = size.getX();
		int lh = size.getY();
		int x = player.getPosition().getX() + (player.getSprite().getSize().getX() / 2);
		int y = player.getPosition().getY() + (player.getSprite().getSize().getY() / 2);
//		int offset = 32;
		while(x - scroll.getX() > (sw / 2)) {
			if(scroll.getX() >= lw - sw) break;
			scroll.moveX(true);
		}
		while(x - scroll.getX() < (sw / 2)) {
			if(scroll.getX() <= 0) break;
			scroll.moveX(false);
		}
		while(y - scroll.getY() > (sh / 2)) {
			if(scroll.getY() >= lh - sh) break;
			scroll.moveY(true);
		}
		while(y - scroll.getY() < (sh / 2)) {
			if(scroll.getY() <= 0) break;
			scroll.moveY(false);
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	public Tile[][] getTiles() {
		return tiles;
	}
	public Vector2i getScroll() {
		return scroll;
	}
	public List<Mob> getMobs() {
		return mobs;
	}
}
