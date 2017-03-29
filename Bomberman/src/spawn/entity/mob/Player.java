package spawn.entity.mob;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import graphic.Sprite;
import input.Input;
import util.Action;
import util.Vector2i;

public class Player extends Mob {
	
	private Input input;

	
	public Player(int x, int y, Input input) {
		super(Sprite.PLAYER, x, y);
		this.input = input;
		radius = 10;
	}
	@Override
	public List<Action> behavior() {
		List<Action> actions = new ArrayList<>();
		if(input.goingUp()) {
			actions.add(Action.UP);
		}if(input.goingDown()) {
			actions.add(Action.DOWN);
		}if(input.goingLeft()) {
			actions.add(Action.LEFT);
		}if(input.goingRight()) {
			actions.add(Action.RIGHT);
		}if(input.isAttacking()) {
			actions.add(Action.ATTACK);
		}
		return actions;
	}
	
	@Override
	public void render(Graphics g, Vector2i scroll, Vector2i offset) {
		if(bomb != null) bomb.render(g, scroll, offset);
		int x = getPosition().getX() - scroll.getX() + offset.getX();
		int y = getPosition().getY() - scroll.getY() + offset.getY();
		g.drawImage(sprite.getImage(), x, y, null);
	}
}