package timeMatch;

/*
 * Speichert daten der Zeitintervalle
 * 
 * @author Bernhard Gschwind
 * Last Change: 20.06.2020
 * - Klasse erstellt (20.06.2020)
 * 
 */

public class Calendar {
	private int x1 = 1;
	private int x2 = 1;
	private int x3 = 0;
	private int x4 = 0;
	private int x5 = 1;
	private int x6 = 0;
	private int x7 = 1;
	private int x8 = 1;
	private int x9 = 0;
	private int x10 = 1;
	private int x11 = 1;
	private int x12 = 1;

	int [] time = {x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12};
	
	public Calendar() {
		

		
	}
	public int getTime(int i) {
		return time [i];
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
