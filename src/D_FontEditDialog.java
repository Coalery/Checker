import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class D_FontEditDialog extends JDialog {
	
	public D_FontEditDialog(final D_OptionDialog parent) {
		super(parent, "사용할 글꼴 변경", true);
		getContentPane().setPreferredSize(new Dimension(500, 190));
		
		String layoutFonts = Util.getConfig("layoutFonts");
		
		String[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		String[] selectedFontsArray = layoutFonts.split(",");
		
		DefaultListModel<String> noSelectedFontsListModel = new DefaultListModel<String>();
		DefaultListModel<String> selectedFontsListModel = new DefaultListModel<String>();
		
		for(int i=0; i<allFonts.length; i++) {
			boolean isSelected = false;
			for(int j=0; j<selectedFontsArray.length; j++)
				if(allFonts[i].equals(selectedFontsArray[j])) {
					selectedFontsListModel.addElement(allFonts[i]);
					isSelected = true;
					break;
				}
			if(!isSelected)
				noSelectedFontsListModel.addElement(allFonts[i]);
		}
		
		setLayout(null);
		
		final JList<String> noSelectedFontsList = new JList<String>(noSelectedFontsListModel);
		final JList<String> selectedFontsList = new JList<String>(selectedFontsListModel);
		
		noSelectedFontsList.setModel(noSelectedFontsListModel);
		selectedFontsList.setModel(selectedFontsListModel);
		
		JScrollPane noSelectedFontsListScroll = new JScrollPane(noSelectedFontsList);
		JScrollPane selectedFontsListScroll = new JScrollPane(selectedFontsList);
		
		JPanel buttonPanelChild = new JPanel();
		buttonPanelChild.setLayout(new FlowLayout());
		
		JButton select = (JButton)Util.getDefaultComponent(new JButton("▶"), Color.WHITE, true);
		JButton unselect = (JButton)Util.getDefaultComponent(new JButton("◀"), Color.WHITE, true);
		
		select.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			List<String> selectionFonts = noSelectedFontsList.getSelectedValuesList();
			
			// Send to selection Area
			for(int i=0; i<selectionFonts.size(); i++) {
				noSelectedFontsListModel.removeElement(selectionFonts.get(i));
				selectedFontsListModel.addElement(selectionFonts.get(i));
			}
			
			// Sorting
			String[] fontsToSort = new String[selectedFontsListModel.getSize()];
			for(int i=0; i<selectedFontsListModel.getSize(); i++)
				fontsToSort[i] = selectedFontsListModel.get(i);
			Arrays.sort(fontsToSort);
			selectedFontsListModel.removeAllElements();
			for(int i=0; i<fontsToSort.length; i++)
				selectedFontsListModel.addElement(fontsToSort[i]);
			
			// Save to Config
			String fontsStringToSave = "";
			for(int i=0; i<fontsToSort.length; i++) {
				if(i > 0)
					fontsStringToSave += ",";
				fontsStringToSave += fontsToSort[i];
			}
			parent.setContentInFontField(fontsStringToSave);
			Util.changeConfig("layoutFonts", fontsStringToSave);
			
			noSelectedFontsList.clearSelection();
			selectedFontsList.clearSelection();
		}});
		
		unselect.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			List<String> selectionFonts = selectedFontsList.getSelectedValuesList();
			
			// You can't move All fonts.
			if(selectionFonts.size() == selectedFontsListModel.getSize()) {
				Util.showMessage("사용하는 글꼴이 최소 한 개는 있어야합니다.", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			// Send to selection Area
			for(int i=0; i<selectionFonts.size(); i++) {
				selectedFontsListModel.removeElement(selectionFonts.get(i));
				noSelectedFontsListModel.addElement(selectionFonts.get(i));
			}
			
			// Sorting
			String[] fontsToSort = new String[noSelectedFontsListModel.getSize()];
			for(int i=0; i<noSelectedFontsListModel.getSize(); i++)
				fontsToSort[i] = noSelectedFontsListModel.get(i);
			Arrays.sort(fontsToSort);
			noSelectedFontsListModel.removeAllElements();
			for(int i=0; i<fontsToSort.length; i++)
				noSelectedFontsListModel.addElement(fontsToSort[i]);
			
			// Save to Config
			String fontsStringToSave = "";
			for(int i=0; i<selectedFontsListModel.getSize(); i++) {
				if(i > 0)
					fontsStringToSave += ",";
				fontsStringToSave += selectedFontsListModel.get(i);
			}
			parent.setContentInFontField(fontsStringToSave);
			Util.changeConfig("layoutFonts", fontsStringToSave);
			
			noSelectedFontsList.clearSelection();
			selectedFontsList.clearSelection();
		}}); // 왠지 상당히 불편하다. 최적화를 하기에는 전역변수가 많아질 것 같아 어렵다. 클래스를 하나 더 만들어야하는걸까.
		
		noSelectedFontsListScroll.setSize(200, 180);
		noSelectedFontsListScroll.setLocation(10, 10);
		
		select.setSize(50, 20);
		select.setLocation(230, 75);
		
		unselect.setSize(50, 20);
		unselect.setLocation(230, 105);
		
		selectedFontsListScroll.setSize(200, 180);
		selectedFontsListScroll.setLocation(300, 10);
		
		noSelectedFontsList.setFont(new Font("돋움", Font.PLAIN, 13));
		selectedFontsList.setFont(new Font("돋움", Font.PLAIN, 13));
		
		add(noSelectedFontsListScroll);
		add(select);
		add(unselect);
		add(selectedFontsListScroll);
		
		pack();
		setResizable(false);
		setVisible(true);
	}
	
}