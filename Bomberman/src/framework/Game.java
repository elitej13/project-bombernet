package framework;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import audio.Audio;
import graphic.Client;
import graphic.menu.GameMenu;
import graphic.menu.MainMenu;
import input.Input;
import net.ConnectMenu;
import util.Settings;

public class Game {
	
	public static int STATE;

	private Input input;
	private MainMenu main;
	private GameMenu menu;
	private ConnectMenu connect;
	private Client screen;
	private Audio bkgMusic;
	
	private int state;
	/* 0 - mainmenu
	 * 1 - connectionmenu
	 * 2 - game play
	 */
	
	public Game(Input input) {
		this.input = input;
		main = new MainMenu(input);
		connect = new ConnectMenu(input);
		setState(0);
		startMusic();
	}
	
	public void update() {
		input.update();
		if(STATE != state) setState(STATE);
		if(state == 0) {
			main.update();
		}else if(state == 1) {
			connect.update();
		}else if(state == 2) {
			screen.update();
		}
	}
	public void render(Graphics g) {
		if(state == 0) {
			main.render(g);
		}else if(state == 1) {
			connect.render(g);
		}else if(state == 2) {
			screen.render(g);
		}else if(state == 3) {
			gameover(g);
		}
	}
	public void startMusic() {
		if(bkgMusic == null) bkgMusic = Audio.MUSIC;
		bkgMusic.playSound(true);
	}
	public void setState(int state) {
		if(state == 0) {
			main.resetMenu();
			this.state = 0;
		}else if(state == 1) {
			connect.resetMenu();
			this.state = 1;
		}else if(state == 2) {
			if(this.state == 0) screen = new Client(input, main.getManager());
			else if(this.state == 1) screen = new Client(input, connect.getManager());
			this.state = 2;
		}else {
			STATE = this.state;
		}
	}
	
	public void gameover(Graphics g) {
		bkgMusic.stopSound();
		int fontsize = 48;
		int x = (Settings.getResolution().getX() / 2) - (fontsize / 2);
		int y = (Settings.getResolution().getY() / 2) - (fontsize / 2);
		g.setFont(new Font("SANS SERIF", Font.BOLD, fontsize));
		g.setColor(Color.RED);
		g.drawString("Game Over", x, y);
	}
}
