package task1;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class bird extends Interface implements Runnable {
private Point location;
	
	private JPanel canvas;
	
	private run frame;
	
	private int state;
	
	private boolean satisfied = false;
	
	private int id;
	
	private int dx = SPEED;
	
	private int dy = SPEED;
	
	private Random random = new Random();
	
	private BufferedImage[] birdImage;
	
	public bird (run drawCanvas, String name, int id) {
		this.id = id;
		frame = drawCanvas;
		canvas = drawCanvas.getCanvas();
		location = new Point(random.nextInt(canvas.getWidth()), random.nextInt(canvas.getHeight()));
		state = MOVING;
		birdImage = new BufferedImage[3];
		this.name = name;
		getImage();
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isSatisfied() {
		return satisfied; 
	}
	
	// Image path
	private void getImage() {
		try {
			birdImage[0] = ImageIO.read(new File("bird1.png"));
			birdImage[1] = ImageIO.read(new File("bird2.png"));
			birdImage[2] = ImageIO.read(new File("bird2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void move() {
		state = MOVING;
		
		Graphics g = canvas.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(location.x, location.y, birdSize, birdSize);
        
        location.x += dx;
        location.y += dy;  

        if (location.x + birdSize > canvas.getWidth()) {
            location.x = canvas.getWidth() - birdSize;
            dx = -SPEED;
        } else if (location.x < 0) {
            location.x = 0;
            dx = SPEED;
        }
        if (location.y + birdSize > canvas.getHeight()) {
            location.y = canvas.getHeight() - birdSize;
            dy = -SPEED;
        } else if (location.y < 0) {
            location.y = 0;
            dy = SPEED;
        }
        draw();
	}
	
	private void draw() {
		Graphics g = canvas.getGraphics();
		g.drawImage(birdImage[0], location.x, location.y, birdSize, birdSize, canvas);
	}
	
	private void eat(int coordinateX) {
		
		Graphics g = canvas.getGraphics();
		Point flyLocation = frame.getFlyLocation(id);
		
		boolean left = false;
		
		if (coordinateX < 0) left = true;
		int index = left ? 2 : 1;
		
		
		g.setColor(Color.BLACK);
		g.drawImage(birdImage[index], location.x, location.y, birdSize, birdSize, canvas);
		g.setColor(Color.RED);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		frame.stopFly(id);
		
		g2.setStroke(new BasicStroke(5));
		g2.drawLine(location.x + (birdSize/2), location.y+(birdSize/2), flyLocation.x, flyLocation.y);
		delay(200);
		
		frame.clearFly(id);
		g2.setColor(canvas.getBackground());
		g2.drawLine(location.x+(birdSize/2), location.y+(birdSize/2), flyLocation.x, flyLocation.y);		
	}
	
	// Hungry method
	private void hungry() {
		state = HUNGRY;
		Point flyLocation = frame.getFlyLocation(id);
		int coordinateX = (location.x+(birdSize/2))-flyLocation.x;
		int coordinateY = (location.y+(birdSize/2))-flyLocation.y;
		
		if (Math.abs(coordinateX) <= tongue_radius && Math.abs(coordinateY) <= tongue_radius) {
			eat(coordinateX);
			satisfied = true;
			frame.printNotHungry(name);
			state = MOVING;
			return;
		}
		
		Graphics g = canvas.getGraphics();
		g.setColor(canvas.getBackground());
		g.fillRect(location.x, location.y, birdSize, birdSize);

		if (location.x + birdSize > canvas.getWidth()) {
            location.x = canvas.getWidth() - birdSize;
            dx = -SPEED;
        } else if (location.x < 0) {
            location.x = 0;
            dx = SPEED;
        }
        if (location.y + birdSize > canvas.getHeight()) {
            location.y = canvas.getHeight() - (birdSize);
            dy = -SPEED;
        } else if (location.y < 0) {
            location.y = 0;
            dy = SPEED;
        }
        if (location.x > flyLocation.x) location.x -= SPEED;
		if (location.x < flyLocation.x) location.x += SPEED;
		if (location.x == flyLocation.x) location.x += 0;
		
		if (location.y > flyLocation.y) location.y -= SPEED; 
		if (location.y < flyLocation.y) location.y += SPEED;
		if (location.y == flyLocation.y) location.y += 0;
        draw();
	}
	
	public void changeState(int state) {
		this.state = state;
	}
		
	@Override
	public void run() {
		draw();
		while (state != DEAD) {
			delay(60);
			if (state == HUNGRY) {
				hungry();
			}
			if (state == MOVING) {
				move();
			}
		}
	}
	
	// Size of frog
	private static final int birdSize = 100;
	
	// Tongue size
	private static final int tongue_radius = 30;
	
}
