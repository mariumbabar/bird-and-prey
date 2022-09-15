package Task201;

import java.awt.Point;

import javax.swing.JPanel;

public interface PresentationLayer {
Point getPreyLocation(int arrIndex);
	
	void killPrey(int arrIndex);
	
	void clearPrey(int arrIndex);
	
	void printSatisfied(String petName);
	
	JPanel getCanvas();
	
}