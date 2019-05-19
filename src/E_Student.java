
public class E_Student {
	private int number;
	private String name;
	private String parentName;
	
	public E_Student(int number, String name, String parentName) {
		this.number = number;
		this.name = name;
		this.parentName = parentName;
	}
	
	public int getNumber() { return number; }
	public String getName() { return name; }
	public String getParentName() { return parentName; }
	
	public void setNumber(int number) { this.number = number; }
	public void setName(String name) { this.name = name; }
	public void setParentName(String parentName) { this.parentName = parentName; }
	
	public String toString() { return number + ":" + name + ":" + parentName; }
}
