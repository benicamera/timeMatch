package timeMatch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
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
        System.out.println(calendarRegister.containsKey("test1"));
        System.out.println(calendarRegister.containsKey("test2"));
        if(!calendarRegister.containsKey("test1")) {
        	calendarRegister.put("test1",new Calendar("test1"));
        	System.out.println("test1");
        }
        toTest();
    }
   
    public void toTest(/* insert Parameters*/) {
        
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
    	for(int i = 4; i > String.format("%d", _year).length(); i--) {
    		sb.append("0");
    	}
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
    
    public String getCalendarName(Calendar calendar) {
    	Calendar[] calendars = (Calendar[]) getCalendarList().toArray();
    	return calendars[getElementIndexOfArray(calendars, calendar)].getName();
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
    	for (int i = 0; i < array.length; i++)
    		if (target.equals(array[i]))
    			return i;
    	return -1;
    }
    
    public ArrayList<CustomMap[]> match (Calendar[] _calendars, String[] _intervall ) { //du hast [] vergessen
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
    	return  agreementSummary(listRaw);
    }
   
    private ArrayList<CustomMap[]> agreementSummary(List<CustomMap> listRaw){
    	String preDayString = listRaw.get(0).getString();
    	ArrayList<CustomMap[]> list = new ArrayList<CustomMap[]>();
    	//System.out.println(intervall[0].getInteger() + "+0");
    	int prevIntervallEnding = listRaw.get(0).getInteger();
    	
    	//////////////////////////////////////////// Wo wird list.get(0)[0].getInteger() 3 ? ///////////////////////////
    	for (int i = 0; i < listRaw.size(); i++) {
    		
        	CustomMap[] intervall = new CustomMap[2];
        	intervall[0] = new CustomMap(listRaw.get(0).getString(), listRaw.get(0).getInteger());
        	
			if(listRaw.get(i).getString().equals(preDayString)) {
				System.out.println("Equals preday string");
				if(listRaw.get(i).getInteger() == prevIntervallEnding + 1) {
					System.out.println("is next hour");
					intervall[1] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					System.out.println(list.get(0)[0].getInteger() + "+1+" + i);
				
				}else { /////////////////////////////////////////////////////////////////////////////////
					list.add(intervall);
					System.out.println(list.get(0)[0].getInteger() + "+1,25+" + i); //hier ist es 1 beim 2. durchgang
					intervall[0] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					prevIntervallEnding = listRaw.get(i).getInteger();
					System.out.println(list.get(0)[0].getInteger() + "+1,5+" + i); ///hier ist es 3 beim 2 durchgang
				}////////////////////////////////////////////////////////////////////////
				///nach dem 2. Durchgang ist hier 3
				System.out.println(list.get(0)[0].getInteger() + "+2+" + i);
				
			}else if(listRaw.get(i).getString().equals(dayStringAdd(preDayString)) && prevIntervallEnding > testCalendar.getNumberOfIntervalls()){
				
				intervall[1] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
				prevIntervallEnding = listRaw.get(i).getInteger();
				preDayString = listRaw.get(i).getString();
				System.out.println(list.get(0)[0].getInteger() + "+3+" + i);
					
				}else {
					list.add(intervall);
					intervall[0] = new CustomMap(listRaw.get(i).getString(), listRaw.get(i).getInteger());
					prevIntervallEnding = listRaw.get(i).getInteger();
					System.out.println(list.get(0)[0].getInteger() + "+4+" + i);
				}
			}
    	System.out.println(list.size());
    	System.out.println(list.get(0)[0].getInteger() + "+5");
    	/////////////////////////////////////////////Hier ist es 3/////////////////////////
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
 
