import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

interface ColorCallback {
	void callback(Color c);
}

@SuppressWarnings("serial")
public class D_ColorPicker extends JDialog {
	
	private JPanel preview;
	
	private JSlider sliderR;
	private JSlider sliderG;
	private JSlider sliderB;
	
	private JTextField tfieldR;
	private JTextField tfieldG;
	private JTextField tfieldB;
	
	public D_ColorPicker(JDialog parent, Color currentColor, final ColorCallback cback) {
		super(parent, "색깔 고르기", true);
		setSize(300, 125);
		setLocation(100, 100);
		
		preview = new JPanel();
		preview.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
		preview.setPreferredSize(new Dimension(50, 1));
		preview.setBackground(currentColor);
		
		JPanel colorSelect = new JPanel();
		colorSelect.setLayout(null);
		
		JLabel labelR = new JLabel("R : ");
		labelR.setSize(20, 16); labelR.setLocation(5, 5);
		colorSelect.add(labelR);
		sliderR = new JSlider(0, 255, currentColor.getRed());
		sliderR.setSize(185, 20); sliderR.setLocation(25, 5);
		sliderR.addChangeListener(new SliderListener());
		colorSelect.add(sliderR);
		tfieldR = new JTextField(String.valueOf(currentColor.getRed()));
		tfieldR.setSize(30, 20); tfieldR.setLocation(210, 5);
		tfieldR.addKeyListener(new TextFieldListener());
		colorSelect.add(tfieldR);
		
		JLabel labelG = new JLabel("G : ");
		labelG.setSize(20, 16); labelG.setLocation(5, 25);
		colorSelect.add(labelG);
		sliderG = new JSlider(0, 255, currentColor.getGreen());
		sliderG.setSize(185, 20); sliderG.setLocation(25, 25);
		sliderG.addChangeListener(new SliderListener());
		colorSelect.add(sliderG);
		tfieldG = new JTextField(String.valueOf(currentColor.getGreen()));
		tfieldG.setSize(30, 20); tfieldG.setLocation(210, 25);
		tfieldG.addKeyListener(new TextFieldListener());
		colorSelect.add(tfieldG);
		
		JLabel labelB = new JLabel("B : ");
		labelB.setSize(20, 16); labelB.setLocation(5, 45);
		colorSelect.add(labelB);
		sliderB = new JSlider(0, 255, currentColor.getBlue());
		sliderB.setSize(185, 20); sliderB.setLocation(25, 45);
		sliderB.addChangeListener(new SliderListener());
		colorSelect.add(sliderB);
		tfieldB = new JTextField(String.valueOf(currentColor.getBlue()));
		tfieldB.setSize(30, 20); tfieldB.setLocation(210, 45);
		tfieldB.addKeyListener(new TextFieldListener());
		colorSelect.add(tfieldB);
		
		JButton apply = new JButton("적용");
		// TODO apply 서식적용 필요
		apply.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent event) {
			cback.callback(new Color(sliderR.getValue(), sliderG.getValue(), sliderB.getValue()));
			setVisible(false);
			dispose();
		}});
		
		add(preview, "West");
		add(colorSelect, "Center");
		add(apply, "South");
		
		setResizable(false);
		setVisible(true);
	}
	
	// For the Test
//	public static void main(String[] args) {
//		new D_ColorPicker(new JFrame(), new Color(172, 135, 111), new Callback() {
//			@Override
//			public void callback(Color c) {
//				System.out.println(c);
//			}
//		});
//	}
	
	private class TextFieldListener extends KeyAdapter {
		public void keyReleased(KeyEvent arg0) {
			JTextField tf = (JTextField)arg0.getSource();
			int n = -1;
			try { n = Integer.parseInt(tf.getText()); } catch(NumberFormatException e) { tf.setText("0"); }
			if(n > 255)
				tf.setText("255");
			
			int r = Integer.parseInt(tfieldR.getText());
			int g = Integer.parseInt(tfieldG.getText());
			int b = Integer.parseInt(tfieldB.getText());
			
			sliderR.setValue(r);
			sliderG.setValue(g);
			sliderB.setValue(b);
			
			preview.setBackground(new Color(r, g, b));
		}
	}
	
	private class SliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			preview.setBackground(new Color(sliderR.getValue(), sliderG.getValue(), sliderB.getValue()));
			tfieldR.setText(String.valueOf(sliderR.getValue()));
			tfieldG.setText(String.valueOf(sliderG.getValue()));
			tfieldB.setText(String.valueOf(sliderB.getValue()));
		}
	}
}