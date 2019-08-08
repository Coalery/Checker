import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class D_OptionDialog extends JDialog {
	
	private final int OPTION_PANEL_COUNT = 1;
	
	private JPanel[] optionPanels;
	
	public D_OptionDialog(JFrame owner, boolean visible) {
		super(owner, "옵션", true);
		addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent event) {
			dispose();
			setVisible(false);
		}});
		setSize(630, 301);
		setLayout(null);
		
		optionPanels = new JPanel[OPTION_PANEL_COUNT];
		
		// <SelectOptionPanel>
		JPanel selectOptionPanel = new JPanel();
		selectOptionPanel.setLayout(new GridLayout(10, 1));
		selectOptionPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 0, new Color(220, 220, 220)));
		
		JButton[] selects = new JButton[OPTION_PANEL_COUNT];
		String[] names = {"일반", "서식 파일"};
		
		for(int i=0; i<OPTION_PANEL_COUNT; i++) {
			selects[i] = new JButton(names[i]);
			selects[i].setFocusPainted(false);
			selects[i].setOpaque(true);
			selects[i].setBackground(new Color(200, 200, 200));
			selects[i].addActionListener(new OF_AcListener(i));
			selectOptionPanel.add(selects[i]);
		}
		
		selectOptionPanel.setSize(100, 272);
		selectOptionPanel.setLocation(0, 0);
		// </SelectOptionPanel>
		
		// <OptionPanelAll>
		for(int i=0; i<optionPanels.length; i++) {
			optionPanels[i] = new JPanel();
			optionPanels[i].setSize(524, 272);
			optionPanels[i].setLocation(100, 0);
			optionPanels[i].setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
		}
		// </OptionPanelAll>
		
		// <OptionPanel_0>
		optionPanels[0].setLayout(null);
		
		JLabel teacherL = new JLabel("선생님 성함 : ");
		teacherL.setSize(80, 20);
		teacherL.setLocation(15, 10);
		
		final JTextField teacher = new JTextField(Util.getConfig("teacher"));
		teacher.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teacher.setSize(340, 20);
		teacher.setLocation(105, 10);
		
		JButton teacherApply = (JButton)Util.getDefaultComponent(new JButton("적용"), Color.WHITE, true);
		teacherApply.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		teacherApply.setSize(50, 20);
		teacherApply.setLocation(460, 10);
		
		teacherApply.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			Util.changeConfig("teacher", teacher.getText());
			F_StartFrame.refresh();
		}});
		
		optionPanels[0].add(teacherL);
		optionPanels[0].add(teacher);
		optionPanels[0].add(teacherApply);
		// </OptionPanel_0>
		
		optionPanels[0].setVisible(true);
		
		add(selectOptionPanel);
		
		for(int i=0; i<optionPanels.length; i++)
			add(optionPanels[i]);
		
		setResizable(false);
		setVisible(visible);
	}
	
	private class OF_AcListener implements ActionListener {
		
		private int index;
		
		private OF_AcListener(int index) {
			this.index = index;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			for(int i=0; i<OPTION_PANEL_COUNT; i++)
				if(i != index)
					optionPanels[i].setVisible(false);
			optionPanels[index].setVisible(true);
//			selectIndex = index;
		}
		
	}
	
}
