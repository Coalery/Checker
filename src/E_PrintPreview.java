import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

@Deprecated
@SuppressWarnings("serial")
public class E_PrintPreview extends JScrollPane {
	private Printable pr;
	private PageFormat p;

	public E_PrintPreview(final Printable pr, final PageFormat p) {
		this.pr = pr;
		this.p = p;
		
		Dimension size = new Dimension((int) p.getPaper().getWidth(), (int) p.getPaper().getHeight());
		if (p.getOrientation() != PageFormat.PORTRAIT)
			size = new Dimension(size.height, size.width);
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		BufferedImage mImage = new java.awt.image.BufferedImage(size.width, size.height, java.awt.image.BufferedImage.TYPE_INT_RGB);
		
		Graphics g = mImage.getGraphics();
		Color c = g.getColor();
		g.setColor(Color.white);
		g.fillRect(0, 0, (int) p.getWidth(), (int) p.getHeight());
		g.setColor(c);
		try {
			pr.print(g, p, 0);
			g.setColor(Color.black);
			g.drawRect(0, 0, (int)p.getWidth()-1, (int)p.getHeight()-1);
		} catch (Exception ex) {}
		label.setIcon(new ImageIcon(mImage));
		
		setViewportView(label);
	}
	
	public void print() {
		try {
			PrinterJob pj = PrinterJob.getPrinterJob();
			pj.defaultPage(p);
			pj.setPrintable(new Printable() {public int print(Graphics g, PageFormat fmt, int index) {
				if (index > 0) return Printable.NO_SUCH_PAGE;
				try { return pr.print(g, fmt, 0); } catch (Exception ex) {}
				return Printable.PAGE_EXISTS;
			}});
			if (pj.printDialog())
				pj.print();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.toString(), "Error in Printing", 1);
		}
	}
}