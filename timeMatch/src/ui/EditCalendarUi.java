package ui;

import java.awt.ActiveEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import timeMatch.Controller;
import java.awt.GridBagLayout;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Insets;


public class EditCalendarUi extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final Controller controller;
	int year;
	int day;
	int month;
	int monthDays;
	String calendarName;
	
	JFrame editFrame = new JFrame();
	
	int buttonGridy = 2;
	int textGridy = 1;
	int gridx = 1;
	
	int globalI;
	
	String buttonText;
	String buttonToolTipString;
	
	int numberOfIntervalls;
	
	JButton[] intervallButtons;
	JLabel[] intervallLabels = new JLabel[12];
	
	Object[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	Object[] days1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28};
	Object[] days2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29};
	Object[] days3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30};
	Object[] days4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31};
	private final JButton okayButton = new JButton("Okay");
	
	/**
	 * Create the frame.
	 */
	public EditCalendarUi(String name, Controller _controller) {
		controller = _controller;
		year = yearInput();
		month = monthInput();
		day = dayInput();
		calendarName = name;
		numberOfIntervalls = controller.getCalendar(name).getNumberOfIntervalls();
		intervallButtons = new JButton[numberOfIntervalls];
		intervallLabels = new JLabel[numberOfIntervalls];
		
		editFrame.setBounds(100, 100, 472, 300);
		editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		editFrame.setTitle(String.format("%s - %d.%d.%d",calendarName, day, month, year));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JButton trashButton = new JButton(new ImageIcon(Gui.class.getResource("/resources/wastebasket.png")));
		trashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Achtung", "Wirklich verwerfen?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
					JOptionPane.showMessageDialog(editFrame, controller.getCalendar(calendarName).resetDay(controller.getDayString(year, month, day)));
				}
				if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)));
					editFrame.setVisible(false); //you can't see me!
					editFrame.dispose(); //Destroy the JFrame object
			}
		});
		
		initIntervallButtons();
	
		trashButton.setToolTipText("Termine verwerfen.");
		GridBagConstraints gbc_trashButton = new GridBagConstraints();
		gbc_trashButton.insets = new Insets(0, 0, 5, 0);
		gbc_trashButton.gridx = 13;
		gbc_trashButton.gridy = 0;
		contentPane.add(trashButton, gbc_trashButton);	
		editFrame.getContentPane().add(contentPane);
		
		GridBagConstraints gbc_okayButton = new GridBagConstraints();
		gbc_okayButton.gridx = 13;
		gbc_okayButton.gridy = 7;
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editFrame.setVisible(false); //you can't see me!
				editFrame.dispose(); //Destroy the JFrame object
			}
		});
		okayButton.setToolTipText("Bearbeitung beenden");
		contentPane.add(okayButton, gbc_okayButton);
		
		contentPane.setVisible(true);
		editFrame.setVisible(true);
	}
	
	private void initIntervallButtons() {
		for (int i = 1; i <= numberOfIntervalls; i++) {
			String toolTextString;
			if(controller.getCalendar(calendarName).isFree(i, controller.getDayString(year, month, day))) {
				buttonText = "Frei";
				toolTextString = "Belegt";
			}else {
				buttonText = "Belegt";
				toolTextString = "Frei";
			}
			
			if(i%5 == 0) {
				gridx = 1;
				buttonGridy += 2;
				textGridy += 2;
			}else if(i < 5){
				gridx = (i%5);
			}else {
				gridx = (i%5) + 1;
			}
			globalI = i;
			intervallButtons[i-1] = new JButton(buttonText);
			intervallButtons[i-1].addActionListener((ActionListener) this);
			intervallLabels[i-1] = new JLabel(intervallLabelBuilder(i));
			intervallButtons[i-1].setToolTipText(String.format("als %s markieren", toolTextString));
			
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = gridx;
			gbc_btnNewButton.gridy = buttonGridy;		
			contentPane.add(intervallButtons[i-1], gbc_btnNewButton);
			
			GridBagConstraints gbc_labels = new GridBagConstraints();
			//gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_labels.gridx = gridx;
			gbc_labels.gridy = textGridy;
			contentPane.add(intervallLabels[i-1], gbc_labels); 
			
		}
	}
	
	 public void actionPerformed (ActionEvent e){
		 System.out.println("-----------");
	        if(e.getSource() == intervallButtons[0]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
	        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(1, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(1, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[0].setToolTipText("Als Frei markieren");
					
				}else {
					controller.getCalendar(calendarName).setFree(1, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[0].setToolTipText("Als Belegt markieren");
				}
	        	intervallButtons[0].setText(buttonText);
				intervallButtons[0].setEnabled(false);
				intervallButtons[0].setEnabled(true);

			}else if(e.getSource() == intervallButtons[1]){
				if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
	        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
				
				if(controller.getCalendar(calendarName).isFree(2, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(2, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[1].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(2, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[1].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[1].setText(buttonText);
				intervallButtons[1].setEnabled(false);
				intervallButtons[1].setEnabled(true);
	        }
	        else if (e.getSource() == intervallButtons[2]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(3, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(3, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[2].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(3, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[2].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[2].setText(buttonText);
				intervallButtons[2].setEnabled(false);
				intervallButtons[2].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[3]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(4, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(4, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[3].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(4, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[3].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[3].setText(buttonText);
				intervallButtons[3].setEnabled(false);
				intervallButtons[3].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[4]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(5, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(5, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[4].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(5, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[4].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[4].setText(buttonText);
				intervallButtons[4].setEnabled(false);
				intervallButtons[4].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[5]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(6, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(6, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[5].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(6, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[5].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[5].setText(buttonText);
				intervallButtons[5].setEnabled(false);
				intervallButtons[5].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[6]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(7, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(7, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[6].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(7, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[6].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[6].setText(buttonText);
				intervallButtons[5].setEnabled(false);
				intervallButtons[5].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[7]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(8, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(8, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[7].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(8, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[7].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[7].setText(buttonText);
				intervallButtons[7].setEnabled(false);
				intervallButtons[7].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[8]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(9, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(9, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[8].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(9, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[8].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[8].setText(buttonText);
				intervallButtons[8].setEnabled(false);
				intervallButtons[8].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[9]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
	        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(10, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(10, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[9].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(10, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[9].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[9].setText(buttonText);
				intervallButtons[9].setEnabled(false);
				intervallButtons[9].setEnabled(true);
	            
	        }else  if (e.getSource() == intervallButtons[10]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(11, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(11, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[10].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(11, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[10].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[10].setText(buttonText);
				intervallButtons[10].setEnabled(false);
				intervallButtons[10].setEnabled(true);
	            
	        }else if (e.getSource() == intervallButtons[11]){
	        	if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)))
        			controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day));
	        	if(controller.getCalendar(calendarName).isFree(12, controller.getDayString(year, month, day))) {
					controller.getCalendar(calendarName).setFree(12, false, controller.getDayString(year, month, day));
					buttonText = "Belegt";
					intervallButtons[11].setToolTipText("Als Frei markieren");
				}else {
					controller.getCalendar(calendarName).setFree(12, true, controller.getDayString(year, month, day));
					buttonText = "Frei";
					intervallButtons[11].setToolTipText("Als Belegt markieren");
				}
				intervallButtons[11].setText(buttonText);
				intervallButtons[11].setEnabled(false);
				intervallButtons[11].setEnabled(true);
	            
	        }
	    }
	
	private String intervallLabelBuilder(int i) {
		StringBuilder sb = new StringBuilder();  
		if(numberOfIntervalls > 24) {
			sb.append(String.format("%dh-", (i+(12 - (24/2 + 1)))));
			sb.append(String.format("%dh", (i+(12 - (24/2)))));
		}else {
			sb.append(String.format("%dh-", (i+(12 - (numberOfIntervalls/2 + 1)))));
			sb.append(String.format("%dh", (i+(12 - (numberOfIntervalls/2)))));
		}
		return sb.toString();
	}
	
	private int yearInput() {
		try {
			return Integer.parseInt(JOptionPane.showInputDialog("Welches Jahr?", "Jahr:"));
		} catch (Exception e) {
			return yearInput();
		}
	}
	
	private int dayInput() {
		Object[] days;
		switch (monthDays) {
		case 28:
			days = (Object[]) days1;
			break;
		case 29: 
			days = (Object[]) days2;
			break;
		case 30:
			days = (Object[]) days3;
			break;
		case 31:
			days = (Object[]) days4;
			break;
			
		default:
			days = (Object[]) days4;
			break;
		}
		day = (int) JOptionPane.showInputDialog( contentPane, "Welcher den Tag", "Tag", JOptionPane.PLAIN_MESSAGE, null, days, "Tag");
		if(day == 0)
			return dayInput();
		else {
			return day;
		}
	}
	
	private int monthInput() {
		int _month = (int)JOptionPane.showInputDialog( contentPane, "Welcher Monat", "Monat", JOptionPane.PLAIN_MESSAGE, null, months, "Monat");
			if(_month < 8) {
				if(_month%2 == 0) {
					if(_month == 2) {
						if(controller.isLeapYear(year))
							monthDays = 29;
						else 
							monthDays = 28;
					}else {
						monthDays = 30;
					}
					}else {
						monthDays = 31;
					}	
				}else {
					if(_month%2 == 0) {
						monthDays = 31;
					}else {
						monthDays = 30;
					}
			}
		if(_month < 1 || _month > 12) {
			return monthInput();
		}else {
			
			return _month;
		}
	}

}
