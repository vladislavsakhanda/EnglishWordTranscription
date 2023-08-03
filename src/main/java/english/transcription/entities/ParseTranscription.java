package english.transcription.entities;

import org.jsoup.Jsoup;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseTranscription {
    public static Words extractEnglishWords(String input) {
        Words words = new Words();

        Pattern pattern = Pattern.compile("\\b[A-Za-z]+\\b");
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            words.add(new Word(matcher.group()));
        }

        return words;
    }

    private final static String URL = "https://ua.opentran.net/english-ukrainian/";

    public static boolean writeWordsToFile(FileWriter output, ArrayList<Word> words) {
        try (BufferedWriter bw = new BufferedWriter(output)) {
            for (Word w : words) {
                bw.write(w.getWord() + " " + w.getTranscription() + " ; " + w.getTranslation() + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    public static ArrayList<Word> getWordsFromFile(File source, String separator) {
        ArrayList<Word> words = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(source));

            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                String[] line = currentLine.split(separator);
                words.add(new Word(line[0].trim(), line[1].trim()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return words;
    }


    /**
     * This method takes a list of words and provides the transcription for each word.
     *
     * @param words the list of words
     **/
    public static void parseWords(List<Word> words) {
        String currentWord = null;

        for (Word word : words) {
            try {
                currentWord = word.getWord();

                word.setTranscription(
                        Jsoup.connect(URL + word.getWord() + ".html").get()
                                .getElementsByClass("dict_transcription")
                                .text());
            } catch (IOException e) {
                System.out.printf("Cannot update " + currentWord);
                e.printStackTrace();
            }
        }
    }
}
