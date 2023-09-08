package english.transcription.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TButton {
    private static final Dimension commonButtonSize = new Dimension(200, 50);
    private static final Font commonButtoFont = new Font("Arial", Font.BOLD, 16);
    private static final Dimension menuButtonSize = new Dimension(100, 30);
    private static final Font menuButtoFont = new Font("Arial", Font.BOLD, 13);

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
                JOptionPane.showMessageDialog(null, "Ви вибрали Пункт 1"));

        secondItem.addActionListener(e ->
                JOptionPane.showMessageDialog(null, "Ви вибрали Пункт 2"));

        JButton menuButton = new JButton("Menu");
        adjustMenuButton(menuButton);
        menuButton.setComponentPopupMenu(popupMenu);

        menuButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) { // Перевірка лівої клавіші миші
                    Component mouseComponent = e.getComponent();
                    System.out.println(mouseComponent);
                    popupMenu.setSize(mouseComponent.getPreferredSize().width, popupMenu.getHeight());
                    popupMenu.show(mouseComponent, 0,
                            (int) (mouseComponent.getAlignmentY() + mouseComponent.getHeight() * 1.05));
                }
            }
        });

        return menuButton;
    }
}
