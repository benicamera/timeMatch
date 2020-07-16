package timeMatch;

import ui.Gui;

/*
 * Main-Class
 * 
 * @author Benjamin Dangl
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */
public class Main {

	@SuppressWarnings("unused") //weil window nicht weiter benutzt wird.
	public static void main(String[] args) {
		try {                           //falls eventuell doch irgendwelche Fehler auftreten sollten
			Gui window = new Gui();
		} catch (Exception e) {
			Gui window = new Gui(e);
		}			
	}
}
