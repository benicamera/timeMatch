package timeMatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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
import java.util.List;

public class Controller {
    final Calendar testCalendar;
    final static String CALENDARPATH_STRING = "C:\\Windows\\TimeMatch\\.saved\\calendars.dat";
    HashMap <String,Calendar> calendarRegister = new HashMap<String, Calendar>(); //erzeugt eine Haschmap f�r die Calendar
    
    public Controller() {
        
        testCalendar = new Calendar("testCalendar");
        loadCalendars();
    }
    
    public void toTest(/* insert Parameters*/) {
        testCalendar.toTest(/*insert Parameters*/);
    }
    
    public void saveCalendars() {
    	
    	try {FileOutputStream fos = new FileOutputStream(CALENDARPATH_STRING);
    		     ObjectOutputStream oos = new ObjectOutputStream(fos);
    			for(Calendar elementCalendar : calendarRegister.values()) {
    				oos.writeObject(elementCalendar);
    			}
    			 oos.close();
    			 fos.close();
    	} catch (FileNotFoundException e) {
			System.out.println("File not found");
			try {FileOutputStream fos = new FileOutputStream(new File(CALENDARPATH_STRING));
	    		     ObjectOutputStream oos = new ObjectOutputStream(fos);
	    			for(Calendar elementCalendar : calendarRegister.values()) {
	    				oos.writeObject(elementCalendar);
	    			}
	    			 oos.close();
	    			 fos.close();
			} catch (IOException ex) {
				System.out.println("Creating: Error initializing stream");
	    		}
		} catch (IOException e) {
			System.out.println("Save: Error initializing stream");
    		}

    }
    
    public void loadCalendars() {
    	boolean cont = true;
    	
    	while (cont) {
    		try (FileInputStream fos = new FileInputStream(CALENDARPATH_STRING);
    	   		     ObjectInputStream oos = new ObjectInputStream(fos)){
    			Calendar obj = (Calendar) oos.readObject();
    		    if (obj != null) {
    		      calendarRegister.put(obj.getName(), obj);
    		    } else {
    		      cont = false;
    		    }
    	   			 oos.close();
    	   			 fos.close();
    	   	} catch (FileNotFoundException e) {
    				System.out.println("Load: File not found");
    				saveCalendars();
    			} catch (IOException e) {
    				System.out.println("Load: Error initializing stream");
    	   		}
    	   catch (Exception e) {
    	    // System.out.println(e.printStackTrace());
    	  }
    	}

   }
    
    public String getDayString(int _year, int _month, int _day) {
    	StringBuilder sb = new StringBuilder();  
    	for(int i = 2; i > String.format("%d", _day).length(); i--) {
    		sb.append("0");
    	}
    	sb.append(String.format("%d", _day));
    	
    	for(int i = 2; i > String.format("%d", _month).length(); i--) {
    		sb.append("0");
    	}
    	sb.append(String.format("%d", _month));
    	
    	sb.append(String.format("%d", _year));
    	
    	return sb.toString();
    }
    
    public boolean isLeapYear(int _year) {
    	return testCalendar.isLeapYear(_year);
    }
    
    public String delete(String calendarNameString) {
    	if(calendarRegister.containsKey(calendarNameString)) {
    		calendarRegister.remove(calendarNameString);
    		saveCalendars();
    	}else {
			return "Kalender nicht gefunden";
		}
    	return "Erfolgreich";
    }
    
    public  ArrayList<String> getCalendarNameList(){
    	List<String> _names = new ArrayList<String>();
    	_names.addAll(calendarRegister.keySet());
    	return (ArrayList<String>) _names;
    }
    
    public boolean isNameTaken(String _calendarName) {
    	return calendarRegister.containsKey(_calendarName);
    }
    
    public String createCalendar (String name) {		//mit der methode ist es m�glich einen neuen Calendar in das Buch aufzunehmen
    	if (calendarRegister.containsKey(name) == false) {		//pr�ft ob der name schon vorhanden ist //Beni: Bei if == !
    		calendarRegister.put(name,new Calendar(name));
    		saveCalendars();
    		return "Erfolgreich";
    	}
        
        return "Fehler";
    }
    
    public int[] match (String name1 , String name2) { //du hast [] vergessen
    	Calendar a = calendarRegister.get(name1);
    	Calendar b = calendarRegister.get(name2);
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
       return calendarRegister.get(name);
    }
    
   
    
}
 
