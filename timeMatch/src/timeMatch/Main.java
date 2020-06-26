package timeMatch;

import java.awt.EventQueue;

/*
 * Main-Class
 * 
 * @author Benjamin Dangl
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */
public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}