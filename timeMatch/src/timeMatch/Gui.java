package timeMatch;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Creates GUI
 * 
 * @author Benjamin Dangl
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */

public class Gui {

	final Controller controller;
	
	 private JFrame frame = new JFrame();
	 private JPanel panel = new JPanel();
	 
	 private JButton showButton = new JButton("Kalender anzeigen");
	 private JButton matchButton = new JButton("Termin finden");
	 private JButton createButton = new JButton("Erstelle Kalender");
	 
	 private JLabel controllerJLabel = new JLabel("-----------------------------------------------");
	 
	 final GridBagConstraints c = new GridBagConstraints();
	
	public Gui() {
		controller = new Controller();
		
		initUI();
		
		createActionListener();
	}
	
	public void toTest(/* insert Parameters*/) {
		controller.toTest(/*insert Parameters*/);
	}
	
	private void initUI() {
		    	
		    	//Baut das Feld
		    	

		    	 panel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
		         panel.setLayout(new GridBagLayout());
		         //gibt die Position und Ausrichtung im Grid an
		         c.gridx = 0;
		         c.gridy = 0;	
		         c.gridheight = 1;
		         c.gridwidth = 3;
		         c.weightx = 1.0;
		         c.weighty = 1.0;
		         panel.add(controllerJLabel, c);
		       
		        c.gridwidth = 1;
		        
		        c.gridy = 4;
		        //Rechtsbündige Ausrichtung
		         c.anchor = GridBagConstraints.EAST;
		         c.gridx = 0;
		         c.anchor = GridBagConstraints.WEST;
		         panel.add(createButton, c);
		         
		         c.gridx = 2;
		         c.anchor = GridBagConstraints.EAST;
		         panel.add(showButton, c);
		         
		         c.gridx = 4;
		         panel.add(matchButton, c);
		         
		         c.gridx = 3;
		         //Macht Lücke
		         panel.add(new JLabel(""), c);
		         
		         c.gridx = 2;
		         //Macht Lücke
		         panel.add(new JLabel(""), c);
		         
		         //Baut das Fenster    
		         frame.add(panel, BorderLayout.CENTER);
		         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		         frame.setTitle("Time Match");
		         frame.setSize(500, 200);
		         frame.setResizable(true);
		         frame.setLocationRelativeTo(null);
		         frame.setVisible(true);

		         System.out.println("New GUI");
		         
		       
		    }
		    
		    private void createActionListener()
		    {
		//fügt ActionListener hinzu (Was soll passieren, wenn der Button gedrückt wurde)
		    	
			    showButton.addActionListener((event) -> {
				   showButton();
			    });

			    createButton.addActionListener((event) -> {
				   createButton();
			    });
			    
			    matchButton.addActionListener((event) -> {
			    	matchButton();
			    	
			    });
		    }
	
	
	private void createButton() {
				// TODO Auto-generated method stub
				
			}
	private void showButton() {
				// TODO Auto-generated method stub
				
			}
	
	private void matchButton() {
		// TODO Auto-generated method stub
		
	}
	
}
