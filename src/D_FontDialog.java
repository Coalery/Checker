import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

interface FontCallback {
	void callback(Font f);
}

@SuppressWarnings("serial")
public class D_FontDialog extends JDialog {
	
	private JList<String> fonts;
	private JList<String> fontStyle;
	private JList<Integer> fontSizes;
	
	private JLabel preview;
	
	public D_FontDialog(JDialog parent, O_HCObject currentObject, final FontCallback cback) {
		super(parent, "글꼴 설정", true);
		setSize(new Dimension(410, 280));
		setLayout(null);
		
		Font currentFont = currentObject.getFont();
		
		JLabel fontsL = new JLabel("글꼴 : ");
		fontsL.setSize(60, 20);
		fontsL.setLocation(5, 5);
		
		fonts = new JList<>(D_LayoutEditorDialog.availableFontsName); // TODO 수정
		fonts.setSelectedValue(currentFont.getName(), true);
		fonts.addListSelectionListener(new FontSelectListener());
		
		JScrollPane fontScroll = new JScrollPane(fonts, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fontScroll.setSize(200, 115);
		fontScroll.setLocation(5, 30);
		fonts.ensureIndexIsVisible(fonts.getSelectedIndex());
		
		fonts.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				label.setFont(new Font((String)value, Font.PLAIN, 13));
				return label;
			}
		});
		
		JLabel fontStyleL = new JLabel("스타일:");
		fontStyleL.setSize(100, 20);
		fontStyleL.setLocation(210, 5);
		
		int[] styles = {Font.PLAIN, Font.ITALIC, Font.BOLD, Font.BOLD | Font.ITALIC};
		int i;
		for(i=0; i<styles.length; i++)
			if(styles[i] == currentFont.getStyle())
				break;
		
		fontStyle = new JList<>(new String[] {"보통", "기울임꼴", "굵게", "굵은 기울임꼴"});
		fontStyle.setSelectedIndex(i);
		fontStyle.addListSelectionListener(new FontSelectListener());
		
		JScrollPane fontStyleScroll = new JScrollPane(fontStyle, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		fontStyleScroll.setSize(120, 115);
		fontStyleScroll.setLocation(210, 30);
		fontStyle.ensureIndexIsVisible(fontStyle.getSelectedIndex());
		
		fontStyle.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel label = (JLabel)super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				String val = (String)value;
				
				if(val.equals("보통")) label.setFont(new Font("굴림", Font.PLAIN, 13));
				else if(val.equals("기울임꼴")) label.setFont(new Font("굴림", Font.ITALIC, 13));
				else if(val.equals("굵게")) label.setFont(new Font("굴림", Font.BOLD, 13));
				else if(val.equals("굵은 기울임꼴")) label.setFont(new Font("굴림", Font.BOLD | Font.ITALIC, 13));
				return label;
			}
		});
		
		JLabel fontSizeL = new JLabel("크기:");
		fontSizeL.setSize(100, 20);
		fontSizeL.setLocation(335, 5);
		
		JTextField fontSize = new JTextField(String.valueOf(currentFont.getSize()));
		fontSize.setSize(65, 20);
		fontSize.setLocation(335, 30);
		
		fontSizes = new JList<Integer>(new Integer[] {8, 9, 10, 11, 13, 14, 16, 18, 20, 22, 24, 26, 28, 36, 48});
		fontSizes.setSelectedValue(currentFont.getSize(), false);
		fontSizes.setFont(new Font("굴림", Font.PLAIN, 13));
		fontSizes.addListSelectionListener(new ListSelectionListener() {public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			fontSize.setText(fontSizes.getSelectedValue().toString());
		}});
		fontSizes.addListSelectionListener(new FontSelectListener());
		
		JScrollPane fontSizeScroll = new JScrollPane(fontSizes);
		fontSizeScroll.setSize(65, 95);
		fontSizeScroll.setLocation(335, 50);
		fontSizes.ensureIndexIsVisible(fontSizes.getSelectedIndex());
		
		preview = new JLabel("ㄱㄴㄷ 가나다 ABC abc", JLabel.RIGHT);
		preview.setSize(395, 60);
		preview.setLocation(5, 150);
		preview.setBackground(Color.WHITE);
		preview.setOpaque(true);
		preview.setVerticalAlignment(JLabel.BOTTOM);
		preview.setVerticalTextPosition(JLabel.BOTTOM);
		preview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		preview.setFont(currentFont);
		
		JButton apply = new JButton("적용");
		apply.setSize(70, 30);
		apply.setLocation(165, 215);
		
		apply.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			cback.callback(preview.getFont());
			setVisible(false);
			dispose();
		}});
		
		add(fontsL); add(fontScroll);
		add(fontStyleL); add(fontStyleScroll);
		add(fontSizeL); add(fontSize); add(fontSizeScroll);
		add(preview); add(apply);
		
		setResizable(false);
		setVisible(true);
	}
	
	class FontSelectListener implements ListSelectionListener {
		@Override
		public void valueChanged(ListSelectionEvent e) {
			if(e.getValueIsAdjusting())
				return;
			String selectedStyle = fontStyle.getSelectedValue();
			int style = -1;
			if(selectedStyle.equals("보통")) style = Font.PLAIN;
			else if(selectedStyle.equals("기울임꼴")) style = Font.ITALIC;
			else if(selectedStyle.equals("굵게")) style = Font.BOLD;
			else if(selectedStyle.equals("굵은 기울임꼴")) style = Font.BOLD | Font.ITALIC;
			
			preview.setFont(new Font(fonts.getSelectedValue(), style, fontSizes.getSelectedValue()));
		}
	}
}