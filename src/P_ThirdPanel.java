import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**StartFrame 에서 조퇴·결과·지각을 누를시에 나올 Panel 을 구성설정하는 클래스
 * @author Coalery ( 김현우 )
 */

@SuppressWarnings("serial")
public class P_ThirdPanel extends P_PanelParent {
	
	private JFrame parent;
	private JTextArea reason;
	private JCheckBox[] range;
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
		print.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			boolean isRangeSelected = false;
			for(int i=0; i<range.length; i++)
				if(range[i].isSelected())
					isRangeSelected = true;
			if(number.getText().equals("") || name.getText().equals("") || !isRangeSelected || reason.getText().equals("") || absence.getText().equals("") || absence.getOriginData().equals(new E_Calendar(0, 0, 0))) {
				Util.showMessage("아직 입력하지 않은 정보가 있습니다.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			
			String lay5_LOCATION = Util.getConfig("lay5_LOCATION");
			
			StringBuilder[] builder = new StringBuilder[1];
			Calendar c = Calendar.getInstance();
			String line;
			
			if(lay5_LOCATION == null) {
				Util.showMessage("이 문서를 출력하기 위한 서식 파일 중 지정이 되지 않은 것이 있습니다.\n옵션에 들어가서 서식 파일의 경로를 지정해주세요.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!new File(lay5_LOCATION).exists()) {
				Util.showMessage("해당 경로에 서식 파일이 존재하지 않습니다.\n옵션에 들어가서 서식 파일의 경로를 확인해주세요.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				fis = new FileInputStream(lay5_LOCATION);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				
				StringBuilder period = new StringBuilder();
				for(int i=0; i<range.length; i++)
					if(range[i].isSelected())
						period.append((i+1) + "교시, ");
				String res = period.toString();
				if(res.length() != 0)
					res = res.substring(0, res.length() - 2);
				
				builder[0] = new StringBuilder();
				while((line = br.readLine()) != null)
					builder[0].append(line
						.replace("$number", number.getText())
						.replace("$name", name.getText())
						.replace("$reason", reason.getText())
						.replace("$absc.year", absence.getOriginData().getYear() + "")
						.replace("$absc.month", absence.getOriginData().getMonth() + "")
						.replace("$absc.day", absence.getOriginData().getDay() + "")
						.replace("$absc.period", res)
						.replace("$year", c.get(Calendar.YEAR) + "")
						.replace("$month", (c.get(Calendar.MONTH) + 1) + "")
						.replace("$day", c.get(Calendar.DAY_OF_MONTH) + "")
						.replace("$abmethod", (String) range_method.getSelectedItem())
						.replace("$teacher", teacher.getText())
					);
			} catch(IOException e) {
				e.printStackTrace(System.err);
				return;
			} finally {
				if(br != null)
					try { br.close(); } catch(IOException e) { e.printStackTrace(System.err); }
				if(isr != null)
					try { isr.close(); } catch(IOException e) { e.printStackTrace(System.err); }
				if(fis != null)
					try { fis.close(); } catch(IOException e) { e.printStackTrace(System.err); }
			}
			
			// TODO
			Util.showPrintPreview(builder, String.format("type:조퇴·결과·지각; number:%s; name:%s; date:%s; teacher:%s;",
					number.getText(),
					name.getText(),
					String.format("%04d. %02d. %02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)),
					teacher.getText()));
		}});
		// </Setting>
		
		// <SettingLabel>
		reason_L.setSize(60, 30);
		reason_L.setLocation(20, 100);
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
		for(int i=0; i<range.length; i++) {
			range[i].setSize(15, 15);
			range[i].setLocation(90 + (int)(i*30.85f), 247);
		}
		range_method.setSize(200, 30);
		range_method.setLocation(90, 297);
		// </SettingComponents>
		
		// <AddToPanel>
		add(reason_L); add(reason_sp);
		add(absence_L); add(absence);
		add(range_L);
		for(int i=0; i<range_specific_L.length; i++) add(range_specific_L[i]);
		for(int i=0; i<range.length; i++) add(range[i]);
		add(range_method_L); add(range_method);
		add(teacher_L); add(teacher);
		// </AddToPanel>
	}
	
	public void initPanel() {
		number.setText("");
		name.setText("");
		reason.setText("");
		for(int i=0; i<range.length; i++)
			range[i].setSelected(false);
		range_method.setSelectedIndex(0);
		absence.setOrigin(new E_Calendar(0, 0, 0));
		absence.setText("");
	}
	
}
