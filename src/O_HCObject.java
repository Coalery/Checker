import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class O_HCObject extends JLabel {
	
	// 이름, 위치, 크기, 글꼴, 글꼴 크기, 글꼴 형태
	
	private String name;
	
	private BorderType borderType;
	private int borderThickness;
	private Color borderColor;
	
	private boolean fixShape;
	
	public O_HCObject(String name) { this(name, 10, 10, 150, 20, "텍스트를 입력하세요.", new Font("굴림", Font.PLAIN, 13), new Color(51, 51, 51), SwingConstants.LEFT, null, 0, null, new Color(255, 255, 255), true, false); }
	
	public O_HCObject(String name, int locX, int locY, int width, int height, String text, Font font, Color textColor, int textAlign, BorderType borderType, int borderThickness, Color borderColor, Color bgColor, boolean bgOpaque, boolean fixShape) {
		this.name = name;
		setLocation(locX, locY);
		setSize(width, height);
		setText(text);
		setFont(font);
		setForeground(textColor);
		setHorizontalAlignment(textAlign);
		this.borderType = borderType;
		this.borderThickness = borderThickness;
		this.borderColor = borderColor;
		setBackground(bgColor);
		setOpaque(bgOpaque);
		this.fixShape = fixShape;
		
		applyBorder();
	}
	
	public String getHCName() { return name; }
	public BorderType getBorderType() { return borderType; }
	public int getBorderThickness() { return borderThickness; }
	public Color getBorderColor() { return borderColor; }
	public boolean isFixShape() { return fixShape; }
	
	public void setHCName(String name) { this.name = name; }
	public void setBorderType(BorderType borderType) {
		this.borderType = borderType;
		
		if(getBorder() == null) {
			borderColor = Color.BLACK;
			borderThickness = 1;
		}
		applyBorder();
	}
	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
		applyBorder();
	}
	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		applyBorder();
	}
	public void setFixShape(boolean fixShape) { this.fixShape = fixShape; }
	
	private void applyBorder() {
		if(borderType == BorderType.LINE)
			setBorder(BorderFactory.createLineBorder(borderColor, borderThickness));
		else if(borderType == BorderType.DASHED)
			setBorder(BorderFactory.createDashedBorder(borderColor, borderThickness, 4, 2, false));
		else if(borderType == BorderType.DOTTED)
			setBorder(BorderFactory.createDashedBorder(borderColor, borderThickness, 1, 1, false));
		else if(borderType == BorderType.DOUBLE_LINE)
			setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(borderColor, borderThickness), BorderFactory.createLineBorder(new Color(255, 255, 255, 255), borderThickness)), BorderFactory.createLineBorder(borderColor, borderThickness)));
		else if(borderType == null)
			setBorder(null);
		repaint();
	}
	
	public enum Options {
		NAME(0), POS_X(1), POS_Y(2), WIDTH(3), HEIGHT(4), TEXT(5), FONT(6), TEXT_COLOR(7), TEXT_ALIGN(8), 
		BORDER_TYPE(9), BORDER_THICK(10), BORDER_COLOR(11), BACKGROUND_COLOR(12), BACKGROUND_OPAQUE(13), FIX_SHAPE(14);
		
		private final int value;
		private Options(int value) { this.value = value; }
		public int getValue() { return value; }
	}
	
	public enum BorderType {
		LINE("실선"), DOTTED("점선"), DASHED("파선"), DOUBLE_LINE("이중 실선");
		
		private final String value;
		private BorderType(String value) { this.value = value; }
		public String getValue() { return value; }
		
		public static BorderType valueOfLabel(String label) {
			for(BorderType b : values())
				if(b.getValue().equals(label))
					return b;
			return null;
		}
	}
}
