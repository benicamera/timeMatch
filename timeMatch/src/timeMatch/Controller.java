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


/*
 * Verrechnet und vergleicht
 * 
 * @author Felix Becht, Hussein Fazeli
 *@version 26.06.2020
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020).
 * 
 */

import java.util.HashMap;
import java.util.List;

public class Controller {
	final Calendar testCalendar = new Calendar("testCalendar"); //damit man wichtige Calendarmethoden immer aubrufen kann.
    final static String CALENDAR_PATH_STRING = "C:\\Calendars\\calendars.dat"; //wo unsere Datei gespeichert ist
    final static Path FOLDER_PATH = Paths.get("C:\\Calendars"); //der weg zu unserem Ordner

    final static int FILE_KEY = 231; //identifikationscode von unseren Dateien
    		
    
    HashMap <String,Calendar> calendarRegister = new HashMap<String, Calendar>(); //erzeugt eine Haschmap f�r die Calendar -> kalenderspeicher
    
    public Controller() {
        loadCalendars(); //lädt die calendars.dat-Datei
    }
   
    //testet ob eine Datei von uns ist
    public boolean isCalendarFile(File file) {
    	try (FileInputStream fos = new FileInputStream(file); //braucht man, um dateien auslesen zu können
	   		     ObjectInputStream oos = new ObjectInputStream(fos)){
    		int key = oos.read(); //liest den FileKey aus
    		return (key == FILE_KEY);
    	}catch (Exception e) { //wenn was schief läuft
	    return false;
	  }
	}
    
    //gibt die Location zum Ordner zurück
    public String getSaveLocationFolder() {
    	return "C:\\Calendars";
    }
    
    //importiert Objekte der Klasse Calendar von einer Datei
    public String importCalendars(File file) {
    	try (FileInputStream fos = new FileInputStream(file);
	   		     ObjectInputStream oos = new ObjectInputStream(fos)){
			int key = oos.read();
			if(key != FILE_KEY) //wenn es keine Datei von uns ist
				return "Datei nicht identifizierbar, möglicherweise kaputt.";
			
			int numberOfObjects = oos.read(); //schreibt gespeicherte numberOfObjects in int. Wie viele Kalender sind in der Datei gespeichert
			for(int i = 0; i < numberOfObjects; i++) {
				Calendar obj = (Calendar) oos.readObject(); //holt Calendar-Objekt aus der Datei
				String nameString = obj.getName(); //holt sich den namen der Kalenders
				//ändert Namen, damit man weiss, dass er importiert wurde
				obj.setName(String.format("%s_%s", nameString, "Importiert_"));
				//Falls es den Namen schon gibt
				while(calendarRegister.containsKey(obj.getName())) {
					String _nameString = obj.getName();
					obj.setName(String.format("%s%d", _nameString, 1));
				}
		      calendarRegister.put(obj.getName(), obj); //speichere den Kalender in unserer Hashmap
			}
			saveCalendars(); //speichere die HashMap als Datei
			return "Importieren erfolgreich";
	   	} catch (FileNotFoundException e) { //wenn ein Fehler aufgetreten ist
				System.out.println("Load: File not found");
				System.out.println(e.getMessage()); //Druckt ganauen Fehler
				saveCalendars();
				return "Datei nicht gefunden..";
			} catch (IOException ex) {
				System.out.println("Load: Error initializing stream");
				System.out.println(ex); //gibt art des fehler aus
				System.out.println(ex.getMessage()); //gibt bedeutung des Fehlers aus
				return String.format("%s: %s: %s", "Load: Error initializing stream", ex, ex.getMessage());
	   		}
	   catch (Exception e) {
	    e.printStackTrace(); //druckt genaue Fehlerwuelle
	    return "Fehler aufgetreten.";
	  }
	}
    	
    //speichert jeden Kalender der HashMap auf einer Datei
    public void saveCalendars() {
    	int numberOfObjects = getCalendarNameList().size(); //wie viele Kalender werden gespeichert
    	if (!Files.exists(FOLDER_PATH)) { //wenn unser ordner nich nicht existiert
    		new File("C:\\\\Calendars").mkdirs(); //erstellt den Ordner
        }
    	 try (FileOutputStream fos = new FileOutputStream(new File(CALENDAR_PATH_STRING)); //braucht man, um dateien zu beschreiben
    	         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
    		 oos.write(FILE_KEY); //damit wir unsere datei identifizieren können
    		 oos.write(numberOfObjects); //schreibt int numberOfObject in Datei.
    	        for (Calendar elementCalendar : calendarRegister.values()) {
    	            oos.writeObject(elementCalendar); //Schreibt Objekt von Calendar in Datei
    	        }
    	      } catch (IOException e) {
    	        System.out.println("Creating: Error initializing stream"); //Falls es einen Error gibt.
    	      }
    	
    }
    
    //lädt Kalender in die calendarRegister HashMap
    public void loadCalendars() {

    		try (FileInputStream fos = new FileInputStream(CALENDAR_PATH_STRING);
    	   		     ObjectInputStream oos = new ObjectInputStream(fos)){
    			int key = oos.read();
    			if(key != FILE_KEY)  //wenn es keine Datei von uns ist
    				return;
    			
    			int numberOfObjects = oos.read(); //schreibt gespeicherte numberOfObjects in int
    			for(int i = 0; i < numberOfObjects; i++) { //holt sich alle Kalender und speichert sie in HashMap
    			Calendar obj = (Calendar) oos.readObject();  
    		      calendarRegister.put(obj.getName(), obj);
    			}
    			
    	   	} catch (FileNotFoundException e) {
    				System.out.println("Load: File not found");
    				System.out.println(e.getMessage()); //Druckt genauen Fehler
    				saveCalendars();
    				return;
    			} catch (IOException ex) {
    				System.out.println("Load: Error initializing stream. Load");
    				System.out.println(ex);
    				System.out.println(ex.getMessage());
    				
    	   		}
    	   catch (Exception e) {
    	    e.printStackTrace(); //druckt genaue Fehlerwuelle
    	  }
    	}
    
    //wandelt Datum aus Integern in Datum als String um: ddmmyyyy
    public String getDayString(int _year, int _month, int _day) {
    	StringBuilder sb = new StringBuilder();  
    	for(int i = 2; i > String.format("%d", _day).length(); i--) { //damit dd eingehalten wird
    		sb.append("0"); //fügt zum String hinzu
    	}
    	sb.append(String.format("%d", _day));
    	
    	for(int i = 2; i > String.format("%d", _month).length(); i--) { //damit mm eingehlaten wird
    		sb.append("0");
    	}
    	sb.append(String.format("%d", _month));
    	for(int i = 4; i > String.format("%d", _year).length(); i--) { //damit yyyy eingehalten wird
    		sb.append("0");
    	}
    	sb.append(String.format("%d", _year));
    	
    	return sb.toString();
    }
    
    //gibt zurück, ob es ein Schaltjahr ist
    public boolean isLeapYear(int _year) {
    	return testCalendar.isLeapYear(_year);
    }
    
    //Löscht Kalender
    public String delete(String calendarNameString) {
    	if(calendarRegister.containsKey(calendarNameString)) { //falls es einen solchen Kalender im Register gibt
    		calendarRegister.remove(calendarNameString); //raus aus dem Register -> jetzt nicht mehr auffindbar, aber noch existent. wird irgendwann gelöscht
    		saveCalendars(); //neue Konfiguration speichern
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
    
    public String getCalendarName(Calendar calendar) {
    	Calendar[] calendars = (Calendar[]) getCalendarList().toArray();
    	return calendars[getElementIndexOfArray(calendars, calendar)].getName();
    }
    
    public boolean isNameTaken(String _calendarName) {
    	return calendarRegister.containsKey(_calendarName);
    }
    
    public boolean isElementOfArray(Object element, Object[] array) {
    	for(Object object : array) {
    		if(element.equals(object))
    			return true;
    	}
    	
    	return false;
    }
    
    public String createCalendar (String name) {		//mit der methode ist es m�glich einen neuen Calendar in das Buch aufzunehmen
    	if (calendarRegister.containsKey(name) == false) {		//pr�ft ob der name schon vorhanden ist //Beni: Bei if == !
    		calendarRegister.put(name,new Calendar(name));
    		saveCalendars();
    		return "Erfolgreich";
    	}
        
        return "Fehler";
    }
    
    public ArrayList<Calendar> getCalendarList(){
    	ArrayList<Calendar> list = new ArrayList<Calendar>();
    	
    	for (int i = 0; i < getCalendarNameList().size(); i++) {
			list.add(calendarRegister.get(getCalendarNameList().get(i)));
		}
    	
    	return list;
    }
    
    public Object[] getObjectArrayIntersection(Object[] array1, Object[] array2) {
    	ArrayList<Object> resultObjects = new ArrayList<Object>();
    	  for(int i = 0; i<array1.length; i++ ) {
    	         for(int j = 0; j<array2.length; j++) {
    	            if(array1[i]==array2[j]) {
    	               resultObjects.add(array2[j]);
    	            }
    	         }
    	      }
    	  
    	  return resultObjects.toArray();
    }
    
    public <T> int getElementIndexOfArray(T[] array, T target) {
    	System.out.println("array length: " + array.length);
    	for (int i = 0; i < array.length; i++)
    		if (target.equals(array[i])) {
    			System.out.println("ElementIndex: " + i);
    			return i;
    		}
    	return -1;
    }
   
    
    public ArrayList<CustomMap> match (Calendar[] _calendars, String[] _intervall ) { //du hast [] vergessen
    	ArrayList<CustomMap> listRaw = new ArrayList<CustomMap>();
    	
    	String startString = _intervall[0];
    	String endString = _intervall[1];
    	int currentIntervall = 1;
    	String currentString = startString;
    	Boolean allFreeBoolean = false;
    	System.out.println(endString);
    	System.out.println(startString);
	    
    	while(!currentString.equals(endString)) { //Solange zwischen den Tagen
    		while(currentIntervall < testCalendar.getNumberOfIntervalls() + 1) { //sloange im Tag 
 	
    		for(int i=0;i<_calendars.length;i++) {
    			
    			if(_calendars[i].isFree(currentIntervall, currentString)) 
    				allFreeBoolean = true;
    			else {
					allFreeBoolean = false;
					break;
				}
    			//System.out.println(allFreeBoolean +" "+ " " + currentIntervall + " " +currentString);
    		}
    		
    		if(allFreeBoolean) { //wenn alle frei
    			listRaw.add(new CustomMap(currentString, currentIntervall));
    			System.out.println(String.format("Added %s, %d to list", currentString, currentIntervall));
    		}
    		currentIntervall++;
   
    	}
    		System.out.println("_"); //Unendlich warum?
    	
    		currentString = dayStringAdd(currentString);
    		currentIntervall = 1;
    	
    	}
    	System.out.println("____________");
    	System.out.println(listRaw.size());
    	//return  agreementSummary(listRaw);
    	return listRaw;
    }
   
    private ArrayList<CustomMap[]> agreementSummary(List<CustomMap> listRaw){
    	String preDayString = listRaw.get(0).getString();
    	ArrayList<CustomMap[]> list = new ArrayList<CustomMap[]>();
    	int prevIntervallEnding = listRaw.get(0).getInteger();
    	
    	for (int i = 0; i < listRaw.size(); i++) {
    		
        	CustomMap[] intervall = new CustomMap[2];
        	intervall[0] = new CustomMap(listRaw.get(0).getString(), listRaw.get(0).getInteger());
        	
			if(listRaw.get(i).getString().equals(preDayString)) {
				System.out.println("PreDayString: " + preDayString);
				System.out.println("PrevIntervallEnding: " + prevIntervallEnding + ". Current Intervall: " + listRaw.get(i).getInteger());
				if(listRaw.get(i).getInteger() == prevIntervallEnding + 1) {
					System.out.println("Is following Integer");
					intervall[1] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());	
				}else if(i > 0){ 
					list.add(intervall);
					System.out.println("Added to list");
					intervall[0] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					prevIntervallEnding = listRaw.get(i).getInteger();
				}
				
				
			}else if(listRaw.get(i).getString().equals(dayStringAdd(preDayString)) && prevIntervallEnding > testCalendar.getNumberOfIntervalls()){ //IntervallEnding ist hier glaube ich bullshit
				intervall[1] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
				preDayString = listRaw.get(i).getString();
					
				}else {
					list.add(intervall);
					intervall[0] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					
				}
			prevIntervallEnding = listRaw.get(i).getInteger();
			if(i >= (listRaw.size() - 1))
				list.add(intervall); System.out.println("Added to list");
			}
 
    	return list;
    }
    
    public  String dayStringAdd(String previousString) {
    	Integer[] dateIntegers = new Integer[3];
    	dateIntegers = reverseDayString(previousString);
    	int day = dateIntegers[0];
    	int month = dateIntegers[1];
    	int year = dateIntegers[2];
    	if(day + 1 > monthDays(month, year)) {
    		if(!(month + 1 > 12)) {
    			day = 1;
    			month++;
    		}else {
    			day = 1;
    			month = 1;
    			year++;
    		}
    	}else{
    		day++;
    	}
    		return getDayString(year, month, day);
    }
    
    public int monthDays(int month, int year) {
    	int monthDays;
    	if(month < 8) {
			if(month%2 == 0) {
				if(month == 2) {
					if(isLeapYear(year))
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
				if(month%2 == 0) {
					monthDays = 31;
				}else {
					monthDays = 30;
				}
		}
    	
    	return monthDays;
    }
    
    public Integer[] reverseDayString(String dayString) {
    	
    	int day;
		int month;
		int year;
		Integer[] splitIntegers = new Integer[3];
		day = Integer.parseInt(dayString.substring(0, 2));
		month = Integer.parseInt(dayString.substring(2,4));
		year = Integer.parseInt(dayString.substring(4,dayString.length()));		
		
		splitIntegers[0] = day;
		splitIntegers[1] = month;
		splitIntegers[2] = year;
		
		return splitIntegers;
    }
    
    public Calendar getCalendar(String name) {
       return calendarRegister.get(name);
    }
    
   
    
}
 
