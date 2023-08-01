package english.transcription.entities;

import java.util.ArrayList;

public class Words extends ArrayList<Word> {
    private ArrayList<Word> words;

    public Words() {

    }

    public Words(ArrayList<Word> words) {
        this.words = words;
    }

    public ArrayList<Word> getWords() {
        return words;
    }

    public void setWords(ArrayList<Word> words) {
        this.words = words;
    }
}
