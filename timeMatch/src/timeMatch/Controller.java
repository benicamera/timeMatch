package timeMatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Controller {
    final Calendar testCalendar;
    final static String CALENDAR_PATH_STRING = "C:\\Calendars\\calendars.dat";
    final Path folderPath = Paths.get("C:\\Calendars");

    
    HashMap <String,Calendar> calendarRegister = new HashMap<String, Calendar>(); //erzeugt eine Haschmap f�r die Calendar
    
    public Controller() {
        testCalendar = new Calendar("testCalendar");
        loadCalendars();
    }
    
    public void toTest(/* insert Parameters*/) {
        testCalendar.toTest(/*insert Parameters*/);
    }
    
    public void saveCalendars() {
    	int numberOfObjects = getCalendarNameList().size();
    	if (!Files.exists(folderPath)) {
    		new File("C:\\\\Calendars").mkdirs(); //erstellt den Ordner
        }
    	 try (FileOutputStream fos = new FileOutputStream(new File(CALENDAR_PATH_STRING)); 
    	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
    		 oos.write(numberOfObjects); //schreibt int numberOfObject in Datei.
    		 System.out.println(numberOfObjects);
    	        for (Calendar elementCalendar : calendarRegister.values()) {
    	            oos.writeObject(elementCalendar); //Schreibt Objekt von Calendar in Datei
    	        }
    	      } catch (IOException e) {
    	        System.out.println("Creating: Error initializing stream"); //Falls es einen Error gibt.
    	      }
    	
    }
    
    public void loadCalendars() {

    		try (FileInputStream fos = new FileInputStream(CALENDAR_PATH_STRING);
    	   		     ObjectInputStream oos = new ObjectInputStream(fos)){
    			int numberOfObjects = oos.read(); //schreibt gespeicherte numberOfObjects in int
    			for(int i = 0; i < numberOfObjects; i++) {
    			Calendar obj = (Calendar) oos.readObject();  
    		      calendarRegister.put(obj.getName(), obj);
    			}
    			
    	   	} catch (FileNotFoundException e) {
    				System.out.println("Load: File not found");
    				System.out.println(e.getMessage()); //Druckt ganauen Fehler
    				saveCalendars();
    				return;
    			} catch (IOException ex) {
    				System.out.println("Load: Error initializing stream");
    				System.out.println(ex);
    				System.out.println(ex.getMessage());
    				
    	   		}
    	   catch (Exception e) {
    	    e.printStackTrace(); //druckt genaue Fehlerwuelle
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
    /*
    public int[] match (Calendar[] calendars, String[] intervall ) { //du hast [] vergessen
    	int[] agreement;
    	List<Integer[]> agreementsList = new ArrayList<Integer[]>();
    	
    	String startString = intervall[0];
    	String endString = intervall[1];
    	int currentIntervall = 0;
    	String currentString = startString;
    	Boolean previousBoolean = false;
    	Boolean allFreeBoolean = false;
    	while(currentString != endString) {
    		boolean previosCalendarBoolean = calendars[0].isFree(currentIntervall, currentString);
    		for(Calendar calendar : calendars) {
    			if(calendar.isFree(currentIntervall, currentString))
    				allFreeBoolean = true;
    			else {
					allFreeBoolean = false;
					break;
				}
    		}
    		
    		if(allFreeBoolean) {
    			
    		}
    	}
    	return agreement;
    }
    */
    public Calendar getCalendar(String name) {
       return calendarRegister.get(name);
    }
    
   
    
}
 
