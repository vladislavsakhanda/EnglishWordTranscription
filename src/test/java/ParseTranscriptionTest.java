import english.transcription.entities.Word;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static english.transcription.entities.ParseTranscription.parseWords;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class ParseTranscriptionTest {
    @Test
    public void parseWordsForInputDataTest() {
        ArrayList<Word> testWords = new ArrayList<>();
        testWords.add(new Word("apple", "яблуко"));
        testWords.add(new Word("invincible", ""));
        testWords.add(new Word("pamper", "", "балувати"));
        testWords.add(new Word("job interview", "", ""));
        testWords.add(new Word("vnfosdnvoid"));
        testWords.add(new Word("people"));
        testWords.add(new Word("432"));
        testWords.add(new Word(""));

        ArrayList<Word> expectedWords = new ArrayList<>();
        expectedWords.add(new Word("apple", "|ˈap(ə)l|", "яблуко"));
        expectedWords.add(new Word("invincible", "|ɪnˈvɪnsɪb(ə)l|", ""));
        expectedWords.add(new Word("pamper", "|ˈpampə|", "балувати"));
        expectedWords.add(new Word("job interview", "", ""));
        expectedWords.add(new Word("vnfosdnvoid", "", ""));
        expectedWords.add(new Word("people", "|ˈpiːp(ə)l|", ""));
        expectedWords.add(new Word("432", "", ""));
        expectedWords.add(new Word("", "", ""));

        parseWords(testWords);

        assertIterableEquals(expectedWords, testWords);

    }
}
