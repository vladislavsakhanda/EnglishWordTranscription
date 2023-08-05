package english.transcription.gui.listeners;

import english.transcription.config.SpringConfig;
import org.jsoup.Jsoup;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GetTranscriptionListener implements ActionListener {
    private final JTextArea inputWords;
    private final JTextArea outputWords;

    private static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);

    public GetTranscriptionListener(JTextArea inputWords, JTextArea outputWords) {
        this.inputWords = inputWords;
        this.outputWords = outputWords;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String output = addTranscriptions(inputWords.getText());

        outputWords.setText(output);
    }

    /**
     * This method takes a String and provides the transcription for each word in this String.
     *
     * @param input Some text that includes english words
     * @return Text with transcriptions of english words
     **/
    public static String addTranscriptions (String input) {
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
    public static String getTranscription(String englishWord) {
        try {
            return Jsoup.connect(
                            context.getBean(SpringConfig.class).getStringOfSite()
                                    + englishWord
                                    + ".html")
                    .get()
                    .getElementsByClass("dict_transcription")
                    .text();
        } catch (IOException e) {
            return null;
        }
    }
}
