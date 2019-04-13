
public class Student {
	private int number;
	private String name;
	
	public Student(int number, String name) {
		this.number = number;
		this.name = name;
	}
	
	public int getNumber() { return number; }
	public String getName() { return name; }
	
	public void setNumber(int number) { this.number = number; }
	public void setName(String name) { this.name = name; }
	
	public String toString() { return number + ":" + name; }
}
