import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class D_OptionDialog extends JDialog {
	
	private final int OPTION_PANEL_COUNT = 2;
	
	private JPanel[] optionPanels;
	
	public D_OptionDialog(JFrame owner, boolean visible) {
		super(owner, "옵션", true);
		
		final Font defaultFont = new Font("돋움", Font.PLAIN, 13);
		
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
		teacherL.setFont(defaultFont);
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
		
		// <OptionPanel_1>
		optionPanels[1].setLayout(null);
		
		Util.createLayoutFile();
		
		String[] labels = {"현장체험 결석계 : ", "현장체험 계획서 : ", "현장체험 동의서 : ", "현장체험 보고서 : ", "결석계 : ", "출결인정 확인서 : "};
		int[] vals = {11, 12, 13, 14, 21, 31};
		
		for(int i=0; i<6; i++) {
			JLabel layoutL = new JLabel(labels[i]);
			layoutL.setFont(defaultFont);
			layoutL.setSize(100, 20);
			layoutL.setLocation(15, 10 + 30 * i);
			
			final JTextField layout = new JTextField(Util.getConfig("layout" + vals[i] + "Path"));
			layout.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			layout.setSize(280, 20);
			layout.setLocation(125, 10 + 30 * i);
			layout.setEditable(false);
			
			JButton layoutpath = (JButton)Util.getDefaultComponent(new JButton("경로"), Color.WHITE, true);
			layoutpath.setFont(defaultFont);
			layoutpath.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			layoutpath.setSize(50, 20);
			layoutpath.setLocation(410, 10 + 30 * i);
			layoutpath.addActionListener(new LayoutPathListener(vals[i], layout));
			
			JButton layoutedit = (JButton)Util.getDefaultComponent(new JButton("수정"), Color.WHITE, true);
			layoutedit.setFont(defaultFont);
			layoutedit.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			layoutedit.setSize(50, 20);
			layoutedit.setLocation(465, 10 + 30 * i);
			layoutedit.addActionListener(new LayoutEditListener(vals[i]));
			
			optionPanels[1].add(layoutL);
			optionPanels[1].add(layout);
			optionPanels[1].add(layoutpath);
			optionPanels[1].add(layoutedit);
		}
		// </OptionPanel_1>
		
		optionPanels[0].setVisible(true);
		optionPanels[1].setVisible(false);
		
		add(selectOptionPanel);
		
		for(int i=0; i<optionPanels.length; i++)
			add(optionPanels[i]);
		
		setResizable(false);
		setVisible(visible);
	}
	
	private class LayoutPathListener implements ActionListener {
		
		private int index;
		private JTextField apply;
		
		private LayoutPathListener(int index, JTextField apply) {
			this.index = index;
			this.apply = apply;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String path = Util.getPathByFileDialog();
			if(path == null) return;
			
			Util.changeConfig("layout" + index + "Path", path);
			apply.setText(path);
		}
	}
	
	private class LayoutEditListener implements ActionListener {
		
		private int index;
		private LayoutEditListener(int index) { this.index = index; }
		
		@Override
		public void actionPerformed(ActionEvent event) {
			String layoutPath = Util.getConfig("layout" + index + "Path");
			
			if(new File(layoutPath).exists())
				new D_LayoutEditorDialog(D_OptionDialog.this, layoutPath);
			else
				Util.showMessage("서식 파일이 존재하지 않습니다.\n옵션을 다시 열어주세요.", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private class OF_AcListener implements ActionListener {
		
		private int index;
		private OF_AcListener(int index) { this.index = index; }
		
		@Override
		public void actionPerformed(ActionEvent event) {
			for(int i=0; i<OPTION_PANEL_COUNT; i++)
				if(i != index)
					optionPanels[i].setVisible(false);
			optionPanels[index].setVisible(true);
		}
	}
	
}
