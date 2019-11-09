import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**StartFrame 에서 조퇴·결과·지각을 누를시에 나올 Panel 을 구성설정하는 클래스
 * @author Coalery ( 김현우 )
 */

@SuppressWarnings("serial")
public class P_ThirdPanel extends P_PanelParent {
	
	private JFrame parent;
	private JTextArea reason;
	private S_RangeSlider slider;
	private JComboBox<String> range_method;
	
	private T_CalendarTextField absence;
	
	@SuppressWarnings("unchecked")
	public P_ThirdPanel(JFrame parent) {
		// <DefaultSetting>
		super();
		setLayout(null);
		this.parent = parent;
		// </DefaultSetting>
		
		// <Define>
		JLabel reason_L = new JLabel("사유 : ");
		reason = new JTextArea();
		JScrollPane reason_sp = new JScrollPane(reason, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		JLabel absence_L = new JLabel("날짜 : ");
		absence = new T_CalendarTextField(this.parent);
		
		JLabel range_L = new JLabel("출결 교시 : ");
		JLabel[] range_specific_L = new JLabel[7];
		slider = new S_RangeSlider(1, 7);
		for(int i=0; i<range_specific_L.length; i++) range_specific_L[i] = new JLabel(String.valueOf(i+1), JLabel.CENTER);
		
		JLabel range_method_L = new JLabel("확인방법 : ");
		range_method = (JComboBox<String>) Util.getDefaultComponent(new JComboBox<>(new String[] {"학생과 면담", "증빙서류 확인", "보호자 확인"}), Color.WHITE);
		
		JLabel reasonExample = new JLabel("<html><div style=\"text-align: center;\">(예)</div></span><br><div style=\"text-align:center;\">출결유형(내용)</div></html>", JLabel.CENTER);
		// </Define>
		
		// <Setting>
		reason.setLineWrap(true);
		reason.setWrapStyleWord(true);
		print.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			boolean isRangeSelected = false;
					isRangeSelected = true;
			if(number.getText().equals("") || name.getText().equals("") || !isRangeSelected || reason.getText().equals("") || absence.getText().equals("") || absence.getOriginData().equals(new E_Calendar(0, 0, 0))) {
				Util.showMessage("아직 입력하지 않은 정보가 있습니다.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int numberN = -1;
			try { numberN = Integer.parseInt(number.getText()); } catch(NumberFormatException e ) {
				Util.showMessage("학번이 숫자가 아닙니다.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Calendar c = Calendar.getInstance();
			String res = null;
			if(slider.getValue() == slider.getUpperValue())
				res = slider.getValue() + "교시";
			else
				res = slider.getValue() + "교시 ~ " + slider.getUpperValue() + "교시";
			E_Calendar cData = absence.getOriginData();
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("$number", numberN);
			map.put("$name", name.getText());
			map.put("$reason", reason.getText());
			map.put("$aYear", cData.getYear());
			map.put("$aMonth", cData.getMonth());
			map.put("$aDay", cData.getDay());
			map.put("$aPeriod", res);
			map.put("$year", c.get(Calendar.YEAR));
			map.put("$month", c.get(Calendar.MONTH) + 1);
			map.put("$day", c.get(Calendar.DAY_OF_MONTH));
			map.put("$aMethod", range_method.getSelectedItem());
			map.put("$teacher", teacher.getText());
			
			new D_PrintPreview(
				parent,
				new JPanel[] { new E_LayoutLoader(Util.getConfig("layout31Path"), map) },
				numberN,
				name.getText(),
				"조퇴·결과·지각",
				String.format("사유:%s;발생일:%s;출결교시:%s;확인방법:%s;", reason.getText(), absence.getText(), res, (String)range_method.getSelectedItem())
			).setVisible(true);
		}});
		// </Setting>
		
		// <SettingLabel>
		reason_L.setSize(60, 30);
		reason_L.setLocation(20, 100);
		reasonExample.setSize(150, 80);
		reasonExample.setLocation(272, 100);
		absence_L.setSize(80, 30);
		absence_L.setLocation(20, 200);
		range_L.setSize(80, 30);
		range_L.setLocation(20, 240);
		for(int i=0; i<range_specific_L.length; i++) {
			range_specific_L[i].setSize(15, 30);
			range_specific_L[i].setLocation(90 + (int)(i*30.85f), 260);
		}
		range_method_L.setSize(80, 30);
		range_method_L.setLocation(20, 297);
		teacher_L.setSize(80, 30);
		teacher_L.setLocation(20, 337);
		teacher.setSize(200, 30);
		teacher.setLocation(90, 337);
		// </SettingLabel>
		
		// <SettingComponents>
		reason_sp.setSize(200, 90);
		reason_sp.setLocation(90, 100);
		absence.setSize(200, 30);
		absence.setLocation(90, 200);
		slider.setSize(200, 20);
		slider.setLocation(90, 247);
		range_method.setSize(200, 30);
		range_method.setLocation(90, 297);
		// </SettingComponents>
		
		// <AddToPanel>
		add(reason_L); add(reason_sp); add(reasonExample);
		add(absence_L); add(absence);
		add(range_L);
		for(int i=0; i<range_specific_L.length; i++) add(range_specific_L[i]);
		add(slider);
		add(range_method_L); add(range_method);
		add(teacher_L); add(teacher);
		// </AddToPanel>
	}
	
	public void initPanel() {
		number.setText("");
		name.setText("");
		reason.setText("");
		slider.setValue(1);
		slider.setUpperValue(7);
		range_method.setSelectedIndex(0);
		absence.setOrigin(new E_Calendar(0, 0, 0));
		absence.setText("");
		teacher.setText(Util.getConfig("teacher"));
	}
	
}
