package graphic.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import framework.Game;
import framework.Manager;
import graphic.Button;
import input.Input;
import util.Settings;
import util.Vector2i;

public class MainMenu {

	private static double NS = 1000000000.0;
	
	private Input input;
	private Manager manager;
	private Button single, multiple, options, quit;
	private Color bkg;
	
	private Font smallFont = new Font("Verdana", Font.BOLD, 24);
	private Font largeFont = new Font("Verdana", Font.BOLD, 48);
	
	private String title = "Bomberman";
	private boolean up, down, select;
	private long timer;
	private int selection;
	
	public MainMenu(Input input) {
		this.input = input;
		int screenX = Settings.getResolution().getX(), screenY = Settings.getResolution().getY();
		int bW = 256, bH = 48;
		int bX  = (screenX / 2) - (bW / 2), bY = 256;
		single = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), "Single Player", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		multiple = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), "Multi Player", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		options = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), "Options", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		quit = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), "Quit", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		selection = -1;
		bkg = Color.BLACK;
		timer = System.nanoTime();
	}
	public void update() {
		up = input.goingUp();
		down = input.goingDown();
		select = input.isSelected();
		if((System.nanoTime() - timer) / NS >= 0.16) {
			if(select) activateSelection();
			if(!(up && down)) {
				if(up) {
					selection--;
					if(selection < 0) selection = 3;
					timer = System.nanoTime();
				}
				if(down) {
					selection++;
					if(selection > 3) selection = 0;
					timer = System.nanoTime();
				}				
			}
			single.isHighlighted(false);
			multiple.isHighlighted(false);
			options.isHighlighted(false);
			quit.isHighlighted(false);
			if(selection == 0) 	single.isHighlighted(true);
			else if(selection == 1) multiple.isHighlighted(true);
			else if(selection == 2) options.isHighlighted(true);
			else if(selection == 3) quit.isHighlighted(true);	
		}
	}
	public void activateSelection() {
		if(selection == 0) {
			manager = new Manager(input, false, false, "", 0);
			Game.STATE = 2;
		}
		else if(selection == 1) Game.STATE = 1;
		else if(selection == 2) ;
		else if(selection == 3) System.exit(0);
	}
	public void resetMenu() {
		timer = System.nanoTime();
		selection = -1;
		single.isHighlighted(false);
		multiple.isHighlighted(false);
		options.isHighlighted(false);
		quit.isHighlighted(false);
	}
	public void render(Graphics g) {
		//Poor coding, this shouldn't be recalculated every frame
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext context = g2d.getFontRenderContext();
		TextLayout txt = new TextLayout(title, largeFont, context);
		Rectangle2D bounds = txt.getBounds();
		int x = (int) (Settings.getResolution().getX() / 2 - bounds.getWidth() / 2);
		
		//Background
		g.setColor(bkg);
		g.fillRect(0, 0, Settings.getResolution().getX(), Settings.getResolution().getY());
		
		//Title
		g.setColor(Color.WHITE);
		g.setFont(largeFont);
		g.drawString(title, x, 128);
		
		//Buttons
		g.setFont(smallFont);
		single.render(g);
		multiple.render(g);
		options.render(g);
		quit.render(g);
	}
	
	public Manager getManager() {
		return manager;
	}
}
