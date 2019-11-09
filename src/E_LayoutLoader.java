import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class E_LayoutLoader extends JPanel {
	public E_LayoutLoader(String filePath, HashMap<String, Object> substitutions) {
		setLayout(null);
		setBackground(Color.WHITE);
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath)), "UTF8"));
			
			String line = null;
			while((line = br.readLine()) != null) {
				String[] splits = line.split(";");
				
				String name = splits[O_HCObject.Options.NAME.getValue()];
				int x = Integer.parseInt(splits[O_HCObject.Options.POS_X.getValue()]);
				int y = Integer.parseInt(splits[O_HCObject.Options.POS_Y.getValue()]);
				int width = Integer.parseInt(splits[O_HCObject.Options.WIDTH.getValue()]);
				int height = Integer.parseInt(splits[O_HCObject.Options.HEIGHT.getValue()]);
				
				String text = splits[O_HCObject.Options.TEXT.getValue()];
				
				Iterator<String> it = substitutions.keySet().iterator();
				while(it.hasNext()) {
					String next = it.next();
					text = text.replace(next, substitutions.get(next).toString());
				}
				
				Font font = new Font("굴림", Font.PLAIN, 13);
				Color textColor = new Color(51, 51, 51);
				int textAlign = Util.stringToAlignType(splits[O_HCObject.Options.TEXT_ALIGN.getValue()]);
				if(!text.equals("")) {
					font = Util.stringToFont(splits[O_HCObject.Options.FONT.getValue()]);
					textColor = Util.stringToColor(splits[O_HCObject.Options.TEXT_COLOR.getValue()]);
				}
				
				O_HCObject.BorderType borderType = null;
				if(!splits[O_HCObject.Options.BORDER_TYPE.getValue()].equals(""))
					borderType = O_HCObject.BorderType.valueOfLabel(splits[O_HCObject.Options.BORDER_TYPE.getValue()]);
				int borderThickness = 0;
				Color borderColor = null;
				if(borderType != null) {
					borderThickness = Integer.parseInt(splits[O_HCObject.Options.BORDER_THICK.getValue()]);
					borderColor = Util.stringToColor(splits[O_HCObject.Options.BORDER_COLOR.getValue()]);
				}
				
				Color bgColor = Util.stringToColor(splits[O_HCObject.Options.BACKGROUND_COLOR.getValue()]);
				boolean bgOpaque = Boolean.valueOf(splits[O_HCObject.Options.BACKGROUND_OPAQUE.getValue()]);
				boolean fixShape = Boolean.valueOf(splits[O_HCObject.Options.FIX_SHAPE.getValue()]);
				
				add(new O_HCObject(name, x, y, width, height, text, font, textColor, textAlign, borderType, borderThickness, borderColor, bgColor, bgOpaque, fixShape));
			}
		} catch(IOException e) {
			e.printStackTrace(System.err);
			Util.showMessage("파일을 불러오던 중 오류가 발생하였습니다.\n> 입출력 오류 발생", JOptionPane.ERROR_MESSAGE);
		} catch(NumberFormatException e) {
			e.printStackTrace(System.err);
			Util.showMessage("파일을 불러오던 중 오류가 발생하였습니다.\n> 문자열에서 숫자로 변환시키는 과정 중 오류 발생", JOptionPane.ERROR_MESSAGE);
		} catch(ArrayIndexOutOfBoundsException e) {
			e.printStackTrace(System.err);
			Util.showMessage("파일을 불러오던 중 오류가 발생하였습니다.\n> 배열의 범위를 넘어서는 오류 발생", JOptionPane.ERROR_MESSAGE);
		} finally {
			if(br != null)
				try { br.close(); } catch(IOException e) {e.printStackTrace(System.err);}
		}
	}
}
