package net;


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

public class ConnectMenu {

	private static double NS = 1000000000.0;
	
	private Input input;
	private Manager manager;
	private Button create, join, back;
	private Color bkg;
	
	private Font smallFont = new Font("Verdana", Font.BOLD, 24);
	private Font largeFont = new Font("Verdana", Font.BOLD, 48);
	
	private String title = "Connection Menu";
	private String question;
	private String buffer;
	private String ip;

	private boolean up, down, select;
	private boolean isAsking;
	private boolean isServer;
	private long timer;
	private int selection;
	private int port;
	private int bX;
	
	public ConnectMenu(Input input) {
		this.input = input;
		int screenX = Settings.getResolution().getX();
		int bW = 256, bH = 48;
		bX  = (screenX / 2) - (bW / 2);
		int bY = 256;
		create = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), 
				"Create Server", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		join = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), 
				"Join Server", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		back = new Button(new Vector2i(bW, bH), new Vector2i(bX, bY), 
				"Back", Color.ORANGE, Color.RED, Color.YELLOW);
		bY += bH + bH;
		selection = -1;
		bkg = Color.BLACK;
		timer = System.nanoTime();
	}
	public void update() {
		input.update();
		select = input.isSelected();
		if(!isAsking) {
			up = input.goingUp();
			down = input.goingDown();
			if((System.nanoTime() - timer) / NS >= 0.16) {
				if(select) activateSelection();
				if(!(up && down)) {
					if(up) {
						selection--;
						if(selection < 0) selection = 2;
						timer = System.nanoTime();
					}
					if(down) {
						selection++;
						if(selection > 2) selection = 0;
						timer = System.nanoTime();
					}				
				}
				create.isHighlighted(false);
				join.isHighlighted(false);
				back.isHighlighted(false);
				if(selection == 0) 	create.isHighlighted(true);
				else if(selection == 1) join.isHighlighted(true);
				else if(selection == 2) back.isHighlighted(true);
			}	
		}else {
			if((System.nanoTime() - timer) / NS >= 0.16) {
				if(select) {
					timer = System.nanoTime();
					if(isServer) {
						port = Integer.parseInt(buffer);
						if(port <= 0 || port >= 65535) {
							input.getAndClearBuffer();
							question = "Invalid PORT number, try again";
							buffer = "";
						}else {
							input.endBuffer();
							manager = new Manager(input, true, isServer, "localhost", port);
							Game.STATE = 2;
						}
					}else {
						if(ip != null) {
							port = Integer.parseInt(buffer);
							if(port <= 0 || port >= 65535) {
								input.getAndClearBuffer();
								question = "Invalid PORT number, try again";
								buffer = "";
							}else {
								input.endBuffer();
								manager = new Manager(input, true, false, ip, port);
								Game.STATE = 2;
							}
						}else  {
							input.getAndClearBuffer();
							question = "Type the SERVER PORT then press ENTER";						
							ip = buffer;
							buffer = "";
						}
					}
				}
			}
			buffer += input.getAndClearBuffer();
			if(buffer.contains("\b")) {
				int ib = buffer.indexOf("\b");
				if(ib > 0) {
					char c = buffer.charAt(ib - 1);
					buffer = buffer.replace(c + "\b", "");					
				}else buffer = "";
			}
		}
	}
	public void activateSelection() {
		if(selection == 0) {
			isServer = true;
			isAsking = true;
			question = "Type the PORT then press ENTER";
			buffer = "";
			input.requestBuffer();
			timer = System.nanoTime();
//			manager = new Manager(input, true, true, "localhost", 22222);
//			Game.STATE = 2;
		}
		else if(selection == 1) {
			isAsking = true;
			question = "Type the SERVER IP then press ENTER";
			buffer = "";
			input.requestBuffer();
			timer = System.nanoTime();
		}
		else if(selection == 2) Game.STATE = 0;
	}
	public void resetMenu() {
		selection = -1;
		create.isHighlighted(false);
		join.isHighlighted(false);
		back.isHighlighted(false);
		timer = System.nanoTime();
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
		
		g.setFont(smallFont);
		if(isAsking) {
			g.drawString(question, x, 256);
			g.drawString(buffer, x, 384);
		}else {
			//Buttons
			create.render(g);
			join.render(g);
			back.render(g);			
		}
		
	}
	public Manager getManager() {
		return manager;
	}
}

