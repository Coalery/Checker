
public class E_Student {
	private int number;
	private String name;
	
	public E_Student(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public int getNumber() { return number; }
	public String getName() { return name; }
	
	public void setNumber(int number) { this.number = number; }
	public void setName(String name) { this.name = name; }
	
	public String toString() { return number + ":" + name; }
}
