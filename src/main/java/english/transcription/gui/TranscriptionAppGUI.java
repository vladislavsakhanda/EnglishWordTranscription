package english.transcription.gui;

import english.transcription.gui.listeners.GetTranscriptionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Objects;

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
    private JButton clearInputWords;
    private JButton getTranscriptionButton;
    private JButton getClipboardButton;
    private static final Dimension buttonSize = new Dimension(200, 50);
    private JPanel buttonsPanel;
    private final Font buttonFont = new Font("Arial", Font.BOLD, 16);
    private final Font titleFont = new Font("Arial", Font.BOLD, 25);
    private final Font textFont = new Font("Arial", Font.PLAIN, 14);
    private final ImageIcon successIcon = new ImageIcon("target/classes/images/tick.png");
    private JLabel tickImage;
    private JLabel gearImage;
    private ImageIcon tickIcon;
    private ImageIcon gearIcon;
    private final ClassLoader classLoader = getClass().getClassLoader();

    public TranscriptionAppGUI() {
        init();
    }

    private void init() {
        createComponents();

        constraints.fill = GridBagConstraints.CENTER;
        panel.setLayout(new GridBagLayout());

        screenSize.width = (int) (screenSize.width * 0.75);
        screenSize.height = (int) (screenSize.height * 0.75);

        addComponents();

        setupFrame();
    }

    private void createComponents() {
        // Create frame
        frame = new JFrame("Transcription");

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
        buttonsPanel = new JPanel();

        // Create buttons
        clearInputWords = new JButton("Clear!");
        getTranscriptionButton = new JButton("Get transcription!");
        getClipboardButton = new JButton("Get clipboard!");

        // Create icons
        tickIcon = new ImageIcon(Objects.requireNonNull(classLoader.getResource("images/tick.png")));
        gearIcon = new ImageIcon(Objects.requireNonNull(classLoader.getResource("images/gear.gif")));
    }

    private void addComponents() {
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

        // Adjusting and adding panel of buttons
        buttonsPanel.setLayout(new GridBagLayout());

        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 2;
        constraints.gridy = 1;
        tickIcon = resizeImageIcon(tickIcon,60, 60);
        gearIcon = resizeImageIcon(gearIcon,60, 60);
        tickImage = new JLabel(tickIcon);
        gearImage = new JLabel(gearIcon);
        tickImage.setVisible(false);
        gearImage.setVisible(false);
        buttonsPanel.add(tickImage, constraints);
        buttonsPanel.add(gearImage, constraints);

        getTranscriptionButton.addActionListener(new GetTranscriptionListener(inputWords, outputWords, tickImage, gearImage));
        getTranscriptionButton.setPreferredSize(buttonSize);
        getTranscriptionButton.setFont(buttonFont);
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 1;
        buttonsPanel.add(getTranscriptionButton, constraints);

        clearInputWords.addActionListener(e -> inputWords.setText(""));
        clearInputWords.setPreferredSize(buttonSize);
        clearInputWords.setFont(buttonFont);
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 2;
        buttonsPanel.add(clearInputWords, constraints);

        getClipboardButton.addActionListener(e -> {
            StringSelection stringSelection = new StringSelection(outputWords.getText());
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        });
        getClipboardButton.setPreferredSize(buttonSize);
        getClipboardButton.setFont(buttonFont);
        constraints.insets = new Insets(0, 20, 20, 0);
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.gridx = 1;
        constraints.gridy = 3;
        buttonsPanel.add(getClipboardButton, constraints);

        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        panel.add(buttonsPanel, constraints);
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
        frame.getContentPane().setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setEnabled(true);
        frame.add(panel);
    }
}
