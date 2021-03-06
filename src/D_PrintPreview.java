import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class D_PrintPreview extends JDialog {
	
	/**매개변수로 받은 레이아웃을 출력한다.
	 * @param parent
	 * @param component 출력할 레이아웃
	 * 
	 * @param number 로그로 기록할 데이터
	 * @param name 로그로 기록할 데이터
	 * @param type 로그로 기록할 데이터
	 * @param etc 로그로 기록할 데이터
	 */
	public D_PrintPreview(JFrame parent, JPanel[] component, int number, String name, String type, String etc) {
		super(parent, "인쇄 미리보기", true);
		
		final JPanel showPanel = new JPanel();
		final CardLayout card = new CardLayout();
		
		showPanel.setPreferredSize(new Dimension((int)(Util.A4_WIDTH * Util.A4_WEIGHT), (int)(Util.A4_HEIGHT * Util.A4_WEIGHT)));
		showPanel.setLayout(card);
		
		for(int i=0; i<component.length; i++)
			showPanel.add(component[i], i + "");
		
		JPanel arrowPanel = new JPanel();
		
		JButton left = (JButton) Util.getDefaultComponent(new JButton("◀"), Color.WHITE, true);
		left.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			card.previous(showPanel);
		}});
		
		JButton printing = (JButton) Util.getDefaultComponent(new JButton("인쇄"), Color.WHITE, true);
		printing.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			
			for(int i=0; i<component.length; i++) {
				PrinterJob pj = PrinterJob.getPrinterJob();
				pj.setJobName(":: Student Document Printing ::");
				
				pj.setPrintable(new E_Printable(component[i]));
				if(!pj.printDialog())
					return;
				try { pj.print(); } catch(PrinterException e) { e.printStackTrace(); }
			}
			E_SqliteDBManager.insertData(number, name, type, etc);
		}});
		
		JButton right = (JButton) Util.getDefaultComponent(new JButton("▶"), Color.WHITE, true);
		right.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			card.next(showPanel);
		}});
		
		if(component.length == 1) {
			left.setEnabled(false);
			right.setEnabled(false);
		}
		
		arrowPanel.add(left);
		arrowPanel.add(printing);
		arrowPanel.add(right);
		
		JScrollPane scroll = new JScrollPane(showPanel);
		scroll.setPreferredSize(new Dimension((int)(Util.A4_WIDTH * Util.A4_WEIGHT) + 18, 500));
		
		add(scroll, "Center");
		add(arrowPanel, "South");
		
		pack();
		
		setResizable(false);
	}
}