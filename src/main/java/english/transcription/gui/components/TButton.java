package english.transcription.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TButton {
    private static final Dimension commonButtonSize = new Dimension(200, 50);
    private static final Font commonButtoFont = new Font("Arial", Font.BOLD, 16);
    private static final Dimension menuButtonSize = new Dimension(120, 40);
    private static final Font menuButtoFont = new Font("Arial", Font.BOLD, 15);
    private static final Dimension chooseTranscriptionButtonSize = new Dimension(130, 30);
    private static final Font chooseTranscriptionButtoFont = new Font("Arial", Font.BOLD, 12);

    public static void adjustChooseTranscriptionButton(JButton jButton) {
        jButton.setPreferredSize(chooseTranscriptionButtonSize);
        jButton.setFont(chooseTranscriptionButtoFont);
        jButton.setFocusable(false);
    }

    public static JButton createChooseTranscriptionButton(String text) {
        JButton button = new JButton(text);
        adjustChooseTranscriptionButton(button);
        return button;
    }

    public static void adjustCommonButton(JButton jButton) {
        jButton.setPreferredSize(commonButtonSize);
        jButton.setFont(commonButtoFont);
        jButton.setFocusable(false);
    }

    public static void adjustMenuButton(JButton jButton) {
        jButton.setPreferredSize(menuButtonSize);
        jButton.setFont(menuButtoFont);
        jButton.setFocusable(false);
    }

    public static JButton createCommonButton(String text) {
        JButton button = new JButton(text);
        adjustCommonButton(button);
        return button;
    }

    public static JButton createMenuButton() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem firstItem = new JMenuItem("Ignoring words");
        firstItem.setPreferredSize(menuButtonSize);
        popupMenu.add(firstItem);

        JMenuItem secondItem = new JMenuItem("Exit");
        secondItem.setPreferredSize(menuButtonSize);
        popupMenu.add(secondItem);

        firstItem.addActionListener(e ->
        {
            TFrame.invokeIgnoringWordsFrame();
        });

        secondItem.addActionListener(e -> System.exit(0));

        JButton menuButton = new JButton("Menu");
        adjustMenuButton(menuButton);
        menuButton.setComponentPopupMenu(popupMenu);

        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                popupMenu.setVisible(false);
                e.consume();

                if (SwingUtilities.isLeftMouseButton(e) || SwingUtilities.isRightMouseButton(e)) {
                    Component mouseComponent = e.getComponent();
                    popupMenu.setSize(mouseComponent.getPreferredSize().width, popupMenu.getHeight());
                    popupMenu.show(mouseComponent, 0,
                            (int) (mouseComponent.getAlignmentY() + mouseComponent.getHeight() * 1.05));
                }
            }
        });

        return menuButton;
    }
}
