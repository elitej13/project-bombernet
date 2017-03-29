package graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import util.Vector2i;

public class Sprite {

	public static final Sprite PLAYER = new Sprite("player");
	public static final Sprite FLOOR = new Sprite("floor");
	public static final Sprite ROCK = new Sprite("rock");
	public static final Sprite WALL = new Sprite("wall");
	public static final Sprite BOMB = new Sprite("bomb");
	public static final Sprite FLAME = new Sprite("flame");
	
	private BufferedImage image;
	private Vector2i size;
	
	public Sprite(String filename) {
		try {
			image = ImageIO.read(new File("rsc/textures/" + filename + ".png"));
		} catch (IOException e) {
			System.err.println("Unable to read the file: " + filename);
		}
		size = new Vector2i(image.getWidth(), image.getHeight());
	}
	public Vector2i getSize() {
		return size;
	}
	public BufferedImage getImage() {
		return image;
	}
}
