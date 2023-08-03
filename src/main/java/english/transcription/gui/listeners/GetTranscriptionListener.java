package english.transcription.gui.listeners;

import english.transcription.entities.ParseTranscription;
import english.transcription.entities.Words;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GetTranscriptionListener implements ActionListener {
    private JTextArea inputWords;
    private JTextArea outputWords;

    public GetTranscriptionListener(JTextArea inputWords, JTextArea outputWords) {
        this.inputWords = inputWords;
        this.outputWords = outputWords;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Words words = ParseTranscription.extractEnglishWords(inputWords.getText());
        outputWords.setText(inputWords.getText());
        System.out.println(words);
    }
}
