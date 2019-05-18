import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

@SuppressWarnings("serial")
public class _MainPanel extends JPanel {
	
	public static final int PANEL_SIZE_X = 400;
	public static final int PANEL_SIZE_Y = 500;
	
	private JComboBox<String> type;
	private JTextField number;
	private JTextField name;
	private JTextArea reason;
	private JComboBox<String> time_F;
	private JComboBox<String> time_S;
	
	public _MainPanel() {
		setLayout(null);
		String[] type_items = {"조퇴증", "외출증", "보건실 입실증"};
		String[] time_items = {"없음", "1교시", "2교시", "3교시", "4교시", "5교시", "6교시", "7교시"};
		
		// <Define>
		JLabel type_L = new JLabel("종류 : ");
		type = new JComboBox<String>(type_items);
		
		JLabel number_L = new JLabel("학번 : ");
		number = new JTextField();
		JButton numberConfirm = new JButton("검색");
		
		JLabel name_L = new JLabel("이름 : ");
		name = new JTextField();
		
		JLabel reason_L = new JLabel("사유 : ");
		reason = new JTextArea();
		JScrollPane reason_sp = new JScrollPane(reason, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		JLabel time_L = new JLabel("시간 : ");
		JLabel time_D = new JLabel("");
		time_F = new JComboBox<String>(time_items);
		time_S = new JComboBox<String>(time_items);
		
		JLabel teacher_L = new JLabel("선생님 : ");
		JLabel teacher = new JLabel(Util.getConfig("teacher"));
		
		JButton print = new JButton("인쇄");
		// </Define>
		
		// <SettingOthers>
		name.setToolTipText("학번을 입력하면 자동으로 입력됩니다.");
		name.setEditable(false);
		name.setFocusable(false);
		number.addKeyListener(new KeyAdapter() {public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				numberConfirm();
			else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				name.setText("");
				number.setText("");
			}
		}});
		numberConfirm.setFocusable(false);
		numberConfirm.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			numberConfirm();
		}});
		Calendar c = Calendar.getInstance();
		time_D.setText((c.get(Calendar.MONTH)+1) + "월 " + c.get(Calendar.DAY_OF_MONTH) + "일");
		print.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			if(number.getText().equals("") || name.getText().equals("") || reason.getText().equals("")) {
				Util.showMessage("아직 입력하지 않은 정보가 있습니다.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JTextPane origin = new JTextPane();
			origin.setContentType("text/html; chraset=UTF-8");
			
			InputStreamReader fr = null;
			BufferedReader br = null;
			
			try {
				fr = new InputStreamReader(new FileInputStream("./style.html"), "UTF-8");
				br = new BufferedReader(fr);
				
				Calendar c = Calendar.getInstance();
				StringBuilder builder = new StringBuilder();
				String line;
				while((line = br.readLine()) != null)
					builder.append(line.replace("$type", type.getItemAt(type.getSelectedIndex()))
							.replace("$number", number.getText())
							.replace("$name", name.getText())
							.replace("$reason", reason.getText())
							.replace("$time", String.format("%s %s ~ %s", time_D.getText(), time_F.getItemAt(time_F.getSelectedIndex()), time_S.getItemAt(time_S.getSelectedIndex())))
							.replace("$date", String.format("%04d. %02d. %02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)))
							.replace("$teacher", teacher.getText()));
				origin.setText(builder.toString());
				
			} catch (IOException ex) {
				ex.printStackTrace(System.err);
				return;
			} finally {
				if(br != null)
					try { br.close(); } catch(IOException exc) {exc.printStackTrace(System.err);}
				if(fr != null)
					try { fr.close(); } catch(IOException exc) {exc.printStackTrace(System.err);}
			}
			
			JDialog preview = new JDialog();
			preview.setModal(true);
			preview.setSize(700, 600);
			preview.setLayout(new BorderLayout());
			preview.setTitle("인쇄 미리보기");
			
			HashPrintRequestAttributeSet set = new HashPrintRequestAttributeSet();
			set.add(MediaSizeName.ISO_A4);
			set.add(OrientationRequested.PORTRAIT);
			PageFormat pf = PrinterJob.getPrinterJob().getPageFormat(set);
			
			final E_PrintPreview _preview = new E_PrintPreview(origin.getPrintable(null, null), pf);
			
			JButton printButton = new JButton("인쇄");
			printButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
				_preview.print();
				Util.log(String.format("type:%s; number:%s; name:%s; reason:%s; time:%s; date:%s; teacher:%s;",
						type.getItemAt(type.getSelectedIndex()),
						number.getText(),
						name.getText(),
						reason.getText(),
						String.format("%s %s ~ %s", time_D.getText(), time_F.getItemAt(time_F.getSelectedIndex()), time_S.getItemAt(time_S.getSelectedIndex())),
						String.format("%04d. %02d. %02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH)),
						teacher.getText()));
			}});
			
			JPanel buttonsPanel = new JPanel();
			buttonsPanel.add(printButton);
			
			preview.getContentPane().add(_preview, BorderLayout.CENTER);
			preview.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);
			
			preview.setResizable(false);
			preview.setVisible(true);
		}});
		// </SettingOthers>
		
		// <SettingLabel>
		type_L.setSize(60, 30);
		type_L.setLocation(20, 20);
		number_L.setSize(60, 30);
		number_L.setLocation(20, 60);
		name_L.setSize(60, 30);
		name_L.setLocation(20, 100);
		reason_L.setSize(60, 30);
		reason_L.setLocation(20, 140);
		time_L.setSize(60, 30);
		time_L.setLocation(20, 290);
		time_D.setSize(80, 30);
		time_D.setLocation(80, 290);
		teacher_L.setSize(60, 30);
		teacher_L.setLocation(20, 330);
		teacher.setSize(200, 30);
		teacher.setLocation(80, 330);
		// </SettingLabel>
		
		// <SettingComponents>
		type.setSize(200, 30);
		type.setLocation(80, 20);
		number.setSize(200, 30);
		number.setLocation(80, 60);
		numberConfirm.setSize(60, 30);
		numberConfirm.setLocation(300, 60);
		name.setSize(200, 30);
		name.setLocation(80, 100);
		reason_sp.setSize(360, 100);
		reason_sp.setLocation(20, 170);
		time_F.setSize(80, 30);
		time_F.setLocation(160, 290);
		time_S.setSize(80, 30);
		time_S.setLocation(250, 290);
		print.setSize(100, 40);
		print.setLocation(140, 380);
		// </SettingComponents>
		
		// <AddToPanel>
		add(type_L); add(type);
		add(number_L); add(number); add(numberConfirm);
		add(name_L); add(name);
		add(reason_L); add(reason_sp);
		add(time_L); add(time_D); add(time_F); add(time_S);
		add(teacher_L); add(teacher);
		add(print);
		// </AddToPanel>
	}
	
	private void numberConfirm() {
		int inpNumber = -1;
		if(number.getText().length() == 0) {
			Util.showMessage("학번을 입력하지 않았습니다.", JOptionPane.ERROR_MESSAGE);
			name.setText("");
			return;
		}
		try {
			inpNumber = Integer.parseInt(number.getText());
		} catch(NumberFormatException e) {
			Util.showMessage("학번을 숫자로 입력해주세요.", JOptionPane.ERROR_MESSAGE);
			number.setText("");
			name.setText("");
			e.printStackTrace(System.err);
			return;
		}
		ArrayList<E_Student> students = Util.readData();
		int i;
		for(i=0; i<students.size(); i++)
			if(students.get(i).getNumber() == inpNumber) {
				name.setText(students.get(i).getName());
				break;
			}
		if(i == students.size()) {
			Util.showMessage("정보가 존재하지 않습니다.", JOptionPane.ERROR_MESSAGE);
			number.setText("");
			name.setText("");
		}
	}
}
