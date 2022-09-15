package Task201;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import Task201.PresentationLayer;

public class Fly  extends Prey {
private PresentationLayer GUI;
	
	private Point delta;
	
	private static final int SIZE = 20;
	
	private static final int SPEED = 8;
	

	public Fly(PresentationLayer GUI) {
		this.GUI = GUI;
		delta = new Point(SPEED,SPEED);
		location = new Point(random.nextInt(GUI.getCanvas().getWidth()), random.nextInt(GUI.getCanvas().getHeight()));
		state = MOVING;
	}

	@Override
	public void clearPrey() {
		Graphics g = GUI.getCanvas().getGraphics();
		g.setColor(GUI.getCanvas().getBackground());
		g.fillOval(location.x, location.y, SIZE, SIZE);
	}

	@Override
	protected void draw() {
		Graphics g = GUI.getCanvas().getGraphics();
		g.setColor(Color.darkGray);
		g.fillOval(location.x, location.y, SIZE, SIZE);
	}
	
	private void move() {
		if (state != MOVING) {return;}	
		clearPrey();
		location.translate(delta.x, delta.y);
        if (location.x + SIZE > GUI.getCanvas().getWidth()) {
            location.x = GUI.getCanvas().getWidth() - SIZE;
            delta.x = -SPEED;
        } else if (location.x < 0) {
            location.x = 0;
            delta.x = SPEED;
        } if (location.y + SIZE > GUI.getCanvas().getHeight()) {
            location.y = GUI.getCanvas().getHeight() - SIZE;
            delta.y = -SPEED;
        } else if (location.y < 0) {
            location.y = 0;
            delta.y = SPEED;
        }
        draw();
	}

	@Override
	public void run() {
		draw();
		while (state != DEAD) {
			delay(40);
			move();
		}
	}
}
