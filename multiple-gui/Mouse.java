package Task201;

import java.awt.Graphics;


import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Task201.PresentationLayer;

public class Mouse extends Prey {
public Mouse(PresentationLayer GUI, String name, int arrIndex) {
		
		this.GUI = GUI;
		delta = new Point(SPEED,SPEED);
		location = new Point(random.nextInt(GUI.getCanvas().getWidth()), random.nextInt(GUI.getCanvas().getHeight()));
		state = MOVING;
		mouseImage = new BufferedImage[2];
		getImage();
		
		
	}
	
	private void getImage() {
		try {
			mouseImage[0] = ImageIO.read(new File("mouse.png"));
			mouseImage[1] = ImageIO.read(new File("mouse.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void draw() {
		Graphics g = GUI.getCanvas().getGraphics();
		g.drawImage(mouseImage[0], location.x, location.y, SIZE, SIZE, GUI.getCanvas());
	}
	
	@Override
	public void clearPrey() {
		Graphics g = GUI.getCanvas().getGraphics();
		g.setColor(GUI.getCanvas().getBackground());
		g.fillRect(location.x, location.y, SIZE, SIZE);
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
	
	private PresentationLayer GUI;
	
	private Point delta;
	
	private BufferedImage[] mouseImage;
	
	
	private static final int SIZE = 50;
	
	private static final int SPEED = 7;
}
