
public class E_Time {
	
	private int hour;
	private int minute;
	private int second;
	
	public E_Time(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	
	public int getHour() { return hour; }
	public int getMinute() { return minute; }
	public int getSecond() { return second; }
	
	public static E_Time parseToTime(String s) {
		int year = Integer.parseInt(s.substring(0, 2));
		int month = Integer.parseInt(s.substring(2, 4));
		int day = Integer.parseInt(s.substring(4, 6));
		
		return new E_Time(year, month, day);
	}
	
	public String toString() { return hour + ":" + minute + ":" + second; }
	
}
