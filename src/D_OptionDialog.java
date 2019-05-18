import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class D_OptionDialog extends JDialog {
	
	private final int LAYOUT_PANEL_OPTION_COUNT = 10;
	private final int OPTION_PANEL_COUNT = 2;
	
	private JPanel[] optionPanels;
//	private int selectIndex;
	
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
		
		// <LayoutPathPanel>
		optionPanels[0] = new JPanel();
		optionPanels[0].setSize(524, 272);
		optionPanels[0].setLocation(100, 0);
		
		optionPanels[1] = new JPanel();
		optionPanels[1].setSize(524, 272);
		optionPanels[1].setLocation(100, 0);
		// </LayoutPathPanel>
		
		// <OptionPanel_0>
		
		// </OptionPanel_0>
		
		// <optionPanel_1>
		optionPanels[1].setLayout(null);
		
		JLabel[] labels = new JLabel[LAYOUT_PANEL_OPTION_COUNT];
		JTextField[] locFields = new JTextField[LAYOUT_PANEL_OPTION_COUNT];
		String[] labelName = {"결석계", "학습 계획서", "학부모 동의서", "학습 결과 보고서", "lay4", "lay5", "lay6", "lay7", "lay8", "lay9"};
		
		for(int i=0; i<LAYOUT_PANEL_OPTION_COUNT; i++) {
			labels[i] = new JLabel(labelName[i]);
			labels[i].setSize(140, 20);
			labels[i].setLocation(30, 10 + 25*i);
			
			locFields[i] = new JTextField();
			locFields[i].setSize(250, 20);
			locFields[i].setLocation(150, 10 + 25*i);
			locFields[i].setEditable(false);
			
			String path = Util.getConfig("lay" + i + "_LOCATION");
			if(path != null)
				locFields[i].setText(path);
			
			JButton button = new JButton("찾기");
			button.setSize(60, 20);
			button.setLocation(430, 10 + 25*i);
			
			button.setFocusPainted(false);
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.addActionListener(new LO_Listener("lay" + i, locFields[i]));
			
			optionPanels[1].add(labels[i]);
			optionPanels[1].add(locFields[i]);
			optionPanels[1].add(button);
		}
		// </optionPanel_1>
		optionPanels[0].setVisible(true);
		optionPanels[1].setVisible(false);
		
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
	
	/**
	 * @author Coalery (김현우)
	 * 기능 : 옵션 - 레이아웃 부분에서 추가되는 버튼들에 추가될 리스너의 기능을 구현함. 
	 */
	private class LO_Listener implements ActionListener {
		
		private String targetName;
		private JTextField text;
		
		public LO_Listener(String targetName, JTextField text) {
			this.targetName = targetName;
			this.text = text;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String path = Util.getPathByFileDialog();
			if(path == null)
				return;
			text.setText(path);
			Util.changeConfig(targetName + "_LOCATION", text.getText());
		}
		
	}
	
}
