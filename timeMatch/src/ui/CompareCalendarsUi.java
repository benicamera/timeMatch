package ui;

import java.nio.channels.SelectableChannel;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import timeMatch.Calendar;
import timeMatch.Controller;

public class CompareCalendarsUi {
	
	private JPanel contentPane;
	final Controller controller;
	JFrame showFrame = new JFrame();
	
	JList<String> list;
	DefaultListModel<String> model = new DefaultListModel<>();
	Calendar[] calendars;
	
	public CompareCalendarsUi(Controller controller) {
		this.controller = controller;
		
		calendars = selectCalendars();
		
		showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		showFrame.setContentPane(contentPane);
	}
	
	private Calendar[] selectCalendars() {
		int numberOfCalendars = Integer.parseInt(JOptionPane.showInputDialog("Wie viele Kalender sollen verglichen werden?"));
		if(numberOfCalendars == 0)
			return selectCalendars();
		Object[] alreadySelectedObjects;
		for (int i = 0; i < numberOfCalendars; i++) {
			ArrayList<Calendar> calendarList = controller.getCalendarList();
			Object[] calendarsToSelect = new Object[calendarList.size()];
			for (int y = 0; y < calendarList.size(); y++) {
				calendarsToSelect[y] = calendarList.get(y);
			}
			
		}
	}
	
	/*
	private void initList(JList<String> list) {
		if(controller.getCalendarNameList().size() <= 0) {
			model.addElement("Keine Kalender gefunden.");
			JOptionPane.showMessageDialog(showFrame, "Keine Kalender gefunden.");
			showFrame.setVisible(false); //you can't see me!
			showFrame.dispose(); //Destroy the JFrame object.
			return;
		}
			
		for(String elementString : controller.getCalendarNameList()) {
			model.addElement(elementString);	
		}
	}
	*/
}
