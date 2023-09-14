package english.transcription.gui;

import english.transcription.gui.components.TButton;
import english.transcription.gui.components.TFont;
import english.transcription.gui.components.TFrame;
import english.transcription.gui.listeners.GetTranscriptionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.Objects;

import static english.transcription.gui.components.TButton.*;

public class TranscriptionAppGUI {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private GridBagConstraints constraints;
    private JLabel input;
    private JLabel output;
    private JTextArea inputWords;
    private JTextArea outputWords;
    private JScrollPane inputScrollPane;
    private JScrollPane outputScrollPane;
    private JPanel buttonsPanel;
    private final ClassLoader classLoader = getClass().getClassLoader();

    public TranscriptionAppGUI() {
        createComponents();

        addComponents();

        setupFrame();
    }

    private void createComponents() {
        // Create frame
        mainFrame = new JFrame("English Transcriptor");

        // Create GridBagConstraints
        constraints = new GridBagConstraints();

        // Create labels
        input = new JLabel("Input");
        output = new JLabel("Output");

        // Create text areas
        inputWords = new JTextArea();
        outputWords = new JTextArea();

        // Create scroll panes
        inputScrollPane = new JScrollPane(inputWords);
        outputScrollPane = new JScrollPane(outputWords);

        // Create panels
        mainPanel = new JPanel();
        buttonsPanel = creatingButtonsPanel();
    }

    private void addComponents() {
        mainPanel.setLayout(new GridBagLayout());

        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.NORTHWEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton menu = createMenuButton();
        mainPanel.add(menu, constraints);

        // Add components to the panel using GridBagConstraints and adjusting
        input.setFont(TFont.titleFont);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        mainPanel.add(input, constraints);

        output.setFont(TFont.titleFont);
        constraints.insets = new Insets(0, 50, 20, 0);
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        mainPanel.add(output, constraints);

        inputWords.setLineWrap(true);
        inputWords.setFont(TFont.textFont);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        mainPanel.add(inputScrollPane, constraints);

        outputWords.setLineWrap(true);
        outputWords.setEditable(false);
        outputWords.setFont(TFont.textFont);
        constraints.insets = new Insets(0, 50, 20, 0);
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        mainPanel.add(outputScrollPane, constraints);

        buttonsPanel = creatingButtonsPanel();
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        mainPanel.add(buttonsPanel, constraints);
    }

    private JPanel creatingButtonsPanel() {
        JPanel newButtonsPanel = new JPanel();

        // Adjusting and adding panel of buttons
        newButtonsPanel.setLayout(new GridBagLayout());

        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 2;
        constraints.gridy = 1;
        ImageIcon tickIcon = new ImageIcon(Objects.requireNonNull(classLoader.getResource("images/tick.png")));
        ImageIcon gearIcon = new ImageIcon(Objects.requireNonNull(classLoader.getResource("images/gear.gif")));
        tickIcon = resizeImageIcon(tickIcon, 60, 60);
        gearIcon = resizeImageIcon(gearIcon, 60, 60);
        JLabel tickImage = new JLabel(tickIcon);
        JLabel gearImage = new JLabel(gearIcon);
        tickImage.setVisible(false);
        gearImage.setVisible(false);
        newButtonsPanel.add(tickImage, constraints);
        newButtonsPanel.add(gearImage, constraints);

        JButton getTranscriptionButton = TButton.createCommonButton("Get transcription!");
        getTranscriptionButton.addActionListener(new GetTranscriptionListener(inputWords, outputWords, tickImage, gearImage));
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 1;
        newButtonsPanel.add(getTranscriptionButton, constraints);

        JButton clearInputWords = TButton.createCommonButton("Clear!");
        clearInputWords.addActionListener(e -> inputWords.setText(""));
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        newButtonsPanel.add(clearInputWords, constraints);

        JButton getClipboardButton = TButton.createCommonButton("Get clipboard!");
        getClipboardButton.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(outputWords.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 3;
        newButtonsPanel.add(getClipboardButton, constraints);

        return newButtonsPanel;
    }


    private static ImageIcon resizeImageIcon(ImageIcon icon, int width, int height) {
        Image image = icon.getImage();
        Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        return new ImageIcon(resizedImage);
    }

    private void setupFrame() {
        mainFrame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = (int) (mainFrame.getWidth() * 0.2);
                int height = (int) (mainFrame.getHeight() * 0.5);
                inputScrollPane.setPreferredSize(new Dimension(width, height));
                inputScrollPane.setMaximumSize(new Dimension(width, height));
                outputScrollPane.setPreferredSize(new Dimension(width, height));
                outputScrollPane.setMaximumSize(new Dimension(width, height));
                mainPanel.revalidate();
            }
        });

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                TFrame.serializeIgnoringWords();
            }
        });

        mainFrame.setLocationRelativeTo(null);

        TFrame.setStartingRelativeFrameSize(mainFrame, 0.75f, 0.75f);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainFrame.setEnabled(true);
        mainFrame.add(mainPanel);
    }
}
