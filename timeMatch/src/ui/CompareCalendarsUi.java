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
import javax.swing.WindowConstants;
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

/*
 * Zeigt Content an
 * 
 * @author Benjamin Dangl
 * @version 08.07.2020
 * - Klasse erstellt (30.06.2020).
 * 
 */

public class CompareCalendarsUi extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final Controller controller;
	JFrame showFrame = new JFrame();
	
	JList<String> list; //Liste zum ausgeben der gemeinsamen Termin
	DefaultListModel<String> model = new DefaultListModel<>(); 
	Calendar[] calendars;
	String[] intervallStrings;
	int monthDays;
	Object[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	Object[] allCalendarStrings;
	
	Calendar dateCalendar;
	
	ArrayList<String> matchIntervallStrings = new ArrayList<String>();
	private JButton backButton;
	private JLabel headerLabel; //Text
	
	private int numberOfElements;
	
	public CompareCalendarsUi(Controller controller, boolean isMatch) {
		this.controller = controller; //this. bezeichtnet die Variable dieser Klasse
		allCalendarStrings = new String[controller.getCalendarNameList().size()]; //deklariert array
		allCalendarStrings = controller.getCalendarNameList().toArray();
		
		showFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		if(isMatch) {
			calendars = getSelectCalendars(); //gibt zu verglichende Kalender zurück
			matchIntervallStrings = initMatch(calendars); //gibt gemeinsame Termine zurück
		}else {
			dateCalendar = getSingleCalendar();
			if(dateCalendar == null)
				return;
			matchIntervallStrings = matchesToString(controller.getEvents(dateCalendar));
		}
		

		showFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
		
		if(isMatch)
			headerLabel = new JLabel("Freie Zeit");
		else {
			headerLabel = new JLabel("Deine Termine");
		}
		
		headerLabel.setForeground(new Color(255, 105, 180));
		headerLabel.setFont(new Font("Roboto", Font.BOLD, 30));
		GridBagConstraints gbc_headerLabel = new GridBagConstraints();
		gbc_headerLabel.insets = new Insets(0, 0, 5, 5);
		gbc_headerLabel.gridx = 1;
		gbc_headerLabel.gridy = 2;
		contentPane.add(headerLabel, gbc_headerLabel);
		
		
		list = new JList<String>(model);
		list.setVisibleRowCount(7);
		initList(list); //initiiert Liste
		
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
		backButton.setToolTipText("Zum Hauptmenu");
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 0, 5);
		gbc_backButton.gridx = 6;
		gbc_backButton.gridy = 8;
		contentPane.add(backButton, gbc_backButton);
		
		int ySize = 30*numberOfElements;
		if(ySize < 250) //sonst fenster zu klein, wenn nur wenige Matches
			ySize = 250;
		showFrame.setBounds(100, 100, 550, ySize); //Höhe ist variabel, je nach anzahl der Termine
		showFrame.setVisible(true);
	}
	
	//initiiert Matches
	private ArrayList<String> initMatch(Calendar[] calendars) {
		
		intervallStrings = createIntervall(); //gibt intervall als String[2] zurück
		matchIntervallStrings = matchesToString(controller.match(calendars, intervallStrings)); //gibt Matches als Strings zurück
		
		if(matchIntervallStrings.size() > 10) { //Wenn mehr als 10 Matches gefunden wurden, frage nach, ob der Intervall beschränkt werden soll
			if(JOptionPane.showConfirmDialog(null, String.format("Es wurden %d Termine gefunden. Suchintervall eingrenzen?", matchIntervallStrings.size()), null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) //wenn ja
				return initMatch(calendars); //Vorgang wiederholen
		}
		return matchIntervallStrings; //gebe Matches zurück
	}
	
	//initiiert Liste
	private void initList(JList<String> list) {
		if(matchIntervallStrings.size() <= 0) { //wenn keine Matches gefunden wurden
			model.addElement("Keine passenden Termine gefunden.");
			JOptionPane.showMessageDialog(showFrame, "Keine passenden Termine gefunden.");
			showFrame.setVisible(false); 
			contentPane.setVisible(false);
			showFrame.dispose(); 
			return;
		}
			
		for(String elementString : matchIntervallStrings) { //Füge alle Matches zum Model/Liste hinzu
			model.addElement(elementString);	
		}
	}
	
	//wandelt Matches zu String um. CustumMap speichert nur das datum als String und den Intervall als Integer
	private ArrayList<String> matchesToString(ArrayList<CustomMap> matches){
		ArrayList<String> stringMatchesStrings = new ArrayList<String>();
		for (CustomMap customMap : matches) { //für jedes Match
			StringBuilder intervallStringBuilder = new StringBuilder();
			if(customMap.getInteger()<24) {
				for(int y = 0; y<3; y++) { //geht das Datum durch
					intervallStringBuilder.append(controller.reverseDayString(customMap.getString())[y]); //reverseDayString gibt Integer[3] mit 0 = tag, 1 = Monat und 2 = Jahr zurück
					if(y < 2)
						intervallStringBuilder.append("."); //Der Punkt im datum
				}
			intervallStringBuilder.append(", ");
			intervallStringBuilder.append((customMap.getInteger()) + "h - " + (customMap.getInteger() + 1) + "h"); //gibt Intervall aus
			intervallStringBuilder.append(".");		
			
			stringMatchesStrings.add(intervallStringBuilder.toString()); //den String zur Liste hinzufügen
			}
			}
		numberOfElements = stringMatchesStrings.size(); //wie viele Matches es gab
		return stringMatchesStrings; //gebe Matches zurück
	}
	
	//erfragt einen Kalender
	private Calendar getSingleCalendar() {
		Calendar selectedCalendar;
		String calendarNameString = (String) JOptionPane.showInputDialog(null, "Welcher Kalender?", "Kalenderauswahl",  JOptionPane.PLAIN_MESSAGE, null, allCalendarStrings, "Kalender");
		if(calendarNameString == null)
			return null;
		selectedCalendar = controller.getCalendar(calendarNameString);
		return selectedCalendar;
	}
	
    //fügt suchintervallstart und -ende zusammen und gibt als Array zurück
	private String[] createIntervall() {
		String[] intervallString = new String[2];
		intervallString[0] = createIntervallStart(); //gibt start zurück
		intervallString[1] = createIntervallEnd(intervallString[0]); //gibt ende zurück
		return intervallString;
	}
	
	//Holt sich enddatum vom Nutzer und gibt zurück
	private String createIntervallEnd(String start) {
		//holt sich startdatum als Integers, damit der nutzer nicht ein Enddatum vor dem Startdatum ählt
		Integer[] dateIntegers = controller.reverseDayString(start);
		int startDay = dateIntegers[0];
		int startMonth = dateIntegers[1];
		int startYear = dateIntegers[2];
		
		//die get...InputMod-Methoden sind gleich wie die normalen, ausser, dass man kein datum vor dem Start auswählen kann
		int year = getYearInputMod(startYear); //gibt Jahreszahl zurück
		int month = getMonthInputMod(startMonth, year, startDay); //Monat
		int day = getDayInputMod(startDay, startMonth, month); //Tag
		
		return controller.getDayString(year, month, day); //gibt Datum als String zurück
	}
	
	//holt sich Tag, das nicht vor dem start ist, vom Nutzer
	private int getDayInputMod(int startDay, int startMonth, int endMonth) {
		Object[] days;
		List<Object> daysList = new ArrayList<Object>();
		int day;
		
		//Für jeden Tag im Monat die Zahl als String für die Auswahl
		if(endMonth != startMonth)  //wenn es der nächste Monat ist
			startDay = 0;
		for (int i = startDay; i < monthDays; i++) {
			daysList.add(String.format("%d", i+1));
		}
		
		days = daysList.toArray();
		
		//erfagt den Tag von der Auswahl
		String dayInputString = (String) JOptionPane.showInputDialog( contentPane, "Welcher den Tag", "Intervallende", JOptionPane.PLAIN_MESSAGE, null, days, "Tag");
		
		//wenn Falsche angabe, wiederhole den Vorgang
		if(dayInputString == "0" || dayInputString == null)
			return getDayInput();
		else {
			day = Integer.parseInt(dayInputString); //input zu Integer
			controller.saveCalendars(); //sicherheitshalber
			return day;
		}
	}
	
	//holt sich Jahr, das nicht vor dem start ist, vom Nutzer
	private int getYearInputMod(int startYear) {
		int year = getYearInput(); //hole Jahr
		if(year < startYear) { //wenn vor Startjahr, wiederhole vorgang
			JOptionPane.showMessageDialog(null, "Auswahl nicht vereinbar");
			return getYearInputMod(startYear);
		}
		return year;
	}
	
	//holt sich Monat, der nicht vor dem start ist, vom Nutzer
	private int getMonthInputMod(int startMonth, int year, int startYear) {
		ArrayList<Object> modMonthsObjectList = new ArrayList<Object>();
		if(year != startYear)
			startMonth = 1;
		for(int i = (startMonth - 1); i < months.length; i++) {
			modMonthsObjectList.add(months[i]); //Fügt jeden wählbaren Monat zur auswahl hinzu
		}
		//holt sich den Monat vom Nutzer
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
	
	//setzt suchintervallstartdatum zusammen
	private String createIntervallStart() {
		int year = getYearInput();
		int month = getMonthInput(year);
		int day = getDayInput();
		
		return controller.getDayString(year, month, day);
	
	}
	
	//Holt sich Jahrezahl vom Nutzer
	private int getYearInput() {
		try {
			return Integer.parseInt((String) JOptionPane.showInputDialog(null, "Welches Jahr?", "Intervallstart", JOptionPane.PLAIN_MESSAGE, null, null, "Jahr:"));
		} catch (Exception e) { //wenn nicht eingetragen wurde oder es sonst einen Fehler gibt
			return getYearInput();
		}
	}
	
	//holt sich Tag vom Nutzer
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
	
	//holt sich monat vom Nutzer
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
	
	//holt sich die zu vergleichenden Kalender vom Nutzer
	private Calendar[] getSelectCalendars() {
		int number = askNumber("Wie viele Kalender sollen abgeglichen werden?"); 
		if(number < 2 || number > controller.getCalendarList().size()) { //wenn ungültige Anzahl
			JOptionPane.showMessageDialog(null, "Vergleich nicht durchführbar");
			showFrame.setVisible(false);
			return null;
		}
		Calendar[] selectedObjects = new Calendar[number];	
		for(int i = 0; i<number; i++) { //hole soviele Kalender, wie ausgewählt
			selectedObjects[i] = getChoosenCalendarWithObjectArrays(selectedObjects); //gibt ausgewählten Kalender zurück
			if(selectedObjects[i] == null) //wenn keine richtige Auswahl
				return null;
		}
		return selectedObjects;
	}
	
	//Holt sich Kalender aus den auswählbaren vom Nutzer
	private Calendar getChoosenCalendarWithObjectArrays(Object[] selectedObjects) {
		//erfragt Kalender aus den Kalendern, die noch nicht ausgewählt wurden. sonst würde es einen schlimmen schlimmen fehler geben.
		Calendar inputCalendar = controller.getCalendar(String.valueOf(JOptionPane.showInputDialog(null, "Welcher Kalender?", "Kalender", JOptionPane.PLAIN_MESSAGE, null, (selectedObjects[0] != null) ? getUnusedFromArrays(allCalendarStrings, calendarArrayToStringArray(selectedObjects)) : allCalendarStrings, controller.getCalendarList().get(0))));
		return (inputCalendar != null) ? inputCalendar : null;	//einzeilige if: (Statement) ? true : false
	}
	
	//wandelt die Objekte der Klasse Calendar aus einem Array in Texte um und fügt sie zur Liste hinzu
	private Object[] calendarArrayToStringArray(Object[] calendars) {
		Object[] stringsObject = new Object[calendars.length]; //Object ist jede Klasse: alle Klassen sind unterklassne der klasse Object
		for (int i = 0; i < calendars.length; i++) {
			if(calendars[i] == null) //wenn ein element des Arrays null ist -> beende for-schleife
				break;
			Calendar tempCalendar = (Calendar) calendars[i]; //setzte mit temporärem Kalender gleich
			stringsObject[i] = tempCalendar.getName(); //FÜge dessen Namen zum array hinzu. //die beiden zeilen hätte man auch in eine machen können, aber so ist es deutlich,deutlich übersichtlicher
		}
		//gibt array zurück
		return stringsObject;
	}
	
	//filtert alle Elemente heraus, die nicht in beiden array drin sind
	private Object[] getUnusedFromArrays(Object[] base, Object[] selection) {
		ArrayList<Object> resultArrayList = new ArrayList<Object>();
		for (int i = 0; i < base.length; i++) {
			if(!controller.isElementOfArray(base[i], selection)) //wenn es kein Element des anderen Arrays ist
				resultArrayList.add(base[i]);
		}
		return resultArrayList.toArray();
	}
	
	//erfragt eine Nummer über 0 beim Nutzer mit einer Nachricht
	private int askNumber(String message) {
		String inputString = JOptionPane.showInputDialog(message);
		return (inputString == null || Integer.parseInt(inputString)<1) ? askNumber(message) : Integer.parseInt(inputString); //if in einer Zeile
	}
}
