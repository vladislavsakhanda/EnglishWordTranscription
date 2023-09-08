package english.transcription.gui;

import english.transcription.gui.components.TButton;
import english.transcription.gui.listeners.GetTranscriptionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import java.util.Objects;

import static english.transcription.gui.components.TButton.*;

public class TranscriptionAppGUI {
    private JFrame frame;
    private JPanel panel;
    private GridBagConstraints constraints;
    private final Dimension screenSize = GraphicsEnvironment.
            getLocalGraphicsEnvironment().
            getMaximumWindowBounds().
            getSize();
    private JLabel input;
    private JLabel output;
    private JTextArea inputWords;
    private JTextArea outputWords;
    private JScrollPane inputScrollPane;
    private JScrollPane outputScrollPane;
    private JPanel buttonsPanel;
    private final Font titleFont = new Font("Arial", Font.BOLD, 25);
    private final Font textFont = new Font("Arial", Font.PLAIN, 14);

    private final ClassLoader classLoader = getClass().getClassLoader();

    public TranscriptionAppGUI() {
        init();
    }

    private void init() {
        createComponents();

        constraints.fill = GridBagConstraints.CENTER;

        screenSize.width = (int) (screenSize.width * 0.75);
        screenSize.height = (int) (screenSize.height * 0.75);

        addComponents();

        setupFrame();
    }

    private void createComponents() {
        // Create frame
        frame = new JFrame("English Transcriptor");

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
        panel = new JPanel();
        buttonsPanel = creatingButtonsPanel();
    }

    private void addComponents() {
        panel.setLayout(new GridBagLayout());

        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.NORTHWEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        JButton menu = createMenuButton();
        panel.add(menu, constraints);

        // Add components to the panel using GridBagConstraints and adjusting
        input.setFont(titleFont);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(input, constraints);

        output.setFont(titleFont);
        constraints.insets = new Insets(0, 50, 20, 0);
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        panel.add(output, constraints);

        inputWords.setLineWrap(true);
        inputWords.setFont(textFont);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(inputScrollPane, constraints);

        outputWords.setLineWrap(true);
        outputWords.setEditable(false);
        outputWords.setFont(textFont);
        constraints.insets = new Insets(0, 50, 20, 0);
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(outputScrollPane, constraints);

        buttonsPanel = creatingButtonsPanel();
        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(buttonsPanel, constraints);
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
//        frame.getContentPane().setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.add(panel);
    }
}
