import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the letters in the game:");
        String word = sc.next();
        char[] letters = word.toCharArray();

        for (char letter : letters) {
            System.out.println(letter);
        }

        ClassLoader classLoader = App.class.getClassLoader();
        String path = classLoader.getResource("words.txt").getFile();
        File wordsFile = new File(path);

        List<String> wordsList = findContainedWords(letters, wordsFile);
        System.out.println(wordsList);
    }

    public static List<String> findContainedWords(char[] letters, File wordsFile) throws FileNotFoundException {
        List<String> containedWords = new ArrayList<>();

        Map<Character, Integer> letterCountMap = getLetterCountMap(letters);

        Scanner fileScanner = new Scanner(wordsFile);

        while (fileScanner.hasNextLine()) {
            String word = fileScanner.nextLine();

            if (word.length() >= 3 && word.length() <= 6 && hasValidLetters(word, letterCountMap)) {
                containedWords.add(word);
            }
        }

        fileScanner.close();

        return containedWords;
    }

    private static Map<Character, Integer> getLetterCountMap(char[] letters) {
        Map<Character, Integer> letterCountMap = new HashMap<>();

        for (char letter : letters) {
            letterCountMap.put(letter, letterCountMap.getOrDefault(letter, 0) + 1);
        }

        return letterCountMap;
    }

    private static boolean hasValidLetters(String word, Map<Character, Integer> letterCountMap) {
        Map<Character, Integer> wordLetterCountMap = getLetterCountMap(word.toCharArray());

        for (Map.Entry<Character, Integer> entry : wordLetterCountMap.entrySet()) {
            char letter = entry.getKey();
            int count = entry.getValue();

            if (!letterCountMap.containsKey(letter) || count > letterCountMap.get(letter)) {
                return false;
            }
        }

        return true;
    }
}
