package ru.geekbrains.java_level1.homework3;

import java.util.concurrent.ThreadLocalRandom;

public class WordPuzzle extends Game {
    private String[] words;
    private String secret;

    public WordPuzzle() {
        this.words = new String[] {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot",
                "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea",
                "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};
    }

    @Override
    protected String getSecret() {
        return secret;
    }

    @Override
    protected void restartGame() {
        int index = ThreadLocalRandom.current().nextInt(0, words.length - 1);
        secret = words[index];
    }

    @Override
    protected boolean runGame() {
        String answer = null;
        while (true) {
            System.out.printf("Угадайте слово. Для выхода введите q! (%s)", secret);
            System.out.println();

            answer = ConsoleHelper.inputString();
            if (answer.equals(secret)) {
                return true;
            } else if (answer.equals("q!")) {
                return false;
            } else {
                System.out.print("Не угадали. Подсказка: ");
                StringBuilder helpString = createHelpString(answer);
                String hiddenHelpString = insertHideLetters(helpString);
                System.out.println(hiddenHelpString);
            }
        }
    }

    private StringBuilder createHelpString(String answer) {
        StringBuilder helpString;
        // Если если начало овета соответствует загаданному слову, тогда в подсказку записываем загаданное
        // слово и добавляем в конец символ '#'
        if (answer.indexOf(secret) == 0) {
            helpString = new StringBuilder(secret);
            helpString.append('#');
        } else {
            // Иначе создаем пустую строку подсказки в которую при посисимвольной проверке загадонного слова и
            // ответа добавляем отгаданную букву либо символ '#'
            helpString = new StringBuilder();
            for (int i = 0; i < secret.length() && i < answer.length(); i++) {
                if (secret.charAt(i) == answer.charAt(i)) {
                    helpString.append(secret.charAt(i));
                } else {
                    helpString.append('#');
                }
            }
            // Если ответ короче загаданного слова дополняем строку подсказки символами '#' до размера загаданного
            // слова
            for (int i = answer.length(); i < secret.length(); i++) {
                helpString.append('#');
            }
        }
        return helpString;
    }

    private static String insertHideLetters(StringBuilder helpString) {
        int startIndex = helpString.indexOf("#");
        int foundIndex;
        // Пока строка подсказки короче 15 символов циклически удваиваем каждый символ '#'
        while (helpString.length() < 15 && startIndex >= 0) {
            if (startIndex >= helpString.length()) {
                startIndex = 0;
            }
            foundIndex = helpString.indexOf("#", startIndex);
            if (foundIndex >= 0) {
                helpString.insert(foundIndex, '#');
                startIndex += 2;
            } else {
                startIndex = 0;
            }
        }

        return helpString.toString();
    }

}
