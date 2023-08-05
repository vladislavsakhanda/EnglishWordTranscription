package english.transcription.gui;

import english.transcription.gui.listeners.GetTranscriptionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

        // Create buttons
        JButton clearInputWords = new JButton("Clear!");
        JButton getTranscriptionButton = new JButton("Get transcription!");
        JButton getClipboardButton = new JButton("Get clipboard!");


        // Add components to the panel using GridBagConstraints and adjusting
        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
//        constraints.insets = new Insets(0, 20, 20, 200);
        input.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(input, constraints);

        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        output.setFont(new Font("Arial", Font.BOLD, 20));
        constraints.insets = new Insets(0, 20, 20, 20);
        panel.add(output, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        inputWords.setLineWrap(true);
        panel.add(inputScrollPane, constraints);

        constraints.gridx = 5;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.anchor = GridBagConstraints.NORTHWEST;
        outputWords.setLineWrap(true);
        outputWords.setEditable(false);
        panel.add(outputScrollPane, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        getTranscriptionButton.addActionListener(new GetTranscriptionListener(inputWords, outputWords));
        panel.add(getTranscriptionButton, constraints);

        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        clearInputWords.addActionListener(e -> inputWords.setText(""));
        panel.add(clearInputWords, constraints);

        constraints.gridx = 6;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        getClipboardButton.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(outputWords.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        panel.add(getClipboardButton, constraints);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = (int) (frame.getWidth() * 0.25);
                int height = (int) (frame.getHeight() * 0.6);
                inputScrollPane.setPreferredSize(new Dimension(width, height));
                outputScrollPane.setPreferredSize(new Dimension(width, height));
                panel.revalidate();
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(screenSize.width, screenSize.height));
        frame.setSize(screenSize);
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.add(panel);
    }
}
