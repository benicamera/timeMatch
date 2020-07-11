package timeMatch;

import java.io.Serializable;
import java.util.HashMap;

/*
 * Speichert daten der Zeitintervalle
 * 
 * @author Bernhard Gschwind
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020).
 * 
 */

//@SuppressWarnings("serial")
public class Calendar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	int numberOfIntervalls = 24;
	
	HashMap <String,boolean[]> calendar = new HashMap<String, boolean[]>();
	
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
		boolean[] intervalls = new boolean[numberOfIntervalls];
		for (int i = 0; i < numberOfIntervalls; i++) {
			intervalls[i] = true; 
		}
		calendar.put(_day, intervalls);	
	}
	
	public String resetDay(String day) {
		calendar.remove(day);
		return "Erfolgreich";
	}
	
	public boolean isFree(int _time, String _day) {
		int time = _time - 1;
	System.out.println("is Free called");
		//Tag nicht da bedeutet alles frei
		if(!calendar.containsKey(_day)){
			System.out.println("Tag nicht geladen");
			return true;
		}
		boolean[] _intervalls = calendar.get(_day);
		System.out.println(_intervalls[time]);
		return _intervalls[time];
		
	}
	public boolean isLoaded(String _day) {
		return calendar.containsKey(_day);
	}
	
	public void setFree(int _time, boolean _free, String _day) {
		int time = _time - 1;
		boolean[] intervalls = new boolean[numberOfIntervalls];
		if(calendar.containsKey(_day)) {
			intervalls = calendar.get(_day);
			intervalls[time] = _free;
			//wenn alle Intervalle frei sind, soll der Tag gelöscht werden, weil gelöscht frei entspricht
			if(allElementsTheSameBool(intervalls) && _free)
				calendar.remove(_day);
			else
			calendar.put(_day, intervalls);	
		}else {
			if(!_free) {
				summonDay(_day);
				intervalls[time] = _free;
				calendar.put(_day, intervalls);	
			}
		}	
	}
	
	public boolean allElementsTheSameBool(boolean[] intervalls) {
	    if (intervalls.length == 0) {
	        return true;
	    } else {
	        boolean first = intervalls[0];
	        for (int i = 0; i < intervalls.length; i++) {
	            if (intervalls[i] != first) {
	                return false;
	            }
	        }
	        return true;
	    }
	}
	
	public int getNumberOfIntervalls() {
		return numberOfIntervalls;
	}
	
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
	
	public void toTest(/* insert Parameters*/) {

	}
	
}
