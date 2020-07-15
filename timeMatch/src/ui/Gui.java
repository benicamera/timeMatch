package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import timeMatch.Controller;
import timeMatch.CustomMap;

/*
 * Zeigt Content an
 * 
 * @author Benjamin Dangl
 * @version 08.07.2020
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020).
 * 
 */

public class Gui {

	private JFrame frame;

	final Controller controller;
	
	CompareCalendarsUi matchWindowCalendarsUi;
	
	FileFilter filter = new FileNameExtensionFilter("Objektdatei", ".dat"); //Filter für Datachooser (eigentlich unnötig)
	
	Object[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}; //die Monate
	
	int monthDays;
	
	public Gui() {
		controller = new Controller();
		toTest();
		initialize();
		
	}
	public void toTest(/*Parameters*/) {
		System.out.println("toTest gui aufgerufen");
		controller.toTest();
	}
	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame(); //erstellt neues Window
		frame.setBounds(100, 100, 560, 300); //bestimmt die größe
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //bestimmt, was passiert, wenn man auf das schließen x klickt
		GridBagLayout gridBagLayout = new GridBagLayout(); //bestimmt Layout
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 66, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout); //weißt der Contentebene des Fensters das Layout zu
		
		JButton importButton = new JButton(""); //erstellt neuen Knopf mit dem Text ""
		importButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/icons8-import-50Verkleinert.png"))); //legt Knopficon = Bild fest
		importButton.addActionListener(new ActionListener() { //legt fest, was passieren soll, wenn man auf den Knopf klickt
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 JFileChooser chooser = new JFileChooser(); //erstell Dateienauswahl
				 
				 //chooser.addChoosableFileFilter(filter);
				 
				 int value = chooser.showOpenDialog(null); //erstell Dateienauswahlfenster
				 if(value == JFileChooser.APPROVE_OPTION) //wenn okay geklickt wurde
			        {
					 if(controller.isCalendarFile(chooser.getSelectedFile())) { //wenn die ausgewählte Datei eine Datei von uns ist
						 JOptionPane.showMessageDialog(frame, controller.importCalendars(chooser.getSelectedFile())); //rufe controller.importCalendars mit der ausgewählten datei aus und gebe Status in einem PopUpWindow aus
					}else {
						JOptionPane.showMessageDialog(frame, String.format("%s speichert keine timeMatch-Kalender oder ist kaputt.", chooser.getSelectedFile().getName())); //Gebe fehlermeldung aus.
					}
			                 
			        }
			}
		});
		
		JButton exportButton = new JButton("");
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showInputDialog(frame, String.format("Die Kalenderdatei befindet sich hier:"), controller.getSaveLocationFolder());
			            }
			        });
				 
		exportButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/icons8-export-50.png")));
		exportButton.setToolTipText("Kalenderdatei in Zwischenablage kopieren.");
		GridBagConstraints gbc_exportButton = new GridBagConstraints(); //neues Layout -> Grid
		gbc_exportButton.insets = new Insets(0, 0, 5, 5);
		gbc_exportButton.gridx = 9; //GridPosition x=9
		gbc_exportButton.gridy = 0; //GridPosition y=0
		frame.getContentPane().add(exportButton, gbc_exportButton); //fügt den exportButton an der Stelle x=9, y=0 zur content ebene hinzu
		importButton.setToolTipText("Kalender importieren."); //fügt den Text "Kalender importieren." als "Hovertext" hinzu.
		
		GridBagConstraints gbc_importButton = new GridBagConstraints();
		gbc_importButton.insets = new Insets(0, 0, 5, 5);
		gbc_importButton.gridx = 13;
		gbc_importButton.gridy = 0;
		frame.getContentPane().add(importButton, gbc_importButton);
		
		JLabel titleLabel = new JLabel("timeMatch!"); //erstellt ein JLabel - Text - mit dem Text "timeMatch!"
		titleLabel.setForeground(new Color(255, 105, 180)); //Vordergrundfarbe
		titleLabel.setBackground(new Color(240, 255, 240)); //Hintergrundfarbe
		titleLabel.setFont(new Font("Rubik", Font.BOLD, 59)); //bestimmt Schriftart und -größe
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.gridwidth = 18;
		gbc_titleLabel.insets = new Insets(0, 0, 5, 0);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 1;
		frame.getContentPane().add(titleLabel, gbc_titleLabel);
		
		JButton matchButton = new JButton("");
		matchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matchWindowCalendarsUi = new CompareCalendarsUi(controller, true); //erstellt Objekt der Klasse CompareCalendarsUi und gibt ihm den controller weiter = neues Fenster
			}
		});
		matchButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/matchButtonIcon.png")));
		matchButton.setToolTipText("suche Match");
		GridBagConstraints gbc_matchButton = new GridBagConstraints();
		gbc_matchButton.insets = new Insets(0, 0, 5, 5);
		gbc_matchButton.gridx = 6;
		gbc_matchButton.gridy = 2;
		frame.getContentPane().add(matchButton, gbc_matchButton);
		
		//////////////////////////////////////////////
		JButton showEventsButton = new JButton(""); 	
		showEventsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showEventsButtonAction();
			}
		});
		
		showEventsButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/getEventsButtonIcon_klein-min.png")));
		showEventsButton.setToolTipText("erhalte deine Termine");
		GridBagConstraints gbc_showEventsButton = new GridBagConstraints();
		gbc_showEventsButton.insets = new Insets(0, 0, 5, 5);
		gbc_showEventsButton.gridx = 1;
		gbc_showEventsButton.gridy = 2;
		showEventsButton.setVisible(true);
		frame.getContentPane().add(showEventsButton, gbc_showEventsButton);		
		////////////////////////////////////////
		
		JButton createButton = new JButton("");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButtonAction(); //ruft createButtonAction() auf -> Zeile 180
			}
		});
		createButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/createButtonIcon.png")));
		createButton.setToolTipText("Kalender erstellen");
		GridBagConstraints gbc_createButton = new GridBagConstraints();
		gbc_createButton.insets = new Insets(0, 0, 5, 5);
		gbc_createButton.gridx = 9;
		gbc_createButton.gridy = 2;
		frame.getContentPane().add(createButton, gbc_createButton);
		
		JLabel spaceLabel2 = new JLabel("");
		GridBagConstraints gbc_spaceLabel2 = new GridBagConstraints();
		gbc_spaceLabel2.insets = new Insets(0, 0, 5, 5);
		gbc_spaceLabel2.gridx = 12;
		gbc_spaceLabel2.gridy = 2;
		frame.getContentPane().add(spaceLabel2, gbc_spaceLabel2);
		
		JButton showButton = new JButton(new ImageIcon(Gui.class.getResource("/resources/showButtonIcon.png")));
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(controller.getCalendarNameList().size() > 0) { //wenn wenigstens ein Kalender existiert
					@SuppressWarnings("unused") //weil in dieser Klasse showuiCalendarsUi nicht mehr benutzt wird
					ShowCalendarsUi showuiCalendarsUi = new ShowCalendarsUi(controller); //erstellt Objekt der Klasse ShowCalendarsUi und gibt ihm den controller weiter = neues Fenster
				}else {
						JOptionPane.showMessageDialog(frame, "Keine Kalender gefunden."); //PopUpWindow mit Fehlermeldung anzeigen
					}
			}
		});
		GridBagConstraints gbc_showButton = new GridBagConstraints();
		gbc_showButton.insets = new Insets(0, 0, 5, 5);
		gbc_showButton.gridx = 13;
		gbc_showButton.gridy = 2;
		frame.getContentPane().add(showButton, gbc_showButton);
		
		JLabel spaceLabel = new JLabel("");
		GridBagConstraints gbc_spaceLabel = new GridBagConstraints();
		gbc_spaceLabel.insets = new Insets(0, 0, 0, 5);
		gbc_spaceLabel.gridx = 0;
		gbc_spaceLabel.gridy = 4;
		frame.getContentPane().add(spaceLabel, gbc_spaceLabel);
		frame.setVisible(true);
	}

	//createButtonAction() erfragt Kalendernamen und erstellt den. Leitet an editAction weiter.
	private void createButtonAction() {
		// TODO Auto-generated method stub
		String calendarName = askCalendarName(); //askCalendarName() -> gibt beim Nutzer erfragten Namen des Kalenders zurück
		if(calendarName == null)
			return; //wenn kein name eingegeben wurde, wird der Vorgang abgebrochen
		JOptionPane.showMessageDialog(frame, String.format("Erstellen: %s", controller.createCalendar(calendarName)), "Kalender erstellen", JOptionPane.INFORMATION_MESSAGE, null); //Zeigt einen kleine Bestätigung an
		controller.saveCalendars(); //Kalender speichern
		editAction(calendarName); //editAction() -> öffnet Bearbeitungsfenster
	}
	
	//////////////////////////////////////////////
	private void showEventsButtonAction() {
			@SuppressWarnings("unused")
			CompareCalendarsUi compareCalendarsUi = new CompareCalendarsUi(controller, false);
	}
	//////////////////////////////////////////////////////////
	
	
	/*editAction erfragt Datum, das bearbeitet werden soll
	 * und öffnet Bearbeitungsfenster für den Kalender im @Parameter
	 */
	private void editAction(String calendarName) {
		int year = yearInput(); //yearInput() erfragt beim Nutzer die Jahreszahl und gibt sie zurück
		if(year < 1) {
			JOptionPane.showMessageDialog(null, "Ungültige Eingabe"); //Fehlermeldung, wenn es unter 1 ist: wenn keine Eingabe getätigt wurde, ist ein Integer 0 und wenn es negativ ist, funktioniert die Speicherung als String nicht mehr zu 100% und -1 signalisiert einen Fehler
			return; //Vorgang beenden
		}
		int month = monthInput(year); //monthInput() erfragt beim Nutzer den Monat und gibt ihn zurück
		if(month < 1) {
			JOptionPane.showMessageDialog(null, "Ungültige Eingabe"); //es gibt keinen Monat unter 1 :P diese Eingabe sollte überhaupt nicht möglich sein, gibt es aber doch einen Weg, wird hier ein Fehler verhindert. -1 entspricht Fehler
			return; //Vorgang beenden
		}
		int day = dayInput(); //dayInput() erfragt beim Nutzer den Tag und gibt ihn zurück
		if(day < 1) {
			JOptionPane.showMessageDialog(null, "Ungültige Eingabe"); //es gibt keinen Tag unter 1 :P diese Eingabe sollte überhaupt nicht möglich sein, gibt es aber doch einen Weg, wird hier ein Fehler verhindert. -1 entspricht Fehler
			return; //Vorgang beenden
		}
		
		@SuppressWarnings("unused")
		EditCalendarUi editCalendarUi = new EditCalendarUi(calendarName, controller, year, month, day); //erstellt Objekt der Klasse EditCalendarUi und gibt Kalendername, controller, Jahreszahl, Monat und Tag weiter
	}
	
	//yearInput() erfragt Jahrezahl beim Nutzer und gibt sie zurück
	private int yearInput() {
		try {
			return Integer.parseInt(JOptionPane.showInputDialog("Welches Jahr?", "Jahr:")); //Wandelt den Output des PopUpWindows zu Integer um
		} catch (Exception e) { //wenn nicht eingetragen wurde oder es sonst einen Fehler gibt
			return -1;
		}
	}
	
	//monthInput() erfragt Monat beim Nutzer, legt fest, wie viele Tage der Monat hat und gibt den Monat zurück
	private int monthInput(int year) {
		int _month = (int)JOptionPane.showInputDialog(null, "Welcher Monat", "Monat", JOptionPane.PLAIN_MESSAGE, null, months, "Monat"); //erfragt Monatszahl. months ist ein Object-Array, der die Zahlen 1-12 enthält. Der Nutzer wählt daraus aus. So wird eine Falsche eingabe nahezu ausgeschlossen
		
		monthDays = controller.monthDays(_month, year);
		
		//Falls eine Ungültige angabe gemacht wird
		if(_month < 1 || _month > 12) { //Sicherung
			return -1;
		}else {
			return _month;
		}
	}

	//dayInput() erfragt den Tag beim Nutzer
	private int dayInput() {
		Object[] days; //Auswahl, aus dem Nutzer auswählen kann
		List<Object> daysList = new ArrayList<Object>(); //vereinfacht die erstellung des auswahl-Arrays
		int day; 
		//Für jeden Tag im Monat die Zahl als String für die Auswahl. So kann man nicht z.B. den 31. Tag im Nocember auswählen
		for (int i = 0; i < monthDays; i++) { 
			daysList.add(String.format("%d", i+1)); //muss wegen Debugzwecken ein String sein.
		}
		
		days = daysList.toArray(); //wandelt daysList in array um und setzte days zu daysList
		
		String dayInputString = (String) JOptionPane.showInputDialog(null, "Welcher den Tag", "Tag", JOptionPane.PLAIN_MESSAGE, null, days, "Tag"); //erfragt Tag mithilfe der Auswahl 
		
		if(dayInputString == "0" || dayInputString == null) //wenn ungültige Ausgaben -> Fehler
			return -1;
		else {
			day = Integer.parseInt(dayInputString); //wandelt inputString zu Integer um
			return day;
		}
	}

	//erfragt den Namen des Kalenders
	private String askCalendarName() {
		String calendarName = (String) JOptionPane.showInputDialog(frame,
			    "Wie soll der Kalender heissen?",
			    "Kalendername:",
			    JOptionPane.QUESTION_MESSAGE); //Erfagt Kalendernamen über
		if(controller.isNameTaken(calendarName)) { //wenn der Name bereits vergeben ist
			JOptionPane.showMessageDialog(frame, "Name bereits vergeben", String.format("%s ist breits vergeben, veruche erneut.", calendarName), JOptionPane.ERROR_MESSAGE, null);
			calendarName = askCalendarName(); //Vorgang wiederholen
		}else if(calendarName == null || calendarName == ""){ //wenn es keine eingabe gab
			JOptionPane.showMessageDialog(frame, "Name nicht werwendbar", String.format("Name ist nicht verwendbar, veruche erneut."), JOptionPane.ERROR_MESSAGE, null);
			return null; //Vorgang beenden
			
		}else
			return calendarName;
		return calendarName;
	}
	
	///////////////////////////////////////////////
	private String askName() {
		String calendarName = (String) JOptionPane.showInputDialog(frame,
			    "Welcher Kalender?",
			    "Kalendername:",
			    JOptionPane.QUESTION_MESSAGE); //Erfagt Kalendernamen über
		if(controller.isNameTaken(calendarName)) { //wenn der Name bereits vergeben ist
			JOptionPane.showMessageDialog(frame, "Name Vorhanden : " + calendarName, String.format(" Erfolgreich "), JOptionPane.PLAIN_MESSAGE, null);
			return calendarName;
		}else if(calendarName == null || calendarName == ""){ //wenn es keine eingabe gab
			JOptionPane.showMessageDialog(frame, "Name nicht werwendbar", String.format("Name ist nicht verwendbar, veruche erneut."), JOptionPane.ERROR_MESSAGE, null);
			calendarName =askName();
			
		}else {
			JOptionPane.showMessageDialog(frame, "Name nicht Vorhanden", String.format("%s Fehler ", calendarName), JOptionPane.ERROR_MESSAGE, null);
			calendarName =askName();
		}
		return calendarName;
	}
	
	///////////////////////////////////////////////////
}
	
	