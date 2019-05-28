import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JToggleButton;

public class Util {
	
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
	 * @param button : 서식을 적용할 컴포넌트
	 * @param c : 컴포넌트에 입힐 색깔
	 * @param isBorderPainted : 컴포넌트의 외곽선 여부
	 * @return 서식을 모두 적용한 컴포넌트를 리턴한다. 맞지 않는 컴포넌트인 경우 null 을 반환합니다.
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
	
	public static void showPrintPreview(StringBuilder[] builder, String logContent) {
		JDialog preview = new JDialog();
		preview.setModal(true);
		preview.setSize(700, 600);
		preview.setLayout(new BorderLayout());
		preview.setTitle("인쇄 미리보기");
		
		final E_PrintPreview[] _previews = new E_PrintPreview[builder.length];
		final JPanel centerPanel = new JPanel();
		final CardLayout card = new CardLayout();
		centerPanel.setLayout(card);
		
		for(int i=0; i<_previews.length; i++) {
			JTextPane origin = new JTextPane();
			origin.setContentType("text/html; charset=UTF-8");
			origin.setText(builder[i].toString());
			
			HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
			set.add(MediaSizeName.ISO_A4);
			set.add(OrientationRequested.PORTRAIT);
			PageFormat pf = PrinterJob.getPrinterJob().getPageFormat(set);
			
			_previews[i] = new E_PrintPreview(origin.getPrintable(null, null), pf);
			centerPanel.add(String.valueOf(i), _previews[i]);
		}
		
		JButton printButton = (JButton)Util.getDefaultComponent(new JButton("인쇄"), Color.WHITE, true);
		printButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			for(int i=0; i<_previews.length; i++)
				_previews[i].print();
			Util.log(logContent);
		}});
		
		JButton previous = (JButton) Util.getDefaultComponent(new JButton("<<"), Color.WHITE, true);
		previous.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			card.previous(centerPanel);
		}});
		
		JButton next = (JButton) Util.getDefaultComponent(new JButton(">>"), Color.WHITE, true);
		next.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			card.next(centerPanel);
		}});
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(previous);
		buttonsPanel.add(next);
		buttonsPanel.add(printButton);
		
		preview.getContentPane().add(centerPanel, "Center");
		preview.getContentPane().add(buttonsPanel, "South");
		
		preview.setResizable(false);
		preview.setVisible(true);
	}
	
}
