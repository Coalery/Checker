import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
			
			FileInputStream fis = null;
			InputStreamReader isr = null;
			BufferedReader br = null;
			
			String lay0_LOCATION = Util.getConfig("lay0_LOCATION");
			String lay1_LOCATION = Util.getConfig("lay1_LOCATION");
			String lay2_LOCATION = Util.getConfig("lay2_LOCATION");
			String lay3_LOCATION = Util.getConfig("lay3_LOCATION");
			
			StringBuilder[] builder = new StringBuilder[4];
			Calendar c = Calendar.getInstance();
			String line;
			
			if(lay0_LOCATION == null || lay1_LOCATION == null || lay2_LOCATION == null || lay3_LOCATION == null) {
				Util.showMessage("이 문서를 출력하기 위한 서식 파일 중 지정이 되지 않은 것이 있습니다.\n옵션에 들어가서 서식 파일의 경로를 지정해주세요.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if(!(new File(lay0_LOCATION).exists() && new File(lay1_LOCATION).exists() && new File(lay2_LOCATION).exists() && new File(lay3_LOCATION).exists())) {
				Util.showMessage("해당 경로에 서식 파일이 존재하지 않습니다.\n옵션에 들어가서 서식 파일의 경로를 확인해주세요.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				fis = new FileInputStream(lay0_LOCATION);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				
				builder[0] = new StringBuilder();
				while((line = br.readLine()) != null)
					builder[0].append(line
						.replace("$number", number.getText())
						.replace("$name", name.getText())
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
			
			try {
				fis = new FileInputStream(lay1_LOCATION);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				
				builder[1] = new StringBuilder();
				while((line = br.readLine()) != null)
					builder[1].append(line);
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
			
			try {
				fis = new FileInputStream(lay2_LOCATION);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				
				builder[2] = new StringBuilder();
				while((line = br.readLine()) != null)
					builder[2].append(line);
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
			
			try {
				fis = new FileInputStream(lay3_LOCATION);
				isr = new InputStreamReader(fis, "UTF-8");
				br = new BufferedReader(isr);
				
				builder[3] = new StringBuilder();
				while((line = br.readLine()) != null)
					builder[3].append(line);
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
			
			Util.printComponent("Layout1_1", new L_Layout1_1(20613, "김현우"));
			Util.printComponent("Layout1_2", new L_Layout1_2(20613, "김현우"));
			Util.printComponent("Layout1_3", new L_Layout1_3(20613, "김현우"));
			Util.printComponent("Layout1_4", new L_Layout1_4(20613, "김현우"));
			Util.log(String.format("type:현장체험학습; number:%s; name:%s; abSdate:%s; abEdate:%s; date:%s; teacher:%s;",
					number.getText(),
					name.getText(),
					absence_start.getText(),
					absence_end.getText(),
					String.format("%04d. %02d. %02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)),
					teacher.getText()));
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
	}
	
}
