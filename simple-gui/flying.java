package task1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import javax.swing.JPanel;

public class flying extends Interface implements Runnable{
private Point location;
	
	private JPanel canvas;
	
	private int state;
	
	private static final int SIZE = 20;
	
	private Random random = new Random();
	
	private int dx = SPEED;
	
	private int dy = SPEED;
	
	
	public flying(run drawCanvas) {
		canvas = drawCanvas.getCanvas();
		location = new Point(random.nextInt(canvas.getWidth()), random.nextInt(canvas.getHeight()));
		state = MOVING;
		name = "fly";
	}
	
	public Point getLocation() {
		return location;
	}
	
	
	private void draw() {
		Graphics g = canvas.getGraphics();
		g.setColor(Color.PINK);
		g.fillRect(location.x, location.y, SIZE, SIZE);
	}
	
	public void changeState(int state) {
		this.state = state;
	}
	
	public void clear() {
		Graphics g = canvas.getGraphics();
		g.setColor(canvas.getBackground());
		g.fillRect(location.x, location.y, SIZE, SIZE);
	}	
	private void move() {
		if (state != MOVING) {return;}
				
		clear();
        
        location.x += dx;
        location.y += dy;  
		
        if (location.x + SIZE > canvas.getWidth()) {
            location.x = canvas.getWidth() - SIZE;
            dx = -SPEED;
        } else if (location.x < 0) {
            location.x = 0;
            dx = SPEED;
        }
        if (location.y + SIZE > canvas.getHeight()) {
            location.y = canvas.getHeight() - SIZE;
            dy = -SPEED;
        } else if (location.y < 0) {
            location.y = 0;
            dy = SPEED;
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
