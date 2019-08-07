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
public class L_Layout1_4 extends JPanel {
	
	public static final int A4_WIDTH = 210;
	public static final int A4_HEIGHT = 297;
	
	public static final float A4_WEIGHT = 2.74f;
	
	public L_Layout1_4(int number, String name) {
		setLayout(null);
		setBackground(Color.WHITE);
		
		int grade = number / 10000;
		number %= 10000;
		int classN = number / 100;
		number %= 100;
		
		JPanel outsideDotBorder = new JPanel();
		outsideDotBorder.setBorder(new DashedBorder());
		outsideDotBorder.setSize((int)(180 * A4_WEIGHT), (int)(247 * A4_WEIGHT));
		outsideDotBorder.setLocation((int)(15 * A4_WEIGHT), (int)(25 * A4_WEIGHT));
		outsideDotBorder.setBackground(Color.WHITE);
		outsideDotBorder.setOpaque(false);
		
		JPanel outsideTableBorder = new JPanel();
		outsideTableBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		outsideTableBorder.setSize((int)(162.65f * A4_WEIGHT), (int)(134.62f * A4_WEIGHT));
		outsideTableBorder.setLocation((int)(A4_WIDTH * A4_WEIGHT / 2) - (int)(162.65f * A4_WEIGHT / 2), 198);
		outsideTableBorder.setBackground(Color.WHITE);
		outsideTableBorder.setOpaque(false);
		
		JPanel insideTableNormalBorder1 = new JPanel();
		insideTableNormalBorder1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		insideTableNormalBorder1.setSize((int)(31.18f * A4_WEIGHT), (int)(134.62f * A4_WEIGHT));
		insideTableNormalBorder1.setLocation(65, 198);
		insideTableNormalBorder1.setBackground(Color.WHITE);
		insideTableNormalBorder1.setOpaque(false);
		
		JPanel insideTableDotBorder1 = new JPanel();
		insideTableDotBorder1.setBorder(new DashedBorder());
		insideTableDotBorder1.setSize((int)(162.65f * A4_WEIGHT), (int)(12.24f * A4_WEIGHT) + 1);
		insideTableDotBorder1.setLocation(65, 232);
		insideTableDotBorder1.setBackground(Color.WHITE);
		insideTableDotBorder1.setOpaque(false);
		
		JLabel content1L = new JLabel("현장 체험 학습 결과 보고서", JLabel.CENTER);
		content1L.setFont(new Font("함초롬돋움", Font.BOLD, 30));
		content1L.setSize(400, 40);
		content1L.setLocation((int)(A4_WIDTH * A4_WEIGHT / 2) - 200, 100);
		
		JLabel content2L = new JLabel(grade + " 학년   " + classN + " 반   " + number + " 번   이름 : " + name);
		content2L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content2L.setSize(200, 20);
		content2L.setLocation(291, 170);
		
		JLabel content3L = new JLabel("학습  기간", JLabel.CENTER);
		content3L.setFont(new Font("함초롬바탕", Font.BOLD, 12));
		content3L.setSize((int)(31.18f * A4_WEIGHT), (int)(12.24f * A4_WEIGHT) + 2);
		content3L.setLocation(65, 198);
		content3L.setOpaque(true);
		content3L.setBackground(new Color(229, 229, 229));
		
		JLabel content4L = new JLabel("장    소", JLabel.CENTER);
		content4L.setFont(new Font("함초롬바탕", Font.BOLD, 12));
		content4L.setSize((int)(31.18f * A4_WEIGHT), (int)(12.24f * A4_WEIGHT) + 1);
		content4L.setLocation(65, 232);
		content4L.setOpaque(true);
		content4L.setBackground(new Color(229, 229, 229));
		
		JLabel content5L = new JLabel("<html><div style=\"text-align: center;\">학습&nbsp;&nbsp;내용</div><br/>"
									+ "<div style=\"text-align: center;\">및</div><br/>"
									+ "<div style=\"text-align: center;\">결&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;과</div><br/></html>", JLabel.CENTER);
		content5L.setFont(new Font("함초롬바탕", Font.BOLD, 12));
		content5L.setSize((int)(31.18f * A4_WEIGHT), (int)(110.14f * A4_WEIGHT));
		content5L.setLocation(65, 265);
		content5L.setOpaque(true);
		content5L.setBackground(new Color(229, 229, 229));
		
		JLabel content6L = new JLabel("2019년      월      일  ~      월      일 (총     일간)");
		content6L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content6L.setSize((int)(131.47f * A4_WEIGHT) - 2, (int)(12.24f * A4_WEIGHT) + 2);
		content6L.setLocation(165, 199);
		
		JLabel content7L = new JLabel("위와 같이 현장 체험 학습 결과 보고서를 제출합니다.");
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content7L.setSize(300, 20);
		content7L.setLocation(65, 570);
		
		JLabel content8L = new JLabel("2019년       월        일", JLabel.CENTER);
		content8L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content8L.setSize(200, 20);
		content8L.setLocation((int)(A4_WIDTH * A4_WEIGHT / 2) - 100, 605);
		
		JLabel content9L = new JLabel("보호자(관계 :  모  )                           (인)");
		content9L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content9L.setSize(300, 20);
		content9L.setLocation(263, 637);
		
		JLabel content10L = new JLabel("확인 : 담임교사                                (인)");
		content10L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content10L.setSize(300, 20);
		content10L.setLocation(263, 659);
		
		JLabel content11L = new JLabel("화 명 고 등 학 교 장  귀 하");
		content11L.setFont(new Font("함초롬돋움", Font.BOLD, 24));
		content11L.setSize(300, 30);
		content11L.setLocation(60, 689);
		
		add(outsideDotBorder); add(outsideTableBorder);
		add(insideTableNormalBorder1); add(insideTableDotBorder1);
		add(content1L); add(content2L); add(content3L); add(content4L); add(content5L);
		add(content6L); add(content7L); add(content8L); add(content9L); add(content10L);
		add(content11L);
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