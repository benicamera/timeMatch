package timeMatch;

/*
 * Verrechnet und vergleicht
 * 
 * @author Felix Becht, Hussein Fazeli
 *@version 26.06.2020
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */

import java.util.HashMap;

public class Controller {
    final Calendar testCalendar;
    HashMap <String,Calendar> Buch = new HashMap<String,Calendar>(); //erzeugt eine Haschmap f�r die Calendar
    
    public Controller() {
        
        testCalendar = new Calendar();
    }
    
    public void toTest(/* insert Parameters*/) {
        testCalendar.toTest(/*insert Parameters*/);
    }
    
    
    
    
    public String createCalendar (String name) {		//mit der methode ist es m�glich einen neuen Calendar in das Buch aufzunehmen
    	if (Buch.containsKey(name) == false) {		//pr�ft ob der name schon vorhanden ist //Beni: Bei if == !
    		Buch.put(name,new Calendar());
    		return "newCalendar: " + name;
    	}
        
        return "Fehler";
    }
    
    public void match (Calendar a , Calendar b) {
    }
    
    public Calendar getCalendar(String name) {
       return Buch.get(name);
    }
    
   
    
}
 
