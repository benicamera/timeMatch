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
	
	HashMap <String,boolean[]> calendar = new HashMap<String, boolean[]>();
	
	public Calendar() {
			
	}
	
	public void summonDay(String _day) {
		/*
		 * Der String setzt sich nach dem Prinzip ddmmyyyy zusammen.
		 */
		boolean[] intervalls = new boolean[numberOfIntervalls];
		System.out.print("Summoned " + _day + " ");
		for (int i = 0; i < numberOfIntervalls; i++) {
			intervalls[i] = true;
			System.out.print(intervalls[i] + ", ");
		}
		System.out.println(".");
		calendar.put(_day, intervalls);	
		
	}
	
	public boolean isFree(int _time, String _day) {
		int time = _time - 1;
		if(!calendar.containsKey(_day)) {
			System.out.println("free");
			return true;
		}
		boolean[] _intervalls = calendar.get(_day);
		_intervalls = calendar.get(_day);
		for (int i = 0; i <calendar.get(_day).length; i++) {
			System.out.print(calendar.get(_day)[i] + ", ");
		}
		for (int i = 0; i < _intervalls.length; i++) {
			System.out.print(_intervalls[i] + ", ");
		}
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
			System.out.println("Contains " + _day);
			intervalls = calendar.get(_day);
			intervalls[time] = _free;
			System.out.println(intervalls[time]);
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
		System.out.println("-------");
		for (int i = 0; i <calendar.get(_day).length; i++) {
			System.out.print(calendar.get(_day)[i] + ", ");
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
