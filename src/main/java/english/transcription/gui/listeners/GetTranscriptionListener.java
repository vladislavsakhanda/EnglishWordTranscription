package english.transcription.gui.listeners;

import english.transcription.gui.components.TFrame;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GetTranscriptionListener implements ActionListener {
    private final JTextArea inputWords;
    private final JTextArea outputWords;
    private final JLabel tickImage;
    private final JLabel gearImage;
    private final JButton chooseUK;
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "project.properties";

    public GetTranscriptionListener(JTextArea inputWords, JTextArea outputWords, JLabel tickImage, JLabel gearImage, JButton chooseUK) {
        this.inputWords = inputWords;
        this.outputWords = outputWords;
        this.tickImage = tickImage;
        this.gearImage = gearImage;
        this.chooseUK = chooseUK;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("Properties file not found: " + PROPERTIES_FILE);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error loading properties", e);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                tickImage.setVisible(false);
                gearImage.setVisible(true);

                UnaryOperator<String> getTranscriptionFunction =
                        chooseUK.isEnabled() ? GetTranscriptionListener::getTranscriptionUS : GetTranscriptionListener::getTranscriptionUK;

                String output = addTranscriptionsParallel(inputWords.getText(), getTranscriptionFunction);
                outputWords.setText(output);

                return null;
            }

            @Override
            protected void done() {
                tickImage.setVisible(true);
                gearImage.setVisible(false);
            }
        };

        worker.execute();
    }

    /**
     * This method takes a String and provides the transcription for each word in this String using Stream API.
     *
     * @param input Some text that includes english words
     * @return Text with transcriptions of english words
     **/
    private static String addTranscriptionsParallel(String input, UnaryOperator<String> getTranscription) {
        String[][] text = Stream.of(input.split("\n"))
                .map(line -> line.split("\\s"))
                .toArray(String[][]::new);

        String[] ignoringWords = TFrame.getIgnoringWords().split("\\s+");

        Arrays.parallelSetAll(text, i -> {
            Arrays.parallelSetAll(text[i], j -> {
                if (Arrays.stream(ignoringWords).parallel().noneMatch(ignoringWord -> ignoringWord.equalsIgnoreCase(text[i][j])) &&
                        Pattern.matches("\\b[A-Za-z]+\\b", text[i][j])) {
                    String transcription = getTranscription.apply((text[i][j]));
                    return transcription.equals("")
                            ? text[i][j] : text[i][j] + " " + transcription;
                }
                return text[i][j];
            });

            return text[i];
        });

        return Arrays.stream(text)
                .map(row -> String.join(" ", row))
                .collect(Collectors.joining("\n"));
    }

    /**
     * This method takes a String and provides the transcription for each word in this String.
     *
     * @param input Some text that includes english words
     * @return Text with transcriptions of english words
     **/
    @Deprecated
    private static String addTranscriptions(String input) {
        Pattern pattern = Pattern.compile("\\b[A-Za-z]+\\b");
        Matcher matcher = pattern.matcher(input);

        StringBuilder replacedText = new StringBuilder();
        int lastMatchEnd = 0;

        while (matcher.find()) {
            String currentWord = matcher.group();
            String transcription = getTranscriptionUK(currentWord);

            if (transcription != null) {
                replacedText.append(input, lastMatchEnd, matcher.start());
                replacedText.append(currentWord).append(" ").append(transcription);
                lastMatchEnd = matcher.end();
            }
        }

        // Append any remaining text after the last match
        replacedText.append(input.substring(lastMatchEnd));

        return replacedText.toString();
    }

    /**
     * This method takes a String and provides the transcription it.
     *
     * @param englishWord String in English
     * @return UK transcription for a word in format "|transcription|"
     **/
    private static String getTranscriptionUK(String englishWord) {

        try {
            String text = Jsoup.connect(String.format("%s%s",
                            properties.getProperty("transcriptionURL"), englishWord))
                    .timeout(5000)
                    .get()
                    .getElementsByClass("pronWR tooltip pronWidget")
                    .text();

            return Objects.equals(text, "") ? "" : text.substring(text.indexOf('/'), text.lastIndexOf('/') + 1).replace('/', '|');
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * This method takes a String and provides the transcription it.
     *
     * @param englishWord String in English
     * @return US transcription for a word in format "|transcription|"
     **/
    private static String getTranscriptionUS(String englishWord) {
        try {
            String text = Jsoup.connect(String.format("%s%s",
                            properties.getProperty("transcriptionURL"), englishWord))
                    .timeout(5000)
                    .get()
                    .getElementsByClass("pronRH tooltip pronWidget")
                    .text();

            return Objects.equals(text, "") ? "" : text.substring(text.indexOf('/'), text.lastIndexOf('/') + 1).replace('/', '|');
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "";
        }
    }
}
