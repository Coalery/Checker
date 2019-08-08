import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * 
 * @author Coalery ( 김현우 )
 * 기능 : StartFrame 에서 결석을 누를시에 나올 Panel 을 구성설정하는 클래스
 * 
 */

@SuppressWarnings("serial")
public class P_SecondPanel extends P_PanelParent {
	
	private JFrame parent;
	
	private JTextArea reason;
	private T_CalendarTextField absence_start;
	private T_CalendarTextField absence_end;
	private JTextField absence_count;
	private JComboBox<String> absence_type;
	private JComboBox<String> absence_method;
	
	@SuppressWarnings("unchecked")
	public P_SecondPanel(JFrame parent) {
		// <DefaultSetting>
		super();
		this.parent = parent;
		// </DefaultSetting>
		
		// <Define>
		JLabel reason_L = new JLabel("사유 : ");
		reason = new JTextArea();
		JScrollPane reason_sp = new JScrollPane(reason, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel absence_L = new JLabel("결석 기간 : ");
		absence_start = new T_CalendarTextField(this.parent);
		absence_end = new T_CalendarTextField(this.parent);
		
		JLabel absence_count_L = new JLabel("일간");
		absence_count = new JTextField();
		
		JLabel absence_type_L = new JLabel("결석유형 : ");
		absence_type = (JComboBox<String>) Util.getDefaultComponent(new JComboBox<String>(new String[] {"인정결", "병결"}), Color.WHITE);
		
		JLabel absence_method_L = new JLabel("확인방법 : ");
		absence_method = (JComboBox<String>) Util.getDefaultComponent(new JComboBox<>(new String[] {"학생과 면담", "증빙서류 확인", "보호자 확인"}), Color.WHITE);
		// </Define>
		
		// <Setting>
		reason.setLineWrap(true);
		reason.setWrapStyleWord(true);
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
			
			E_Calendar originS = absence_start.getOriginData();
			E_Calendar originE = absence_end.getOriginData();
			new E_PrintPreview(
				parent,
				new JPanel[] {new L_Layout2_1(Integer.parseInt(number.getText()), name.getText(), reason.getText(), originS.getYear(), originS.getMonth(), originS.getDay(), originE.getMonth(), originE.getDay(), absence_count_ToInt, c.get(Calendar.YEAR), c.get(Calendar.MONTH) + 1, c.get(Calendar.DAY_OF_MONTH), parentName, (String)absence_type.getSelectedItem(), (String)absence_method.getSelectedItem(), teacher.getText())},
				Integer.parseInt(number.getText()),
				name.getText(),
				"결석",
				String.format("사유:%s;결석시작일:%s;결석끝일:%s;결석유형:%s;확인방법:%s;", reason.getText(), absence_start.getText(), absence_end.getText(), (String)absence_type.getSelectedItem(), (String)absence_method.getSelectedItem())
			).setVisible(true);
		}});
		// </Setting>
		
		// <SettingLabel>
		reason_L.setSize(60, 30);
		reason_L.setLocation(20, 100);
		absence_L.setSize(80, 30);
		absence_L.setLocation(20, 200);
		absence_count_L.setSize(60, 30);
		absence_count_L.setLocation(345, 200);
		absence_type_L.setSize(80, 30);
		absence_type_L.setLocation(20, 240);
		absence_method_L.setSize(80, 30);
		absence_method_L.setLocation(20, 280);
		teacher_L.setSize(80, 30);
		teacher_L.setLocation(20, 320);
		teacher.setSize(200, 30);
		teacher.setLocation(90, 320);
		// </SettingLabel>
		
		// <SettingComponents>
		reason_sp.setSize(200, 90);
		reason_sp.setLocation(90, 100);
		absence_start.setSize(95, 30);
		absence_start.setLocation(90, 200);
		absence_end.setSize(95, 30);
		absence_end.setLocation(195, 200);
		absence_count.setSize(30, 30);
		absence_count.setLocation(310, 200);
		absence_type.setSize(200, 30);
		absence_type.setLocation(90, 240);
		absence_method.setSize(200, 30);
		absence_method.setLocation(90, 280);
		// </SettingComponents>
		
		// <AddToPanel>
		add(reason_L); add(reason_sp);
		add(absence_L); add(absence_count_L); add(absence_start); add(absence_end); add(absence_count);
		add(absence_type_L); add(absence_type);
		add(absence_method_L); add(absence_method);
		add(teacher_L); add(teacher);
		// </AddToPanel>
	}
	
	public void initPanel() {
		number.setText("");
		name.setText("");
		reason.setText("");
		absence_start.setOrigin(new E_Calendar(0, 0, 0));
		absence_start.setText("");
		absence_end.setOrigin(new E_Calendar(0, 0, 0));
		absence_end.setText("");
		absence_count.setText("");
		absence_type.setSelectedIndex(0);
		absence_method.setSelectedIndex(0);
		teacher.setText(Util.getConfig("teacher"));
	}
	
}
