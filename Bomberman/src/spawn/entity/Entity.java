package spawn.entity;

import java.awt.Graphics;

import graphic.Sprite;
import util.Vector2i;

public class Entity {
	
	protected Sprite sprite;
	protected Vector2i pos;
	protected boolean solid;
	
	public Entity(Sprite sprite, boolean solid, int x, int y) {
		this.sprite = sprite;
		this.solid = solid;
		pos = new Vector2i(x, y);
	}
	public Sprite getSprite() {
		return sprite;
	}
	public Vector2i getPosition() {
		return pos;
	}
	public void render(Graphics g, Vector2i scroll, Vector2i offset) {
		int x = getPosition().getX() - scroll.getX() + offset.getX();
		int y = getPosition().getY() - scroll.getY() + offset.getY();
		g.drawImage(sprite.getImage(), x, y, null);
	}
	public boolean isSolid() {
		return solid;
	}
}
