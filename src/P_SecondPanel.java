import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
			
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			
			String lay4_LOCATION = Util.getConfig("lay4_LOCATION");
			
			StringBuilder[] builder = new StringBuilder[1];
			Calendar c = Calendar.getInstance();
			String line;
			
			if(lay4_LOCATION == null) {
				Util.showMessage("이 문서를 출력하기 위한 서식 파일 중 지정이 되지 않은 것이 있습니다.\n옵션에 들어가서 서식 파일의 경로를 지정해주세요.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!new File(lay4_LOCATION).exists()) {
				Util.showMessage("해당 경로에 서식 파일이 존재하지 않습니다.\n옵션에 들어가서 서식 파일의 경로를 확인해주세요.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				fis = new FileInputStream(lay4_LOCATION);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				
				builder[0] = new StringBuilder();
				while((line = br.readLine()) != null)
					builder[0].append(line
						.replace("$number", number.getText())
						.replace("$name", name.getText())
						.replace("$reason", reason.getText())
						.replace("$absc.tot", String.format("%d년 %d월 %d일 ~ %d월 %d일, 총 %d일간", absence_start.getOriginData().getYear(), absence_start.getOriginData().getMonth(), absence_start.getOriginData().getDay(), absence_end.getOriginData().getMonth(), absence_end.getOriginData().getDay(), absence_count_ToInt))
						.replace("$absc.year", absence_start.getOriginData().getYear() + "")
						.replace("$absc.smonth", absence_start.getOriginData().getMonth() + "")
						.replace("$absc.sday", absence_start.getOriginData().getDay() + "")
						.replace("$absc.dmonth", absence_end.getOriginData().getMonth() + "")
						.replace("$absc.dday", absence_end.getOriginData().getDay() + "")
						.replace("$absc.cnt", absence_count_ToInt + "")
						.replace("$year", c.get(Calendar.YEAR) + "")
						.replace("$month", (c.get(Calendar.MONTH) + 1) + "")
						.replace("$day", c.get(Calendar.DAY_OF_MONTH) + "")
						.replace("$paname", parentName)
						.replace("$abtype", (String) absence_type.getSelectedItem())
						.replace("$abmethod", (String) absence_method.getSelectedItem())
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
			
			Util.showPrintPreview(builder, String.format("type:결석; number:%s; name:%s; abSdate:%s; abEdate:%s; date:%s; teacher:%s;",
					number.getText(),
					name.getText(),
					absence_start.getText(),
					absence_end.getText(),
					String.format("%04d. %02d. %02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)),
					teacher.getText()));
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
	}
	
}
