package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import util.Vector2i;

public class Mouse implements MouseListener, MouseMotionListener{

	private Vector2i position;
	private boolean leftClick, rightClick;
	
	public Mouse() {
		position = new Vector2i(0,0);
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		position.set(e.getX(), e.getY());
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		position.set(e.getX(), e.getY());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 0) leftClick = true;
		if(e.getButton() == 1) rightClick = true;
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == 0) leftClick = false;
		if(e.getButton() == 1) rightClick = false;
	}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	
	public Vector2i getPosition() {
		return position;
	}
	public boolean getLeftClick() {
		return leftClick;
	}
	public boolean getRightClick() {
		return rightClick;
	}
}
