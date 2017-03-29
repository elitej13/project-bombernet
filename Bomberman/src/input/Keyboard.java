package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	 
	private boolean keys[] = new boolean[526];
	boolean up, down, left, right, space, enter, esc;
	private boolean isBuffering;
	private String buffer;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] | keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] | keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] | keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] | keys[KeyEvent.VK_D];
		enter = keys[KeyEvent.VK_ENTER];
		space = keys[KeyEvent.VK_SPACE];
		esc = keys[KeyEvent.VK_ESCAPE];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if(isBuffering && e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
			if(e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
				buffer += "\b";
			}else buffer += e.getKeyChar();
		}
	}
	public void requestBuffer() {
		isBuffering = true;
		buffer = "";
	}
	public void endBuffer() {
		isBuffering = false;
	}
	public String getBuffer() {
		return buffer;
	}
	public void clearBuffer() {
		buffer = "";
	}
}
