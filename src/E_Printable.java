import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

import javax.swing.JPanel;

public class E_Printable implements Printable {
	
	private JPanel printPanel;
	
	public E_Printable(JPanel printPanel) {
		this.printPanel = printPanel;
		printPanel.setPreferredSize(new Dimension((int)(Util.A4_WIDTH * Util.A4_WEIGHT), (int)(Util.A4_HEIGHT * Util.A4_WEIGHT)));
	}
	
	@Override
	public int print(Graphics pg, PageFormat pf, int pageNum) {
		if(pageNum > 0) {
			return Printable.NO_SUCH_PAGE;
		}
		
		Graphics2D g2 = (Graphics2D) pg;
		g2.translate(pf.getImageableX(), pf.getImageableY());
		
		printPanel.paint(g2);
		return Printable.PAGE_EXISTS;
	}
	
}
