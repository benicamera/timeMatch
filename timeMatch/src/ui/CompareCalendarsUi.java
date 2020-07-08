package ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import timeMatch.Calendar;
import timeMatch.Controller;
import timeMatch.CustomMap;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

public class CompareCalendarsUi extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final Controller controller;
	JFrame showFrame = new JFrame();
	
	JList<String> list;
	DefaultListModel<String> model = new DefaultListModel<>();
	Calendar[] calendars;
	String[] intervallStrings;
	int monthDays;
	Object[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	Object[] allCalendarStrings;
	
	ArrayList<String> matchIntervallStrings = new ArrayList<String>();
	private JButton backButton;
	private JLabel headerLabel;
	
	public CompareCalendarsUi(Controller controller) {
		this.controller = controller;
		allCalendarStrings = new String[controller.getCalendarNameList().size()];
		allCalendarStrings = controller.getCalendarNameList().toArray();
		
		calendars = getSelectCalendars();
		
		matchIntervallStrings = initMatch(calendars);

		showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		showFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{217, 1, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		contentPane.setVisible(true);
		
		showFrame.setContentPane(contentPane);
		
		headerLabel = new JLabel("Termine");
		headerLabel.setForeground(new Color(255, 105, 180));
		headerLabel.setFont(new Font("Roboto", Font.BOLD, 30));
		GridBagConstraints gbc_headerLabel = new GridBagConstraints();
		gbc_headerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_headerLabel.gridx = 1;
		gbc_headerLabel.gridy = 2;
		contentPane.add(headerLabel, gbc_headerLabel);
		
		
		list = new JList<String>(model);
		list.setVisibleRowCount(7);
		initList(list);
		
		GridBagConstraints gbc_list = new GridBagConstraints();
		gbc_list.anchor = GridBagConstraints.NORTHWEST;
		gbc_list.ipady = 100;
		gbc_list.ipadx = 100;
		gbc_list.insets = new Insets(0, 0, 5, 5);
		gbc_list.gridx = 1;
		gbc_list.gridy = 5;
		contentPane.add(list, gbc_list);
		
		backButton = new JButton("Zurück");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showFrame.setVisible(false);
			}
		});
		backButton.setToolTipText("Zum Hauptmenü");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 0, 5);
		gbc_backButton.gridx = 6;
		gbc_backButton.gridy = 8;
		contentPane.add(backButton, gbc_backButton);
		
		showFrame.setVisible(true);
	}
	
	private ArrayList<String> initMatch(Calendar[] calendars) {
		
		intervallStrings = createIntervall();
		matchIntervallStrings = matchesToStrings(controller.match(calendars, intervallStrings));
		
		if(matchIntervallStrings.size() >= 10) {
			if(JOptionPane.showConfirmDialog(null, String.format("Es wurden %d Termine gefunden. Suchintervall eingrenzen?", matchIntervallStrings.size()), null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
				return initMatch(calendars);
		}
		
		return matchIntervallStrings;
	}
	
	private void initList(JList<String> list) {
		if(matchIntervallStrings.size() <= 0) {
			model.addElement("Keine passenden Termine gefunden.");
			JOptionPane.showMessageDialog(showFrame, "Keine passenden Termine gefunden.");
			showFrame.setVisible(false); //you can't see me!
			showFrame.dispose(); //Destroy the JFrame object.
			return;
		}
			
		for(String elementString : matchIntervallStrings) {
			model.addElement(elementString);	
		}
	}
	
	private ArrayList<String> matchesToStrings(ArrayList<CustomMap[]> matches){
		ArrayList<String> stringMatchesStrings = new ArrayList<String>();
		for (CustomMap[] customMaps : matches) {
			StringBuilder intervallStringBuilder = new StringBuilder();
			System.out.println(customMaps.length);
			for(int i = 0; i<customMaps.length; i++) {
				for(int y = 0; y<3; y++) {
					System.out.println(y+ "++++");
					System.out.println(customMaps[i]);
					intervallStringBuilder.append(controller.reverseDayString(customMaps[i].getString())[y]);
					if(y < 2)
						intervallStringBuilder.append(".");
				}
			intervallStringBuilder.append(", ");
			
			//CustomMao[1] existiert nicht - warum
			if(i==1)
				intervallStringBuilder.append((customMaps[i].getInteger()) + "h ");
			else if(i==0)
				intervallStringBuilder.append((customMaps[i].getInteger() - 1) + "h ");
			if( i < 1) {
				if(customMaps[i+1] != null) 
				intervallStringBuilder.append("bis ");
			}else {
				intervallStringBuilder.append(".");
				break;
			}
		}
			stringMatchesStrings.add(intervallStringBuilder.toString());
		}
		return stringMatchesStrings;
	}
	private String[] createIntervall() {
		String[] intervallString = new String[2];
		intervallString[0] = createIntervallStart();
		intervallString[1] = createIntervallEnd(intervallString[0]);
		return intervallString;
	}
	
	private String createIntervallEnd(String start) {
		Integer[] dateIntegers = controller.reverseDayString(start);
		int startDay = dateIntegers[0];
		int startMonth = dateIntegers[1];
		int startYear = dateIntegers[2];
		
		int year = getYearInputMod(startYear);
		int month = getMonthInputMod(startMonth, year);
		int day = getDayInputMod(startDay);
		
		return controller.getDayString(year, month, day);
	}
	
	private int getDayInputMod(int startDay) {
		Object[] days;
		List<Object> daysList = new ArrayList<Object>();
		int day;
		
		//Für jeden Tag im Monat die Zahl als String für die Auswahl
		for (int i = startDay; i < monthDays; i++) {
			daysList.add(String.format("%d", i+1));
		}
		
		days = daysList.toArray();
		
		String dayInputString = (String) JOptionPane.showInputDialog( contentPane, "Welcher den Tag", "Intervallende", JOptionPane.PLAIN_MESSAGE, null, days, "Tag");
		
		if(dayInputString == "0" || dayInputString == null)
			return getDayInput();
		else {
			day = Integer.parseInt(dayInputString);
			controller.saveCalendars();
			return day;
		}
	}
	
	private int getYearInputMod(int startYear) {
		int year = getYearInputMod();
		if(year < startYear) {
			JOptionPane.showMessageDialog(null, "Auswahl nicht vereinbar");
			return getYearInputMod(startYear);
		}
		return year;
	}
	
	private int getYearInputMod() {
		try {
			return Integer.parseInt((String) JOptionPane.showInputDialog(null, "Welches Jahr?", "Intervallende", JOptionPane.PLAIN_MESSAGE, null, null, "Jahr:"));
		} catch (Exception e) { //wenn nicht eingetragen wurde oder es sonst einen Fehler gibt
			return getYearInputMod();
		}
	}
	private int getMonthInputMod(int startMonth, int year) {
		ArrayList<Object> modMonthsObjectList = new ArrayList<Object>();
		for(int i = (startMonth - 1); i < months.length; i++) {
			modMonthsObjectList.add(months[i]);
		}
		
		int _month = (int)JOptionPane.showInputDialog( contentPane, "Welcher Monat", "Intervallende", JOptionPane.PLAIN_MESSAGE, null, modMonthsObjectList.toArray(), "Monat");
		
		//Wie viele Tage hat der Monat?
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
			return _month;
	}
	
	private String createIntervallStart() {
		int year = getYearInput();
		int month = getMonthInput(year);
		int day = getDayInput();
		
		return controller.getDayString(year, month, day);
	
	}
	
	private int getYearInput() {
		try {
			return Integer.parseInt((String) JOptionPane.showInputDialog(null, "Welches Jahr?", "Intervallstart", JOptionPane.PLAIN_MESSAGE, null, null, "Jahr:"));
		} catch (Exception e) { //wenn nicht eingetragen wurde oder es sonst einen Fehler gibt
			return getYearInput();
		}
	}
	
	private int getDayInput() {
		Object[] days;
		List<Object> daysList = new ArrayList<Object>();
		int day;
		
		//Für jeden Tag im Monat die Zahl als String für die Auswahl
		for (int i = 0; i < monthDays; i++) {
			daysList.add(String.format("%d", i+1));
		}
		
		days = daysList.toArray();
		
		String dayInputString = (String) JOptionPane.showInputDialog( null, "Welcher den Tag", "Intervallstart", JOptionPane.PLAIN_MESSAGE, null, days, "Tag");
		
		if(dayInputString == "0" || dayInputString == null)
			return getDayInput();
		else {
			day = Integer.parseInt(dayInputString);
			controller.saveCalendars();
			return day;
		}
	}
	
	private int getMonthInput(int year) {
		int _month = (int)JOptionPane.showInputDialog( contentPane, "Welcher Monat", "Intervallstart", JOptionPane.PLAIN_MESSAGE, null, months, "Monat");
		
		//Wie viele Tage hat der Monat?
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
			
		//Falls eine Ungültige angabe gemacht wird, das Fenster wird zum beispiel geschlossen.
		if(_month < 1 || _month > 12) {
			return getMonthInput(year);
		}else {
			
			return _month;
		}
		
	}
	
	private Calendar[] getSelectCalendars() {
		int number = askNumber("Wie viele Kalender sollen abgeglichen werden??");
		Calendar[] selectedObjects = new Calendar[number];	
		for(int i = 0; i<number; i++) {
			System.out.println("getSelectedCalendar-for + " + i);
			System.out.println(selectedObjects[0]);
			selectedObjects[i] = getChoosCalendarWithObjectArrays(selectedObjects);
		}
		
		return selectedObjects;
	}
	
	private Calendar getChoosCalendarWithObjectArrays(Object[] selectedObjects) {
		System.out.println(selectedObjects.length);
		Calendar inputCalendar = controller.getCalendar(String.valueOf(JOptionPane.showInputDialog(null, "Welcher Kalender?", "Kalender", JOptionPane.PLAIN_MESSAGE, null, (selectedObjects[0] != null) ? getUnusedFromArrays(allCalendarStrings, calendarArrayToStringArray(selectedObjects)) : allCalendarStrings, controller.getCalendarList().get(0))));
		return (inputCalendar != null) ? inputCalendar : getChoosCalendarWithObjectArrays(selectedObjects);	
	}
	
	private Object[] calendarArrayToStringArray(Object[] calendars) {
		Object[] stringsObject = new Object[calendars.length];
		for (int i = 0; i < calendars.length; i++) {
			if(calendars[i] == null)
				break;
			Calendar tempCalendar = (Calendar) calendars[i];
			stringsObject[i] =  tempCalendar.getName();
		}
		
		return stringsObject;
	}
	
	private Object[] getUnusedFromArrays(Object[] base, Object[] selection) {
		ArrayList<Object> resultArrayList = new ArrayList<Object>();
		
		for (int i = 0; i < base.length; i++) {
			if(!controller.isElementOfArray(base[i], selection))
				resultArrayList.add(base[i]);
		}
		
		System.out.println(resultArrayList.size());
		return resultArrayList.toArray();
	}

	private int askNumber(String message) {
	String inputString = JOptionPane.showInputDialog(message);
	return (inputString == null || Integer.parseInt(inputString)<1) ? askNumber(message) : Integer.parseInt(inputString); //if in einer Zeile
	}
}
