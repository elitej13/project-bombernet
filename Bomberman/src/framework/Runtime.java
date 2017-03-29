package framework;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import input.Input;
import util.Settings;

public class Runtime implements Runnable, WindowListener {
	
	private Thread thread;
	private JFrame frame;
	private Game game;
	private Input input;
	private boolean running, suspended;
	
	public Runtime() {
		frame = new JFrame();
		input = new Input();
		game = new Game(input);
		frame.addWindowListener(this);
		frame.addMouseMotionListener(input.getMouse());
		frame.addMouseListener(input.getMouse());
		frame.addKeyListener(input.getKeys());
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		suspended = false;
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		stop();
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		stop();
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		suspended = true;
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		suspended = false;
	}
	@Override
	public void windowIconified(WindowEvent arg0) {	
		suspended = true;
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		suspended = false;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Displayer");
		frame.requestFocus();
		thread.start();
	}
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				if(!suspended) game.update();
				updates++;
				delta--;
			}
			if(!suspended) render();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(Settings.getTitle() + "  | " + updates + " ups, " + frames + " fps");
				frames = 0;
				updates = 0;
			}
		}
		stop();
	}
	public void render() {
		BufferStrategy bs = frame.getBufferStrategy();
		if (bs == null) {
			frame.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		frame.paintComponents(g);
		game.render(g);
		g.dispose();
		bs.show();
	}
	public synchronized void stop() {
		running = false;
		thread.interrupt();
		System.exit(0);
	}
	public static void main(String[] args) {
		int w = 640;
//		int h = 512;
//		int w = 800;
//		int h = 640;
//		int w = (9 * 32);
		int h = 704;
		Settings.initSize(w, h);
		Runtime rt = new Runtime();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		int xOffset = (int)((width - w) / 2);
		int yOffset = (int)((height - h + 50) / 2);
		if(xOffset < 0) xOffset = 0;
		if(yOffset < 0) yOffset = 0;
		rt.frame.setPreferredSize(Settings.getDimension());
		rt.frame.setTitle(Settings.getTitle());
		rt.frame.setBackground(Color.BLACK);
		rt.frame.setLocation(xOffset, yOffset);
		Settings.setInsets(rt.frame);
		rt.frame.setResizable(false);
		rt.frame.setFocusable(true);
		rt.frame.setVisible(true);
		rt.frame.pack();
		rt.start();
	}

}
