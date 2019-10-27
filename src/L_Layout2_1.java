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
	
	public L_Layout2_1(int number, String name, String reason, int asYear, int asMonth, int asDay, int adMonth, int adDay, int aDays, int year, int month, int day, String parentName, String aType, String aMethod, String teacher) {
		setLayout(null);
		setBackground(Color.WHITE);
		
		JPanel outsideDotBorder = new JPanel();
		outsideDotBorder.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK), BorderFactory.createLineBorder(Color.WHITE)), BorderFactory.createLineBorder(Color.BLACK)));
		outsideDotBorder.setSize((int)(180 * Util.A4_WEIGHT), (int)(247 * Util.A4_WEIGHT));
		outsideDotBorder.setLocation((int)(15 * Util.A4_WEIGHT), (int)(25 * Util.A4_WEIGHT));
		outsideDotBorder.setBackground(Color.WHITE);
		outsideDotBorder.setOpaque(false);
		
		JLabel titleL = new JLabel("결       석       계", JLabel.CENTER);
		titleL.setFont(new Font("함초롬돋움", Font.BOLD, 30));
		titleL.setSize(300, 100);
		titleL.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 150, 80);
		
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
		
		JLabel content2L = new JLabel("1.  결석  사유  :    " + reason);
		content2L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content2L.setSize(380, 20);
		content2L.setLocation(85, 245);
		
		JLabel content3L = new JLabel("2.  결석  기간  :    " + asYear + "년    " + asMonth + "월    " + asDay + "일  ~    " + adMonth + "월    " + adDay + "일 (총  " + aDays + " 일간)");
		content3L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content3L.setSize(430, 20);
		content3L.setLocation(85, 269);
		
		JLabel content4L = new JLabel("3.  서류  첨부  :    유 (    ○    ),  무 (      )");
		content4L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content4L.setSize(380, 20);
		content4L.setLocation(85, 293);
		
		JLabel content5L = new JLabel(year + "년   " + month + "월        " + day +"일", JLabel.CENTER);
		content5L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content5L.setSize(200, 20);
		content5L.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - 100, 345);
		
		JLabel content6L = new JLabel("보호자(학생과  관계:  모  )             " + parentName + "          (인)");
		content6L.setFont(new Font("함초롬바탕", Font.PLAIN, 12));
		content6L.setSize(300, 20);
		content6L.setLocation(213, 382);
		
		JPanel insideDotBorder = new JPanel();
		insideDotBorder.setBorder(new DashedBorder());
		insideDotBorder.setSize((int)(154 * Util.A4_WEIGHT), (int)(88.23 * Util.A4_WEIGHT));
		insideDotBorder.setLocation((int)(Util.A4_WIDTH * Util.A4_WEIGHT / 2) - (int)(154 * Util.A4_WEIGHT / 2), 425);
		insideDotBorder.setBackground(Color.WHITE);
		insideDotBorder.setOpaque(false);
		
		JLabel content7L = new JLabel("※  담임교사의  의견서");
		content7L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content7L.setSize(250, 20);
		content7L.setLocation(97, 457);
		
		JLabel content8L = new JLabel("1. 결석 유형  :    " + aType);
		content8L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content8L.setSize(250, 20);
		content8L.setLocation(105, 488);
		
		JLabel content9L_1 = new JLabel("2. 내");
		content9L_1.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content9L_1.setSize(250, 20);
		content9L_1.setLocation(105, 508);
		
		JLabel content9L_2 = new JLabel("용  :    " + reason);
		content9L_2.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content9L_2.setSize(250, 20);
		content9L_2.setLocation(163, 508);
		
		JLabel content10L = new JLabel("3. 확인 방법  :    " + aMethod);
		content10L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content10L.setSize(250, 20);
		content10L.setLocation(105, 548);
		
		JLabel content11L = new JLabel("담임교사", JLabel.RIGHT);
		content11L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content11L.setSize(100, 20);
		content11L.setLocation(247, 590);
		
		JLabel content12L = new JLabel(teacher +"      (인)", JLabel.RIGHT);
		content12L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content12L.setSize(250, 20);
		content12L.setLocation(217, 590);
		
		JLabel content13L = new JLabel("출결담당교사", JLabel.RIGHT);
		content13L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content13L.setSize(150, 20);
		content13L.setLocation(197, 610);

		JLabel content14L = new JLabel("(인)", JLabel.RIGHT);
		content14L.setFont(new Font("함초롬바탕", Font.PLAIN, 13));
		content14L.setSize(250, 20);
		content14L.setLocation(217, 610);
		
		JLabel footer = new JLabel("화명고등학교장  귀하", JLabel.CENTER);
		footer.setFont(new Font("함초롬바탕", Font.BOLD, 22));
		footer.setSize(250, 30);
		footer.setLocation(34, 698);
		
		add(outsideDotBorder);
		add(titleL); add(numberL); add(nameL);
		add(content1L); add(content2L); add(content3L); add(content4L); add(content5L); 
		add(content6L); add(content7L); add(content8L); add(content9L_1); add(content9L_2); add(content10L);
		add(content11L); add(content12L); add(content13L); add(content14L);
		add(insideDotBorder); add(footer);
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
