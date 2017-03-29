package input;

public class Input {
	private String controller;
	private Mouse mouse;
	private Keyboard keys;
	private boolean menu, select, quit, attack, up, down, left, right;
	
	/** 
	 *  Add listeners to main frame/panel 
	 *  for mouse and keyboard to work.
	 */
	public Input() {
		mouse = new Mouse();
		keys = new Keyboard();
		controller = "KEYBOARD";
	}
	
	public void update() {
		if(controller.equals("KEYBOARD")) {
			keys.update();
			if(keys.enter) select = true;
			else select = false;
			if(keys.esc) quit = true;
			else quit = false;
			if(keys.space) attack = true;
			else attack = false;
			if(keys.up) up = true;
			else up = false;
			if(keys.down) down = true;
			else down = false;
			if(keys.left) left = true;
			else left = false;
			if(keys.right) right = true;
			else right = false;
		}
	}
	public boolean getMenu() {
		return menu;
	}
	public boolean goingUp() {
		return up;
	}
	public boolean goingDown() {
		return down;
	}
	public boolean goingLeft() {
		return left;
	}
	public boolean goingRight() {
		return right;
	}
	public boolean isAttacking() {
		return attack;
	}
	public boolean isSelected() {
		return select;
	}
	public Mouse getMouse() {
		return mouse;
	}
	public Keyboard getKeys() {
		return keys;
	}
	public void requestBuffer() {
		keys.requestBuffer();
	}
	public void endBuffer() {
		keys.endBuffer();
	}
	public String getAndClearBuffer() {
		String buffer = keys.getBuffer();
		keys.clearBuffer();
		return buffer;
	}
}
