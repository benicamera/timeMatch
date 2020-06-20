package timeMatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * Creates GUI
 * 
 * @author Benjamin Dangl
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */

public class Gui {

	final Controller controller;
	
	 private JFrame frame = new JFrame();
	 private JPanel panel = new JPanel();
	 
	  private JButton showButton = new JButton("Kalender anzeigen");
	  private JButton matchButton = new JButton("Termin finden");
	  private JButton createButton = new JButton("Erstelle Kalender");
	
	public Gui() {
		controller = new Controller();
	}
	
	public void toTest(/* insert Parameters*/) {
		controller.toTest(/*insert Parameters*/);
	}
}
