package util;

public class Vector2i {
	
	private int x, y;
	
	public Vector2i(Vector2i vector) {
		this.x = vector.x;
		this.y = vector.y;
	}
	public Vector2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void moveX(boolean dir) {
		if(dir) x++;
		else x--;
	}
	public void moveY(boolean dir) {
		if(dir) y++;
		else y--;
	}
	public void add(Vector2i vector) {
		this.x += vector.x;
		this.y += vector.y;
	}
	public void subtract(Vector2i vector) {
		this.x -= vector.x;
		this.y -= vector.y;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public void set(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void addX(int x) {
		this.x += x;
	}
	public void addY(int y) {
		this.y += y;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
