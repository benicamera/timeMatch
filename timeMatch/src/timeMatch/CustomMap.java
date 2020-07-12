package timeMatch;

public class CustomMap {
	/* Speichert einen String und einen Integer
	 * @author Benjamin Dangl
	 * @version 30.06.2020
	 */
	private String string;
	private int integer;
	
	public CustomMap(String string, int integer) {
		this.string = string;
		this.integer = integer;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}
}
