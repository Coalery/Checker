import java.awt.Color;
import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
	
	public static final int A4_WIDTH = 210;
	public static final int A4_HEIGHT = 297;
	
	public static final float A4_WEIGHT = 2.74f;
	
	public static void createDataFile() {
		File dataFile = new File("./data.hc");
		
		if(!dataFile.exists()) {
			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			
			try {
				fos = new FileOutputStream("./data.hc");
				osw = new OutputStreamWriter(fos, "UTF8");
				bw = new BufferedWriter(osw);
				
				bw.write("# 맨 앞에 '#' 을 입력하면, 주석처리가 됩니다.");
				bw.newLine();
				bw.newLine();
				bw.write("# < 작성법 >");
				bw.newLine();
				bw.write("# 학번:이름:학부모명(모)");
				bw.newLine();
				bw.write("# 예) 10101:OOO:AAA");
				bw.newLine();
			} catch(IOException e) {
				e.printStackTrace(System.err);
			} finally {
				if(bw != null)
					try { bw.close(); } catch(IOException e) { e.printStackTrace(System.err); }
				if(osw != null)
					try { osw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
				if(fos != null)
					try { fos.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			}
		}
	}
	
	public static ArrayList<E_Student> readData() {
		ArrayList<E_Student> result = new ArrayList<E_Student>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		Util.createDataFile();
		
		try {
			fis = new FileInputStream("./data.hc");
			isr = new InputStreamReader(fis, "UTF8");
			br = new BufferedReader(isr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.length() == 0)
					continue;
				if(line.charAt(0) == 0xFEFF)
					line = line.substring(1);
				if(line.charAt(0) == '#')
					continue;
				
				String[] read = line.split(":");
				if(read.length != 3) continue;
				
				try {
					int number = Integer.parseInt(read[0]);
					String name = read[1];
					String parentName = read[2];
					
					result.add(new E_Student(number, name, parentName)); 
				} catch (NumberFormatException ex) { continue; }
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(isr != null)
				try { isr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fis != null)
				try { fis.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public static void changeConfig(String key, String value) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		
		try {
			String json = "";
			File f = new File("config.hc");
			if(!f.exists()) {
				f.createNewFile();
				json = "{}";
			} else {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("config.hc"), "UTF8"));
				String line;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) == 65279)
						line = line.substring(1);
					json += line;
				}
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			}
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject)parser.parse(json);
			object.put(key, value);
			
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("config.hc"), "UTF8"));
			String s = object.toJSONString();
			bw.write(s);
			
			try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			
			
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace(System.err);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(bw != null)
				try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
	}
	
	public static String getConfig(String key) {
		BufferedReader br = null;
		
		try {
			String json = "";
			File f = new File("config.hc");
			if(!f.exists()) {
				return null;
			} else {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("config.hc"), "UTF8"));
				String line;
				while((line = br.readLine()) != null) {
					if(line.charAt(0) == 65279)
						line = line.substring(1);
					json += line;
				}
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			}
			
			JSONParser parser = new JSONParser();
			JSONObject object = (JSONObject)parser.parse(json);
			return (String)object.get(key);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace(System.err);
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		return null;
	}
	
	/**JOptionPane 의 showMessageDialog 를 통해 간단히 메세지를 보여준다.
	 * @author Coalery ( 김현우 )
	 * @param message : 보여줄 메세지
	 * @param type : 메세지의 타입
	 * @see javax.swing.JOptionPane
	 */
	public static void showMessage(String message, int type) {
		JFrame tmp = new JFrame();
		JOptionPane.showMessageDialog(tmp, message, "알림", type);
		tmp.dispose();
	}
	
	/**FileDialog 를 통해 선택한 파일의 절대경로를 리턴한다.
	 * @author Coalery ( 김현우 )
	 * @return 선택한 파일의 절대경로
	 * @see java.awt.FileDialog
	 */
	public static String getPathByFileDialog() {
		JFrame f = new JFrame();
		FileDialog fd = new FileDialog(f, "파일 선택");
		fd.setVisible(true);
		f.dispose();
		
		if(fd.getDirectory() == null || fd.getFile() == null)
			return null;
		
		return fd.getDirectory() + fd.getFile(); 
	}
	
	/**Windows 의 cmd 명령어를 실행시킨다.
	 * @author Coalery ( 김현우 )
	 * @param cmd : 실행시킬 명령어
	 * @return 명령어 실행 결과
	 */
	public static String execCommand(String cmd) {
		Process process = null;
		BufferedReader br = null;
		StringBuffer readBuffer = null;
		
		try {
			cmd = "cmd.exe /c " + cmd;
			
			process = Runtime.getRuntime().exec(cmd);
			br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			readBuffer = new StringBuffer();
			
			while((line = br.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			
			return readBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		} finally {
			try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		return null;
	}
	
	/**매개변수로 넣은 버튼에 여러가지 서식을 입힙니다. JButton, JMenuItem, JToggleButton 은 다른 메서드를 사용해야 합니다.
	 * @author Coalery ( 김현우 )
	 * @param button : 서식을 적용할 컴포넌트
	 * @param c : 컴포넌트에 입힐 색깔
	 * @return 서식을 모두 적용한 컴포넌트를 리턴한다.
	 * @see javax.swing.JComponent
	 * @see #getDefaultComponent(JComponent, Color, boolean)
	 */
	public static JComponent getDefaultComponent(JComponent component, Color c) {
		component.setOpaque(true);
		component.setBackground(c);
		return component;
	}
	
	/**매개변수로 넣은 컴포넌트에 여러가지 서식을 입힙니다. JButton, JMenuItem, JToggleButton 만 사용 가능합니다.
	 * @author Coalery ( 김현우 )
	 * @param component : 서식을 적용할 컴포넌트
	 * @param c : 컴포넌트에 입힐 색깔
	 * @param isBorderPainted : 컴포넌트의 외곽선 여부
	 * @return 서식을 모두 적용한 컴포넌트를 리턴한다. 맞지 않는 컴포넌트인 경우 null 을 반환한다.
	 * @see javax.swing.AbstractButton
	 * @see javax.swing.JButton
	 * @see javax.swing.JMenuItem
	 * @see javax.swing.JToggleButton
	 */
	public static JComponent getDefaultComponent(JComponent component, Color c, boolean isBorderPainted) {
		if(component instanceof JButton) {
			JButton tmpButton = (JButton) component;
			tmpButton.setBorderPainted(isBorderPainted);
			tmpButton.setFocusPainted(false);
			tmpButton.setOpaque(true);
			tmpButton.setBackground(c);
			return tmpButton;
		} else if(component instanceof JMenuItem) {
			JMenuItem tmpMenuItem = (JMenuItem) component;
			tmpMenuItem.setBorderPainted(isBorderPainted);
			tmpMenuItem.setFocusPainted(false);
			tmpMenuItem.setOpaque(true);
			tmpMenuItem.setBackground(c);
			return tmpMenuItem;
		} else if(component instanceof JToggleButton) {
			JToggleButton tmpToggle = (JToggleButton) component;
			tmpToggle.setBorderPainted(isBorderPainted);
			tmpToggle.setFocusPainted(false);
			tmpToggle.setOpaque(true);
			tmpToggle.setBackground(c);
			return tmpToggle;
		}
		return null;
	}
	
	/**선생님의 성함을 입력 받고, 저장하는 Dialog 를 띄웁니다.
	 * @author Coalery ( 김현우 )
	 * @see javax.swing.JOptionPane
	 */
	public static void teacherNameDialog() {
		String teacherName = JOptionPane.showInputDialog("프로그램을 사용하시는 사용자의 이름을 입력하세요.");
		if(teacherName == null)
			System.exit(0);
		if(teacherName.equals(""))
			System.exit(0);
		Util.changeConfig("teacher", teacherName);
	}
}
