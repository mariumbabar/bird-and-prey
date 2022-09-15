package Task201;

import java.awt.Point;
import java.util.Random;

public abstract class Prey implements Runnable {
public abstract void clearPrey();
	
	protected abstract void draw();
	
	public abstract void run();
	
	protected void delay(int m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void changeState(int state) {
		this.state = state;
	}
	
	public Point getLocation() {
		return location;
	};
	
	protected int state;
	
	protected Point location;
	
	protected Random random = new Random();
	
	public static final int DEAD = -1;
	
	public static final int MOVING = 0;
}
