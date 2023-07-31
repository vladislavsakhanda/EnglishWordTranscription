package gui;

import javax.swing.*;
import java.awt.*;

public class TranscriptionApp {
    JFrame frame;
    JPanel panel;
    GridBagConstraints constraints;

    public TranscriptionApp() {
        init();
    }

    private void init() {
        frame = new JFrame("Transcription");
        panel = new JPanel();
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        panel.setLayout(new GridBagLayout());

        Dimension screenSize = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getMaximumWindowBounds().
                getSize();
        screenSize.width = screenSize.width / 2;
        screenSize.height = screenSize.height / 2;


        JLabel inputTitle = new JLabel("Input");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 20, 20, 200);
        panel.add(inputTitle, constraints);

        JLabel outputTitle = new JLabel("Output");
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 20, 20, 20);
        panel.add(outputTitle, constraints);

        JTextArea inputWords = new JTextArea();
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        inputWords.setLineWrap(true);
        inputWords.setPreferredSize(new Dimension(200, 200));
        panel.add(inputWords, constraints);

        JTextArea outputWords = new JTextArea();
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        outputWords.setLineWrap(true);
        outputWords.setPreferredSize(new Dimension(200, 200));
        panel.add(outputWords, constraints);

        JButton getTranscriptionButton = new JButton("Get transcription!");
        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(getTranscriptionButton, constraints);

        //
        frame.setSize(screenSize);
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.add(panel);
    }

    public static void main(String[] args) {
        new TranscriptionApp();
    }
}
