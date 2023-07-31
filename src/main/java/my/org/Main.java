package my.org;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import static my.org.ParseTranscription.*;


public class Main {
    public static void main(String[] args) {
        File file = new File("src/main/resources/tmp/file.txt");
        ArrayList<Word> words = getWordsFromFile(file, ";");

        parseWords(words);
        System.out.println(words);

        try {
            FileWriter fileWriter = new FileWriter("src/main/resources/tmp/output.txt");
            writeWordsToFile(fileWriter, words);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}