package Task201;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Task201.GUI;

public class MainGUI {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException e) {
	       e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    catch (InstantiationException e) {
	    	e.printStackTrace();
	    }
	    catch (IllegalAccessException e) {
	    	e.printStackTrace();
	    }
		
		new GUI();
	}
}
