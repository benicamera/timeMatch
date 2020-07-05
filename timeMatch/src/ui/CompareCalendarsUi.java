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
		
		calendars = getSelectCalendars();
		
		showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		showFrame.setContentPane(contentPane);
	}
	
	private Calendar[] getSelectCalendars() {
		int number = askNumber("Wie viele Kalender sollen abgeglichen werden??");
		Object[] selectedObjects = new Object[number];	
		for(int i = 0; i<number; i++) {
			selectedObjects[i] = getChoosCalendarWithObjectArrays(selectedObjects);
		}
		
		return (Calendar[]) selectedObjects;
	}
	
	private Calendar getChoosCalendarWithObjectArrays(Object[] selectedObjects) {
		Object[] allCalendarsObjects = controller.getCalendarList().toArray();
		Calendar inputCalendar = controller.getCalendar(JOptionPane.showInputDialog("Welcher Kalender?", getCalendarNamesFromArray(getUnusedFromArrays(allCalendarsObjects, selectedObjects))));
				return (inputCalendar != null) ? inputCalendar : getChoosCalendarWithObjectArrays(selectedObjects);
	}
	
	private String[]  getCalendarNamesFromArray(Object[] array) {
		ArrayList<String> resultArrayList = new ArrayList<String>();
		
		for (Object object : array) {
			resultArrayList.add(controller.getCalendarName((Calendar) object));
		}
		
		return (String[]) resultArrayList.toArray();
	}
	
	private Object[] getUnusedFromArrays(Object[] base, Object[] selection) {
		ArrayList<Object> resultArrayList = new ArrayList<Object>();
		for (Object object : controller.getObjectArrayIntersection(base, selection)) {
			if(controller.getElementIndexOfArray(base, object) < 0)
				resultArrayList.add(object);
		}
		
		return resultArrayList.toArray();
	}
	
	
	
	private int askNumber(String message) {
	String inputString = JOptionPane.showInputDialog(message);
	return (inputString == null || Integer.parseInt(inputString)<1) ? askNumber(message) : Integer.parseInt(inputString); //if in einer Zeile
	}
}
