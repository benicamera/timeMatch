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
    
    public int[] match (String name1 , String name2) { //du hast [] vergessen
    	Calendar a = Buch.get(name1);
    	Calendar b = Buch.get(name2);
    	int [] agreement = new int[11];
		for (int p=0; p<11; p++) {     
            agreement[p] = 0;
        }
    	for(int i=0;i<11;i++) {
    		if (a==b /*&& a==true*/) {  //zu testzwecken rauskommentiert
				agreement[i] = 1;
			}
    	}
    	return agreement;
    }
    
    public Calendar getCalendar(String name) {
       return Buch.get(name);
    }
    
   
    
}
 
