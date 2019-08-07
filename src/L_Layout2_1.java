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
public class L_Layout2_1 extends JPanel {
	
	// A4 Size  210 : 297
	
	public static final int A4_WIDTH = 210;
	public static final int A4_HEIGHT = 297;
	
	public static final float A4_WEIGHT = 2.74f;
	
	public L_Layout2_1(int number, String name) {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JPanel outsideDotBorder = new JPanel();
		outsideDotBorder.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createLineBorder(Color.WHITE)), BorderFactory.createLineBorder(Color.BLACK)));
		outsideDotBorder.setSize((int)(180 * A4_WEIGHT), (int)(247 * A4_WEIGHT));
		outsideDotBorder.setLocation((int)(15 * A4_WEIGHT), (int)(25 * A4_WEIGHT));
		outsideDotBorder.setBackground(Color.WHITE);
		outsideDotBorder.setOpaque(false);
		
		JLabel titleL = new JLabel("결       석       계", JLabel.CENTER);
		titleL.setFont(new Font("함초롬돋움", Font.BOLD, 30));
		titleL.setSize(300, 100);
		titleL.setLocation((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT / 2) - 150, 80);
		
		JLabel numberL = new JLabel("학  번  :  " + number);
		numberL.setFont(new Font("함초롬돋움", Font.PLAIN, 12));
		numberL.setSize(100, 20);
		numberL.setLocation(320, 160);
		
		JLabel nameL = new JLabel("이  름  :  " + name);
		nameL.setFont(new Font("함초롬돋움", Font.PLAIN, 12));
		nameL.setSize(100, 20);
		nameL.setLocation(320, 183);
		
		JLabel content1L = new JLabel("위  학생은  다음과  같은  사유로  인하여  결석계를  제출합니다.");
		content1L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content1L.setSize(400, 20);
		content1L.setLocation(75, 220);
		
		JLabel content2L = new JLabel("1.  결석  사유  :    현장체험학습으로  인한  인정결");
		content2L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content2L.setSize(380, 20);
		content2L.setLocation(85, 245);
		
		JLabel content3L = new JLabel("2.  결석  기간  :    2019년      월      일  ~      월      일 (총     일간)");
		content3L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content3L.setSize(380, 20);
		content3L.setLocation(85, 269);
		
		JLabel content4L = new JLabel("※  결석 사유와 관련된 서류를 첨부하여 제출해주세요.");
		content4L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content4L.setSize(380, 20);
		content4L.setLocation(85, 293);
		
		JLabel content5L = new JLabel("2019년    월          일", JLabel.CENTER);
		content5L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content5L.setSize(200, 20);
		content5L.setLocation((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT / 2) - 100, 330);
		
		JLabel content6L = new JLabel("보호자(학생과  관계:  모  )                            (인)");
		content6L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content6L.setSize(300, 20);
		content6L.setLocation(213, 367);
		
		JPanel insideDotBorder = new JPanel();
		insideDotBorder.setBorder(new DashedBorder());
		insideDotBorder.setSize((int)(154 * A4_WEIGHT), (int)(66.23 * A4_WEIGHT));
		insideDotBorder.setLocation((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT / 2) - (int)(154 * A4_WEIGHT / 2), 395);
		insideDotBorder.setBackground(Color.WHITE);
		insideDotBorder.setOpaque(false);
		
		JLabel content7L = new JLabel("※  담임교사의  의견서");
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content7L.setSize(250, 20);
		content7L.setLocation(97, 410);
		
		JLabel content8L = new JLabel("1. 결석 유형  :    인정결");
		content8L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content8L.setSize(250, 20);
		content8L.setLocation(105, 448);
		
		JLabel content9L = new JLabel("2. 확인 방법  :    내부결재");
		content9L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content9L.setSize(250, 20);
		content9L.setLocation(105, 473);
		
		JLabel content10L = new JLabel("담임교사                            (인)", JLabel.RIGHT);
		content10L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content10L.setSize(250, 20);
		content10L.setLocation(237, 530);
		
		JLabel footer = new JLabel("화명고등학교장  귀하", JLabel.CENTER);
		footer.setFont(new Font("함초롬바탕", Font.BOLD, 22));
		footer.setSize(250, 30);
		footer.setLocation((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT / 2) - 125, 591);
		
		JPanel footerBorder1 = new JPanel();
		footerBorder1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder1.setSize((int)(92.13 * A4_WEIGHT), (int)(32.01 * A4_WEIGHT));
		footerBorder1.setLocation(264, 631);
		footerBorder1.setBackground(Color.WHITE);
		footerBorder1.setOpaque(false);
		
		JPanel footerBorder2 = new JPanel();
		footerBorder2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder2.setSize((int)(92.13 * A4_WEIGHT), 3);
		footerBorder2.setLocation(264, 651);
		footerBorder2.setBackground(Color.WHITE);
		
		JPanel footerBorder3 = new JPanel();
		footerBorder3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder3.setSize((int)(23.03 * A4_WEIGHT), (int)(32.01 * A4_WEIGHT));
		footerBorder3.setLocation(264, 631);
		footerBorder3.setBackground(Color.WHITE);
		footerBorder3.setOpaque(false);
		
		JPanel footerBorder4 = new JPanel();
		footerBorder4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		footerBorder4.setSize((int)(23.03 * A4_WEIGHT), (int)(32.01 * A4_WEIGHT));
		footerBorder4.setLocation(388, 631);
		footerBorder4.setBackground(Color.WHITE);
		footerBorder4.setOpaque(false);
		
		JLabel footerLabel1 = new JLabel("계", JLabel.CENTER);
		footerLabel1.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel1.setSize((int)(23.03 * A4_WEIGHT), (int)(7.52 * A4_WEIGHT));
		footerLabel1.setLocation(264, 631);
		
		JLabel footerLabel2 = new JLabel("교무부장", JLabel.CENTER);
		footerLabel2.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel2.setSize((int)(23.03 * A4_WEIGHT), (int)(7.52 * A4_WEIGHT));
		footerLabel2.setLocation(326, 631);
		
		JLabel footerLabel3 = new JLabel("교감", JLabel.CENTER);
		footerLabel3.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel3.setSize((int)(23.03 * A4_WEIGHT), (int)(7.52 * A4_WEIGHT));
		footerLabel3.setLocation(388, 631);
		
		JLabel footerLabel4 = new JLabel("교장", JLabel.CENTER);
		footerLabel4.setFont(new Font("함초롬돋움", Font.PLAIN, 11));
		footerLabel4.setSize((int)(23.03 * A4_WEIGHT) + 1, (int)(7.52 * A4_WEIGHT));
		footerLabel4.setLocation(451, 631);
		
		add(outsideDotBorder);
		add(titleL); add(numberL); add(nameL);
		add(content1L); add(content2L); add(content3L); add(content4L); add(content5L); 
		add(content6L); add(content7L); add(content8L); add(content9L); add(content10L);
		add(insideDotBorder); add(footer);
		add(footerBorder1); add(footerBorder2); add(footerBorder3); add(footerBorder4);
		add(footerLabel1); add(footerLabel2); add(footerLabel3); add(footerLabel4);
	}
	
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
