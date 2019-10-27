import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**날짜를 선택하는 JDialog 를 구성하고, 구성하는 데에 필요한 기능을 가지고 있다.
 * @author Coalery ( 김현우 )
 * @see javax.swing.JDialog
 */
@SuppressWarnings("serial")
public class D_CalendarDialog extends JDialog {
	
	private T_CalendarTextField textToWrite;
	
	private JPanel calendar;
	private JLabel when;
	
	private int year;
	private int month;
	
	/**날짜를 선택하는 JDialog 를 구성한다.
	 * @author Coalery ( 김현우 )
	 * @param owner : modal 에 의해 제한될 JDialog 를 띄운 프레임.
	 * @param textToWrite : 결과를 리턴해주기 위한 텍스트 필드
	 */
	public D_CalendarDialog(JFrame owner, T_CalendarTextField textToWrite) {
		// <DefaultSetting>
		super(owner, "날짜 선택", true);
		setSize(300, 250);
		setLocation(200, 150);
		
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH)+1;
		this.textToWrite = textToWrite;
		// </DefaultSetting>
		
		// <UpperComponentSetting>
		JPanel upper = new JPanel();
		upper.setLayout(new BorderLayout());
		upper.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
		
		JButton left = (JButton) Util.getDefaultComponent(new JButton("◀"), Color.WHITE, true);
		when = new JLabel(year + " / " + month, JLabel.CENTER);
		JButton right = (JButton) Util.getDefaultComponent(new JButton("▶"), Color.WHITE, true);
		
		left.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			month--;
			if(month == 0) {
				year--;
				month = 12;
			}
			initCalendarPanel();
		}});
		
		right.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			month++;
			if(month == 13) {
				year++;
				month = 1;
			}
			initCalendarPanel();
		}});
		upper.add(left, "West");
		upper.add(when, "Center");
		upper.add(right, "East");
		// </UpperComponentSetting>
		
		calendar = new JPanel();
		calendar.setLayout(new GridLayout(5, 7));
		calendar.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		initCalendarPanel();
		
		add(upper, "North");
		add(calendar, "Center");
		setResizable(false);
		setVisible(true);
	}
	
	private void initCalendarPanel() {
		calendar.removeAll();
		calendar.revalidate();
		calendar.repaint();
		
		when.setText(String.format("%04d / %02d", year, month));
		ArrayList<Integer> list = getCalendarList(year, month);
		if(list.size() <= 35)
			calendar.setLayout(new GridLayout(6, 7));
		else
			calendar.setLayout(new GridLayout(7, 7));
		
		String[] days = {"일", "월", "화", "수", "목", "금", "토"};
		for(int i=0; i<days.length; i++)
			calendar.add(new JLabel(days[i], JLabel.CENTER));
		
		for(int i=0; i<list.size(); i++) {
			if(list.get(i) == 0) {
				calendar.add(new JPanel());
			} else {
				JButton date = (JButton) Util.getDefaultComponent(new JButton(list.get(i).toString()), Color.WHITE, false);
				date.addActionListener(new CD_AcListener(year, month, list.get(i)));
				date.setBorder(null);
				calendar.add(date);
			}
		}
		if(list.size() <= 35) {
			for(int i=0; i<35-list.size(); i++)
				calendar.add(new JPanel());
		} else {
			for(int i=0; i<42-list.size(); i++)
				calendar.add(new JPanel());
		}
	}
	
	/**년도와 월을 받아서, 해당 값에 맞는 날짜들의 정보를 리스트로 반환한다.<br>
	 * 날짜가 없는 날은 0 으로 모두 들어가며, 그 뒤로는 1, 2, 3 … ( 해당 월의 마지막 날 ) 의 구성으로 리스트가 반환된다.
	 * @author Coalery ( 김현우 )
	 * @param year : 리스트를 받아올 년도
	 * @param month : 리스트를 받아올 월
	 * @return 받은 값들에 의해 리스트를 만들고, 그 리스트를 리턴한다.
	 * @see java.util.Calendar
	 */
    private ArrayList<Integer> getCalendarList(int year, int month) {
        int START_DAY_OF_WEEK = 0;
        int END_DAY = 0;
        
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        
        start.set(year, month - 1, 1);
        end.set(year, month, 1);
        end.add(Calendar.DATE, -1);
        
        START_DAY_OF_WEEK = start.get(Calendar.DAY_OF_WEEK);
        END_DAY = end.get(Calendar.DATE);
        
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        for(int q = 1 ; q < START_DAY_OF_WEEK ; q++)
            list.add(0);
        for(int q = 1 ; q <= END_DAY ; q++)
            list.add(q);
        return list;
    }
	
    /**"D_CalendarDialog" 클래스의 내부 클래스로, 날짜 선택 각각의 버튼마다 들어갈 ActionListener 를 구현한다.
     * @author Coalery ( 김현우 )
     * @see java.awt.event.ActionListener
     */
    private class CD_AcListener implements ActionListener {
    	
    	private int year;
    	private int month;
    	private int day;
    	
    	public CD_AcListener(int year, int month, int day) {
    		this.year = year;
    		this.month = month;
    		this.day = day;
		}
    	
    	@Override
    	public void actionPerformed(ActionEvent event) {
    		textToWrite.setText(String.format("%04d/%02d/%02d", year, month, day));
    		textToWrite.setOrigin(new E_Calendar(year, month, day));
    		
    		D_CalendarDialog.this.setVisible(false);
    		D_CalendarDialog.this.dispose();
    	}
    	
    }
    
}
