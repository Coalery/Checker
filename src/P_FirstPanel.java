import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		// </Define>
		
		// <Setting>
		print.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			if(number.getText().equals("") || name.getText().equals("") || absence_start.getText().equals("") || absence_end.getText().equals("") || absence_count.getText().equals("")) {
				Util.showMessage("아직 입력하지 않은 정보가 있습니다.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int absence_count_ToInt = -1;
			try { absence_count_ToInt = Integer.parseInt(absence_count.getText()); } catch (NumberFormatException e) {
				Util.showMessage("총 날짜 수는 숫자로 입력해주세요.", JOptionPane.ERROR_MESSAGE);
				absence_count.setText("");
				return;
			}
			
			Calendar c = Calendar.getInstance();
			
			int numberN = Integer.parseInt(number.getText());
			
			E_Calendar originS = absence_start.getOriginData();
			E_Calendar originE = absence_end.getOriginData();
			
			int sNumber = numberN;
			int sGrade = sNumber / 10000;
			sNumber %= 10000;
			int sClass = sNumber / 100;
			sNumber %= 100;
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("$sGrade", sGrade);
			map.put("$sClass", sClass);
			map.put("$sNumber", sNumber);
			map.put("$number", numberN);
			map.put("$name", name.getText());
			map.put("$asYear", originS.getYear());
			map.put("$asMonth", originS.getMonth());
			map.put("$asDay", originS.getDay());
			map.put("$adMonth", originE.getMonth());
			map.put("$adDay", originE.getDay());
			map.put("$aDays", absence_count_ToInt);
			map.put("$year", c.get(Calendar.YEAR));
			map.put("$month", c.get(Calendar.MONTH) + 1);
			map.put("$day", c.get(Calendar.DAY_OF_MONTH));
			map.put("$parentName", parentName);
			map.put("$teacher", teacher.getText());
			
			new D_PrintPreview(
				parent,
				new JPanel[] {
					new E_LayoutLoader(Util.getConfig("layout11Path"), map),
					new E_LayoutLoader(Util.getConfig("layout12Path"), map),
					new L_Layout1_3(numberN, name.getText(), originS.getYear(), originS.getMonth(), originS.getDay(), originE.getMonth(), originE.getDay(), absence_count_ToInt, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), parentName),
					new L_Layout1_4(numberN, name.getText(), originS.getYear(), originS.getMonth(), originS.getDay(), originE.getMonth(), originE.getDay(), absence_count_ToInt, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), parentName, teacher.getText())
				},
				numberN,
				name.getText(),
				"현장체험학습",
				String.format("결석시작일:%s;결석끝일:%s;", absence_start.getText(), absence_end.getText())
			).setVisible(true);
		}});
		absence_start.setFocusable(false);
		absence_end.setFocusable(false);
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
		teacher.setText(Util.getConfig("teacher"));
	}
	
}
