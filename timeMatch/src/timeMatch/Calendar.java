package timeMatch;

import java.io.Serializable;
import java.util.HashMap;

/*
 * Speichert daten der Zeitintervalle
 * 
 * @author Bernhard Gschwind
 * @version 08.07.2020
 * - Klasse erstellt (20.06.2020).
 * 
 */


public class Calendar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	public final static int NUMBER_OF_INTERVALLS = 24; //legt fest, wie viele Intervalle ein Tag hat. Jeder Intervall ist eine Stunde lang
	
	HashMap <String,boolean[]> calendar = new HashMap<String, boolean[]>(); //speichert Tage
	
	public Calendar(String name) {
			this.name = name; //this.name ist private String name
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String _name) {
		name = _name;
	}
	
	public void summonDay(String _day) {
		/*
		 * Der String setzt sich nach dem Prinzip ddmmyyyy zusammen.
		 * Erstellt tag
		 */
		boolean[] intervalls = new boolean[NUMBER_OF_INTERVALLS];
		for (int i = 0; i < NUMBER_OF_INTERVALLS; i++) { //setzt ganzen Tag auf frei, da die intervalle sonst belegt wären
			intervalls[i] = true; 
		}
		calendar.put(_day, intervalls);	//speichert den Tag
	}
	
	//setzt alle Intervalle auf frei = löscht den Tag. Um den Speicher nicht zu belasten, speichern wir Tage nur, wenn sie besetzt sind
	public String resetDay(String day) {
		calendar.remove(day);
		return "Erfolgreich";
	}
	
	//gibt zurück, ob Intervall frei ist
	public boolean isFree(int _time, String _day) {
		int time = _time - 1;
		//Tag nicht da bedeutet alles frei
		if(!calendar.containsKey(_day)){
			return true;
		}
		boolean[] _intervalls = calendar.get(_day); //der Array speichert, ob ein intervall frei ist
		return _intervalls[time];
		
	}
	
	//ob der Tag geladen ist = ein intervall ist besetzt
	public boolean isLoaded(String _day) {
		return calendar.containsKey(_day);
	}
	
	//setzt Intervall auf Frei oder Belegt
	public void setFree(int _time, boolean _free, String _day) {
		int time = _time - 1;
		boolean[] intervalls = new boolean[NUMBER_OF_INTERVALLS];
		if(calendar.containsKey(_day)) { //wenn der Tag geladen ist
			intervalls = calendar.get(_day);
			intervalls[time] = _free;
			
			//wenn alle Intervalle frei sind, soll der Tag gelöscht werden, weil gelöscht frei entspricht. Folgene if funktioniert hier, weil wir davor den intervall gesetzt haben
			if(allElementsTheSameBool(intervalls) && _free)
				calendar.remove(_day);
			else
			calendar.put(_day, intervalls);	
		}else {
			if(!_free) {
				summonDay(_day); //wenn der Tag nicht geladen ist und belegt werden soll, dann erstelle den Tag
				intervalls[time] = _free;
				calendar.put(_day, intervalls);	
			}
		}	
	}
	
	//gibt zurück, ob alle elemente eines Boolean arrays gleich sind
	public boolean allElementsTheSameBool(boolean[] intervalls) {
	    if (intervalls.length == 0) {
	        return true;
	    } else {
	        boolean first = intervalls[0];
	        for (int i = 0; i < intervalls.length; i++) {
	            if (intervalls[i] != first) { //wenn ein element nicht dem ersten entspricht, false
	                return false;
	            }
	        }
	        return true;
	    }
	}
	
	public int getNumberOfIntervalls() {
		return NUMBER_OF_INTERVALLS;
	}
	
	//gibt zurück, ob ein jahr ein Schaltjahr ist
	public boolean isLeapYear(int _year) {
		/*
		 *     Schaltjahre müssen durch 4 teilbar sein.
    	 * Ist das Jahr auch durch 100 teilbar, ist es kein Schaltjahr, es sei denn...
         * ...das Jahr ist ebenfalls durch 400 teilbar – dann ist es ein Schaltjahr.
		 */
		if(_year%4 == 0) {
			if(_year%100 == 0) {
				if(_year%400 == 0)
					return true;
				return false;
			}
			return true;
		}
		return false;
	}
	public HashMap <String,boolean[]> getCalendarHashMap(){
		return calendar;
		
	}
}
