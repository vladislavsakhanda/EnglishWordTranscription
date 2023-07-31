package my.org;

import java.util.Objects;

public class Word {
    private String content = "";
    private String transcription = "";
    private String translation = "";

    private Word() {
    }

    public Word(String content) {
        this.content = content;
    }

    public Word(String content, String translation) {
        this.content = content;
        this.translation = translation;
    }

    public Word(String content, String transcription, String translation) {
        this.content = content;
        this.transcription = transcription;
        this.translation = translation;
    }

    public String getContent() {
        return content;
    }

    public String getTranscription() {
        return transcription;
    }

    public String getTranslation() {
        return translation;
    }

    public void setContent(String content) {
        this.content = content;
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
                "word='" + content + '\'' +
                ", transcription='" + transcription + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Word anotherWord = (Word) o;
        return Objects.equals(content, anotherWord.content)
                && Objects.equals(transcription, anotherWord.transcription)
                && Objects.equals(translation, anotherWord.translation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, transcription, translation);
    }
}
