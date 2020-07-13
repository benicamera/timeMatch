package ui;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Insets;
import javax.swing.ImageIcon;
import timeMatch.Controller;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui {

	private JFrame frame;

	final Controller controller;
	
	public Gui() {
		controller = new Controller();
		initialize();
		toTest();
	}
	
	public void toTest(/*Parameters*/) {
		controller.toTest(/*Parameters*/);
	}

	/**
	 * Initialize the contents of the frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{66, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JLabel titleLabel = new JLabel("timeMatch!");
		titleLabel.setForeground(new Color(221, 160, 221));
		titleLabel.setBackground(new Color(240, 255, 240));
		titleLabel.setFont(new Font("Rubik", Font.BOLD, 59));
		GridBagConstraints gbc_titleLabel = new GridBagConstraints();
		gbc_titleLabel.gridwidth = 10;
		gbc_titleLabel.insets = new Insets(0, 0, 5, 0);
		gbc_titleLabel.gridx = 0;
		gbc_titleLabel.gridy = 0;
		frame.getContentPane().add(titleLabel, gbc_titleLabel);
		
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
		gbc_createButton.gridx = 0;
		gbc_createButton.gridy = 1;
		frame.getContentPane().add(createButton, gbc_createButton);
		
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
		gbc_showButton.gridx = 5;
		gbc_showButton.gridy = 1;
		frame.getContentPane().add(showButton, gbc_showButton);
		
		JLabel spaceLabel2 = new JLabel("");
		GridBagConstraints gbc_spaceLabel2 = new GridBagConstraints();
		gbc_spaceLabel2.insets = new Insets(0, 0, 5, 5);
		gbc_spaceLabel2.gridx = 7;
		gbc_spaceLabel2.gridy = 1;
		frame.getContentPane().add(spaceLabel2, gbc_spaceLabel2);
		
		JButton matchButton = new JButton("");
		matchButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/matchButtonIcon.png")));
		matchButton.setToolTipText("suche Match");
		GridBagConstraints gbc_matchButton = new GridBagConstraints();
		gbc_matchButton.insets = new Insets(0, 0, 5, 0);
		gbc_matchButton.gridx = 9;
		gbc_matchButton.gridy = 1;
		frame.getContentPane().add(matchButton, gbc_matchButton);
		
		
		
		//////////////////////////////////////////////
		JButton showEventsButton = new JButton("");
		showEventsButton.setIcon(new ImageIcon(Gui.class.getResource("/resources/matchButtonIcon.png")));
		showEventsButton.setToolTipText("showEvents");
		GridBagConstraints gbc_showEventsButton = new GridBagConstraints();
		gbc_showEventsButton.insets = new Insets(0, 0, 5, 0);
		gbc_showEventsButton.gridx = 10;
		gbc_showEventsButton.gridy = 1;
		frame.getContentPane().add(showEventsButton, gbc_showEventsButton);		
		////////////////////////////////////////
		
		
		JLabel spaceLabel = new JLabel("");
		GridBagConstraints gbc_spaceLabel = new GridBagConstraints();
		gbc_spaceLabel.insets = new Insets(0, 0, 0, 5);
		gbc_spaceLabel.gridx = 0;
		gbc_spaceLabel.gridy = 3;
		frame.getContentPane().add(spaceLabel, gbc_spaceLabel);
		frame.setVisible(true);
	}

	private void createButtonAction() {
		// TODO Auto-generated method stub
		String calendarName = askCalendarName();
		JOptionPane.showMessageDialog(frame, String.format("Erstellen: %s", controller.createCalendar(calendarName)), "Kalender erstellen", JOptionPane.INFORMATION_MESSAGE, null);
		EditCalendarUi editCalendarUi = new EditCalendarUi(calendarName, controller);
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
			calendarName = askCalendarName();
			
		}else
			return calendarName;
		return calendarName;
	}
}