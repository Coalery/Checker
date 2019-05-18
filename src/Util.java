import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Util {
	
	public static ArrayList<E_Student> readData() {
		ArrayList<E_Student> result = new ArrayList<E_Student>();
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader("./data.hc");
			br = new BufferedReader(fr);
			
			String line = null;
			while((line = br.readLine()) != null) {
				if(line.length() == 0)
					continue;
				if(line.charAt(0) == 0xFEFF)
					line = line.substring(1);
				if(line.charAt(0) == '#')
					continue;
				String[] read = line.split(";");
				
				for(int i=0; i<read.length; i++) {
					try { result.add(new E_Student(Integer.parseInt(read[i].split(":")[0]), read[i].split(":")[1])); } catch (NumberFormatException ex) {}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fr != null)
				try { fr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		
		return result;
	}
	
	public static void changeConfig(String key, String value) {
		BufferedWriter bw = null;
		
		System.out.println("key: " + key + ", value: " + value);
		
		try {
			String config = getConfig(key);
			if(config == null) {
				bw = new BufferedWriter(new FileWriter("./config.hc", true));
				bw.newLine();
				bw.append(key + "=" + value);
			} else {
				FileReader fr = null;
				BufferedReader br = null;
				
				String res = null;
				
				try {
					fr = new FileReader("./config.hc");
					br = new BufferedReader(fr);
					
					StringBuilder sb = new StringBuilder();
					String line;
					while((line = br.readLine()) != null)
						sb.append(line + "\n");
					
					res = sb.toString();
					if(res.charAt(res.length()-1) == '\n')
						res = res.substring(0, res.length()-1);
				} catch (IOException e) {
					e.printStackTrace(System.err);
				} finally {
					if(br != null)
						try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
					if(fr != null)
						try { fr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
				}
				bw = new BufferedWriter(new FileWriter("./config.hc"));
				bw.write(res.replace(config, value));
			}
			
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(bw != null)
				try { bw.close(); } catch (IOException e) { e.printStackTrace(System.err); }
		}
	}
	
	/**"config.hc" 파일에서 값을 가져온다. (K-V)<br>
	 * 매개변수로 받은 Key 값이 존재하지 않을 경우, null 을 리턴한다.
	 * 
	 * @author Coalery ( 김현우 )
	 * @param key : 받아올 데이터의 Key 값
	 * @return Key 값에 의해 불러온 Value
	 */
	public static String getConfig(String key) {
		String value = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader("./config.hc");
			br = new BufferedReader(fr);
			
			if(br.ready()) {
				String line;
				while((line = br.readLine()) != null) {
					if(line.split("=")[0].equals(key)) {
						value = line.split("=")[1];
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fr != null)
				try { fr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		
		return value;
	}
	
	/**"log.hc" 파일에 로그를 기록한다.
	 * @author Coalery ( 김현우 )
	 * @param content : 로그에 입력할 기록 내용
	 */
	public static void log(String content) {
		Calendar c = Calendar.getInstance();
		String s = String.format("[%04d-%02d-%02d/%02d:%02d:%02d.%03d] %s%n", c.get(Calendar.YEAR), (c.get(Calendar.MONTH)+1), c.get(Calendar.DAY_OF_MONTH), c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), c.get(Calendar.SECOND), c.get(Calendar.MILLISECOND), content);
		
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			File f = new File("./log.hc");
			fw = new FileWriter(f, f.exists());
			bw = new BufferedWriter(fw);
			
			bw.write(s);
		} catch(IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(bw != null)
				try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fw != null)
				try { fw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
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
	
	/**매개변수로 넣은 버튼에 여러가지 서식을 입힙니다. 버튼에 서식 입히기 귀찮아서 만들었다고 합니다.
	 * @author Coalery ( 김현우 )
	 * @param button : 서식을 적용할 버튼
	 * @param c : 서식을 모두 적용하고 버튼에 입힐 색깔
	 * @param isBorderPainted : 버튼의 외곽선 여부
	 * @return 서식을 모두 적용한 버튼을 리턴한다.
	 * @see javax.swing.JButton
	 */
	public static JButton getDefaultButton(JButton button, Color c, boolean isBorderPainted) {
		button.setOpaque(true);
		button.setBorderPainted(isBorderPainted);
		button.setFocusPainted(false);
		button.setBackground(c);
		return button;
	}
	
	/**매개변수로 넣은 버튼에 여러가지 서식을 입힙니다. 버튼에 서식 입히기 귀찮아서 만들었다고 합니다.
	 * @author Coalery ( 김현우 )
	 * @param button : 서식을 적용할 버튼
	 * @param c : 서식을 모두 적용하고 버튼에 입힐 색깔
	 * @param f : 버튼의 텍스트 폰트 지정
	 * @param isBorderPainted : 버튼의 외곽선 여부
	 * @return 서식을 모두 적용한 버튼을 리턴한다.
	 * @see javax.swing.JButton
	 */
	public static JButton getDefaultButton(JButton button, Color c, Font f, boolean isBorderPainted) {
		button.setOpaque(true);
		button.setBorderPainted(isBorderPainted);
		button.setFocusPainted(false);
		button.setFont(f);
		button.setBackground(c);
		return button;
	}
    
}
