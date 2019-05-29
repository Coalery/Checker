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
	
	private final int LAYOUT_PANEL_OPTION_COUNT = 6;
	private final int OPTION_PANEL_COUNT = 2;
	
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
		
		JLabel working = new JLabel("< 공사중 >");
		
		working.setSize(60, 20);
		working.setLocation(232, 126);
		
		optionPanels[0].add(working);
		// </OptionPanel_0>
		
		// <optionPanel_1>
		optionPanels[1].setLayout(null);
		
		JPanel[] structs = new JPanel[LAYOUT_PANEL_OPTION_COUNT];
		String[] labelName = {"결석계", "학습 계획서", "학부모 동의서", "학습 결과 보고서", "결석계", "출결 인정 확인서"};
		
		for(int i=0; i<LAYOUT_PANEL_OPTION_COUNT; i++) {
			structs[i] = new JPanel();
			structs[i].setLayout(null);
			structs[i].setSize(500, 20);
			
			JLabel label = new JLabel(labelName[i]);
			label.setSize(140, 20);
			label.setLocation(0, 0);
			
			JTextField locField = new JTextField();
			locField.setSize(250, 20);
			locField.setLocation(150, 0);
			locField.setEditable(false);
			
			String path = Util.getConfig("lay" + i + "_LOCATION");
			if(path != null)
				locField.setText(path);
			
			JButton button = new JButton("찾기");
			button.setSize(60, 20);
			button.setLocation(420, 0);
			
			button.setFocusPainted(false);
			button.setOpaque(true);
			button.setBackground(Color.WHITE);
			button.addActionListener(new LO_Listener("lay" + i, locField));
			
			structs[i].add(label);
			structs[i].add(locField);
			structs[i].add(button);
		}
		
		JPanel firstOption = new JPanel();
		firstOption.setLayout(null);
		firstOption.setBorder(BorderFactory.createTitledBorder("현장체험"));
		firstOption.setSize(515, 110);
		firstOption.setLocation(5, 5);
		
		for(int i=0; i<4; i++) {
			structs[i].setSize(490, 20);
			structs[i].setLocation(17, 15 + i*22);
			firstOption.add(structs[i]);
		}
		optionPanels[1].add(firstOption);
		
		JPanel secondOption = new JPanel();
		secondOption.setLayout(null);
		secondOption.setBorder(BorderFactory.createTitledBorder("결석"));
		secondOption.setSize(515, 43);
		secondOption.setLocation(5, 124);
		
		structs[4].setSize(490, 20);
		structs[4].setLocation(17, 15);
		secondOption.add(structs[4]);
		
		optionPanels[1].add(secondOption);
		
		JPanel thirdOption = new JPanel();
		thirdOption.setLayout(null);
		thirdOption.setBorder(BorderFactory.createTitledBorder("조퇴·결과·지각"));
		thirdOption.setSize(515, 43);
		thirdOption.setLocation(5, 181);
		
		structs[5].setSize(490, 20);
		structs[5].setLocation(17, 15);
		thirdOption.add(structs[5]);
		
		optionPanels[1].add(thirdOption);
		
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
