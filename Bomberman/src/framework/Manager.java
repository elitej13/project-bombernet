package framework;

import java.util.List;

import input.Input;
import net.NetworkInterface;
import spawn.entity.mob.Mob;
import spawn.entity.tile.Tile;
import util.Settings;
import util.Vector2i;

public class Manager {

	private static double NS = 100000000.0;
	
	private NetworkInterface ni;
	private Level level;
	private boolean isOnlinePlay;
	private long timer;
	
	public Manager(Input input, boolean isOnlinePlay, boolean isServer, String ip, int port) {
		this.isOnlinePlay = isOnlinePlay;
		int w = (Settings.getResolution().getX() / Tile.SIZE) - 1;
		int h = (Settings.getResolution().getY() - Settings.TOP_INSET) / Tile.SIZE - 3;
		if(isOnlinePlay) ni = new NetworkInterface(isServer, ip, port);
		else level = new Level(w, h, input);
	}
	public void update() {
		if(isOnlinePlay) {
			
		}else {
			level.update();
			
		}
	}
	
	public Vector2i getScroll() {
		return level.getScroll();
	}
	public Tile[][] getTiles() {
		return level.getTiles();
	}
	public List<Mob> getMobs() {
		return level.getMobs();
	}
}
