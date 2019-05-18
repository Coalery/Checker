import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class T_CalendarTextField extends JTextField {
	
	private String originText;
	
	public T_CalendarTextField(JFrame owner) {
		addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {new D_CalendarDialog(owner, T_CalendarTextField.this);}});
		addKeyListener(new KeyAdapter() {public void keyReleased(KeyEvent e) {setText(originText);}});
	}
	
	public void setOriginText(String originText) { this.originText = originText; }
	public String getOriginText() { return originText; }
	
}
