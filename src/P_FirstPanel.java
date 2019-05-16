import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Coalery ( 김현우 )
 * 기능 : StartFrame 에서 현장체험을 누를시에 나올 Panel 을 구성설정하는 클래스
 * 
 */

@SuppressWarnings("serial")
public class P_FirstPanel extends JPanel {
	
	private JTextField number;
	private JTextField name;
	private JTextField absence;
	
	public P_FirstPanel() {
		// <DefaultSetting>
		setLayout(null);
		// </DefaultSetting>
		
		// <Define>
		JLabel number_L = new JLabel("학번 : ");
		number = new JTextField();
		JButton numberConfirm = new JButton("검색");
		
		JLabel name_L = new JLabel("이름 : ");
		name = new JTextField();
		
		JLabel absence_L = new JLabel("결석 : ");
		absence = new JTextField();
		
//		JLabel teacher_L = new JLabel("선생님 : ");
//		JLabel teacher = new JLabel(Util.getConfig("teacher"));
		// </Define>
		
		// <Setting>
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
		
		// </Setting>
		
		// <SettingLabel>
		number_L.setSize(60, 30);
		number_L.setLocation(20, 60);
		name_L.setSize(60, 30);
		name_L.setLocation(20, 100);
		absence_L.setSize(60, 30);
		absence_L.setLocation(20, 140);
		// </SettingLabel>
		
		// <SettingComponents>
		number.setSize(200, 30);
		number.setLocation(80, 60);
		numberConfirm.setSize(60, 30);
		numberConfirm.setLocation(300, 60);
		name.setSize(200, 30);
		name.setLocation(80, 100);
		absence.setSize(200, 30);
		absence.setLocation(80, 140);
		// </SettingComponents>
		
		// <AddToPanel>
		add(number_L); add(number);
		add(name_L); add(name);
		add(absence_L); add(absence);
		add(numberConfirm);
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
		ArrayList<ETC_Student> students = Util.readData();
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
