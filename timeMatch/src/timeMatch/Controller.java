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
    final Calendar testCalendar;
    final static String CALENDAR_PATH_STRING = "C:\\Calendars\\calendars.dat";
    final Path folderPath = Paths.get("C:\\Calendars");

    
    HashMap <String,Calendar> calendarRegister = new HashMap<String, Calendar>(); //erzeugt eine Haschmap f�r die Calendar
    
    public Controller() {
        testCalendar = new Calendar("testCalendar");
        loadCalendars();
        //toTest();
    }
   /* 
    public void toTest(/* insert Parameters*) {
        testCalendar.toTest(/*insert Parameters*);
        System.out.println("********");
        Calendar[] calendars = new Calendar[2];
        calendars[0] = calendarRegister.get("test1");
        calendars[1] = calendarRegister.get("test2");
        String[] intervall = new String[2];
        intervall[0] = "01010001";
        intervall[1] = "02010001";
        List<CustomMap[]> list = new ArrayList<CustomMap[]>();
        System.out.println("********");
        list = match(calendars,intervall );
        System.out.println(list.size() + "*");
        for (int i = 0; i < list.size(); i++) {
        	System.out.println("-------------------");
			System.out.print(list.get(i)[0].getString() + " ; ");
			System.out.print(list.get(i)[0].getInteger() + " - ");
			if(list.get(i)[1] != null) {
				System.out.print(list.get(i)[1].getString() + " ; ");
				System.out.print(list.get(i)[1].getInteger());
			}
		}
        
    }
    */
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
    
    
    public List<CustomMap[]> match (Calendar[] calendars, String[] intervall ) { //du hast [] vergessen
    	List<CustomMap[]> agreementsList = new ArrayList<CustomMap[]>();
    	List<CustomMap> listRaw = new ArrayList<CustomMap>();
    	
    	String startString = intervall[0];
    	String endString = intervall[1];
    	int currentIntervall = 1;
    	String currentString = startString;
    	Boolean allFreeBoolean = false;
    	System.out.println(endString);
    	System.out.println(startString);
	    
    	while(currentString != endString) { //Solange zwischen den Tagen
    		while(currentIntervall <= testCalendar.getNumberOfIntervalls()) { //sloange im Tag
    		
    		for(Calendar calendar : calendars) { //schaut ob alle frei sind
    			
    			if(currentIntervall == 0 && currentString == null)
    				System.out.println("null");
    			else
    				System.out.println("Not null");
    			
    			System.out.println(currentIntervall);
    			//System.out.println(calendar.isFree(currentIntervall, currentString));
    			if(calendar.isFree(currentIntervall, currentString)) // nullpointer exception
    				allFreeBoolean = true;
    			else {
					allFreeBoolean = false;
					break;
				}
    		}
    		
    		if(allFreeBoolean) { //wenn alle frei
    			listRaw.add(new CustomMap(currentString, currentIntervall));
    		currentIntervall++;
		if(currentIntervall <= testCalendar.getNumberOfIntervalls())
			System.out.println("Planned Stop");
    	}
    		System.out.println("_"); //Unendlich warum?
    	}
    		currentString = dayStringAdd(currentString);
    		currentIntervall = 1;
    		System.out.println("*");
    	}
    	System.out.println("____________");
    	System.out.println(listRaw.size());
    	return  agreementSummary(listRaw);
    }
   
    private ArrayList<CustomMap[]> agreementSummary(List<CustomMap> listRaw){
    	String preDayString = listRaw.get(0).getString();
    	ArrayList<CustomMap[]> list = new ArrayList<CustomMap[]>();
    	CustomMap[] intervall = new CustomMap[2];
    	intervall[0] = new CustomMap(listRaw.get(0).getString(), listRaw.get(0).getInteger());
    	int prevIntervallEnding = intervall[0].getInteger();
    	
    	for (int i = 1; i < listRaw.size(); i++) {
			if(listRaw.get(i).getString() == preDayString) {
				if(listRaw.get(i).getInteger() == prevIntervallEnding + 1) {
					intervall[1] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
				
				}else {
					list.add(intervall);
					intervall[0] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					prevIntervallEnding = listRaw.get(i).getInteger();
				}
				
			}else if(listRaw.get(i).getString() == dayStringAdd(preDayString) && prevIntervallEnding > testCalendar.getNumberOfIntervalls()){
				
				intervall[1] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
				prevIntervallEnding = listRaw.get(i).getInteger();
				preDayString = listRaw.get(i).getString();
				
					
				}else {
					list.add(intervall);
					intervall[0] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					prevIntervallEnding = listRaw.get(i).getInteger();
				}
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
 
