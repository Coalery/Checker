import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Util {
	
	public static void writeData(Student s) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			ArrayList<Student> input = readData();
			input.add(s);
			
			fw = new FileWriter("./data.hc");
			bw = new BufferedWriter(fw);
			
			String toWrite = "";
			for(int i=0; i<input.size(); i++)
				toWrite += input.get(i).toString() + (i == input.size()-1 ? "" : ";");
			bw.write(toWrite);
		} catch(IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(bw != null)
				try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fw != null)
				try { fw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
	}
	
	public static void writeNewData(ArrayList<Student> list) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter("./data.hc");
			bw = new BufferedWriter(fw);
			
			String toWrite = "";
			for(int i=0; i<list.size(); i++)
				toWrite += list.get(i).toString() + (i == list.size()-1 ? "" : ";");
			bw.write(toWrite);
		} catch(IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if(bw != null)
				try { bw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fw != null)
				try { fw.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
	}
	
	public static ArrayList<Student> readData() {
		ArrayList<Student> result = new ArrayList<Student>();
		FileReader fr = null;
		BufferedReader br = null;
		
		try {
			fr = new FileReader("./data.hc");
			br = new BufferedReader(fr);
			
			if(br.ready()) {
				String[] read = br.readLine().split(";");
				
				for(int i=0; i<read.length; i++)
					result.add(new Student(Integer.parseInt(read[i].split(":")[0]), read[i].split(":")[1]));
			}
		} catch (IOException e ) {
			e.printStackTrace(System.err);
		} finally {
			if(br != null)
				try { br.close(); } catch (IOException e) {e.printStackTrace(System.err);}
			if(fr != null)
				try { fr.close(); } catch (IOException e) {e.printStackTrace(System.err);}
		}
		
		return result;
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
					if(line.split(":")[0].equals(key)) {
						value = line.split(":")[1];
						break;
					}
				}
			}
		} catch (IOException e ) {
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
	
}
