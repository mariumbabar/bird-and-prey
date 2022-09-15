package Task201;

import java.awt.Point;
import java.util.Random;

public abstract class Pet implements Runnable {
abstract void hungry();
	
	abstract void draw();
	
	public abstract void run();
	
	public void changeState(int state) {
		this.state = state;
	}
	
	public boolean isSatisfied() {
		return satisfied;
	}
	
	public String getName() {
		return name;
	}
	
	protected void delay(int m) {
		try {
			Thread.sleep(m);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected String name;
	
	protected int arrIndex;
	
	protected int state;
	
	protected Point location;
	
	protected Random random = new Random();
	
	protected boolean satisfied = false;
	
	//class final variables
	public static final int HUNGRY = 1;
	
	public static final int DEAD = -1;
	
	public static final int MOVING = 0;
	
	protected static final int SPEED = 8;
	
}
