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
	
	Frame editFrame = new Frame();
	
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
	
	/**
	 * Create the frame.
	 */
	public EditCalendarUi(String name, Controller _controller) {
		controller = _controller;
		year = yearInput();
		month = monthInput();
		day = dayInput();
		calendarName = name;
		///numberOfIntervalls = controller.getCalendar(name).getNumberOfIntervalls();
		numberOfIntervalls = 12;
		intervallButtons = new JButton[numberOfIntervalls];
		intervallLabels = new JLabel[numberOfIntervalls];
		initIntervallButtons();
		setTitle(String.format("%s - %n.%n.%n",calendarName, day, month, year));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JButton trashButton = new JButton(new ImageIcon(Gui.class.getResource("/resources/wastebasket.png")));
		trashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Achtung", "Wirklich löschen?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
					//JOptionPane.showMessageDialog(null, controller.deleteCalendar(calendarName));
				}
				if(!controller.isNameTaken(calendarName))
					editFrame.dispatchEvent(new WindowEvent(editFrame, WindowEvent.WINDOW_CLOSING));
			}
		});
		trashButton.setToolTipText("Kalender löschen");
		GridBagConstraints gbc_trashButton = new GridBagConstraints();
		gbc_trashButton.insets = new Insets(0, 0, 5, 0);
		gbc_trashButton.gridx = 13;
		gbc_trashButton.gridy = 0;
		contentPane.add(trashButton, gbc_trashButton);	
		editFrame.add(contentPane);
		
		contentPane.setVisible(true);
		
	}
	
	private void initIntervallButtons() {
		for (int i = 1; i <= numberOfIntervalls; i++) {
			/*
			if(controller.getCalendar(calendarName).isFree(i)) {
				buttonText = "Frei";
				
			}else {
				buttonText = "Belegt";
			}
			*/
			if(i < 5) {
				gridx = 1;
				buttonGridy += 2;
				textGridy += 2;
			}else {
				gridx = i;
			}
			globalI = i;
			buttonText = String.format("%n", globalI);
			intervallButtons[i-1] = new JButton(buttonText);
			
			intervallLabels[i-1] = new JLabel(String.format("%nh-%nh", i+5, i+6));
			intervallButtons[i-1].setToolTipText(String.format("als %s markieren", buttonText));
			
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = gridx;
			gbc_lblNewLabel.gridy = textGridy;
			contentPane.add(intervallLabels[i-1], gbc_lblNewLabel);
			
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = gridx;
			gbc_btnNewButton.gridy = buttonGridy;
			intervallButtons[i-1].setToolTipText(String.format("als %s markieren", buttonText));
			contentPane.add(intervallButtons[i-1], gbc_btnNewButton);
			
			
			/*
			intervallButtons[i-1].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(controller.getCalendar(calendarName).isFree(globalI)) {
						controller.getCalendar(calendarName).setFree(globalI, false);
						buttonText = "Belegt";
						
					}else {
						controller.getCalendar(calendarName).setFree(globalI, true);
						buttonText = "Frei";
					}
					intervallButtons[globalI-1].setToolTipText(String.format("als %s markieren", buttonText));
				}
			});
			*/
		}
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
		day = (int) JOptionPane.showInputDialog( contentPane, "Wähle den Tag", "Tag", JOptionPane.PLAIN_MESSAGE, null, days, "Tag");
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
