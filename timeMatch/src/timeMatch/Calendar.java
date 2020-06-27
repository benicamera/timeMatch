package timeMatch;

import java.util.HashMap;

/*
 * Speichert daten der Zeitintervalle
 * 
 * @author Bernhard Gschwind
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */

public class Calendar {
	
	int numberOfIntervalls = 12;
	
	HashMap <String,Boolean[]> calendar = new HashMap<String, Boolean[]>();
	
	public Calendar() {
			
	}
	
	private void summonDay(String _day) {
		/*
		 * Der String setzt sich nach dem Prinzip ddmmyyyy zusammen.
		 */
		Boolean[] intervalls = new Boolean[numberOfIntervalls];
		for (int i = 0; i < numberOfIntervalls; i++) {
			intervalls[i] = true;
		}
		calendar.put(_day, intervalls);	
	}
	
	public boolean isFree(int _time, String _day) {
		if(!calendar.containsKey(_day)) {
			return true;
		}
		return calendar.get(_day)[_time - 1];
	}
	
	public void setFree(int _time, boolean _free, String _day) {
		Boolean[] intervalls = new Boolean[numberOfIntervalls];
		if(calendar.containsKey(_day)) {
			intervalls = calendar.get(_day);
			intervalls[_time-1] = _free;
			if(allElementsTheSameBool(intervalls) && _free)
				calendar.remove(_day);
			else
			calendar.put(_day, intervalls);	
		}else {
			if(!_free) {
				summonDay(_day);
				intervalls[_time-1] = _free;
				calendar.put(_day, intervalls);	
			}
		}
		
	}
	
	public boolean allElementsTheSameBool(Boolean[] intervalls) {
	    if (intervalls.length == 0) {
	        return true;
	    } else {
	        boolean first = intervalls[0];
	        for (boolean element : intervalls) {
	            if (element != first) {
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
    Ist das Jahr auch durch 100 teilbar, ist es kein Schaltjahr, es sei denn...
    ...das Jahr ist ebenfalls durch 400 teilbar – dann ist es ein Schaltjahr.
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
