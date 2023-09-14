package english.transcription.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class TFrame {
    private static JFrame ignoringWordsFrame;
    private static JTextArea ignoringWords;
    private static final ClassLoader classLoader = TFrame.class.getClassLoader();
    private static String targetClassesPath = System.getProperty("user.dir") + File.separator + "EnglishTranscriptor.jar";
    //targetClassesPath = System.getProperty("user.dir") + File.separator + "target" +
    //            File.separator + "classes" + File.separator + "serialized"
    private static Path pathToSerializingFile = Path.of(targetClassesPath + File.separator + "ignoringWords.ser");

    static {
        invokeIgnoringWordsFrame();
    }

    public static String getIgnoringWords() {
        return ignoringWords.getText();
    }

    public static void invokeIgnoringWordsFrame() {
        if (ignoringWordsFrame == null) {
            ignoringWordsFrame = new JFrame("Add Ignoring Words");
            setStartingRelativeFrameSize(ignoringWordsFrame, 0.5f, 0.6f);
            ignoringWordsFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            ignoringWordsFrame.setLocationRelativeTo(null);
            ignoringWordsFrame.setVisible(false);

            ignoringWords = new JTextArea();
            ignoringWords.setLineWrap(true);
            JScrollPane scrollPane = new JScrollPane(ignoringWords);
            ignoringWords.setFont(TFont.textFont);
            ignoringWords.setText(deserializeIgnoringWords());
            ignoringWordsFrame.getContentPane().add(scrollPane);

            ignoringWordsFrame.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentHidden(ComponentEvent e) {
                    serializeIgnoringWords();
                }
            });
        } else {
            serializeIgnoringWords();
            ignoringWordsFrame.setVisible(true);
            ignoringWords.setText(deserializeIgnoringWords());
        }

    }

    public static void serializeIgnoringWords() {
        String userHome = System.getProperty("user.home");
        String fileName = userHome + File.separator + "EnglishTranscriptor" + File.separator + "ignoringWords.ser";

        Path path = Path.of(fileName);

        if (Files.exists(path)) {
            try {
                FileOutputStream fileOut = new FileOutputStream(path.toFile());
                ObjectOutputStream out = new ObjectOutputStream(fileOut);
                out.writeObject(ignoringWords.getText());
                out.close();
                fileOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            creatingSerializingFile();
        }
    }

    private static String deserializeIgnoringWords() {
        String userHome = System.getProperty("user.home");
        String fileName = userHome + File.separator + "EnglishTranscriptor" + File.separator + "ignoringWords.ser";

        Path path = Path.of(fileName);

        if (Files.exists(path)) {
            try {
                FileInputStream fileIn = new FileInputStream(path.toFile());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                String deserializedObject = (String) in.readObject();
                in.close();
                fileIn.close();
                return deserializedObject;
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("File does not exist");
            }
        } else {
            creatingSerializingFile();
        }

        return "";
    }

    private static void creatingSerializingFile() {
        String userHome = System.getProperty("user.home");
        String programDirectory = userHome + File.separator + "EnglishTranscriptor";
        String fileName = "ignoringWords.ser";

        File directory = new File(programDirectory);

        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("Directory " + programDirectory + " has created successfully.");
            } else {
                System.err.println("Error during creating directory " + programDirectory);
                return;
            }
        }

        File file = new File(programDirectory, fileName);

        try {
            if (file.createNewFile()) {
                System.out.println("File " + fileName + " has created successfully.");
            } else {
                System.out.println("File " + fileName + " already exists.");
            }
        } catch (IOException e) {
            System.err.println("Error during creating directory " + fileName + ": " + e.getMessage());
        }
    }


    public static void setStartingRelativeFrameSize(JFrame jFrame, float startingSizeWidth, float startingSizeHeight) {
        Dimension screenSize = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getMaximumWindowBounds().
                getSize();
        screenSize.width = (int) (screenSize.width * startingSizeWidth);
        screenSize.height = (int) (screenSize.height * startingSizeHeight);
        jFrame.setMinimumSize(new Dimension(screenSize.width, screenSize.height));
    }
}
