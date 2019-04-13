
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class P_DataInputPanel extends JPanel {
	
	public static final int PANEL_SIZE_X = 310;
	public static final int PANEL_SIZE_Y = 200;
	
	private JTextField number;
	private JTextField name;
	
	public P_DataInputPanel() {
		setLayout(null);
		
		JLabel number_L = new JLabel("학번 : ");
		JLabel name_L = new JLabel("이름 : ");
		
		number = new JTextField();
		name = new JTextField();
		
		JButton submit = new JButton("입력");
		
		number_L.setSize(60, 30);
		number_L.setLocation(20, 20);
		
		number.setSize(200, 30);
		number.setLocation(80, 20);
		
		name_L.setSize(60, 30);
		name_L.setLocation(20, 60);
		
		name.setSize(200, 30);
		name.setLocation(80, 60);
		
		submit.setSize(100, 40);
		submit.setLocation(105, 110);
		
		number.addKeyListener(new Adapt());
		name.addKeyListener(new Adapt());
		
		submit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			submit();
		}});
		
		add(number_L); add(name_L);
		add(number); add(name);
		add(submit);
	}
	
	private void submit() {
		String i_name = name.getText();
		String i_number = number.getText();
		int i_number_int = -1;
		
		if(i_name.length() == 0 || i_number.length() == 0) {
			Util.showMessage("학번이나 이름을 입력하지 않았습니다.", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			i_number_int = Integer.parseInt(i_number);
		} catch (NumberFormatException e) {
			Util.showMessage("학번을 숫자로 입력해주세요.", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace(System.err);
			return;
		}
		
		Student s = new Student(i_number_int, i_name);
		Util.writeData(s);
		number.setText("");
		name.setText("");
		
		Util.showMessage("입력 완료 : " + s, JOptionPane.INFORMATION_MESSAGE);
	}
	
	class Adapt extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				submit();
		}
	}
	
}
