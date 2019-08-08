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
public class L_Layout1_3 extends JPanel {
	
	public L_Layout1_3(int number, String name, int asYear, int asMonth, int asDay, int adMonth, int adDay, int aDays, int year, int month, int day, String parentName) {
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
		
		JLabel content1L = new JLabel("학부모  동의서", JLabel.CENTER);
		content1L.setFont(new Font("함초롬돋움", Font.PLAIN, 31));
		content1L.setSize(200, 40);
		content1L.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 100, 120);
		
		JLabel content2L = new JLabel("학");
		content2L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content2L.setSize(50, 20);
		content2L.setLocation(323, 197);
		
		JLabel content3L = new JLabel("번 :  " + grade + " 학년   " + classN + " 반   " + number + " 번");
		content3L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content3L.setSize(200, 20);
		content3L.setLocation(359, 197);
		
		JLabel content4L = new JLabel("성");
		content4L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content4L.setSize(50, 20);
		content4L.setLocation(323, 222);
		
		JLabel content5L = new JLabel("명 :  " + name);
		content5L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content5L.setSize(150, 20);
		content5L.setLocation(359, 222);
		
		JLabel content6L = new JLabel("위  학생은  " + asYear + "년   " + asMonth + "월    " + asDay + "일부터    " + adMonth + "월    " + adDay + "일까지  체류하게");
		content6L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content6L.setSize(600, 20);
		content6L.setLocation(80, 295);
		
		JLabel content7L = new JLabel("되었습니다. (총  " + aDays + " 일간)");
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content7L.setSize(400, 20);
		content7L.setLocation(65, 327);
		
		JLabel content8L = new JLabel("이 기간 동안 학생의 안전과 지도는 전적으로 보호자의 자율적 판단에");
		content8L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content8L.setSize(500, 20);
		content8L.setLocation(80, 359);
		
		JLabel content9L = new JLabel("따라 행해지며, 학교 측에 일체의 책임을 묻지 않음에 동의합니다.");
		content9L.setFont(new Font("함초롬바탕", Font.PLAIN, 14));
		content9L.setSize(500, 20);
		content9L.setLocation(65, 391);
		
		JLabel content10L = new JLabel(year + "년     " + month + "월      " + day + "일", JLabel.CENTER);
		content10L.setFont(new Font("함초롬바탕", Font.PLAIN, 15));
		content10L.setSize(200, 20);
		content10L.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 100, 500);
		
		JLabel content11L = new JLabel("보호자         " + parentName + "        (인)");
		content11L.setFont(new Font("함초롬바탕", Font.PLAIN, 15));
		content11L.setSize(220, 20);
		content11L.setLocation(300, 550);
		
		JLabel content12L = new JLabel("화 명 고 등 학 교 장  귀 하");
		content12L.setFont(new Font("함초롬돋움", Font.BOLD, 24));
		content12L.setSize(300, 30);
		content12L.setLocation(60, 610);
		
		add(outsideDotBorder);
		add(content1L); add(content2L); add(content3L); add(content4L); add(content5L);
		add(content6L); add(content7L); add(content8L); add(content9L); add(content10L);
		add(content11L); add(content12L);
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