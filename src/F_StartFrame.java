import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class F_StartFrame extends JFrame {
	
	private GridBagConstraints c;
	private CardLayout card;
	
	private JPanel radioHeadPanel;
	private JPanel centerPanel;
	
	private static P_FirstPanel firstP;
	private static P_SecondPanel secondP;
	private static P_ThirdPanel thirdP;
	
	public F_StartFrame() {
		super("학급 행정 매니저");
		setSize(420, 600);
		
		try {System.setErr(new PrintStream("./err.hc"));} catch (IOException e) {e.printStackTrace();}
		addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent e) {System.exit(0);}});
		
		if(!(new File("./config.hc").exists()))
			Util.teacherNameDialog();
		if(Util.getConfig("teacher") == null)
			Util.teacherNameDialog();
		
		// <Menu>
		JMenuBar menubar = new JMenuBar();
		JMenu menu = new JMenu("도구");
		menu.setFont(new Font("굴림", Font.PLAIN, 13));
		
		JMenuItem modifyData = new JMenuItem("학생 데이터 수정");
		modifyData.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			Util.createDataFile();
			Util.execCommand("notepad.exe data.hc");
		}});
		modifyData.setFont(new Font("굴림", Font.PLAIN, 13));
		
		JMenuItem data = new JMenuItem("출력 데이터 보기");
		data.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {new D_SqliteShowDialog(F_StartFrame.this);}});
		data.setFont(new Font("굴림", Font.PLAIN, 13));
		
		JMenuItem option = new JMenuItem("옵션");
		option.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {new D_OptionDialog(F_StartFrame.this, true);}});
		option.setFont(new Font("굴림", Font.PLAIN, 13));
		
		menu.add(modifyData);
		menu.add(data);
		menu.addSeparator();
		menu.add(option);
		
		menubar.add(menu);
		setJMenuBar(menubar);
		// </Menu>
		
		radioHeadPanel = new JPanel();
		ButtonGroup rbGroup = new ButtonGroup();
		JRadioButton rb1 = new JRadioButton("현장체험");
		JRadioButton rb2 = new JRadioButton("결석");
		JRadioButton rb3 = new JRadioButton("조퇴·결과·지각");
		rb1.setSelected(true);
		
		rb1.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) { card.show(centerPanel, "First"); firstP.initPanel(); }});
		rb2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) { card.show(centerPanel, "Second"); secondP.initPanel(); }});
		rb3.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) { card.show(centerPanel, "Third"); thirdP.initPanel(); }});
		
		c = new GridBagConstraints();
		c.weightx = 1.0;
		c.weighty = 1.0;
		
		c.fill = GridBagConstraints.BOTH;
		
		layoutGridBag(rb1, 0, 0, 1, 1);
		layoutGridBag(rb2, 0, 1, 1, 1);
		layoutGridBag(rb3, 0, 2, 2, 1);
		
		radioHeadPanel.setLayout(new GridBagLayout());
		rbGroup.add(rb1); rbGroup.add(rb2); rbGroup.add(rb3);
		
		radioHeadPanel.setPreferredSize(new Dimension(radioHeadPanel.getPreferredSize().width, 50));
		
		radioHeadPanel.setBorder(BorderFactory.createMatteBorder(6, 6, 6, 6, new Color(200, 200, 200)));
		
		add(radioHeadPanel, "North");
		
		centerPanel = new JPanel();
		card = new CardLayout();
		centerPanel.setLayout(card);
		centerPanel.setBorder(BorderFactory.createMatteBorder(0, 6, 6, 6, new Color(200, 200, 200)));
		
		add(centerPanel, "Center");
		
		firstP = new P_FirstPanel(F_StartFrame.this);
		secondP = new P_SecondPanel(F_StartFrame.this);
		thirdP = new P_ThirdPanel(F_StartFrame.this);
		
		centerPanel.add("First", firstP);
		centerPanel.add("Second", secondP);
		centerPanel.add("Third", thirdP);
		
		setResizable(false);
		setVisible(true);
	}
	
	public void layoutGridBag(Component obj, int x, int y, int width, int height) {
		c.gridx = x;
		c.gridy = y;
		c.gridwidth = width;
		c.gridheight = height;
		radioHeadPanel.add(obj, c);
	}
	
	public static void refresh() {
		firstP.initPanel();
		secondP.initPanel();
		thirdP.initPanel();
	}
	
	public static void main(String[] args) {
		new F_StartFrame();
	}
	
}