import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.AbstractBorder;

@SuppressWarnings("serial")
public class L_Layout1_1 extends JPanel {
	
	// A4 Size  210 : 297
	
	public static final int A4_WIDTH = 210;
	public static final int A4_HEIGHT = 297;
	
	public static final float A4_WEIGHT = 2.74f;
	
	public L_Layout1_1(int number, String name) {
		setLayout(null);
		setBackground(Color.WHITE);
		setSize((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT), (int)(L_Layout1_1.A4_HEIGHT * L_Layout1_1.A4_WEIGHT));
		
		JPanel outsideDotBorder = new JPanel();
		outsideDotBorder.setBorder(new DashedBorder());
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
		numberL.setLocation(320, 165);
		
		JLabel nameL = new JLabel("이  름  :  " + name);
		nameL.setFont(new Font("함초롬돋움", Font.PLAIN, 12));
		nameL.setSize(100, 20);
		nameL.setLocation(320, 188);
		
		JLabel content1L = new JLabel("위  학생은  다음과  같은  사유로  인하여  결석계를  제출합니다.");
		content1L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content1L.setSize(400, 20);
		content1L.setLocation(75, 225);
		
		JLabel content2L = new JLabel("1.  결석  사유  :    현장체험학습으로  인한  인정결");
		content2L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content2L.setSize(380, 20);
		content2L.setLocation(85, 253);
		
		JLabel content3L = new JLabel("2.  결석  기간  :    2019년      월      일  ~      월      일 (총     일간)");
		content3L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content3L.setSize(380, 20);
		content3L.setLocation(85, 278);
		
		JLabel content4L = new JLabel("2019년    월          일", JLabel.CENTER);
		content4L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content4L.setSize(200, 20);
		content4L.setLocation((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT / 2) - 100, 360);
		
		JLabel content5L = new JLabel("보호자(학생과  관계:  모  )                       (인)");
		content5L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content5L.setSize(300, 20);
		content5L.setLocation(213, 400);
		
		JPanel insideDotBorder = new JPanel();
		insideDotBorder.setBorder(new DashedBorder());
		insideDotBorder.setSize((int)(154 * A4_WEIGHT), (int)(83.2 * A4_WEIGHT));
		insideDotBorder.setLocation((int)(L_Layout1_1.A4_WIDTH * L_Layout1_1.A4_WEIGHT / 2) - (int)(154 * A4_WEIGHT / 2), 445);
		insideDotBorder.setBackground(Color.WHITE);
		insideDotBorder.setOpaque(false);
		
		JLabel content6L = new JLabel("※  담임교사의  의견서");
		content6L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content6L.setSize(250, 20);
		content6L.setLocation(97, 475);
		
		JLabel content7L = new JLabel("1. 결석 유형  :    인정결");
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content7L.setSize(250, 20);
		content7L.setLocation(105, 508);

		JLabel content8L_1 = new JLabel("2. 내");
		content8L_1.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content8L_1.setSize(250, 20);
		content8L_1.setLocation(105, 528);
		
		JLabel content8L_2 = new JLabel("용  :    현장체험학습");
		content8L_2.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content8L_2.setSize(250, 20);
		content8L_2.setLocation(163, 528);
		
		JLabel content9L = new JLabel("3. 확인 방법  :    내부결재");
		content9L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content9L.setSize(250, 20);
		content9L.setLocation(105, 563);
		
		JLabel content10L = new JLabel("담임교사                            (인)", JLabel.RIGHT);
		content10L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content10L.setSize(250, 20);
		content10L.setLocation(237, 600);
		
		JLabel content11L = new JLabel("출결담당교사                            (인)", JLabel.RIGHT);
		content11L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content11L.setSize(250, 20);
		content11L.setLocation(237, 620);
		
		JLabel footer = new JLabel("화명고등학교장  귀하");
		footer.setFont(new Font("함초롬바탕", Font.BOLD, 22));
		footer.setSize(250, 30);
		footer.setLocation(55, 685);
		
		add(outsideDotBorder);
		add(titleL); add(numberL); add(nameL);
		add(content1L); add(content2L); add(content3L); add(content4L);
		add(content5L); add(content5L);
		add(insideDotBorder);
		add(content6L); add(content7L); add(content8L_1); add(content8L_2); add(content9L);
		add(content10L); add(content11L);
		add(footer);
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
