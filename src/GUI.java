import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class GUI extends JFrame {
	public GUI(String title) {
		super(title);
		
		try {System.setErr(new PrintStream("./err.hc"));} catch (IOException e) {e.printStackTrace();}
		addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}});
		
		if(!(new File("./config.hc").exists())) {
			String teacherName = JOptionPane.showInputDialog("이 프로그램을 사용하시는 선생님의 성함을 입력해주세요.\n* 초기 한번만 하면 됩니다.\n* 입력하지 않으시면 프로그램 가동이 불가능합니다.");
			if(teacherName == null)
				System.exit(0);
			if(teacherName.equals(""))
				System.exit(0);
			
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try {
				fw = new FileWriter("./config.hc");
				bw = new BufferedWriter(fw);
				
				bw.write("teacher:" + teacherName);
			} catch(IOException e) {
				e.printStackTrace(System.err);
			} finally {
				if(bw != null)
					try {bw.close();} catch(IOException e) {e.printStackTrace(System.err);}
				if(fw != null)
					try {fw.close();} catch(IOException e) {e.printStackTrace(System.err);}
			}
		}
		
		add(new P_MainPanel(), "Center");
		
		// <Menu>
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("도구");
		JMenuItem toDataInputPanel = new JMenuItem("데이터 추가");
		toDataInputPanel.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			JDialog diPanel = new JDialog();
			diPanel.setTitle("데이터 추가");
			diPanel.setSize(P_DataInputPanel.PANEL_SIZE_X, P_DataInputPanel.PANEL_SIZE_Y);
			diPanel.add(new P_DataInputPanel(), "Center");
			diPanel.setModal(true);
			diPanel.setResizable(false);
			diPanel.setVisible(true);
		}});
		JMenuItem toDataShowPanel = new JMenuItem("데이터 수정");
		toDataShowPanel.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			JDialog dsPanel = new JDialog();
			dsPanel.setTitle("데이터 수정");
			dsPanel.setSize(P_DataShowPanel.PANEL_SIZE_X, P_DataShowPanel.PANEL_SIZE_Y);
			dsPanel.add(new P_DataShowPanel(), "Center");
			dsPanel.setModal(true);
			dsPanel.setResizable(false);
			dsPanel.pack();
			dsPanel.setVisible(true);
		}});
		
		menu.add(toDataInputPanel);
		menu.add(toDataShowPanel);
		menubar.add(menu);
		setJMenuBar(menubar);
		// </Menu>
		
		setSize(P_MainPanel.PANEL_SIZE_X, P_MainPanel.PANEL_SIZE_Y);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new GUI("Checker");
	}
}
