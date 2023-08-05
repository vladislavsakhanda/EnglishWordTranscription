import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class Example {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dynamic Size Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;

        // Create components
        JLabel label = new JLabel("Resize the frame to see dynamic size changes:");
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        // Add components to the panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(label, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        panel.add(scrollPane, constraints);

        // Add the panel to the frame
        frame.add(panel);

        // Add component listener to dynamically adjust sizes
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = (int) (frame.getWidth() * 0.4);
                int height = (int) (frame.getHeight() * 0.4);
                scrollPane.setPreferredSize(new Dimension(width, height));
                panel.revalidate();
            }
        });

        // Set frame properties and make it visible
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

