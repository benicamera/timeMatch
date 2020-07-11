package ui;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import timeMatch.Controller;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.lang.reflect.UndeclaredThrowableException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Insets;


public class EditCalendarUi extends JFrame implements ActionListener{

	/**
	 * .
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane; //Ebene
	final Controller controller;
	int year;
	int day;
	int month;
	int monthDays;
	String calendarName;
	
	JFrame editFrame = new JFrame(); //neues Fenster
	
	int buttonGridy = 2; //y-Positionsvaribale für die Buttons
	int textGridy = 1; //y-Positionsvaribale für den Text
	int gridx = 1; //x-Position
	
	String buttonText;
	String buttonToolTipString;
	
	int numberOfIntervalls; //Anzahl der Intervalle
	
	JButton[] intervallButtons; //alle Knöpfe für die Intervalle
	JLabel[] intervallLabels = new JLabel[12]; //Alle Beschriftungen für die Intervalle
	
	
	private final JButton okayButton = new JButton("Okay"); //Bestätigungsknopf
	
	/**
	 * Create the frame.
	 */
	public EditCalendarUi(String name, Controller _controller, int _year, int _month, int _day) {
		
		controller = _controller; //gibt controller weiter
		//setzt Datum fest
		this.year = _year; 
		this.month = _month;
		this.day = _day;
		calendarName = name;
		numberOfIntervalls = controller.getCalendar(name).getNumberOfIntervalls(); //holt sich die anzahl der Intervalle von der Kalender-Klasse: Unterste Klasse in der Hierarchie: Schnellere Anpassung 
		intervallButtons = new JButton[numberOfIntervalls];
		intervallLabels = new JLabel[numberOfIntervalls];
		
		editFrame.setBounds(100, 100, 472, 300);
		editFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editFrame.setTitle(String.format("%s - %d.%d.%d",calendarName, day, month, year)); //setzt den Titel des Fensters
		
		contentPane = new JPanel(); //neue Ebene
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JButton trashButton = new JButton(new ImageIcon(Gui.class.getResource("/resources/wastebasket.png")));
		trashButton.setToolTipText("Termine verwerfen.");
		trashButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(JOptionPane.showConfirmDialog(null, "Achtung", "Wirklich verwerfen?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
					JOptionPane.showMessageDialog(editFrame, controller.getCalendar(calendarName).resetDay(controller.getDayString(year, month, day)));
				}
				controller.saveCalendars();
				if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day)));
					editFrame.setVisible(false); //macht fenster nicht sichtbar
					editFrame.dispose(); //Löscht Object
			}
		});
		
		//Legt Position fest
		GridBagConstraints gbc_trashButton = new GridBagConstraints();
		gbc_trashButton.insets = new Insets(0, 0, 5, 0);
		gbc_trashButton.gridx = 13;
		gbc_trashButton.gridy = 0;
		contentPane.add(trashButton, gbc_trashButton);	
		editFrame.getContentPane().add(contentPane);
		
		initIntervallButtons(); //Fügt alle Knöpfe hinzu
		
		GridBagConstraints gbc_okayButton = new GridBagConstraints();
		gbc_okayButton.gridx = 13;
		gbc_okayButton.gridy = 7;
		okayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveCalendars();
				editFrame.setVisible(false); 
				editFrame.dispose(); 
			}
		});
		okayButton.setToolTipText("Bearbeitung beenden");
		contentPane.add(okayButton, gbc_okayButton);
		
		contentPane.setVisible(true);
		editFrame.setVisible(true);
	}
	
	private void initIntervallButtons() {
		for (int i = 1; i <= numberOfIntervalls; i++) { //für jeden Intervall einen Knopf
			
			String toolTextString; //wird nacher gebraucht
			
			if(controller.getCalendar(calendarName).isFree(i, controller.getDayString(year, month, day))) {
				buttonText = "Frei"; //wenn frei -> Knopftext zu "Frei
				toolTextString = "Belegt"; //und toolTextString zum Gegenteil
			}else {
				buttonText = "Belegt";
				toolTextString = "Frei";
			}
			
			//Damit nicht alle in einer Reihe sind
			if(i%5 == 0) {
				gridx = 1;
				buttonGridy += 2; 
				textGridy += 2;
			}else if(i < 5){
				gridx = (i%5);
			}else {
				gridx = (i%5) + 1;
			}
			
			intervallButtons[i-1] = new JButton(buttonText); //erstelle neuen Knopf
			intervallButtons[i-1].setActionCommand("" + i); //Damit der Knopf immer weiß, welchen intervall er repräsentiert
			intervallButtons[i-1].addActionListener((ActionListener) this); //fügt ActionListener hinzu: Führt die actionPerformed()-Methode aus
			intervallLabels[i-1] = new JLabel(intervallLabelBuilder(i)); //Text über Button soll den Text von intervallLabelBuilder tragen
			intervallButtons[i-1].setToolTipText(String.format("als %s markieren", toolTextString)); //"Hovertext" festlegen
			
			//fügt Knopf und Text zu Ebene hinzu
			GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
			gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
			gbc_btnNewButton.gridx = gridx;
			gbc_btnNewButton.gridy = buttonGridy;		
			contentPane.add(intervallButtons[i-1], gbc_btnNewButton); 
			
			GridBagConstraints gbc_labels = new GridBagConstraints();
			gbc_labels.gridx = gridx;
			gbc_labels.gridy = textGridy;
			contentPane.add(intervallLabels[i-1], gbc_labels); 
			
		}
	}
	
	 public void actionPerformed (ActionEvent e){
		 
		 int index = Integer.parseInt( e.getActionCommand()); //holt sich das "i", also den intervall, den der Knopf repräsentiert
		 
		 //einzelige if-Statements kann man auch ohne Klammern schreiben :)
		 if(!controller.getCalendar(calendarName).isLoaded(controller.getDayString(year, month, day))) //wenn der Tag nicht geladen ist, soll er erstellt werden
 				controller.getCalendar(calendarName).summonDay(controller.getDayString(year, month, day)); //controller.getDayString() wandelt datum in String um
		 
		 //wenn er frei war, dann belegt und andersherum
		 if(controller.getCalendar(calendarName).isFree(index, controller.getDayString(year, month, day))) {
			controller.getCalendar(calendarName).setFree(index, false, controller.getDayString(year, month, day));
			buttonText = "Belegt";
			intervallButtons[index - 1].setToolTipText("Als Frei markieren");	
		}else {
			controller.getCalendar(calendarName).setFree(index, true, controller.getDayString(year, month, day));
			buttonText = "Frei";
			intervallButtons[index - 1].setToolTipText("Als Belegt markieren");
		}
		
		//setzt neuen Text
		intervallButtons[index-1].setText(buttonText);
		
		//Kommentar ist für die Dokumentation ist das selbe nur einzeln
		
	/*
	 * System.out.println("-----------");
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
	 */
	        controller.saveCalendars(); //damit änderungen sofort gespeichert werden
	    }
	
	//kreiert die Texte je nach intervall
	private String intervallLabelBuilder(int i) {
		
		StringBuilder sb = new StringBuilder();  //Stringbuilder vereinfacht String name += String nachname 
		
		if(numberOfIntervalls > 24) {
			//String.format setzt für %d zahlen und für %s Strings ein, in der reinfolge wie dahinter aufgeführt.
			sb.append(String.format("%dh-", (i+(12 - (24/2 + 1)))));
			sb.append(String.format("%dh", (i+(12 - (24/2)))));
		}else {
			sb.append(String.format("%dh-", (i+(12 - (numberOfIntervalls/2 + 1))))); //von    bis
			sb.append(String.format("%dh", (i+(12 - (numberOfIntervalls/2)))));
		}
		return sb.toString(); //StringBuilder ist kein Objekt der Klasse String und muss deshalb konvertiert werden.
	}
	
}
