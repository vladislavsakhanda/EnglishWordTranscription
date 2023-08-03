package english.transcription.gui;

import english.transcription.gui.listeners.GetTranscriptionListener;

import javax.swing.*;
import java.awt.*;

public class TranscriptionAppGUI {
    private final JFrame frame = new JFrame("Transcription");
    private final JPanel panel = new JPanel();
    private final GridBagConstraints constraints = new GridBagConstraints();
    private final Dimension screenSize = GraphicsEnvironment.
            getLocalGraphicsEnvironment().
            getMaximumWindowBounds().
            getSize();

    public TranscriptionAppGUI() {
        init();
    }

    private void init() {
        constraints.fill = GridBagConstraints.BOTH;
        panel.setLayout(new GridBagLayout());

        screenSize.width = (int) (screenSize.width * 0.75);
        screenSize.height = (int) (screenSize.height * 0.75);

        // Create labels
        JLabel input = new JLabel("Input");
        JLabel output = new JLabel("Output");

        // Create text areas
        JTextArea inputWords = new JTextArea();
        JTextArea outputWords = new JTextArea();

        // Create scroll panes
        JScrollPane inputScrollPane = new JScrollPane(inputWords);
        JScrollPane outputScrollPane = new JScrollPane(outputWords);

        // Create button
        JButton getTranscriptionButton = new JButton("Get transcription!");

        // Add components to the panel using GridBagConstraints and adjusting
        constraints.gridx = 1;
        constraints.gridy = 2;
        getTranscriptionButton.addActionListener(new GetTranscriptionListener(inputWords, outputWords));
        panel.add(getTranscriptionButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.weightx = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 20, 20, 200);
        panel.add(input, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.insets = new Insets(0, 20, 20, 20);
        panel.add(output, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        inputWords.setLineWrap(true);
        inputScrollPane.setPreferredSize(new Dimension(300, 400));
        panel.add(inputScrollPane, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        outputWords.setLineWrap(true);
        outputWords.setEditable(false);
        outputScrollPane.setPreferredSize(new Dimension(300, 400));
        panel.add(outputScrollPane, constraints);

        frame.setSize(screenSize);
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.add(panel);
    }


}
