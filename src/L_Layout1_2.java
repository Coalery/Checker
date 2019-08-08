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
public class L_Layout1_2 extends JPanel {
	
	public L_Layout1_2(int number, String name, int asYear, int asMonth, int asDay, int adMonth, int adDay, int aDays, int year, int month, int day, String parentName) {
		setLayout(null);
		setBackground(Color.WHITE);
		
		int grade = number / 10000;
		number %= 10000;
		int classN = number / 100;
		number %= 100;
		
		JPanel outsideDotBorder = new JPanel();
		outsideDotBorder.setBorder(new DashedBorder());
		outsideDotBorder.setSize((int)(180 * Util.A4_WEIGHT), (int)(247 * Util.A4_WEIGHT));
		outsideDotBorder.setLocation((int)(15 * Util.A4_WEIGHT), (int)(25 * Util.A4_WEIGHT));
		outsideDotBorder.setBackground(Color.WHITE);
		outsideDotBorder.setOpaque(false);
		
		JLabel titleL = new JLabel("현장 체험 학습 계획서", JLabel.CENTER);
		titleL.setFont(new Font("함초롬돋움", Font.BOLD, 30));
		titleL.setSize(300, 35);
		titleL.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 150, 78);
		
		JPanel outsideTableBorder = new JPanel();
		outsideTableBorder.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		outsideTableBorder.setSize((int)(162.97f * Util.A4_WEIGHT), (int)(162.05f * Util.A4_WEIGHT));
		outsideTableBorder.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - (int)(162.97f * Util.A4_WEIGHT / 2), 132);
		outsideTableBorder.setBackground(Color.WHITE);
		outsideTableBorder.setOpaque(false);
		
		JPanel insideTableNormalBorder1 = new JPanel();
		insideTableNormalBorder1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		insideTableNormalBorder1.setSize((int)(23.55f * Util.A4_WEIGHT), (int)(162.05f * Util.A4_WEIGHT));
		insideTableNormalBorder1.setLocation(64, 132);
		insideTableNormalBorder1.setBackground(Color.WHITE);
		insideTableNormalBorder1.setOpaque(false);
		
		JPanel insideTableNormalBorder2 = new JPanel();
		insideTableNormalBorder2.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		insideTableNormalBorder2.setSize((int)(162.97f * Util.A4_WEIGHT), (int)(38.84f * Util.A4_WEIGHT));
		insideTableNormalBorder2.setLocation(64, 132);
		insideTableNormalBorder2.setBackground(Color.WHITE);
		insideTableNormalBorder2.setOpaque(false);
		
		JPanel insideTableNormalBorder3 = new JPanel();
		insideTableNormalBorder3.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		insideTableNormalBorder3.setSize((int)(162.97f * Util.A4_WEIGHT), (int)(28.59f * Util.A4_WEIGHT));
		insideTableNormalBorder3.setLocation(64, 237);
		insideTableNormalBorder3.setBackground(Color.WHITE);
		insideTableNormalBorder3.setOpaque(false);
		
		JPanel insideTableDotBorder1 = new JPanel();
		insideTableDotBorder1.setBorder(new DashedBorder());
		insideTableDotBorder1.setSize((int)(162.97f * Util.A4_WEIGHT), (int)(10.87f * Util.A4_WEIGHT) + 1);
		insideTableDotBorder1.setLocation(64, 132);
		insideTableDotBorder1.setBackground(Color.WHITE);
		insideTableDotBorder1.setOpaque(false);
		
		JPanel insideTableDotBorder2 = new JPanel();
		insideTableDotBorder2.setBorder(new DashedBorder());
		insideTableDotBorder2.setSize((int)(162.97f * Util.A4_WEIGHT), (int)(16.86f * Util.A4_WEIGHT) + 1);
		insideTableDotBorder2.setLocation(64, 161);
		insideTableDotBorder2.setBackground(Color.WHITE);
		insideTableDotBorder2.setOpaque(false);
		
		JPanel insideTableDotBorder3 = new JPanel();
		insideTableDotBorder3.setBorder(new DashedBorder());
		insideTableDotBorder3.setSize((int)(139.42f * Util.A4_WEIGHT) + 1, (int)(17.62f * Util.A4_WEIGHT));
		insideTableDotBorder3.setLocation(127, 237);
		insideTableDotBorder3.setBackground(Color.WHITE);
		insideTableDotBorder3.setOpaque(false);
		
		JPanel insideTableNormalBorder4 = new JPanel();
		insideTableNormalBorder4.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		insideTableNormalBorder4.setSize((int)(27.89f * Util.A4_WEIGHT), (int)(28.59f * Util.A4_WEIGHT));
		insideTableNormalBorder4.setLocation(203, 237);
		insideTableNormalBorder4.setBackground(Color.WHITE);
		insideTableNormalBorder4.setOpaque(false);
		
		JPanel insideTableNormalBorder5 = new JPanel();
		insideTableNormalBorder5.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		insideTableNormalBorder5.setSize((int)(27.89f * Util.A4_WEIGHT), (int)(28.59f * Util.A4_WEIGHT));
		insideTableNormalBorder5.setLocation(356, 237);
		insideTableNormalBorder5.setBackground(Color.WHITE);
		insideTableNormalBorder5.setOpaque(false);
		
		JPanel insideTableDotBorder4 = new JPanel();
		insideTableDotBorder4.setBorder(new DashedBorder());
		insideTableDotBorder4.setSize((int)(162.97f * Util.A4_WEIGHT), (int)(17.9f * Util.A4_WEIGHT));
		insideTableDotBorder4.setLocation(64, 314);
		insideTableDotBorder4.setBackground(Color.WHITE);
		insideTableDotBorder4.setOpaque(false);
		
		JLabel content1L = new JLabel("학  생", JLabel.CENTER);
		content1L.setFont(new Font("함초롬바탕", Font.BOLD, 11));
		content1L.setBackground(new Color(229, 229, 229));
		content1L.setSize((int)(23.55f * Util.A4_WEIGHT) - 2, (int)(10.87f * Util.A4_WEIGHT) - 1);
		content1L.setLocation(65, 133);
		content1L.setOpaque(true);
		
		JLabel content2L = new JLabel("일  시", JLabel.CENTER);
		content2L.setFont(new Font("함초롬바탕", Font.BOLD, 11));
		content2L.setBackground(new Color(229, 229, 229));
		content2L.setSize((int)(23.55f * Util.A4_WEIGHT) - 2, (int)(16.86f * Util.A4_WEIGHT) + 1);
		content2L.setLocation(65, 161);
		content2L.setOpaque(true);
		
		JLabel content3L = new JLabel("장  소", JLabel.CENTER);
		content3L.setFont(new Font("함초롬바탕", Font.BOLD, 11));
		content3L.setBackground(new Color(229, 229, 229));
		content3L.setSize((int)(23.55f * Util.A4_WEIGHT) - 2, (int)(11.11f * Util.A4_WEIGHT) - 1);
		content3L.setLocation(65, 208);
		content3L.setOpaque(true);
		
		JLabel content4L = new JLabel("<html><div style=\"text-align: center;\">학습방법<br>(신청유형)</div></html>", JLabel.CENTER);
		content4L.setFont(new Font("함초롬바탕", Font.BOLD, 11));
		content4L.setBackground(new Color(229, 229, 229));
		content4L.setSize((int)(23.55f * Util.A4_WEIGHT) - 2, (int)(28.59f * Util.A4_WEIGHT) - 1);
		content4L.setLocation(65, 238);
		content4L.setOpaque(true);
		
		JLabel content5L = new JLabel("학습주제", JLabel.CENTER);
		content5L.setFont(new Font("함초롬바탕", Font.BOLD, 11));
		content5L.setBackground(new Color(229, 229, 229));
		content5L.setSize((int)(23.55f * Util.A4_WEIGHT) - 2, (int)(17.9f * Util.A4_WEIGHT) + 2);
		content5L.setLocation(65, 312);
		content5L.setOpaque(true);
		
		JLabel content6L = new JLabel("<html><div style=\"text-align: center;\">학 습 할<br>내  용</div></html>", JLabel.CENTER);
		content6L.setFont(new Font("함초롬바탕", Font.BOLD, 11));
		content6L.setSize((int)(23.55f * Util.A4_WEIGHT) - 2, (int)(76.72f * Util.A4_WEIGHT) + 4);
		content6L.setLocation(65, 363);
		content6L.setBackground(new Color(229, 229, 229));
		content6L.setOpaque(true);
		
		JLabel content7L = new JLabel("<html><div style=\"text-align: center;\">친.인척방문<br>체험학습</div></html>", JLabel.CENTER);
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content7L.setSize((int)(27.89f * Util.A4_WEIGHT) - 2, (int)(17.62f * Util.A4_WEIGHT) - 2);
		content7L.setLocation(128, 238);
		
		JLabel content8L = new JLabel("<html><div style=\"text-align: center;\">고적답사.<br>향토행사참석<br>체험학습</div></html>", JLabel.CENTER);
		content8L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content8L.setSize((int)(27.89f * Util.A4_WEIGHT) - 2, (int)(17.62f * Util.A4_WEIGHT) - 2);
		content8L.setLocation(204, 237);
		
		JLabel content9L = new JLabel("<html><div style=\"text-align: center;\">가족동반여행<br>체험학습</div></html>", JLabel.CENTER);
		content9L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content9L.setSize((int)(27.89f * Util.A4_WEIGHT) - 2, (int)(17.62f * Util.A4_WEIGHT) - 2);
		content9L.setLocation(280, 238);
		
		JLabel content10L = new JLabel("<html><div style=\"text-align: center;\">국외<br>체험학습</div></html>", JLabel.CENTER);
		content10L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content10L.setSize((int)(27.89f * Util.A4_WEIGHT) - 2, (int)(17.62f * Util.A4_WEIGHT) - 2);
		content10L.setLocation(356, 238);
		
		JLabel content11L = new JLabel("기타", JLabel.CENTER);
		content11L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content11L.setSize((int)(27.89f * Util.A4_WEIGHT) - 2, (int)(17.62f * Util.A4_WEIGHT) - 2);
		content11L.setLocation(432, 238);
		
		JLabel content12L = new JLabel(+ grade + "  학년          " + classN + "  반       " + number + "  번      이름  :  " + name);
		content12L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content12L.setSize((int)(139.42f * Util.A4_WEIGHT) - 2, (int)(10.87f * Util.A4_WEIGHT) - 2);
		content12L.setLocation(145, 133);
		
		JLabel content13L = new JLabel(asYear + "년   " + asMonth + " 월   " + asDay + " 일  ~   " + adMonth + " 월   " + adDay + " 일 (총  " + aDays + " 일간)");
		content13L.setFont(new Font("함초롬바탕", Font.PLAIN, 11));
		content13L.setSize((int)(139.42f * Util.A4_WEIGHT) - 2, (int)(16.86f * Util.A4_WEIGHT) - 2);
		content13L.setLocation(145, 162);
		
		JLabel content14L = new JLabel(year + "년    " + month + "월    " + day + "일", JLabel.CENTER);
		content14L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content14L.setSize(150, 20);
		content14L.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 75, 590);
		
		JLabel content15L = new JLabel("신청인    학");
		content15L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content15L.setSize(230, 20);
		content15L.setLocation(282, 625);
		
		JLabel content16L = new JLabel("생 :");
		content16L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content16L.setSize(60, 20);
		content16L.setLocation(363, 625);
		
		JLabel content17L = new JLabel(name + "        인", JLabel.RIGHT);
		content17L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content17L.setSize(140, 20);
		content17L.setLocation(365, 625);
		
		JLabel content18L = new JLabel("학부모 :");
		content18L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content18L.setSize(230, 20);
		content18L.setLocation(337, 650);
		
		JLabel content19L = new JLabel(parentName + "        인", JLabel.RIGHT);
		content19L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content19L.setSize(140, 20);
		content19L.setLocation(365, 650);
		
		JLabel content20L = new JLabel("화 명 고 등 학 교 장  귀 하");
		content20L.setFont(new Font("함초롬돋움", Font.BOLD, 21));
		content20L.setSize(250, 30);
		content20L.setLocation(55, 687);
		
		add(outsideDotBorder);
		add(titleL);
		add(outsideTableBorder);
		add(insideTableNormalBorder1); add(insideTableNormalBorder2); add(insideTableNormalBorder3);
		add(insideTableDotBorder1); add(insideTableDotBorder2); add(insideTableDotBorder3);
		add(insideTableNormalBorder4); add(insideTableNormalBorder5);
		add(insideTableDotBorder4);
		add(content1L); add(content2L); add(content3L); add(content4L); add(content5L);
		add(content6L); add(content7L); add(content8L); add(content9L); add(content10L);
		add(content11L); add(content12L); add(content13L); add(content14L); add(content15L);
		add(content16L); add(content17L); add(content18L); add(content19L); add(content20L);
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
