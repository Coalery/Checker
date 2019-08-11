import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Demo application panel to display a range slider.
 */
@SuppressWarnings("serial")
public class RangeSliderDemo extends JPanel {
	
    private S_RangeSlider rangeSlider = new S_RangeSlider();

    public RangeSliderDemo() {
        setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        setLayout(new GridBagLayout());
        
        rangeSlider.setPreferredSize(new Dimension(240, rangeSlider.getPreferredSize().height));
        rangeSlider.setMinimum(1);
        rangeSlider.setMaximum(7);
        
        // Add listener to update display.
        rangeSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                S_RangeSlider slider = (S_RangeSlider) e.getSource();
                System.out.println(slider.getValue());
                System.out.println(slider.getUpperValue());
            }
        });
        add(rangeSlider      , new GridBagConstraints(0, 2, 2, 1, 0.0, 0.0,
            GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
    }
    
    public void display() {
        // Initialize values.
        rangeSlider.setValue(3);
        rangeSlider.setUpperValue(7);
        
        // Create window frame.
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setTitle("Range Slider Demo");
        
        // Set window content and validate.
        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(this, BorderLayout.CENTER);
        frame.pack();
        
        // Set window location and display.
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    /**
     * Main application method.
     * @param args String[]
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new RangeSliderDemo().display();
            }
        });
    }
}