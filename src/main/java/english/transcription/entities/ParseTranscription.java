package english.transcription.entities;

public class ParseTranscription {
    
    
//    public ParseTranscription(Environment environment) {
//        ParseTranscription.environment = environment;
//    }
//
//
//
//
//    public static boolean writeWordsToFile(FileWriter output, ArrayList<Word> words) {
//        try (BufferedWriter bw = new BufferedWriter(output)) {
//            for (Word w : words) {
//                bw.write(w.getWord() + " " + w.getTranscription() + " ; " + w.getTranslation() + "\n");
//            }
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//    }
//
//    public static ArrayList<Word> getWordsFromFile(File source, String separator) {
//        ArrayList<Word> words = new ArrayList<>();
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(source));
//
//            String currentLine;
//            while ((currentLine = br.readLine()) != null) {
//                String[] line = currentLine.split(separator);
//                words.add(new Word(line[0].trim(), line[1].trim()));
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return words;
//    }


    /**
     * This method takes a list of words and provides the transcription for each word.
     *
     * @param words the list of words
     **/
//    public static void parseWords(List<Word> words) {
//        String currentWord = null;
//
//        for (Word word : words) {
//            try {
//                currentWord = word.getWord();
//
//                word.setTranscription(
//                        Jsoup.connect(URL + word.getWord() + ".html").get()
//                                .getElementsByClass("dict_transcription")
//                                .text());
//            } catch (IOException e) {
//                System.out.printf("Cannot update " + currentWord);
//                e.printStackTrace();
//            }
//        }
//    }
}
