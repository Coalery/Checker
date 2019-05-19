import java.awt.Color;
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

@SuppressWarnings("serial")
public class P_PanelParent extends JPanel {
	
	protected JTextField number;
	protected JTextField name;
	
	public P_PanelParent() {
		// <DefaultSetting>
		setLayout(null);
		// </DefaulTSetting>
		
		// <Define>
		JLabel number_L = new JLabel("학번 : ");
		number = new JTextField();
		JButton numberConfirm = Util.getDefaultButton(new JButton("검색"), Color.WHITE, true);
		
		JLabel name_L = new JLabel("이름 : ");
		name = new JTextField();
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
		number_L.setLocation(20, 20);
		name_L.setSize(60, 30);
		name_L.setLocation(20, 60);
		// </SettingLabel>
		
		// <SettingComponents>
		number.setSize(200, 30);
		number.setLocation(90, 20);
		numberConfirm.setSize(60, 29);
		numberConfirm.setLocation(310, 20);
		name.setSize(200, 30);
		name.setLocation(90, 60);
		// </SettingComponents>
		
		// <AddToPanel>
		add(number_L); add(number); add(numberConfirm);
		add(name_L); add(name);
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