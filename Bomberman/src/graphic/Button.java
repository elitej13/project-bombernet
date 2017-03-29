package graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import util.Vector2i;

public class Button {
	
	private Vector2i size;
	private Vector2i pos;
	private String title;
	private Color hlgt;
	private Color bkg;
	private Color textCol;
	private boolean isHighlighted;
	
	
	public Button() {
		this(new Vector2i(100, 50), new Vector2i(100, 100), "Button", Color.YELLOW, Color.WHITE, Color.BLACK);
	}
	public Button(Vector2i size, Vector2i position, String title, Color highlight, Color background, Color text) {
		this.size = size;
		pos = position;
		this.title = title;
		hlgt = highlight;
		bkg = background;
		textCol = text;
	}
	
	public void render(Graphics g) {
		//Poor coding, this shouldn't be recalculated every frame
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext context = g2d.getFontRenderContext();
		TextLayout txt = new TextLayout(title, g.getFont(), context);
		Rectangle2D bounds = txt.getBounds();
		
		int x = pos.getX();
		int y =  pos.getY();
		int w = size.getX();
		int h = size.getY();
		
		if(isHighlighted) g.setColor(hlgt);
		else g.setColor(bkg);
		g.fillRect(x, y, w, h);
		g.setColor(textCol);
		
		x += (int) ((size.getX() - bounds.getWidth()) / 2);
		y += (int) ((size.getY() - (bounds.getHeight() - txt.getDescent())) / 2);
		y += txt.getAscent() - txt.getDescent();
		g.drawString(title, x, y);
	}
	public void isHighlighted(boolean highlight) {
		isHighlighted = highlight;
	}
}
