package util;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Settings {
	
	private static Vector2i resolution;
	private static String title;
	public static int TOP_INSET, LEFT_INSET, BOTTOM_INSET, RIGHT_INSET;
	
	public static void setInsets(JFrame frame) {
		Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(frame.getGraphicsConfiguration());
		TOP_INSET = scnMax.top;
		LEFT_INSET = scnMax.left;
		BOTTOM_INSET = scnMax.right;
		RIGHT_INSET = scnMax.bottom;
	}
	public static void initSize(int w, int h) {
		resolution = new Vector2i(w, h);
		title = "Title";
	}
	public static Vector2i getResolution() {
		return resolution;
	}
	public static Dimension getDimension() {
		return new Dimension(resolution.getX(), resolution.getY());
	}
	public static String getTitle() {
		return title;
	}
}
