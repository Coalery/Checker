import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
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
import javax.swing.SwingConstants;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
	
	public static final int A4_WIDTH = 210;
	public static final int A4_HEIGHT = 297;
	
	public static final float A4_WEIGHT = 2.74f;
	
	public static void createLayoutFile() {
		File layoutDirectory = new File("./layouts");
		if(!layoutDirectory.exists())
			layoutDirectory.mkdir();
		
		String[] defcontents = {"11", "12", "13", "14", "21", "31"};
		int[] vals = {11, 12, 13, 14, 21, 31};
		
		for(int i=0; i<vals.length; i++) {
			File f = new File("./layouts/layout" + vals[i] + ".hclay");
			
			String tmpPath = Util.getConfig("layout" + vals[i] + "Path");
			if(tmpPath != null)
				if(new File(tmpPath).exists())
					continue;
			
			if(!f.exists()) {
				FileOutputStream fos = null;
				OutputStreamWriter osw = null;
				BufferedWriter bw = null;
				
				try {
					fos = new FileOutputStream(f);
					osw = new OutputStreamWriter(fos, "UTF8");
					bw = new BufferedWriter(osw);
					
					bw.write(defcontents[i]);
					Util.changeConfig("layout" + vals[i] + "Path", f.getAbsolutePath());
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
	}
	
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
//		component.setFont(new Font("돋움", Font.PLAIN, 13));
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
	
	/**색깔을 받아 문자열로 반환한다.
	 * @see java.awt.Color
	 * @param c 변환할 색깔
	 * @return 색깔을 문자열로 변환한 결과
	 */
	public static String colorToString(Color c) {
		if(c == null)
			return "R:0, G:0, B:0";
		return String.format("R:%d, G:%d, B:%d", c.getRed(), c.getGreen(), c.getBlue());
	}
	
	/**문자열을 받아 색깔로 반환한다.<br>
	 * 문자열의 형식은 "R:r, G:g, B:b" (여기서 r, g, b 는 각각의 RGB 값)
	 * @see java.awt.Color
	 * @param s 변환할 문자열
	 * @return 문자열을 색깔로 변환한 결과
	 */
	public static Color stringToColor(String s) throws NumberFormatException, ArrayIndexOutOfBoundsException {
		String[] strs = s.split(", ");
		
		int r = Integer.parseInt(strs[0].split(":")[1]);
		int g = Integer.parseInt(strs[1].split(":")[1]);
		int b = Integer.parseInt(strs[2].split(":")[1]);
		
		return new Color(r, g, b);
	}
	
	/**글꼴을 받아 문자열로 반환한다.
	 * @see java.awt.Font
	 * @param f 변환할 글꼴
	 * @return 글꼴을 문자열로 변환한 결과
	 */
	public static String fontToString(Font f) {
		String fontName = f.getFontName();
		
		String fontString = fontName + ", ";
		if(fontName.contains("Bold"))
			fontString = fontName.substring(0, fontName.length() - 5) + ", ";
		if(f.getStyle() == Font.PLAIN)
			fontString += "보통";
		else if(f.getStyle() == Font.BOLD)
			fontString += "굵게";
		else if(f.getStyle() == Font.ITALIC)
			fontString += "기울임꼴";
		else if(f.getStyle() == (Font.BOLD | Font.ITALIC))
			fontString += "굵은 기울임꼴";
		fontString += ", " + f.getSize();
		return fontString;
	}
	
	/**문자열을 받아 색깔로 반환한다.<br>
	 * 문자열의 형식은 "<글꼴이름>, <글꼴 스타일>, <글꼴 크기>"
	 * @see java.awt.Font
	 * @param s 변환할 문자열
	 * @return 문자열을 색깔로 변환한 결과
	 */
	public static Font stringToFont(String s) throws NumberFormatException, ArrayIndexOutOfBoundsException {
		String[] strs = s.split(", ");
		int fontSize = Integer.parseInt(strs[2]);
		
		if(strs[1].equals("보통"))
			return new Font(strs[0], Font.PLAIN, fontSize);
		else if(strs[1].equals("기울임꼴"))
			return new Font(strs[0], Font.ITALIC, fontSize);
		else if(strs[1].equals("굵게"))
			return new Font(strs[0], Font.BOLD, fontSize);
		else if(strs[1].equals("굵은 기울임꼴"))
			return new Font(strs[0], Font.BOLD | Font.ITALIC, fontSize);
		return null;
	}
	
	/**글자 정렬 형식을 받아 문자열로 반환한다.
	 * @see javax.swing.SwingConstants
	 * @param a 변환할 글자 정렬 형식
	 * @return 글자 정렬 형식을 문자열로 변환한 결과
	 */
	public static String alignTypeToString(int a) {
		if(a == SwingConstants.LEFT)
			return "왼쪽";
		else if(a == SwingConstants.CENTER)
			return "가운데";
		else if(a == SwingConstants.RIGHT)
			return "오른쪽";
		return null;
	}
	
	/**문자열을 받아 글자 정렬 형식으로 반환한다. 문자열은 왼쪽, 가운데, 오른쪽 중 하나이다.<br>
	 * 만약 세 항목과 같지 않은 값이 매개변수로 들어오면, 기본값인 왼쪽을 반환한다.
	 * @see javax.swing.SwingConstants
	 * @param s 변환할 문자열
	 * @return 문자열을 글자 정렬 형식로 변환한 결과
	 */
	public static int stringToAlignType(String s) {
		if(s.equals("왼쪽"))
			return SwingConstants.LEFT;
		else if(s.equals("가운데"))
			return SwingConstants.CENTER;
		else if(s.equals("오른쪽"))
			return SwingConstants.RIGHT;
		return SwingConstants.LEFT;
	}
}
