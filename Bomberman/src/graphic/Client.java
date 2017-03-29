package graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import framework.Manager;
import input.Input;
import spawn.entity.mob.Mob;
import util.Settings;
import util.Vector2i;

public class Client {

	private static double NS = 1000000000.0;
	
	private Vector2i offset;
	private Manager manager;
	private boolean isPaused, started;
	private long timer;
	private int time, startTime;

	public Client(Input input, Manager manager) {
		this.manager = manager;
		offset = new Vector2i(Settings.LEFT_INSET, Settings.TOP_INSET + 64);
		timer = System.nanoTime();
		startTime = 4;
		time = 120;
	}	

	public void update() {
		if(!started) starting();
		else {
			if(!isPaused) {
				manager.update();			
			}						
		}
	}
	public void starting() {
		if((System.nanoTime() - timer) / NS >= 1.0) {
			timer = System.nanoTime();
			startTime -= 1;
			if(startTime == 0) {
				started = true;
			}
		}
	}
	public void renderCountdown(Graphics g) {
		g.setColor(Color.RED);
		g.setFont(new Font("Verdana", Font.BOLD, 128));
		Graphics2D g2d = (Graphics2D) g;
		FontRenderContext context = g2d.getFontRenderContext();
		TextLayout txt = new TextLayout("" + startTime, g.getFont(), context);
		Rectangle2D bounds = txt.getBounds();
		int x = (int)(Settings.getResolution().getX() / 2 - bounds.getWidth() / 2);
		int y = (int)(Settings.getResolution().getY() / 2 - bounds.getHeight() / 2);
		g.drawString("" + startTime, x, y);
	}
	
	public void render(Graphics g) {
		ui(g);
		for(int i = 0; i < manager.getTiles()[0].length; i++) {
			for(int j = 0; j < manager.getTiles().length; j++) {
				manager.getTiles()[j][i].render(g, manager.getScroll(), offset);
			}			
		}
		for(Mob m : manager.getMobs()) m.render(g, manager.getScroll(), offset);
//		level.getPlayer().render(g, level.getScroll(), offset);
		if(!started) renderCountdown(g);
	}
	public void ui(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Settings.getResolution().getX(), offset.getY());
		g.setFont(new Font("Verdana", Font.BOLD, 48));
		g.setColor(Color.RED);
		g.drawString("Time: " + timer(), g.getFont().getSize() / 2, (offset.getY() + g.getFont().getSize()) / 2);
	}
	public int timer() {
		if(started) return time - (int)((System.nanoTime() - timer) / NS);
		else return time;
	}
}