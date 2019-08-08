
public class E_Calendar {
	
	private int year;
	private int month;
	private int day;
	
	public E_Calendar(int year, int month, int day) {
		this.year = year;
		this.month = month;
		this.day = day;
	}
	
	public int getYear() { return year; }
	public int getMonth() { return month; }
	public int getDay() { return day; }
	
	public static E_Calendar parseToCalendar(String s) {
		int year = Integer.parseInt(s.substring(0, 4));
		int month = Integer.parseInt(s.substring(4, 6));
		int day = Integer.parseInt(s.substring(6, 8));
		
		return new E_Calendar(year, month, day);
	}
	
}
