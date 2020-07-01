package timeMatch;

public class CustomMapArray{
	private String string;
	private Integer[] integer;
	
	public CustomMapArray(String string, Integer[] array) {
		integer = new Integer[array.length];
		integer = array;
		this.string = string;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public Integer[] getInteger() {
		return integer;
	}

	public void setInteger(Integer[] integer) {
		this.integer = integer;
	}
	
}
