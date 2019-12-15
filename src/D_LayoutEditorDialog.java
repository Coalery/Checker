import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

@SuppressWarnings("serial")
public class D_LayoutEditorDialog extends JDialog {
	
	private final int A4_WIDTH = 210;
	private final int A4_HEIGHT = 297;
	
	private final float A4_WEIGHT = 2.74f;
	
	private JPanel field;
	
	private HashMap<Integer, TableCellEditor> editors;
	private ArrayList<O_HCObject> objectList;
	private O_HCObject selectedObject;
	
	private JPanel selectFrame;
	private JLabel currentSelect;
	private DefaultTableModel dtm;
	
	private String filePath;
	
	private Stack<J_Job> jobStack;
	private Stack<J_Job> nextStack;
	
	public D_LayoutEditorDialog(JDialog parent, String filePath) {
		super(parent, "서식 에디터", true);
		
		this.filePath = filePath;
		jobStack = new Stack<J_Job>();
		nextStack = new Stack<J_Job>();
		selectedObject = null;
		editors = new HashMap<Integer, TableCellEditor>();
		objectList = new ArrayList<O_HCObject>();
		selectFrame = new JPanel();
		selectFrame.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		selectFrame.setBackground(Color.WHITE);
		addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent event) {
			setVisible(false);
			dispose();
		}});
		addKeyListener(new ShortCutKeyAdapter());
		
		field = new JPanel();
		field.setPreferredSize(new Dimension((int)(A4_WIDTH * A4_WEIGHT), (int)(A4_HEIGHT * A4_WEIGHT)));
		field.setBackground(Color.WHITE);
		field.setLayout(null);
		
		JScrollPane fieldScroll = new JScrollPane(field);
		fieldScroll.setPreferredSize(new Dimension((int)(A4_WIDTH * A4_WEIGHT) + 18, 650));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.WHITE);
		buttonsPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.BLACK));
		buttonsPanel.setPreferredSize(new Dimension((int)(A4_WIDTH * A4_WEIGHT), 30));
		
		JButton create = new JButton("생성");
		create.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		create.setBackground(Color.WHITE);
		create.setSize(50, 24);
		create.setOpaque(false);
		create.setFocusPainted(false);
		create.setLocation(3, 3);
		create.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			O_HCObject object = new O_HCObject("Object" + String.format("%04d", (int)(Math.random() * 10000)));
			field.add(object);
			objectList.add(object);
			object.addMouseListener(new ObjectSelectAdapter(object));
			
			select(object);
			initTable();
			
			field.repaint();
			
			jobStack.push(new J_Job(J_Job.J_JobType.CREATE, new J_DefineJob(object)));
			nextStack.clear();
		}});
		create.setFocusable(false);
		create.addKeyListener(new ShortCutKeyAdapter());
		
		JButton save = new JButton("저장");
		save.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		save.setBackground(Color.GREEN);
		save.setSize(50, 24);
		save.setOpaque(false);
		save.setFocusPainted(false);
		save.setLocation(56, 3);
		save.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			unselect();
			save();
		}});
		save.setFocusable(false);
		save.addKeyListener(new ShortCutKeyAdapter());
		
		currentSelect = new JLabel("현재 선택 : 없음");
		currentSelect.setSize(250, 24);
		currentSelect.setLocation(200, 3);
		
		buttonsPanel.setLayout(null);
		buttonsPanel.add(create);
		buttonsPanel.add(save);
		buttonsPanel.add(currentSelect);
		
		dtm = new DefaultTableModel(new Object[][] {}, new String[] {"항목", "내용"}) {
			@Override
			public boolean isCellEditable(int row, int column) {
				if(column == 0)
					return false;
				if(selectedObject == null)
					return false;
				if(row == O_HCObject.Options.FONT.getValue() || row == O_HCObject.Options.TEXT_COLOR.getValue() || row == O_HCObject.Options.BORDER_COLOR.getValue() || row == O_HCObject.Options.BACKGROUND_COLOR.getValue())
					return false;
				if(selectedObject.getBorder() == null) 
					if(row == O_HCObject.Options.BORDER_THICK.getValue() || row == O_HCObject.Options.BORDER_COLOR.getValue())
						return false;
				return true;
			}
		};
		
		JTable fieldTable = new JTable(dtm) {
			public TableCellEditor getCellEditor(int row, int column) {
				int modelColumn = convertColumnIndexToModel(column);
				if(modelColumn == 1 && editors.containsKey(row))
					return editors.get(row);
				return super.getCellEditor(row, column);
			}
		};
		fieldTable.addKeyListener(new ShortCutKeyAdapter());
		fieldTable.addMouseListener(new MouseAdapter() {public void mouseClicked(MouseEvent event) {
			if(selectedObject == null)
				return;
			int row = fieldTable.rowAtPoint(event.getPoint());
			int col = fieldTable.columnAtPoint(event.getPoint());
			
			if(row == O_HCObject.Options.FONT.getValue() && col == 1) { // 폰트 수정
				new D_FontDialog(D_LayoutEditorDialog.this, selectedObject, new FontCallback() {
					@Override
					public void callback(Font f) {
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.FONT, selectedObject, selectedObject.getFont(), f)));
						nextStack.clear();
						selectedObject.setFont(f);
						initTable();
					}
				});
			} else if(row == O_HCObject.Options.TEXT_COLOR.getValue() && col == 1) {
				new D_ColorPicker(D_LayoutEditorDialog.this, selectedObject.getForeground(), new ColorCallback() {
					@Override
					public void callback(Color c) {
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.TEXT_COLOR, selectedObject, selectedObject.getForeground(), c)));
						nextStack.clear();
						selectedObject.setForeground(c);
						initTable();
					}
				});
			} else if(row == O_HCObject.Options.BORDER_COLOR.getValue() && col == 1) {
				if(selectedObject.getBorder() == null)
					return;
				new D_ColorPicker(D_LayoutEditorDialog.this, selectedObject.getBorderColor(), new ColorCallback() {
					@Override
					public void callback(Color c) {
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.BORDER_COLOR, selectedObject, selectedObject.getBorderColor(), c)));
						nextStack.clear();
						selectedObject.setBorderColor(c);
						initTable();
					}
				});
			} else if(row == O_HCObject.Options.BACKGROUND_COLOR.getValue() && col == 1) {
				new D_ColorPicker(D_LayoutEditorDialog.this, selectedObject.getBackground(), new ColorCallback() {
					@Override
					public void callback(Color c) {
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.BACKGROUND_COLOR, selectedObject, selectedObject.getBackground(), c)));
						nextStack.clear();
						selectedObject.setBackground(c);
						initTable();
					}
				});
			}
		}});
		
		DefaultListSelectionModel sm = new DefaultListSelectionModel();
		sm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		fieldTable.setSelectionModel(sm);
		fieldTable.getColumnModel().getColumn(0).setPreferredWidth(300);
		fieldTable.getColumnModel().getColumn(1).setPreferredWidth(700);
		
		new TableCellListener(fieldTable, new AbstractAction() {public void actionPerformed(ActionEvent e) {
			if(selectedObject == null)
				return;
			
			TableCellListener tcl = (TableCellListener)e.getSource();
			if(tcl.getOldValue().equals(tcl.getNewValue()) || tcl.getOldValue() == tcl.getNewValue())
	        	return;
	        
	        if(tcl.getRow() == O_HCObject.Options.NAME.getValue()) {
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.NAME, selectedObject, tcl.getOldValue(), tcl.getNewValue())));
	        	nextStack.clear();
	        	selectedObject.setHCName((String)tcl.getNewValue());
	        	currentSelect.setText("현재 선택 : " + tcl.getNewValue());
	        } else if(tcl.getRow() == O_HCObject.Options.POS_X.getValue()) {
	        	if(selectedObject.isFixShape()) {
	        		Util.showMessage("모양과 위치가 고정되어있습니다.\n수정을 하시려면 고정을 풀어주세요.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.POS_X.getValue(), 1);
	        		return;
	        	}
	        	int x = -1;
	        	try { x = Integer.parseInt((String)tcl.getNewValue()); } catch(NumberFormatException ex) {
	        		Util.showMessage("숫자만 입력할 수 있습니다.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.POS_X.getValue(), 1);
	        		return;
	        	}
	        	
	        	Point privLocation = new Point(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
	        	Point nextLocation = new Point(x, selectFrame.getLocation().y + 1);
	        	
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, selectedObject, privLocation, nextLocation)));
	        	nextStack.clear();
	        	
	        	dtm.setValueAt(x, O_HCObject.Options.POS_X.getValue(), 1);
	        	selectFrame.setLocation(x - 1, selectFrame.getLocation().y);
	        } else if(tcl.getRow() == O_HCObject.Options.POS_Y.getValue()) {
	        	if(selectedObject.isFixShape()) {
	        		Util.showMessage("모양과 위치가 고정되어있습니다.\n수정을 하시려면 고정을 풀어주세요.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.POS_X.getValue(), 1);
	        		return;
	        	}
	        	int y = -1;
	        	try { y = Integer.parseInt((String)tcl.getNewValue()); } catch(NumberFormatException ex) {
	        		Util.showMessage("숫자만 입력할 수 있습니다.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.POS_Y.getValue(), 1);
	        		return;
	        	}
	        	
	        	Point privLocation = new Point(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
	        	Point nextLocation = new Point(selectFrame.getLocation().x + 1, y);
	        	
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, selectedObject, privLocation, nextLocation)));
	        	nextStack.clear();
	        	
	        	dtm.setValueAt(y, O_HCObject.Options.POS_Y.getValue(), 1);
	        	selectFrame.setLocation(selectFrame.getLocation().x, y - 1);
	        } else if(tcl.getRow() == O_HCObject.Options.WIDTH.getValue()) {
	        	if(selectedObject.isFixShape()) {
	        		Util.showMessage("모양과 위치가 고정되어있습니다.\n수정을 하시려면 고정을 풀어주세요.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.POS_X.getValue(), 1);
	        		return;
	        	}
	        	int width = -1;
	        	try { width = Integer.parseInt((String)tcl.getNewValue()); } catch(NumberFormatException ex) {
	        		Util.showMessage("숫자만 입력할 수 있습니다.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.WIDTH.getValue(), 1);
	        		return;
	        	}
	        	
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.WIDTH, selectedObject, selectedObject.getSize().width, width)));
	        	nextStack.clear();
	        	
	        	dtm.setValueAt(width, O_HCObject.Options.WIDTH.getValue(), 1);
	        	selectFrame.setSize(width + 2, selectFrame.getSize().height);
	        	selectedObject.setSize(width, selectFrame.getSize().height - 2);
	        } else if(tcl.getRow() == O_HCObject.Options.HEIGHT.getValue()) {
	        	if(selectedObject.isFixShape()) {
	        		Util.showMessage("모양과 위치가 고정되어있습니다.\n수정을 하시려면 고정을 풀어주세요.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.POS_X.getValue(), 1);
	        		return;
	        	}
	        	int height = -1;
	        	try { height = Integer.parseInt((String)tcl.getNewValue()); } catch(NumberFormatException ex) {
	        		Util.showMessage("숫자만 입력할 수 있습니다.", JOptionPane.ERROR_MESSAGE);
	        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.HEIGHT.getValue(), 1);
	        		return;
	        	}
	        	
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.HEIGHT, selectedObject, selectedObject.getSize().height, height)));
	        	nextStack.clear();
	        	
	        	dtm.setValueAt(height, O_HCObject.Options.HEIGHT.getValue(), 1);
	        	selectFrame.setSize(selectFrame.getSize().width, height + 2);
	        	selectedObject.setSize(selectFrame.getSize().width - 2, height);
	        } else if(tcl.getRow() == O_HCObject.Options.TEXT.getValue()) {
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.TEXT, selectedObject, tcl.getOldValue(), tcl.getNewValue())));
	        	nextStack.clear();
	        	selectedObject.setText((String)tcl.getNewValue());
	        } else if(tcl.getRow() == O_HCObject.Options.TEXT_ALIGN.getValue()) {
	        	int privAlign = Util.stringToAlignType((String)tcl.getOldValue());
	        	int nextAlign = Util.stringToAlignType((String)tcl.getNewValue());
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.TEXT_ALIGN, selectedObject, privAlign, nextAlign)));
	        	nextStack.clear();
	        	selectedObject.setHorizontalAlignment(nextAlign);
	        } else if(tcl.getRow() == O_HCObject.Options.BORDER_TYPE.getValue()) {
	        	O_HCObject.BorderType privType = O_HCObject.BorderType.valueOfLabel((String)tcl.getOldValue());
	        	O_HCObject.BorderType nextType = O_HCObject.BorderType.valueOfLabel((String)tcl.getNewValue());
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.BORDER_TYPE, selectedObject, privType, nextType)));
	        	nextStack.clear();
	        	selectedObject.setBorderType(nextType);
	        	initTable();
	        } else if(tcl.getRow() == O_HCObject.Options.BORDER_THICK.getValue()) {
	        	int nextThick = -1;
	        	if(tcl.getNewValue() instanceof Integer)
	        		nextThick = (int)tcl.getNewValue();
	        	else if(tcl.getNewValue() instanceof String) {
		        	try { nextThick = Integer.parseInt((String)tcl.getNewValue()); } catch(NumberFormatException ex) {
		        		Util.showMessage("숫자만 입력할 수 있습니다.", JOptionPane.ERROR_MESSAGE);
		        		dtm.setValueAt(tcl.getOldValue(), O_HCObject.Options.BORDER_COLOR.getValue(), 1);
		        		return;
		        	}
	        	}
	        	int privThick = (int)tcl.getOldValue();
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.BORDER_THICK, selectedObject, privThick, nextThick)));
	        	nextStack.clear();
	        	selectedObject.setBorderThickness(nextThick);
	        } else if(tcl.getRow() == O_HCObject.Options.BACKGROUND_OPAQUE.getValue()) {
	        	boolean privOpaque = (boolean)tcl.getOldValue();
	        	boolean nextOpaque = (boolean)tcl.getNewValue();
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.BACKGROUND_OPAQUE, selectedObject, privOpaque, nextOpaque)));
	        	nextStack.clear();
	        	selectedObject.setOpaque(nextOpaque);
	        	repaint();
	        } else if(tcl.getRow() == O_HCObject.Options.FIX_SHAPE.getValue()) {
	        	boolean privFix = (boolean)tcl.getOldValue();
	        	boolean nextFix = (boolean)tcl.getNewValue();
	        	jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.FIX_SHAPE, selectedObject, privFix, nextFix)));
	        	nextStack.clear();
	        	selectedObject.setFixShape((boolean)tcl.getNewValue());
	        	repaint();
	        }
		}});
		JComboBox<String> combo1 = new JComboBox<>(new String[] {"왼쪽", "가운데", "오른쪽"});
		combo1.setFont(new Font("굴림", Font.PLAIN, 12));
		JComboBox<String> combo2 = new JComboBox<>(new String[] {"없음", "실선", "점선", "파선", "이중 실선"});
		combo2.setFont(new Font("굴림", Font.PLAIN, 12));
		
		editors.put(O_HCObject.Options.TEXT_ALIGN.getValue(), new DefaultCellEditor(combo1));
		editors.put(O_HCObject.Options.BORDER_TYPE.getValue(), new DefaultCellEditor(combo2));
		editors.put(O_HCObject.Options.BACKGROUND_OPAQUE.getValue(), new DefaultCellEditor(new JCheckBox()));
		editors.put(O_HCObject.Options.FIX_SHAPE.getValue(), new DefaultCellEditor(new JCheckBox()));
		
		initTable();
		
		JScrollPane scroll = new JScrollPane(fieldTable);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setPreferredSize(new Dimension(250, 100));
		scroll.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		
		add(fieldScroll, "Center");
		add(buttonsPanel, "North");
		add(scroll, "East");
		
		setFocusable(true);
		addKeyListener(new MainKeyAdapter());
		fieldTable.setFocusable(true);
		fieldTable.addKeyListener(new MainKeyAdapter());
		create.setFocusable(true);
		create.addKeyListener(new MainKeyAdapter());
		
		if(load()) {
			pack();
			setResizable(false);
			setVisible(true);
		} else {
			dispose();
		}
	}
	
	public void unselect() {
		if(selectedObject == null)
			return;
		selectFrame.remove(selectedObject);
		O_HCObject obj = selectedObject;
		obj.setLocation(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
		
		obj.removeMouseMotionListener(obj.getMouseMotionListeners()[0]);
		MouseListener[] mouseListeners = obj.getMouseListeners();
		
		for(int i=0; i<mouseListeners.length; i++)
			if(mouseListeners[i] instanceof ObjectDragMoveAdapter)
				obj.removeMouseListener(mouseListeners[i]);
		
		selectedObject = null;
		currentSelect.setText("현재 선택 : 없음");
		initTable();
		
		field.remove(selectFrame);
		field.add(obj);
		field.repaint();
	}
	
	public void select(O_HCObject obj) {
		unselect();
		selectFrame.add(obj);
		selectFrame.setLocation(obj.getLocation().x - 1, obj.getLocation().y - 1);
		selectFrame.setSize(obj.getSize().width + 2, obj.getSize().height + 2);
		selectFrame.setLayout(null);
		obj.setLocation(1, 1);
		
		currentSelect.setText("현재 선택 : " + obj.getHCName());
		selectedObject = obj;
		selectedObject.addMouseMotionListener(new ObjectDragAdapter(obj));
		selectedObject.addMouseListener(new ObjectDragMoveAdapter(obj));
		
		field.remove(obj);
		field.add(selectFrame);
		initTable();
		field.repaint();
	}
	
	public void initTable() {
		if(selectedObject == null) {
			while(dtm.getRowCount() > 0)
				dtm.removeRow(0);
			dtm.addRow(new Object[] {"이름", ""});
			dtm.addRow(new Object[] {"x", 0});
			dtm.addRow(new Object[] {"y", 0});
			dtm.addRow(new Object[] {"너비", 0});
			dtm.addRow(new Object[] {"높이", 0});
			dtm.addRow(new Object[] {"텍스트", ""});
			dtm.addRow(new Object[] {"글꼴", ""});
			dtm.addRow(new Object[] {"글자 색", ""});
			dtm.addRow(new Object[] {"글자 정렬", ""});
			dtm.addRow(new Object[] {"테두리 종류", ""});
			dtm.addRow(new Object[] {"테두리 굵기", 0});
			dtm.addRow(new Object[] {"테두리 색", ""});
			dtm.addRow(new Object[] {"배경 색", ""});
			dtm.addRow(new Object[] {"불투명 배경", ""});
			dtm.addRow(new Object[] {"고정", ""});
		} else {
			dtm.setValueAt(selectedObject.getHCName(), O_HCObject.Options.NAME.getValue(), 1); // 이름
			dtm.setValueAt(selectFrame.getLocation().x + 1, O_HCObject.Options.POS_X.getValue(), 1); // x
			dtm.setValueAt(selectFrame.getLocation().y + 1, O_HCObject.Options.POS_Y.getValue(), 1); // y
			dtm.setValueAt(selectedObject.getSize().width, O_HCObject.Options.WIDTH.getValue(), 1); // 너비
			dtm.setValueAt(selectedObject.getSize().height, O_HCObject.Options.HEIGHT.getValue(), 1); // 높이
			dtm.setValueAt(selectedObject.getText(), O_HCObject.Options.TEXT.getValue(), 1); // 텍스트
			dtm.setValueAt(Util.fontToString(selectedObject.getFont()), O_HCObject.Options.FONT.getValue(), 1); // 글꼴
			dtm.setValueAt(Util.colorToString(selectedObject.getForeground()), O_HCObject.Options.TEXT_COLOR.getValue(), 1); // 글자 색
			dtm.setValueAt(Util.alignTypeToString(selectedObject.getHorizontalAlignment()), O_HCObject.Options.TEXT_ALIGN.getValue(), 1);
			
			Border b = selectedObject.getBorder();
			
			if(b == null) {
				dtm.setValueAt("없음", O_HCObject.Options.BORDER_TYPE.getValue(), 1);
				dtm.setValueAt(0, O_HCObject.Options.BORDER_THICK.getValue(), 1);
				dtm.setValueAt("", O_HCObject.Options.BORDER_COLOR.getValue(), 1);
			} else {
				dtm.setValueAt(selectedObject.getBorderType().getValue(), O_HCObject.Options.BORDER_TYPE.getValue(), 1); // 테두리 종류
				dtm.setValueAt(selectedObject.getBorderThickness(), O_HCObject.Options.BORDER_THICK.getValue(), 1); // 테두리 굵기
				dtm.setValueAt(Util.colorToString(selectedObject.getBorderColor()), O_HCObject.Options.BORDER_COLOR.getValue(), 1); // 테두리 색
			}
			
			dtm.setValueAt(Util.colorToString(selectedObject.getBackground()), O_HCObject.Options.BACKGROUND_COLOR.getValue(), 1); // 배경 색
			dtm.setValueAt(selectedObject.isOpaque(), O_HCObject.Options.BACKGROUND_OPAQUE.getValue(), 1); // 불투명 배경
			dtm.setValueAt(selectedObject.isFixShape(), O_HCObject.Options.FIX_SHAPE.getValue(), 1); // 모양, 위치 고정
		}
	}
	
	public void save() {
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(filePath)), "UTF8"));
			for(int i=0; i<objectList.size(); i++) {
				O_HCObject object = objectList.get(i);
				
				String name = object.getHCName();
				int x = object.getLocation().x;
				int y = object.getLocation().y;
				int width = object.getSize().width;
				int height = object.getSize().height;
				
				String textSTR = "";
				String text = object.getText();
				if(text.equals(""))
					textSTR = ";;;;";
				else {
					Font font = object.getFont();
					Color textColor = object.getForeground();
					int textAlign = object.getHorizontalAlignment();
					textSTR = text + ";" + Util.fontToString(font) + ";" + Util.colorToString(textColor) + ";" + Util.alignTypeToString(textAlign) + ";";
				}
				
				String borderSTR = "";
				O_HCObject.BorderType borderType = object.getBorderType();
				
				if(borderType == null)
					borderSTR = ";0;;";
				else {
					int borderThickness = object.getBorderThickness();
					Color borderColor = object.getBorderColor();
					borderSTR = borderType.getValue() + ";" + borderThickness + ";" + Util.colorToString(borderColor) + ";";
				}
				
				Color bgColor = object.getBackground();
				boolean bgOpaque = object.isOpaque();
				boolean fixShape = object.isFixShape();
				
				String res = name + ";" + x + ";" + y + ";" + width + ";" + height + ";" + textSTR + borderSTR + Util.colorToString(bgColor) + ";" + bgOpaque + ";" + fixShape + ";";
				bw.write(res);
				if(i != objectList.size() - 1)
					bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(bw != null)
				try { bw.close(); } catch(IOException e) {e.printStackTrace(System.err);}
		}
	}
	
	public boolean load() {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF8"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				String[] splits = line.split(";");
				
				String name = splits[O_HCObject.Options.NAME.getValue()];
				int x = Integer.parseInt(splits[O_HCObject.Options.POS_X.getValue()]);
				int y = Integer.parseInt(splits[O_HCObject.Options.POS_Y.getValue()]);
				int width = Integer.parseInt(splits[O_HCObject.Options.WIDTH.getValue()]);
				int height = Integer.parseInt(splits[O_HCObject.Options.HEIGHT.getValue()]);
				
				String text = splits[O_HCObject.Options.TEXT.getValue()];
				Font font = new Font("굴림", Font.PLAIN, 13);
				Color textColor = new Color(51, 51, 51);
				int textAlign = Util.stringToAlignType(splits[O_HCObject.Options.TEXT_ALIGN.getValue()]);
				if(!text.equals("")) {
					font = Util.stringToFont(splits[O_HCObject.Options.FONT.getValue()]);
					textColor = Util.stringToColor(splits[O_HCObject.Options.TEXT_COLOR.getValue()]);
				}
				
				O_HCObject.BorderType borderType = null;
				if(!splits[O_HCObject.Options.BORDER_TYPE.getValue()].equals(""))
					borderType = O_HCObject.BorderType.valueOfLabel(splits[O_HCObject.Options.BORDER_TYPE.getValue()]);
				int borderThickness = 0;
				Color borderColor = null;
				if(borderType != null) {
					borderThickness = Integer.parseInt(splits[O_HCObject.Options.BORDER_THICK.getValue()]);
					borderColor = Util.stringToColor(splits[O_HCObject.Options.BORDER_COLOR.getValue()]);
				}
				
				Color bgColor = Util.stringToColor(splits[O_HCObject.Options.BACKGROUND_COLOR.getValue()]);
				boolean bgOpaque = Boolean.valueOf(splits[O_HCObject.Options.BACKGROUND_OPAQUE.getValue()]);
				boolean fixShape = Boolean.valueOf(splits[O_HCObject.Options.FIX_SHAPE.getValue()]);
				
				O_HCObject object = new O_HCObject(name, x, y, width, height, text, font, textColor, textAlign, borderType, borderThickness, borderColor, bgColor, bgOpaque, fixShape);
				field.add(object);
				objectList.add(object);
				object.addMouseListener(new ObjectSelectAdapter(object));
			}
		} catch(IOException e) {
			e.printStackTrace(System.err);
			Util.showMessage("파일을 불러오던 중 오류가 발생하였습니다.\n> 입출력 오류 발생", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch(NumberFormatException e) {
			e.printStackTrace(System.err);
			Util.showMessage("파일을 불러오던 중 오류가 발생하였습니다.\n> 문자열에서 숫자로 변환시키는 과정 중 오류 발생", JOptionPane.ERROR_MESSAGE);
			return false;
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace(System.err);
			Util.showMessage("파일을 불러오던 중 오류가 발생하였습니다.\n> 배열의 범위를 넘어서는 오류 발생", JOptionPane.ERROR_MESSAGE);
			return false;
		} finally {
			if(br != null)
				try { br.close(); } catch(IOException e) {e.printStackTrace(System.err);}
		}
		field.repaint();
		return true;
	}
	
	public void priv() {
		if(jobStack.empty())
			return;
		J_Job job = jobStack.pop();
		
		if(job.getJobType() == J_Job.J_JobType.CREATE) {
			J_DefineJob defjob = (J_DefineJob)job.getJob();
			O_HCObject target = defjob.getTarget();
			
			if(!objectList.contains(target))
				return;
			
			if(target.equals(selectedObject)) {
				selectFrame.remove(selectedObject);
				selectedObject = null;
				currentSelect.setText("현재 선택 : 없음");
				initTable();
				field.remove(selectFrame);
			}
			objectList.remove(target);
			field.remove(target);
			
			field.repaint();
		} else if(job.getJobType() == J_Job.J_JobType.REMOVE) {
			J_DefineJob defjob = (J_DefineJob)job.getJob();
			
			O_HCObject object = defjob.getTarget();
			if(objectList.contains(object))
				return;
			
			field.add(object);
			objectList.add(object);
			object.addMouseListener(new ObjectSelectAdapter(object));
			
			select(object);
			initTable();
			
			field.repaint();
		} else if(job.getJobType() == J_Job.J_JobType.VALUE_CHANGE) {
			J_ValueChangeJob changejob = (J_ValueChangeJob)job.getJob();
			
			O_HCObject object = changejob.getTarget();
			if(!objectList.contains(object))
				return;
			
			if(changejob.getChangeType() == O_HCObject.Options.NAME)
				object.setName((String)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.POS_X) { // 위치 변경
				if(selectedObject.equals(object)) {
					Point privLocation = (Point)changejob.getPrivValue();
					
					selectFrame.setLocation(privLocation.x - 1, privLocation.y - 1);
					dtm.setValueAt(privLocation.x, 1, 1);
					dtm.setValueAt(privLocation.y, 2, 1);
				} else
					object.setLocation((Point)changejob.getPrivValue());
			} else if(changejob.getChangeType() == O_HCObject.Options.WIDTH) {
				if(selectedObject.equals(object)) {
					int privWidth = (int)changejob.getPrivValue();
					
					selectFrame.setSize(privWidth + 2, selectFrame.getSize().height);
		        	selectedObject.setSize(privWidth, selectFrame.getSize().height - 2);
		        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
					dtm.setValueAt(selectedObject.getSize().height, 4, 1);
				} else
					object.setSize((int)changejob.getPrivValue(), (int)object.getSize().getHeight());
			} else if(changejob.getChangeType() == O_HCObject.Options.HEIGHT) {
				if(selectedObject.equals(object)) {
					int privHeight = (int)changejob.getPrivValue();
					
					selectFrame.setSize(selectFrame.getSize().width, privHeight + 2);
		        	selectedObject.setSize(selectFrame.getSize().width - 2, privHeight);
		        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
					dtm.setValueAt(selectedObject.getSize().height, 4, 1);
				} else
					object.setSize((int)object.getSize().getWidth(), (int)changejob.getPrivValue());
			} else if(changejob.getChangeType() == O_HCObject.Options.TEXT)
				object.setText((String)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.FONT)
				object.setFont((Font)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.TEXT_COLOR)
				object.setForeground((Color)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.TEXT_ALIGN)
				object.setHorizontalAlignment((int)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BORDER_TYPE) {
				object.setBorderType((O_HCObject.BorderType)changejob.getPrivValue());
			} else if(changejob.getChangeType() == O_HCObject.Options.BORDER_THICK)
				object.setBorderThickness((int)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BORDER_COLOR)
				object.setBorderColor((Color)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BACKGROUND_COLOR)
				object.setBackground((Color)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BACKGROUND_OPAQUE)
				object.setOpaque((boolean)changejob.getPrivValue());
			else if(changejob.getChangeType() == O_HCObject.Options.FIX_SHAPE)
				object.setFixShape((boolean)changejob.getPrivValue());
			
			initTable();
			field.repaint();
		}
		nextStack.push(job);
	}
	
	public void next() {
		if(nextStack.empty())
			return;
		for(int i=0; i<nextStack.size(); i++)
			System.out.println(nextStack.get(i));
		J_Job job = nextStack.pop();
		
		if(job.getJobType() == J_Job.J_JobType.CREATE) {
			J_DefineJob defjob = (J_DefineJob)job.getJob();
			O_HCObject target = defjob.getTarget();
			
			if(objectList.contains(target))
				return;
			
			field.add(target);
			objectList.add(target);
			target.addMouseListener(new ObjectSelectAdapter(target));
			
			select(target);
			initTable();
			
			field.repaint();
		} else if(job.getJobType() == J_Job.J_JobType.REMOVE) {
			J_DefineJob defjob = (J_DefineJob)job.getJob();
			O_HCObject target = defjob.getTarget();
			
			if(!objectList.contains(target))
				return;
			
			if(target.equals(selectedObject)) {
				target.setLocation(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
				selectFrame.remove(selectedObject);
				selectedObject = null;
				currentSelect.setText("현재 선택 : 없음");
				initTable();
				field.remove(selectFrame);
			}
			objectList.remove(target);
			field.remove(target);
			
			field.repaint();
		} else if(job.getJobType() == J_Job.J_JobType.VALUE_CHANGE) {
			J_ValueChangeJob changejob = (J_ValueChangeJob)job.getJob();
			
			O_HCObject object = changejob.getTarget();
			if(!objectList.contains(object))
				return;
			
			if(changejob.getChangeType() == O_HCObject.Options.NAME)
				object.setName((String)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.POS_X) { // 위치 변경
				Point curLocation = (Point)changejob.getCurrentValue();
				if(selectedObject.equals(object)) {
					selectFrame.setLocation(curLocation.x - 1, curLocation.y - 1);
					dtm.setValueAt(curLocation.x, 1, 1);
					dtm.setValueAt(curLocation.y, 2, 1);
				} else
					object.setLocation(curLocation);
			} else if(changejob.getChangeType() == O_HCObject.Options.WIDTH) {
				if(selectedObject.equals(object)) {
					int curWidth = (int)changejob.getCurrentValue();
					
					selectFrame.setSize(curWidth + 2, selectFrame.getSize().height);
		        	selectedObject.setSize(curWidth, selectFrame.getSize().height - 2);
		        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
					dtm.setValueAt(selectedObject.getSize().height, 4, 1);
				} else
					object.setSize((int)changejob.getCurrentValue(), (int)object.getSize().getHeight());
			} else if(changejob.getChangeType() == O_HCObject.Options.HEIGHT) {
				if(selectedObject.equals(object)) {
					int curHeight = (int)changejob.getCurrentValue();
					
					selectFrame.setSize(selectFrame.getSize().width, curHeight + 2);
		        	selectedObject.setSize(selectFrame.getSize().width - 2, curHeight);
		        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
					dtm.setValueAt(selectedObject.getSize().height, 4, 1);
				} else
					object.setSize((int)object.getSize().getWidth(), (int)changejob.getCurrentValue());
			} else if(changejob.getChangeType() == O_HCObject.Options.TEXT)
				object.setText((String)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.FONT)
				object.setFont((Font)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.TEXT_COLOR)
				object.setForeground((Color)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.TEXT_ALIGN)
				object.setHorizontalAlignment((int)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BORDER_TYPE) {
				object.setBorderType((O_HCObject.BorderType)changejob.getCurrentValue());
			} else if(changejob.getChangeType() == O_HCObject.Options.BORDER_THICK)
				object.setBorderThickness((int)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BORDER_COLOR)
				object.setBorderColor((Color)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BACKGROUND_COLOR)
				object.setBackground((Color)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.BACKGROUND_OPAQUE)
				object.setOpaque((boolean)changejob.getCurrentValue());
			else if(changejob.getChangeType() == O_HCObject.Options.FIX_SHAPE)
				object.setFixShape((boolean)changejob.getCurrentValue());
			
			initTable();
			field.repaint();
		}
		jobStack.push(job);
	}
	
	private class ObjectSelectAdapter extends MouseAdapter {
		private O_HCObject obj;
		private ObjectSelectAdapter(O_HCObject obj) { this.obj = obj; }
		
		@Override
		public void mouseReleased(MouseEvent e) {
			if(selectedObject == null || !selectedObject.equals(obj))
				select(obj);
		}
	}
	
	private class ObjectDragMoveAdapter extends MouseAdapter {
		private Point privPoint;
		private O_HCObject obj;
		private ObjectDragMoveAdapter(O_HCObject obj) { this.obj = obj; }
		
		@Override
		public void mousePressed(MouseEvent e) {
			privPoint = new Point(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
		}
		
		@Override
		public void mouseReleased(MouseEvent e) {
			Point nextPoint = new Point(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
			jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, obj, privPoint, nextPoint)));
			nextStack.clear();
		}
	}
	
	private class ObjectDragAdapter extends MouseMotionAdapter {
		private O_HCObject obj;
		private ObjectDragAdapter(O_HCObject obj) { this.obj = obj; }
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if(selectedObject != null && selectedObject.equals(obj) && !selectedObject.isFixShape()) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, field);
				selectFrame.setLocation(p);
				
				dtm.setValueAt(selectFrame.getLocation().x + 1, 1, 1);
				dtm.setValueAt(selectFrame.getLocation().y + 1, 2, 1);
			}
		}
	}
	
	private class ShortCutKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(!((e.getModifiersEx() == 128 || e.getModifiersEx() == 2) && e.getKeyCode() != KeyEvent.VK_CONTROL))
				return;
			if(e.getKeyCode() == KeyEvent.VK_Z)
				priv();
			else if(e.getKeyCode() == KeyEvent.VK_Y)
				next();
		}
	}
	
	private class MainKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(selectedObject != null) {
				if((e.getModifiersEx() == 128 || e.getModifiersEx() == 2) && e.getKeyCode() != KeyEvent.VK_CONTROL) { // Ctrl 을 누른 상태라면
					if(e.getKeyCode() == KeyEvent.VK_LEFT && !selectedObject.isFixShape()) {
						Dimension d = selectedObject.getSize();
						if(d.width <= 1)
							return;
			        	selectFrame.setSize(d.width + 1, selectFrame.getSize().height);
			        	selectedObject.setSize(d.width - 1, selectFrame.getSize().height - 2);
			        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
						dtm.setValueAt(selectedObject.getSize().height, 4, 1);
						
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.WIDTH, selectedObject, selectedObject.getSize().width + 1, selectedObject.getSize().width)));
						nextStack.clear();
					} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !selectedObject.isFixShape()) {
						Dimension d = selectedObject.getSize();
			        	selectFrame.setSize(d.width + 3, selectFrame.getSize().height);
			        	selectedObject.setSize(d.width + 1, selectFrame.getSize().height - 2);
			        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
						dtm.setValueAt(selectedObject.getSize().height, 4, 1);
						
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.WIDTH, selectedObject, selectedObject.getSize().width - 1, selectedObject.getSize().width)));
						nextStack.clear();
					} else if(e.getKeyCode() == KeyEvent.VK_UP && !selectedObject.isFixShape()) {
						Dimension d = selectedObject.getSize();
						if(d.height <= 1)
							return;
			        	selectFrame.setSize(selectFrame.getSize().width, d.height + 1);
			        	selectedObject.setSize(selectFrame.getSize().width - 2, d.height - 1);
			        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
						dtm.setValueAt(selectedObject.getSize().height, 4, 1);
						
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.HEIGHT, selectedObject, selectedObject.getSize().height + 1, selectedObject.getSize().height)));
						nextStack.clear();
					} else if(e.getKeyCode() == KeyEvent.VK_DOWN && !selectedObject.isFixShape()) {
						Dimension d = selectedObject.getSize();
			        	selectFrame.setSize(selectFrame.getSize().width, d.height + 3);
			        	selectedObject.setSize(selectFrame.getSize().width - 2, d.height + 1);
			        	dtm.setValueAt(selectedObject.getSize().width, 3, 1);
						dtm.setValueAt(selectedObject.getSize().height, 4, 1);
						
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.HEIGHT, selectedObject, selectedObject.getSize().height - 1, selectedObject.getSize().height)));
						nextStack.clear();
					}
				} else { // 아니라면
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						unselect();
					} else if(e.getKeyCode() == KeyEvent.VK_LEFT && !selectedObject.isFixShape()) {
						Point selectFrameLoc = selectFrame.getLocation();
						
						Point privLocation = new Point(selectFrameLoc.x + 1, selectFrameLoc.y + 1);
						Point nextLocation = new Point(selectFrameLoc.x, 	 selectFrameLoc.y + 1);
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, selectedObject, privLocation, nextLocation)));
						nextStack.clear();
						
						selectFrame.setLocation(selectFrame.getLocation().x - 1, selectFrame.getLocation().y);
						dtm.setValueAt(selectFrame.getLocation().x + 1, 1, 1);
						dtm.setValueAt(selectFrame.getLocation().y + 1, 2, 1);
					} else if(e.getKeyCode() == KeyEvent.VK_RIGHT && !selectedObject.isFixShape()) {
						Point selectFrameLoc = selectFrame.getLocation();
						
						Point privLocation = new Point(selectFrameLoc.x + 1, selectFrameLoc.y + 1);
						Point nextLocation = new Point(selectFrameLoc.x + 2, selectFrameLoc.y + 1);
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, selectedObject, privLocation, nextLocation)));
						nextStack.clear();
						
						selectFrame.setLocation(selectFrame.getLocation().x + 1, selectFrame.getLocation().y);
						dtm.setValueAt(selectFrame.getLocation().x + 1, 1, 1);
						dtm.setValueAt(selectFrame.getLocation().y + 1, 2, 1);
					}  else if(e.getKeyCode() == KeyEvent.VK_UP && !selectedObject.isFixShape()) {
						Point selectFrameLoc = selectFrame.getLocation();
						
						Point privLocation = new Point(selectFrameLoc.x + 1, selectFrameLoc.y + 1);
						Point nextLocation = new Point(selectFrameLoc.x + 1, selectFrameLoc.y);
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, selectedObject, privLocation, nextLocation)));
						nextStack.clear();
						
						selectFrame.setLocation(selectFrame.getLocation().x, selectFrame.getLocation().y - 1);
						dtm.setValueAt(selectFrame.getLocation().x + 1, 1, 1);
						dtm.setValueAt(selectFrame.getLocation().y + 1, 2, 1);
					} else if(e.getKeyCode() == KeyEvent.VK_DOWN && !selectedObject.isFixShape()) {
						Point selectFrameLoc = selectFrame.getLocation();
						
						Point privLocation = new Point(selectFrameLoc.x + 1, selectFrameLoc.y + 1);
						Point nextLocation = new Point(selectFrameLoc.x + 1, selectFrameLoc.y + 2);
						jobStack.push(new J_Job(J_Job.J_JobType.VALUE_CHANGE, new J_ValueChangeJob(O_HCObject.Options.POS_X, selectedObject, privLocation, nextLocation)));
						nextStack.clear();
						
						selectFrame.setLocation(selectFrame.getLocation().x, selectFrame.getLocation().y + 1);
						dtm.setValueAt(selectFrame.getLocation().x + 1, 1, 1);
						dtm.setValueAt(selectFrame.getLocation().y + 1, 2, 1);
					} else if(e.getKeyCode() == KeyEvent.VK_DELETE) {
						O_HCObject copyObject = selectedObject;
						copyObject.setLocation(selectFrame.getLocation().x + 1, selectFrame.getLocation().y + 1);
						jobStack.push(new J_Job(J_Job.J_JobType.REMOVE, new J_DefineJob(selectedObject)));
						nextStack.clear();
						
						selectFrame.remove(selectedObject);
						objectList.remove(selectedObject);
						selectedObject = null;
						currentSelect.setText("현재 선택 : 없음");
						initTable();
						
						field.remove(selectFrame);
						field.repaint();
					}
				}
			}
		}
	}
	
}