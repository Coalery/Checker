import java.awt.Color;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**StartFrame 에서 조퇴·결과·지각·외출을 누를시에 나올 Panel 을 구성설정하는 클래스
 * @author Coalery ( 김현우 )
 */

@SuppressWarnings("serial")
public class P_ThirdPanel extends P_PanelParent {
	
	private JTextArea reason;
	private JCheckBox[] range;
	private JComboBox<String> range_method;
	
	@SuppressWarnings("unchecked")
	public P_ThirdPanel() {
		// <DefaultSetting>
		super();
		setLayout(null);
		// </DefaultSetting>
		
		// <Define>
		JLabel reason_L = new JLabel("사유 : ");
		reason = new JTextArea();
		JScrollPane reason_sp = new JScrollPane(reason, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel range_L = new JLabel("출결 교시 : ");
		JLabel[] range_specific_L = new JLabel[7];
		range = new JCheckBox[7];
		for(int i=0; i<range.length; i++) range[i] = new JCheckBox();
		for(int i=0; i<range_specific_L.length; i++) range_specific_L[i] = new JLabel(String.valueOf(i+1), JLabel.CENTER);
		
		JLabel range_method_L = new JLabel("확인방법 : ");
		range_method = (JComboBox<String>) Util.getDefaultComponent(new JComboBox<>(new String[] {"학생과 면담", "증빙서류 확인", "보호자 확인"}), Color.WHITE);
		// </Define>
		
		// <Setting>
		reason.setLineWrap(true);
		reason.setWrapStyleWord(true);
		for(int i=0; i<range.length; i++) {
			range[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		// </Setting>
		
		// <SettingLabel>
		reason_L.setSize(60, 30);
		reason_L.setLocation(20, 100);
		range_L.setSize(80, 30);
		range_L.setLocation(20, 200);
		for(int i=0; i<range_specific_L.length; i++) {
			range_specific_L[i].setSize(15, 30);
			range_specific_L[i].setLocation(90 + (int)(i*30.85f), 220);
		}
		range_method_L.setSize(80, 30);
		range_method_L.setLocation(20, 257);
		// </SettingLabel>
		
		// <SettingComponents>
		reason_sp.setSize(200, 90);
		reason_sp.setLocation(90, 100);
		for(int i=0; i<range.length; i++) {
			range[i].setSize(15, 15);
			range[i].setLocation(90 + (int)(i*30.85f), 207);
		}
		range_method.setSize(200, 30);
		range_method.setLocation(90, 257);
		// </SettingComponents>
		
		// <AddToPanel>
		add(reason_L); add(reason_sp);
		add(range_L);
		for(int i=0; i<range_specific_L.length; i++) add(range_specific_L[i]);
		for(int i=0; i<range.length; i++) add(range[i]);
		add(range_method_L); add(range_method);
		// </AddToPanel>
	}
	
	public void initPanel() {
		number.setText("");
		name.setText("");
		reason.setText("");
		for(int i=0; i<range.length; i++)
			range[i].setSelected(false);
		range_method.setSelectedIndex(0);
	}
	
}
