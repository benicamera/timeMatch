package timeMatch;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		        
		        c.gridx = 0;
		        panel.add(new JLabel(""), c);
		        c.gridy = 4;
		        //Rechtsbündige Ausrichtung
		         c.anchor = GridBagConstraints.EAST;
		         c.gridx = 1;
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
		         
		         c.gridx = 5;
		         //Macht Lücke
		         panel.add(new JLabel(""), c);
		         
		         //Baut das Fenster    
		         frame.add(panel, BorderLayout.CENTER);
		         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		         frame.setTitle("Time Match");
		         frame.setSize(600, 200);
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
	
	private void equipCalendar(int monthSort, String month, int year) {
		
		int days;
		
		JFrame calendarFrame = new JFrame();
		JPanel calendarPanel = new JPanel();
		
		calendarPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        calendarPanel.setLayout(new GridBagLayout());
        
        c.gridx = 0;
        c.gridy = 0;	
        
        
        //MonthSort: 0 = 28 Tage; 1 = 29 Tage; 2 = 30 Tage; 3 = 31 Tage;
        switch (monthSort) {
		case 0: {
			
			c.gridheight = 30;
			days = 28;
			break;
		}
		case 1: {
			c.gridheight = 31;
			days = 29;
			break;
		}
		case 2:{
			c.gridheight = 32;
			days = 30;
			break;
		}
		case 3:{
			c.gridheight = 33;
			days = 31;
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + monthSort);
		}
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        calendarPanel.add(new JLabel("Tag auswählen"));
        
        for(int i = 1; i <= days; i++ ) {
        	c.gridy = i;
        	c.gridx = 0;
        	calendarPanel.add(new JLabel(String.valueOf(i)));
        	c.gridy = 2;
        	int _i = i;
        	JButton button = new JButton("Weiter");
        	button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chooseIntervalls(_i, month, year);
                }
            });
        	
        	calendarPanel.add(button, c);
        }
        
        calendarFrame.add(panel, BorderLayout.CENTER);
        calendarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calendarFrame.setTitle(month + " " + String.valueOf(year));
        calendarFrame.setSize(600, 200);
        calendarFrame.setResizable(true);
        calendarFrame.setLocationRelativeTo(null);
        calendarFrame.setVisible(true);
		
	}
	
	private void chooseIntervalls(int _day, String month, int year) {
		
		JFrame dayFrame = new JFrame();
		JPanel dayPanel = new JPanel();
		
		dayPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        dayPanel.setLayout(new GridBagLayout());
		
        c.gridx = 0;
        c.gridy = 0;	
        c.gridheight = 2;
        c.gridwidth = 13;
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        for(int i = 1; i <= 12; i++ ) {
        	c.gridy = 1;
        	c.gridx = i;
        	dayPanel.add(new JLabel(String.valueOf(i)));
        	c.gridy = 2;
        	int _i = i;
        	JButton button = new JButton("Zeit markieren");
        	button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    chooseIntervalls(_i, month, year);
                }
            });
        	
        	dayPanel.add(button, c);
        }
        
        
        dayFrame.add(panel, BorderLayout.CENTER);
        dayFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dayFrame.setTitle(month + " " + String.valueOf(year));
        dayFrame.setSize(600, 200);
        dayFrame.setResizable(true);
        dayFrame.setLocationRelativeTo(null);
        dayFrame.setVisible(true);
	}
	
}
