package Task201;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Task201.PresentationLayer;


public class Bird extends Pet {
	public Bird(PresentationLayer GUI, String name, int arrIndex) {
		this.arrIndex = arrIndex;
		this.GUI = GUI;
		delta = new Point(SPEED,SPEED);
		location = new Point(random.nextInt(GUI.getCanvas().getWidth()), random.nextInt(GUI.getCanvas().getHeight()));
		state = MOVING;
		birdImage = new BufferedImage[2];
		this.name = name;
		getImage();
	}
	
	private void getImage() {
		try {
			birdImage[0] = ImageIO.read(new File("bird1.png"));
			birdImage[1] = ImageIO.read(new File("bird2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void move() {
		state = MOVING;
		
		Graphics g = GUI.getCanvas().getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(location.x, location.y, SIZE, SIZE);
        
		location.translate(delta.x, delta.y);

        if (location.x + SIZE > GUI.getCanvas().getWidth()) {
            location.x = GUI.getCanvas().getWidth() - SIZE;
            delta.x = -SPEED;
        } else if (location.x < 0) {
            location.x = 0;
            delta.x = SPEED;
        }
        if (location.y + SIZE > GUI.getCanvas().getHeight()) {
            location.y = GUI.getCanvas().getHeight() - SIZE;
            delta.y = -SPEED;
        } else if (location.y < 0) {
            location.y = 0;
            delta.y = SPEED;
        }
        draw();
	}
	
	private void eat() {
		Graphics g = GUI.getCanvas().getGraphics();
		
		//Redraw
		g.setColor(Color.BLUE);
		g.drawImage(birdImage[1], location.x, location.y, SIZE, SIZE, GUI.getCanvas());
		
		GUI.killPrey(arrIndex);
		delay(200);
		GUI.clearPrey(arrIndex);
		satisfied = true;
		GUI.printSatisfied(name);
	}

	@Override
	protected void hungry() {
		state = HUNGRY;
		Point preyLocation = GUI.getPreyLocation(arrIndex);
		
		if ((preyLocation.x>location.x && preyLocation.x<(location.x+SIZE))&&(preyLocation.y>location.y && preyLocation.y<(location.y+SIZE))) {
			eat();
			state = MOVING;
			return;
		}
		
		Graphics g = GUI.getCanvas().getGraphics();
		g.setColor(GUI.getCanvas().getBackground());
		g.fillRect(location.x, location.y, SIZE, SIZE);

		if (location.x + SIZE > GUI.getCanvas().getWidth()) {
            location.x = GUI.getCanvas().getWidth() - SIZE;
            delta.x = -SPEED;
        } else if (location.x < 0) {
            location.x = 0;
            delta.x = SPEED;
        }
        if (location.y + SIZE > GUI.getCanvas().getHeight()) {
            location.y = GUI.getCanvas().getHeight() - SIZE;
            delta.y = -SPEED;
        } else if (location.y < 0) {
            location.y = 0;
            delta.y = SPEED;
        }
        
        if (location.x > preyLocation.x) location.x -= SPEED;
		if (location.x < preyLocation.x) location.x += SPEED;
		if (location.x == preyLocation.x) location.x += 0;
		
		if (location.y > preyLocation.y) location.y -= SPEED; 
		if (location.y < preyLocation.y) location.y += SPEED;
		if (location.y == preyLocation.y) location.y += 0;
        draw();
	}

	@Override
	protected void draw() {
		Graphics g = GUI.getCanvas().getGraphics();
		g.drawImage(birdImage[0], location.x, location.y, SIZE, SIZE, GUI.getCanvas());
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

	private PresentationLayer GUI;
	
	private Point delta;
	
	private BufferedImage[] birdImage;
	
	private static final int SIZE = 120;
	
	private static final int SPEED = 7;
	
}
