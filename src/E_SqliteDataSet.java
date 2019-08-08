
public class E_SqliteDataSet {
	private int number;
	private String name;
	private String type;
	
	private E_Calendar writeDate;
	private E_Time writeTime;
	
	private String teacher;
	private String etc;
	
	public E_SqliteDataSet(int number, String name, String type, String writeDate, String writeTime, String teacher, String etc) {
		this(number, name, type, E_Calendar.parseToCalendar(writeDate), E_Time.parseToTime(writeTime), teacher, etc);
	}
	
	public E_SqliteDataSet(int number, String name, String type, E_Calendar writeDate, E_Time writeTime, String teacher, String etc) {
		this.number = number;
		this.name = name;
		this.type = type;
		
		this.writeDate = writeDate;
		this.writeTime = writeTime;
		
		this.teacher = teacher;
		this.etc = etc;
	}
	
	public int getNumber() { return number; }
	public String getName() { return name; }
	public String getType() { return type; }
	public E_Calendar getWriteDate() { return writeDate; }
	public E_Time getWriteTime() { return writeTime; }
	public String getTeacher() { return teacher; }
	public String getETC() { return etc; }
}
