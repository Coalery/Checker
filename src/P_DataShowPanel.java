import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

@SuppressWarnings("serial")
public class P_DataShowPanel extends JPanel {
	
	public static final int PANEL_SIZE_X = 300;
	public static final int PANEL_SIZE_Y = 200;
	
	private String[][] rowData;
	
	public P_DataShowPanel() {
		setLayout(new BorderLayout());
		List<Student> students = Util.readData();
		students.sort(new Comparator<Student>() {
			public int compare(Student o1, Student o2) {
				if(o1.getNumber() > o2.getNumber()) return 1;
				else if(o1.getNumber() == o2.getNumber()) return 0;
				else return -1;
			}
		});
		
		String[] columnNames = {"학번", "이름"};
		rowData = new String[students.size()][2];
		
		for(int i=0; i<students.size(); i++) {
			rowData[i][0] = String.valueOf(students.get(i).getNumber());
			rowData[i][1] = students.get(i).getName();
		}
		
		JTable table = new JTable(rowData, columnNames);
		table.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
		table.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(!evt.getPropertyName().equals("ancestor") && evt.getNewValue() == null) {
					System.out.println("Hi");
					ArrayList<Student> list = new ArrayList<Student>();
					rowData[table.getSelectedRow()][table.getSelectedColumn()] = (String)table.getValueAt(table.getSelectedRow(), table.getSelectedColumn());
					for(int i=0; i<table.getRowCount(); i++)
						list.add(new Student(Integer.parseInt(table.getValueAt(i, 0).toString()), (String)table.getValueAt(i, 1)));
					Util.writeNewData(list);
				}
			}
		});
		
		JScrollPane scroll = new JScrollPane(table);
		
		add(scroll, "Center");
	}
}
