import java.awt.FileDialog;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Util {
	
	public static ArrayList<ETC_Student> readData() {
		ArrayList<ETC_Student> result = new ArrayList<ETC_Student>();
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
					try { result.add(new ETC_Student(Integer.parseInt(read[i].split(":")[0]), read[i].split(":")[1])); } catch (NumberFormatException ex) {}
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
		
		try {
			String config = getConfig(key);
			if(config == null) {
				bw = new BufferedWriter(new FileWriter("./config.hc", true));
				bw.newLine();
				bw.append(key + "=" + value);
			} else {
				FileReader fr = null;
				BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				
				try {
					fr = new FileReader("./config.hc");
					br = new BufferedReader(fr);
					
					String line;
					while((line = br.readLine()) != null)
						sb.append(line);
				} catch (IOException e) {
					e.printStackTrace(System.err);
				} finally {
					if(br != null)
						try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
					if(fr != null)
						try { fr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
				}
				bw = new BufferedWriter(new FileWriter("./config.hc"));
				bw.write(sb.toString().replace(config, value));
			}
			
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(bw != null)
				try { bw.close(); } catch (IOException e) { e.printStackTrace(System.err); }
		}
	}
	
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
	
	public static void showMessage(String message, int type) {
		JFrame tmp = new JFrame();
		JOptionPane.showMessageDialog(tmp, message, "알림", type);
		tmp.dispose();
	}
	
	public static String getPathByFileDialog() {
		JFrame f = new JFrame();
		FileDialog fd = new FileDialog(f, "파일 선택");
		fd.setVisible(true);
		f.dispose();
		
		if(fd.getDirectory() == null || fd.getFile() == null)
			return null;
		
		return fd.getDirectory() + fd.getFile(); 
	}
	
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
	
}
