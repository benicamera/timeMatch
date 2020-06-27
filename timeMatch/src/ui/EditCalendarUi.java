package ui;

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

public class EditCalendarUi extends JFrame{

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
				
				if(JOptionPane.showConfirmDialog(null, "Achtung", "Wirklich löschen?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
					//JOptionPane.showMessageDialog(null, controller.deleteCalendar(calendarName));
				}
				if(!controller.isNameTaken(calendarName))
					editFrame.setVisible(false); //you can't see me!
					editFrame.dispose(); //Destroy the JFrame object
			}
		});
		
		initIntervallButtons();
	
		trashButton.setToolTipText("Kalender loeschen");
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
			
			if(controller.getCalendar(calendarName).isFree(i, controller.getDayString(year, month, day))) {
				buttonText = "Frei";
				System.out.println(buttonText);
			}else {
				buttonText = "Belegt";
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
			
			intervallLabels[i-1] = new JLabel(intervallLabelBuilder(i));
			intervallButtons[i-1].setToolTipText(String.format("als %s markieren", buttonText));
			
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = gridx;
			gbc_btnNewButton.gridy = buttonGridy;
			intervallButtons[i-1].setToolTipText(String.format("als %s markieren", buttonText));
			
			contentPane.add(intervallButtons[i-1], gbc_btnNewButton);
			
			GridBagConstraints gbc_labels = new GridBagConstraints();
			//gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_labels.gridx = gridx;
			gbc_labels.gridy = textGridy;
			contentPane.add(intervallLabels[i-1], gbc_labels); 

			intervallButtons[i-1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(controller.getCalendar(calendarName).isFree(globalI, controller.getDayString(year, month, day))) {
						controller.getCalendar(calendarName).setFree(globalI, false, controller.getDayString(year, month, day));
						buttonText = "Belegt";
						
					}else {
						controller.getCalendar(calendarName).setFree(globalI, true, controller.getDayString(year, month, day));
						buttonText = "Frei";
					}
					intervallButtons[globalI-1].setText(buttonText);
					intervallButtons[globalI-1].setToolTipText(String.format("als %s markieren", buttonText));
				}
			});
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
		int _month = (int)JOptionPane.showInputDialog( contentPane, "Wähle den Monat", "Monat", JOptionPane.PLAIN_MESSAGE, null, months, "Monat");
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
