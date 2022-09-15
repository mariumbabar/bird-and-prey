package task1;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class run extends JFrame implements ActionListener {
	//JPanel
	private JPanel container;
	private JPanel gamepanel;
	private JPanel controlpanel;
	private JPanel info;
	
	//JButton
	private JButton Reset;
	private JButton Hungry;
	private JButton MakeButton;
	private JButton MakeButton1;
	
	//Notification 
	private JTextArea message;
	
	private ArrayList<Object> pets;
	
	
	// Frame is created
	public run() {
		super("BIRD PREY TASK 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 800));
		setPreferredSize(this.getMinimumSize());
		setBackground(Color.BLUE);
		init();
		pack();
		setVisible(true);
		pets = new ArrayList<Object>();
	}
	
	private void init() {
		
		container = new JPanel();
		gamepanel = new JPanel();
		gamepanel.setDoubleBuffered(true);
		
		controlpanel = new JPanel();
		
		info = new JPanel();
		
		
		// Buttons are named
		
		
		Reset = new JButton("Reset");
		
		
		Hungry = new JButton("Hungry");
		
		
		MakeButton = new JButton("Name your Bird");
		
		
		MakeButton1 = new JButton("Make bird");
		
		// TextArea
		message = new JTextArea("");
		message.setOpaque(false);
		message.setEditable(false);
		message.setLineWrap(true);
		message.setFont(new Font("Calibri", Font.BOLD, 25));
				
		
		MakeButton.addActionListener(this);
		MakeButton.setBackground(Color.YELLOW);
		MakeButton1.addActionListener(this);
		MakeButton1.setBackground(Color.YELLOW);
		Reset.addActionListener(this);
		Reset.setBackground(Color.YELLOW);
		Hungry.addActionListener(this);
		Hungry.setBackground(Color.YELLOW);
		
		info.setMinimumSize(new Dimension(40,220));
		info.setPreferredSize(info.getMinimumSize());
		info.setBackground(Color.PINK);
		controlpanel.setMinimumSize(new Dimension(280, 0));
		controlpanel.setPreferredSize(controlpanel.getMinimumSize());
		controlpanel.setBackground(Color.YELLOW);
		
		container.setLayout(new BorderLayout());
		controlpanel.setLayout(new GridLayout(2, 2, 10, 30));
		
		
		info.setLayout(new GridLayout(1,1));
		
		getContentPane().add(container);
		
		
		
		container.add(controlpanel, BorderLayout.WEST);
		container.add(gamepanel, BorderLayout.CENTER);
		container.add(info, BorderLayout.PAGE_END);
		info.add(message);
		
		
		
		controlpanel.add(MakeButton);
		controlpanel.add(Hungry);
		controlpanel.add(Reset);
		controlpanel.add(MakeButton1);
		
		
		container.setBorder(BorderFactory.createEmptyBorder(20,40,30,30));
		container.setBackground(Color.GRAY);
		
		controlpanel.setBorder(BorderFactory.createTitledBorder("Game Buttons"));
		info.setBorder(BorderFactory.createTitledBorder("Message Panel"));
		message.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		
		gamepanel.setBackground(Color.WHITE);
		
	}
	
	private void clearMessage() {
		message.setText("");
	}
	
	public Point getFlyLocation(int id) {
		Interface[] bb = (Interface[]) pets.get(id);
		flying fly = (flying) bb[1];
		return fly.getLocation();
	}
	
	// Try/catch exception
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
		
		JFrame pet = new run();
		pet.setVisible(true);
	}
	
	public JPanel getCanvas() {
		return gamepanel;
	}
	
	public void stopFly(int id) {
		Interface[] bb2 = (Interface[]) pets.get(id);
		flying fly = (flying) bb2[1];
		fly.changeState(Interface.DEAD);
	}
	
	public void clearFly(int id) {
		Interface[] bb3 = (Interface[]) pets.get(id);
		flying fly = (flying) bb3[1];
		fly.clear();
	}
	
	public void printNotHungry(String name) {
		printMessage(name +  " is no longer hungry.");
	}

	private void printMessage(String messageToAdd) {
		String previous = message.getText();
		String newMessage = previous + messageToAdd + "\r\n";
		message.setText(newMessage);
	}
	
	private void makePetPrey(String birdName) {
		
		// Object of Bird class is created
		bird bird = new bird(this, birdName, pets.size());
		
		Thread t2 = new Thread(bird);
		t2.start();
		
		// Object of Fly class is created
		flying fly = new flying(this);
		Thread t1 = new Thread(fly);
		t1.start();
		
		// Added to the ArrayList
		Interface[] bb4 = {bird, fly};
		pets.add(bb4);
	}
	


	// ActionListener
	public void actionPerformed(ActionEvent e) {
		clearMessage();
		if (e.getSource() == MakeButton) {
			String petname = JOptionPane.showInputDialog(this, "Name The Bird");
			if (petname == null) return;
			if (petname.length()>=0)
					message.setText(petname + " is on the panel");
			
	    if (petname.length() <= 0);
		JOptionPane.showInputDialog(this, "Enter the Bird's Name");
		message.setText("Enter the name to proceed");	
			makePetPrey(petname);
	
		 if(e.getSource() == Hungry) 
		{
			if (pets.isEmpty()) 
			{
				message.setText("You did not give the bird a name");
				return;
			}
			
			for (Object arrObject : pets) {
				Interface[] pet = (Interface[]) arrObject;
				bird bird = (bird) pet[0];
				if (!bird.isSatisfied()) {
					String info = bird.getName() + " is hungry.\r\n"; 
					printMessage(info);
					bird.changeState(Interface.HUNGRY);
				}
			}			
						
		} 
		else if (e.getSource() == Reset)
		{
			if (pets.isEmpty())
				return;
			message.setText("Play Area in Empty now!");
			for (Object arrObject : pets) {
				Interface[] pet = (Interface[]) arrObject;
				bird bird = (bird) pet[0];
				flying fly = (flying) pet[1];
				bird.changeState(Interface.DEAD);
				fly.changeState(Interface.DEAD);
				gamepanel.repaint();
			}
			
		}
		 
		}
		
	}
	
}


		
		 
		
		
	
	

	


