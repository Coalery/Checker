import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**StartFrame 에서 현장체험을 누를시에 나올 Panel 을 구성설정하는 클래스
 * @author Coalery ( 김현우 )
 */
@SuppressWarnings("serial")
public class P_FirstPanel extends P_PanelParent {
	
	private JFrame parent;
	
	private T_CalendarTextField absence_start;
	private T_CalendarTextField absence_end;
	private JTextField absence_count;
	
	public P_FirstPanel(JFrame parent) {
		// <DefaultSetting>
		super();
		this.parent = parent;
		// </DefaultSetting>
		
		// <Define>
		JLabel absence_L = new JLabel("결석 기간 : ");
		absence_start = new T_CalendarTextField(this.parent);
		absence_end = new T_CalendarTextField(this.parent);
		
		JLabel absence_count_L = new JLabel("일간");
		absence_count = new JTextField();
		
		JLabel teacher_L = new JLabel("선생님 : ");
		JLabel teacher = new JLabel(Util.getConfig("teacher"));
		// </Define>
		
		// <Setting>
		// </Setting>
		
		// <SettingLabel>
		absence_L.setSize(80, 30);
		absence_L.setLocation(20, 100);
		absence_count_L.setSize(60, 30);
		absence_count_L.setLocation(345, 100);
		teacher_L.setSize(60, 30);
		teacher_L.setLocation(20, 140);
		teacher.setSize(100, 30);
		teacher.setLocation(90, 140);
		// </SettingLabel>
		
		// <SettingComponents>
		absence_start.setSize(95, 30);
		absence_start.setLocation(90, 100);
		absence_end.setSize(95, 30);
		absence_end.setLocation(195, 100);
		absence_count.setSize(30, 30);
		absence_count.setLocation(310, 100);
		// </SettingComponents>
		
		// <AddToPanel>
		add(absence_L); add(absence_count_L);
		add(absence_start); add(absence_end); add(absence_count);
		add(teacher_L); add(teacher);
		// </AddToPanel>
	}
	
	public void initPanel() {
		number.setText("");
		name.setText("");
		absence_start.setText("");
		absence_end.setText("");
		absence_count.setText("");
	}
	
}
