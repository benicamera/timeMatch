package ui;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import timeMatch.Controller;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.awt.MouseInfo;
import java.awt.Font;
import java.awt.Color;

public class ShowCalendarsUi extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	final Controller controller;
	JFrame showFrame = new JFrame();
	JList<String> list; //erstellt anzeigbare Liste
	DefaultListModel<String> model = new DefaultListModel<>(); //erstell Listmodel für die Liste
	JPopupMenu menu; //PopUpMenu
	boolean menuVisble = false;
	
	int monthDays;
	Object[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	private final JButton backButton = new JButton("Schliessen"); //Schliessen-Knopf

	public ShowCalendarsUi(Controller _controller) {
		controller = _controller;
		showFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		showFrame.setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		contentPane.addMouseListener(new MouseAdapter() { //Fügt mouseListener hinzu: was passiert, wenn die Maus auf der ebene gedrückt wird.
			@Override
			public void mousePressed(MouseEvent e) {
				if(menu.isVisible())
					menu.setVisible(false);
			}
		});
		
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel calendarListLabel = new JLabel("Liste der Kalender");
		calendarListLabel.setForeground(new Color(255, 105, 180));
		calendarListLabel.setFont(new Font("Roboto", Font.BOLD, 30));
		GridBagConstraints gbc_calendarListLabel = new GridBagConstraints();
		gbc_calendarListLabel.gridheight = 2;
		gbc_calendarListLabel.gridwidth = 15;
		gbc_calendarListLabel.insets = new Insets(0, 0, 5, 5);
		gbc_calendarListLabel.gridx = 1;
		gbc_calendarListLabel.gridy = 2;
		contentPane.add(calendarListLabel, gbc_calendarListLabel);
		
		list = new JList<String>(model);
		list.setVisibleRowCount(8);
		
		initList(list);
		
		MouseListener mouseListener = new MouseAdapter() { //neuer  mouseListener: Was passiert, wenn die Maus 1 oder 2 mal gedrückt wird
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 1 || e.getClickCount() == 2) {
		          String selectedItem = (String) list.getSelectedValue(); //welcher Kalender ist ausgewählt
		          if(menu != null) { //wenn bereits ein PopUpMenu da ist
		        	  if(menu.isVisible()) //wenn es sichtbar ist
		        		  menu.setVisible(false); //mach es unsichtbar: sonst kann man unendlich viele menus öffnen
		          }
		          showPopUpMenu(selectedItem); //Zeigt PopUpMenu für ausgewählten Kalender an.
		         }
		    }
		};
		list.addMouseListener(mouseListener); //fügt den mouseListener der Liste hinzu: er wird nur eingesetzt, wenn man innerhalb der Liste klickt
		contentPane.setVisible(true); //mache Ebene sichtbar
		showFrame.getContentPane().add(contentPane); //füge Ebene zum Fenster hinzu
		
		GridBagConstraints gbc_backButton = new GridBagConstraints();
		gbc_backButton.insets = new Insets(0, 0, 5, 5);
		gbc_backButton.gridx = 16;
		gbc_backButton.gridy = 3;
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(menu != null)
					menu.setVisible(false); //wenn ein PopUpMenu da ist, mache es weg: sonst würde es dort bleiben. Für immer.
				showFrame.setVisible(false); //mache Fenster unsichtbar
			}
		});
		
		GridBagConstraints gbc_list_1 = new GridBagConstraints();
		gbc_list_1.insets = new Insets(0, 0, 5, 5);
		gbc_list_1.ipadx = 100;
		gbc_list_1.ipady = 40;
		gbc_list_1.gridx = 5;
		gbc_list_1.gridy = 5;
		contentPane.add(list, gbc_list_1);
		contentPane.add(backButton, gbc_backButton);
		showFrame.setResizable(true);
		showFrame.setVisible(true);
	}
	
	//initiiert die Liste, bzw. das Model, welches dann der Liste hinzugefügt wird
	private void initList(JList<String> list) {
		if(controller.getCalendarNameList().size() <= 0) { //wenn es keine Kalender gibt
			model.addElement("Keine Kalender gefunden."); //"Keine Kalender gefunden" in Liste packen
			JOptionPane.showMessageDialog(showFrame, "Keine Kalender gefunden."); //als PopUpMeldung nich mal
			showFrame.setVisible(false); 
			showFrame.dispose(); //Fenster schliessen
			return; //vorgang beenden
		}
			
		for(String elementString : controller.getCalendarNameList()) { //Jeden Kalendernamen dem Model hinzufügen, welches dann der Liste hinzugefügt wird -> man fügt Namen der Liste hinzu.
			model.addElement(elementString);	
		}
	}
	
	//zeigt PopUpMenu für ausgewählten Kalender
	private void showPopUpMenu(String selectedItem) {
		final String calendarNameString = selectedItem; //soviel wie möglich final
		
		menu = new JPopupMenu(calendarNameString); //erstelle neues PopUpMenu mit dem Namen des Kalenders als Namen
		JMenuItem deleteItem = new JMenuItem("Vernichten"); //ein Menupunkt ist "Vernichten"
		JMenuItem editItem = new JMenuItem("Bearbeiten"); //der andere "Bearbeiten
		
		deleteItem.addActionListener(new ActionListener() { //was passiert, wenn "Vernichten gewählt wurde	
			@Override //weil wir hier die actionPerformed-methode überschreiben
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(JOptionPane.showConfirmDialog(null, "Achtung", "Wirklich vernichten?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) { //Fragt sicherheitshalber noch mal nach
					JOptionPane.showMessageDialog(null, controller.delete(calendarNameString)); //Gibt ergebnis des löschvorgangs als PopUpWindow zurück
				}
				
				if(!controller.isNameTaken(calendarNameString)) { //wenn's ihn nicht (mehr) gibt (also wenn er gelöscht wurde): Fenster schliessen und Menu schliessen
					menu.setVisible(false);
				showFrame.setVisible(false); //you can't see me!
				showFrame.dispose(); //Destroy the JFrame object
		}
			
			}
		});
	
		editItem.addActionListener(new ActionListener() { //wenn "Bearbeiten" gedrückt wurde	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				editAction(calendarNameString); //öffnet bearbeitungsfenster
				//Fensterschliessen und Menu wegmachen
				menu.setVisible(false);
				showFrame.setVisible(false); 
				showFrame.dispose(); 
			}
		});
		//Menupunkte dem Menu hinzufügen
		menu.add(deleteItem);
		menu.add(editItem);
		
		menu.setLocation(MouseInfo.getPointerInfo().getLocation()); //setzt Menu dahin, wo die Maus ist
		menu.setVisible(true); //macht menu sichtbar
		menuVisble = true;
	}
	
	//Erfagt Datum und öffnet Bearbeitungs fenster, ist in der GUI-klasse bereits beschrieben
	private void editAction(String calendarName) {
		int year = yearInput();
		if(year < 1) {
			JOptionPane.showMessageDialog(null, "Ungültige Eingabe");
			return;
		}
		int month = monthInput(year);
		if(month < 1) {
			JOptionPane.showMessageDialog(null, "Ungültige Eingabe");
			return;
		}
		int day = dayInput();
		if(day < 1) {
			JOptionPane.showMessageDialog(null, "Ungültige Eingabe");
			return;
		}
		
		@SuppressWarnings("unused")
		EditCalendarUi editCalendarUi = new EditCalendarUi(calendarName, controller, year, month, day);
	}
	
	//erfagt Jahreszahl
	private int yearInput() {
		try {
			return Integer.parseInt(JOptionPane.showInputDialog("Welches Jahr?", "Jahr:"));
		} catch (Exception e) { //wenn nicht eingetragen wurde oder es sonst einen Fehler gibt
			return -1;
		}
	}
	
	//erfagt Monat
	private int monthInput(int year) {
		int _month = (int)JOptionPane.showInputDialog(null, "Welcher Monat", "Monat", JOptionPane.PLAIN_MESSAGE, null, months, "Monat");
		
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
			return -1;
		}else {
			
			return _month;
		}
	}

	//erfagt Tag
	private int dayInput() {
		Object[] days;
		List<Object> daysList = new ArrayList<Object>();
		int day;
		//Für jeden Tag im Monat die Zahl als String für die Auswahl
		for (int i = 0; i < monthDays; i++) {
			daysList.add(String.format("%d", i+1));
		}
		
		days = daysList.toArray();
		
		String dayInputString = (String) JOptionPane.showInputDialog(null, "Welcher den Tag", "Tag", JOptionPane.PLAIN_MESSAGE, null, days, "Tag");
		
		if(dayInputString == "0" || dayInputString == null)
			return -1;
		else {
			day = Integer.parseInt(dayInputString);
			controller.saveCalendars();
			return day;
		}
	}
	

}
