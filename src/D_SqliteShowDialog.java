import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class D_SqliteShowDialog extends JDialog {
	
	private ArrayList<E_SqliteDataSet> dataList;
	private DefaultTableModel dtm;
	
	public D_SqliteShowDialog(JFrame parent) {
		super(parent, "데이터  보기", true);
		
		setPreferredSize(new Dimension(800, 500));
		
		JPanel upperPanel = new JPanel();
		upperPanel.setLayout(null);
		upperPanel.setPreferredSize(new Dimension(800, 30));
		
		JComboBox<String> combo = new JComboBox<String>(new String[] {"학번", "이름", "유형", "출력 날짜"});
		combo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		combo.setBackground(Color.white);
		combo.setSize(151, 30);
		combo.setLocation(0, 0);
		
		JTextField field = new JTextField();
		field.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		field.setSize(591, 30);
		field.setLocation(150, 0);
		
		JComboBox<String> typeCombo = new JComboBox<String>(new String[] {"현장체험학습", "결석", "조퇴·결과·지각"});
		typeCombo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		typeCombo.setBackground(Color.WHITE);
		typeCombo.setSize(591, 30);
		typeCombo.setLocation(150, 0);
		typeCombo.setVisible(false);
		
		T_CalendarTextField cfield = new T_CalendarTextField(parent);
		cfield.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		cfield.setSize(591, 30);
		cfield.setLocation(150, 0);
		cfield.setVisible(false);
		
		JButton find = (JButton)Util.getDefaultComponent(new JButton("찾기"), new Color(192, 192, 192), true);
		find.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		find.setSize(60, 30);
		find.setLocation(740, 0);
		
		combo.addItemListener(new ItemListener() {public void itemStateChanged(ItemEvent e) {
			String selected = (String)combo.getSelectedItem();
			if(selected.equals("유형")) {
				field.setVisible(false);
				typeCombo.setVisible(true);
				cfield.setVisible(false);			
			} else if(selected.equals("출력 날짜")) {
				field.setVisible(false);
				typeCombo.setVisible(false);
				cfield.setVisible(true);
			} else {
				field.setVisible(true);
				typeCombo.setVisible(false);
				cfield.setVisible(false);
			}
				
			field.setText("");
			typeCombo.setSelectedIndex(0);
			cfield.setText("");
		}});
		
		find.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			String selected = (String)combo.getSelectedItem();
			
			if(selected.equals("출력 날짜")) {
				if(cfield.getText().equals("")) {
					Util.showMessage("입력되지 않은 데이터가 있습니다.", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else if(!selected.equals("유형")) {
				if(field.getText().equals("")) {
					Util.showMessage("입력되지 않은 데이터가 있습니다.", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			
			String sql = "select * from log where ";
			if(selected.equals("학번"))
				sql += "number=" + field.getText();
			else if(selected.equals("이름"))
				sql += "name like '%" + field.getText() + "%'";
			else if(selected.equals("유형"))
				sql += "type='" + typeCombo.getSelectedItem() + "'";
			else if(selected.equals("출력 날짜")) {
				E_Calendar c = cfield.getOriginData();
				sql += "writeDate='" + (c.getYear() * 10000 + c.getMonth() * 100 + c.getDay()) + "'";
			}
			
			dataList = E_SqliteDBManager.getData(sql);
			while(dtm.getRowCount() > 0)
				dtm.removeRow(0);
			for(int i=0; i<dataList.size(); i++) {
				E_SqliteDataSet ds = dataList.get(i);
				dtm.addRow(new String[] {String.valueOf(ds.getNumber()), ds.getName(), ds.getType(), ds.getWriteDate().toString(), ds.getWriteTime().toString(), ds.getTeacher(), ds.getETC()});
			}
		}});
		
		upperPanel.add(combo);
		upperPanel.add(field);
		upperPanel.add(typeCombo);
		upperPanel.add(cfield);
		upperPanel.add(find);
		
		dtm = new DefaultTableModel(new String[][] {}, new String[] {"학번", "이름", "유형", "출력 날짜", "출력 시간", "선생님", "그 외"}) {
			public boolean isCellEditable(int row, int column) { return false; }
		};
		JTable table = new JTable(dtm);
		JScrollPane tableScroll = new JScrollPane(table);
		
		table.addMouseListener(new MouseAdapter() {public void mouseClicked(MouseEvent e) {
			if(e.getClickCount() == 2) {
				E_SqliteDataSet ds = dataList.get(table.getSelectedRow());
				
				JDialog detail = new JDialog(parent, "상세 정보", true);
				detail.setPreferredSize(new Dimension(300, 200));
				
				JPanel panel = new JPanel();
				panel.setLayout(null);
				
				ArrayList<String> keylist = new ArrayList<String>();
				HashMap<String, String> map = new HashMap<>();
				map.put("학번", String.valueOf(ds.getNumber())); keylist.add("학번");
				map.put("이름", ds.getName()); keylist.add("이름");
				map.put("유형", ds.getType()); keylist.add("유형");
				map.put("출력 날짜", ds.getWriteDate().toString()); keylist.add("출력 날짜");
				map.put("출력 시간", ds.getWriteTime().toString()); keylist.add("출력 시간");
				map.put("선생님", ds.getTeacher()); keylist.add("선생님");
				
				String[] etcSplit = ds.getETC().split(";");
				for(int i=0; i<etcSplit.length; i++) {
					String[] split = etcSplit[i].split(":");
					map.put(split[0], split[1]);
					keylist.add(split[0]);
				}
				
				for(int i=0; i<keylist.size(); i++) {
					String key = keylist.get(i);
					JLabel l0_1 = new JLabel(key + " : ");
					l0_1.setSize(100, 20);
					l0_1.setLocation(10, 10 + i*30);
					
					JLabel l0_2 = new JLabel(map.get(key));
					l0_2.setSize(300, 20);
					l0_2.setLocation(110, 10 + i*30);
					
					panel.add(l0_1);
					panel.add(l0_2);
				}
				
				panel.setPreferredSize(new Dimension(300, keylist.size() * 30 + 10));
				
				JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				
				detail.add(pane, "Center");
				detail.pack();
				detail.setVisible(true);
			}
		}});
		table.setToolTipText("더블 클릭시, 상세 정보가 보여집니다.");
		
		add(upperPanel, "North");
		add(tableScroll, "Center");
		
		pack();
		setResizable(false);
		setVisible(true);
	}
	
}
