package english.transcription.entities;

import java.util.Objects;

public class Word {
    private String word = "";
    private String transcription = "";
    private String translation = "";

    private Word() {
    }

    public Word(String content) {
        this.word = content;
    }

    public Word(String content, String translation) {
        this.word = content;
        this.translation = translation;
    }

    public Word(String content, String transcription, String translation) {
        this.word = content;
        this.transcription = transcription;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return "Word{" +
                "word='" + word + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Word anotherWord = (Word) o;
        return Objects.equals(word, anotherWord.word)
                && Objects.equals(transcription, anotherWord.transcription)
                && Objects.equals(translation, anotherWord.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, transcription, translation);
    }
}
