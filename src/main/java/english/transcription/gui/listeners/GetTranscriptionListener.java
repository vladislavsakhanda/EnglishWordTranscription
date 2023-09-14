package english.transcription.gui.listeners;

import english.transcription.gui.components.TFrame;
import org.jsoup.Jsoup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class GetTranscriptionListener implements ActionListener {
    private final JTextArea inputWords;
    private final JTextArea outputWords;
    private final JLabel tickImage;
    private final JLabel gearImage;
    private static final Properties properties = new Properties();
    private static final String PROPERTIES_FILE = "project.properties";

    public GetTranscriptionListener(JTextArea inputWords, JTextArea outputWords, JLabel tickImage, JLabel gearImage) {
        this.inputWords = inputWords;
        this.outputWords = outputWords;
        this.tickImage = tickImage;
        this.gearImage = gearImage;

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
            protected Void doInBackground() throws Exception {
                tickImage.setVisible(false);
                gearImage.setVisible(true);

                String output = addTranscriptionsParallel(inputWords.getText());
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
    private static String addTranscriptionsParallel(String input) {
        String[][] text = Stream.of(input.split("\n"))
                .map(line -> line.split("\\s"))
                .toArray(String[][]::new);

        String[] ignoringWords = TFrame.getIgnoringWords().split("\\s+");

        Arrays.parallelSetAll(text, i -> {
            Arrays.parallelSetAll(text[i], j -> {
                if (Arrays.stream(ignoringWords).parallel().noneMatch(ignoringWord -> ignoringWord.equals(text[i][j])) &&
                        Pattern.matches("\\b[A-Za-z]+\\b", text[i][j])) {
                    return text[i][j] + " " + getTranscription(text[i][j]);
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
            String transcription = getTranscription(currentWord);

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
     * @return Transcription for a word in format "|transcription|"
     **/
    private static String getTranscription(String englishWord) {
        try {
            return Jsoup.connect(String.format("%s%s.html",
                            properties.getProperty("transcriptionURL"), englishWord))
                    .get()
                    .getElementsByClass("dict_transcription")
                    .text();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
