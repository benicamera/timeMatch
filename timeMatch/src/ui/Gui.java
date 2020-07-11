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

public class Gui {

	private JFrame frame;

	final Controller controller;
	
	CompareCalendarsUi matchWindowCalendarsUi;
	
	FileFilter filter = new FileNameExtensionFilter("Objektdatei", ".dat");
	
	Object[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
	
	int monthDays;
	
	public Gui() {
		controller = new Controller();
		initialize();
		toTest();
	}
	
	public void toTest(/*Parameters*/) {
		//controller.toTest(/*Parameters*/);
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 560, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 66, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JButton importButton = new JButton("");
		importButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/icons8-import-50Verkleinert.png")));
		importButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				 JFileChooser chooser = new JFileChooser(); //erstell Dateienauswahl
				 
				 chooser.addChoosableFileFilter(filter);
				 
				 int value = chooser.showOpenDialog(null);
				 if(value == JFileChooser.APPROVE_OPTION)
			        {
					 if(controller.isCalendarFile(chooser.getSelectedFile())) {
						 JOptionPane.showMessageDialog(frame, controller.importCalendars(chooser.getSelectedFile()));
					}else {
						JOptionPane.showMessageDialog(frame, String.format("%s speichert keine timeMatch-Kalender oder ist kaputt.", chooser.getSelectedFile().getName()));
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
		GridBagConstraints gbc_exportButton = new GridBagConstraints();
		gbc_exportButton.insets = new Insets(0, 0, 5, 5);
		gbc_exportButton.gridx = 9;
		gbc_exportButton.gridy = 0;
		frame.getContentPane().add(exportButton, gbc_exportButton);
		importButton.setToolTipText("Kalender importieren.");
		
		GridBagConstraints gbc_importButton = new GridBagConstraints();
		gbc_importButton.insets = new Insets(0, 0, 5, 5);
		gbc_importButton.gridx = 13;
		gbc_importButton.gridy = 0;
		frame.getContentPane().add(importButton, gbc_importButton);
		
		JLabel titleLabel = new JLabel("timeMatch!");
		titleLabel.setForeground(new Color(255, 105, 180));
		titleLabel.setBackground(new Color(240, 255, 240));
		titleLabel.setFont(new Font("Rubik", Font.BOLD, 59));
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.gridwidth = 18;
		gbc_titleLabel.insets = new Insets(0, 0, 5, 0);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 1;
		frame.getContentPane().add(titleLabel, gbc_titleLabel);
		
		JButton matchButton = new JButton("");
		matchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				matchWindowCalendarsUi = new CompareCalendarsUi(controller);
			}
		});
		matchButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/matchButtonIcon.png")));
		matchButton.setToolTipText("suche Match");
		GridBagConstraints gbc_matchButton = new GridBagConstraints();
		gbc_matchButton.insets = new Insets(0, 0, 5, 5);
		gbc_matchButton.gridx = 6;
		gbc_matchButton.gridy = 2;
		frame.getContentPane().add(matchButton, gbc_matchButton);
		
		JButton createButton = new JButton("");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createButtonAction();
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
				if(controller.getCalendarNameList().size() > 0) {
					@SuppressWarnings("unused")
					ShowCalendarsUi showuiCalendarsUi = new ShowCalendarsUi(controller);
				}else {
						JOptionPane.showMessageDialog(frame, "Keine Kalender gefunden.");
					}
			System.out.println("show erstellt");
			}
		});
		//showButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/showButtonIcon.png")));
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
	
	public void destroyCompare() {
		matchWindowCalendarsUi = null;
	}

	private void createButtonAction() {
		// TODO Auto-generated method stub
		String calendarName = askCalendarName();
		if(calendarName == null)
			return;
		JOptionPane.showMessageDialog(frame, String.format("Erstellen: %s", controller.createCalendar(calendarName)), "Kalender erstellen", JOptionPane.INFORMATION_MESSAGE, null);
		editAction(calendarName);
	}
	
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
	
	private int yearInput() {
		try {
			return Integer.parseInt(JOptionPane.showInputDialog("Welches Jahr?", "Jahr:"));
		} catch (Exception e) { //wenn nicht eingetragen wurde oder es sonst einen Fehler gibt
			return -1;
		}
	}
	
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

	private String askCalendarName() {
		String calendarName = (String) JOptionPane.showInputDialog(frame,
			    "Wie soll der Kalender heissen?",
			    "Kalendername:",
			    JOptionPane.QUESTION_MESSAGE);
		if(controller.isNameTaken(calendarName)) {
			JOptionPane.showMessageDialog(frame, "Name bereits vergeben", String.format("%s ist breits vergeben, veruche erneut.", calendarName), JOptionPane.ERROR_MESSAGE, null);
			calendarName = askCalendarName();
		}else if(calendarName == null || calendarName == ""){
			JOptionPane.showMessageDialog(frame, "Name nicht werwendbar", String.format("Name ist nicht verwendbar, veruche erneut."), JOptionPane.ERROR_MESSAGE, null);
			return null;
			
		}else
			return calendarName;
		return calendarName;
	}
}
	
	