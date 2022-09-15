package Task201;

import Task201.ApplicationLayer;
import Task201.PetPreyApp;
import Task201.Prey;
import Task201.Pet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame implements PresentationLayer, ActionListener {

	private JPanel container;
	private JPanel canvaspanel;
	private JPanel controlpanel;
	private JPanel info;
	
	private JButton reset;
	private JButton hungry;
	private JButton makeButton;
	
	private JTextArea message;
	
	private ArrayList<Object> petPrey;
	
	
	
	public GUI() {
		super("TASK 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1000, 600));
		setPreferredSize(this.getMinimumSize());
		init();
		pack();
		setVisible(true);
		petPrey = new ArrayList<Object>();
	}
	
	private void init() {
		// Initializing inner panels
		container = new JPanel();
		canvaspanel = new JPanel();
		canvaspanel.setDoubleBuffered(true);
		controlpanel = new JPanel();
		info = new JPanel();
		
		// Initializing buttons
		ImageIcon resetImage = new ImageIcon("");
		reset = new JButton("Reset", resetImage);
		reset.setBackground(Color.BLUE);
		
		ImageIcon hungryImage = new ImageIcon("");
		hungry = new JButton("Hungry", hungryImage);
		hungry.setBackground(Color.GREEN);
		
		ImageIcon makeImage = new ImageIcon("");
		makeButton = new JButton("Make Pet"
				+ "", makeImage);
		makeButton.setBackground(Color.ORANGE);
		
		
		// Initializing message area
		message = new JTextArea("");
		message.setOpaque(false);
		message.setEditable(false);
		message.setLineWrap(true);
		message.setFont(new Font("TimesRoman", Font.BOLD, 20));
		
		
		// Adding actionListener to buttons
		makeButton.addActionListener(this);
		makeButton.setBackground(Color.RED);
		reset.addActionListener(this);
		reset.setBackground(Color.RED);
		hungry.addActionListener(this);
		hungry.setBackground(Color.RED);
		
		info.setBackground(Color.RED);
		info.setMinimumSize(new Dimension(20,90));
		info.setPreferredSize(info.getMinimumSize());
		info.setBackground(Color.RED);
		controlpanel.setMinimumSize(new Dimension(240, 0));
		controlpanel.setPreferredSize(controlpanel.getMinimumSize());
		controlpanel.setBackground(Color.PINK);
		
		container.setLayout(new BorderLayout());
		controlpanel.setLayout(new GridLayout(3, 3, 10,60));
		
		info.setBackground(Color.PINK);
		info.setLayout(new GridLayout(1,1));
		info.setBackground(Color.PINK);
		getContentPane().add(container);
		
		
		// Adding inner panels to frame's pane 
		container.add(canvaspanel, BorderLayout.CENTER);
		container.add(controlpanel, BorderLayout.WEST);
		container.add(info, BorderLayout.PAGE_END);
		info.setBackground(Color.CYAN);
		info.add(new JScrollPane(message));
		info.setBackground(Color.CYAN);
		
		// Adding buttons to control panel
		controlpanel.add(makeButton);
		controlpanel.add(hungry);
		controlpanel.add(reset);
		
		
		// Setting borders
		container.setBorder(BorderFactory.createEmptyBorder(30,30,30,30));
		container.setBackground(Color.CYAN);
		
		controlpanel.setBorder(BorderFactory.createTitledBorder("Game Buttons"));
		info.setBorder(BorderFactory.createTitledBorder("Message Panel"));
		info.setBackground(Color.WHITE);
		info.setBackground(Color.PINK);

		message.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		message.setBackground(Color.WHITE);
		canvaspanel.setBackground(Color.WHITE);
	}
	
	private void clearMessage() {
		message.setText("");
	}
	
	private void printMessage(String messageToAdd) {
		String previous = message.getText();
		String newMessage = previous + messageToAdd + "\r\n";
		message.setText(newMessage);
	}
	
	private void makePetPrey(String name, String petID, String preyID) {
		ApplicationLayer creator = new PetPreyApp();
		Object[] petPreyObj = creator.createPetPrey(petID, preyID, this, name, petPrey.size());
		Pet pet = (Pet) petPreyObj[0];
		Prey prey = (Prey) petPreyObj[1];
		Thread t1 = new Thread(pet);
		Thread t2 = new Thread(prey);
		t1.start();
		t2.start();
		Object[] petAndPrey = {pet, prey};
		petPrey.add(petAndPrey);
	}

	@Override
	public Point getPreyLocation(int arrIndex) {
		Object[] petPreyArr = (Object[]) petPrey.get(arrIndex);
		Prey prey = (Prey) petPreyArr[1];
		return prey.getLocation();
	}

	@Override
	public void killPrey(int arrIndex) {
		Object[] petPreyArr = (Object[]) petPrey.get(arrIndex);
		Prey prey = (Prey) petPreyArr[1];
		prey.changeState(Prey.DEAD);
	}

	@Override
	public void clearPrey(int arrIndex) {
		Object[] petPreyArr = (Object[]) petPrey.get(arrIndex);
		Prey prey = (Prey) petPreyArr[1];
		prey.clearPrey();;
	}

	@Override
	public void printSatisfied(String petName) {
		printMessage(petName + "'s has eaten.");
	}

	@Override
	public JPanel getCanvas() {
		return canvaspanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		clearMessage();
		if (e.getSource() == makeButton) {
			String[] petStrings = {"BIRD", "CAT"};
			String[] preyStrings = {"INSECT", "MOUSE"};
			String[] options = {"ENTER", "EXIT"};
			String petGuide = "Select a Pet.";
			String preyGuide = "Choose the Prey";
			String nameGuide = "Enter Name of the Pet";
			JComboBox<String> petList = new JComboBox<String>(petStrings);
			JComboBox<String> preyList = new JComboBox<String>(preyStrings);
			JTextField petname = new JTextField();
			Object[] components = {petGuide, petList, preyGuide, preyList, nameGuide, petname};
			int result = JOptionPane.showOptionDialog(this, components, "Select Pet & Prey",
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (result != 0) return;
			if (petname.getText() == null || ((petname.getText()).trim()).length() <= 0) {
				message.setText("First enter a name for the pet!");
				return;
			
			}
			
			
			makePetPrey(petname.getText(), (String)petList.getSelectedItem(), (String)preyList.getSelectedItem());
		} else if(e.getSource() == hungry) {
			if (petPrey.isEmpty()) {
				message.setText("No available pets, create pet first.");
				return;
			}
			for (Object arrObject : petPrey) {
				Object[] petPreyArr = (Object[]) arrObject;
				Pet pet = (Pet) petPreyArr[0];
				if (!pet.isSatisfied()) {
					String info = pet.getName() + " is hungry."; 
					printMessage(info);
					pet.changeState(Pet.HUNGRY);
				}
			}			
						
		} else if (e.getSource() == reset) {
			if (petPrey.isEmpty()) return;
			for (Object arrObject : petPrey) {
				Object[] petPreyArr = (Object[]) arrObject;
				Pet pet = (Pet) petPreyArr[0];
				Prey prey = (Prey) petPreyArr[1];
				pet.changeState(Pet.DEAD);
				prey.changeState(Pet.DEAD);
				canvaspanel.repaint();
			}
		} 
	}
	
}

