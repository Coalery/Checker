import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class L_Layout3_1 extends JPanel {
	
	// A4 Size  210 : 297
	
	public L_Layout3_1(int number, String name, String reason, int aYear, int aMonth, int aDay, String aPeriod, int year, int month, int day, String aMethod, String teacher) {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JPanel outsideDotBorder = new JPanel();
		outsideDotBorder.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createLineBorder(Color.WHITE)), BorderFactory.createLineBorder(Color.BLACK)));
		outsideDotBorder.setSize((int)(180 * Util.A4_WEIGHT), (int)(247 * Util.A4_WEIGHT));
		outsideDotBorder.setLocation((int)(15 * Util.A4_WEIGHT), (int)(25 * Util.A4_WEIGHT));
		outsideDotBorder.setBackground(Color.WHITE);
		outsideDotBorder.setOpaque(false);
		
		JLabel titleL = new JLabel("출결  인정  확인서", JLabel.CENTER);
		titleL.setFont(new Font("함초롬돋움", Font.BOLD, 30));
		titleL.setSize(300, 100);
		titleL.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 150, 73);
		
		JLabel numberL = new JLabel("학  번  :  " + number);
		numberL.setFont(new Font("함초롬돋움", Font.PLAIN, 12));
		numberL.setSize(100, 20);
		numberL.setLocation(329, 170);
		
		JLabel nameL = new JLabel("이  름  :  " + name);
		nameL.setFont(new Font("함초롬돋움", Font.PLAIN, 12));
		nameL.setSize(100, 20);
		nameL.setLocation(329, 193);
		
		JLabel content1L = new JLabel("위  학생은  다음과  같은  사유로  인하여  확인서를  제출합니다.");
		content1L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content1L.setSize(400, 20);
		content1L.setLocation(75, 230);
		
		JLabel content2L = new JLabel("1.  출결  인정  내용  :    " + reason);
		content2L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content2L.setSize(380, 20);
		content2L.setLocation(85, 257);
		
		JLabel content3L = new JLabel("2.  출결  인정  기간  :    " + aYear + "년    " + aMonth + "월    " + aDay + "일    " + aPeriod);
		content3L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content3L.setSize(380, 20);
		content3L.setLocation(85, 283);
		
		JLabel content4L = new JLabel("※ 지각, 조퇴, 결과와 관련된 서류를 첨부해주세요.");
		content4L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content4L.setSize(380, 20);
		content4L.setLocation(85, 309);
		
		JLabel content5L = new JLabel(year + "년      " + month + "월      " + day + "일", JLabel.CENTER);
		content5L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content5L.setSize(200, 20);
		content5L.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 100, 348);
		
		JPanel insideDotBorder = new JPanel();
		insideDotBorder.setBorder(new DashedBorder());
		insideDotBorder.setSize((int)(154 * Util.A4_WEIGHT), (int)(44.26 * Util.A4_WEIGHT));
		insideDotBorder.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - (int)(154 * Util.A4_WEIGHT / 2), 395);
		insideDotBorder.setBackground(Color.WHITE);
		insideDotBorder.setOpaque(false);
		
		JLabel content6L = new JLabel("※  담임교사의  의견서");
		content6L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content6L.setSize(250, 20);
		content6L.setLocation(97, 397);
		
		JLabel content7L = new JLabel("1.  확인  내용  :  " + reason);
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content7L.setSize(250, 20);
		content7L.setLocation(105, 417);
		
		JLabel content8L = new JLabel("2.  확인  방법  :  " + aMethod);
		content8L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content8L.setSize(300, 20);
		content8L.setLocation(105, 455);
		
		JLabel content9L = new JLabel("담임교사      " + teacher + "    인", JLabel.LEFT);
		content9L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content9L.setSize(250, 20);
		content9L.setLocation(330, 482);
		
		JLabel footer = new JLabel("화명고등학교장 귀하", JLabel.CENTER);
		footer.setFont(new Font("함초롬바탕", Font.BOLD, 22));
		footer.setSize(250, 30);
		footer.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 125, 540);
		
		JPanel footerBorder1 = new JPanel();
		footerBorder1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder1.setSize((int)(88.14f * Util.A4_WEIGHT), (int)(30.01f * Util.A4_WEIGHT));
		footerBorder1.setLocation(264, 596);
		footerBorder1.setBackground(Color.WHITE);
		footerBorder1.setOpaque(false);
		
		JPanel footerBorder2 = new JPanel();
		footerBorder2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder2.setSize((int)(88.14 * Util.A4_WEIGHT), 3);
		footerBorder2.setLocation(264, 616);
		footerBorder2.setBackground(Color.WHITE);
		
		JPanel footerBorder3 = new JPanel();
		footerBorder3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder3.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(30.01 * Util.A4_WEIGHT));
		footerBorder3.setLocation(264, 596);
		footerBorder3.setBackground(Color.WHITE);
		footerBorder3.setOpaque(false);
		
		JPanel footerBorder4 = new JPanel();
		footerBorder4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder4.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(30.01 * Util.A4_WEIGHT));
		footerBorder4.setLocation(388, 596);
		footerBorder4.setBackground(Color.WHITE);
		footerBorder4.setOpaque(false);
		
		JLabel footerLabel1 = new JLabel("계", JLabel.CENTER);
		footerLabel1.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel1.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(6.52 * Util.A4_WEIGHT));
		footerLabel1.setLocation(264, 597);
		
		JLabel footerLabel2 = new JLabel("교무부장", JLabel.CENTER);
		footerLabel2.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel2.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(6.52 * Util.A4_WEIGHT));
		footerLabel2.setLocation(326, 597);
		
		JLabel footerLabel3 = new JLabel("교감", JLabel.CENTER);
		footerLabel3.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel3.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(6.52 * Util.A4_WEIGHT));
		footerLabel3.setLocation(388, 597);
		
		JLabel footerLabel4 = new JLabel("교장", JLabel.CENTER);
		footerLabel4.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel4.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(6.52 * Util.A4_WEIGHT));
		footerLabel4.setLocation(446, 597);
		
		JLabel footerLabel5 = new JLabel("전결", JLabel.CENTER);
		footerLabel5.setFont(new Font("바탕", Font.PLAIN, 15));
		footerLabel5.setSize((int)(22.03 * Util.A4_WEIGHT), (int)(23.49 * Util.A4_WEIGHT));
		footerLabel5.setLocation(389, 616);
		
		add(outsideDotBorder);
		add(titleL); add(numberL); add(nameL);
		add(content1L); add(content2L); add(content3L); add(content4L); add(content5L); 
		add(content6L); add(content6L); add(content7L); add(content8L); add(content9L);
		add(insideDotBorder); add(footer);
		add(footerBorder1); add(footerBorder2); add(footerBorder3); add(footerBorder4);
		add(footerLabel1); add(footerLabel2); add(footerLabel3); add(footerLabel4); add(footerLabel5);
	}
	
//	public static void main(String[] args) {
//		JFrame frame = new JFrame();
//		frame.getContentPane().add(new L_Layout3_1(20613, "김현우", "사유", 2019, 8, 11, "1~2교시", 2019, 8, 7, "확인방법", "쌤성함"));
//		frame.getContentPane().setPreferredSize(new Dimension((int)(Util.A4_WIDTH * Util.A4_WEIGHT), (int)(A4_HEIGHT * Util.A4_WEIGHT)));
//		frame.pack();
//		
//		frame.addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent event) {System.exit(0);}});
//		frame.setResizable(false);
//		frame.setVisible(true);
//	}
	
	private class DashedBorder extends AbstractBorder {
		@Override
		public void paintBorder(Component comp, Graphics g, int x, int y, int w, int h) {
			Graphics2D gg = (Graphics2D)g;
			gg.setColor(Color.BLACK);
			gg.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] {1.0f}, 0));
			gg.drawRect(x, y, w-1, h-1);
		}
	}
	
}
