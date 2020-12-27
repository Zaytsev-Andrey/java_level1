package ru.geekbrains.java_level1.homework3;

public class Homework3 {
    public static void main(String[] args) {
        System.out.println("Меню: ");
        System.out.println("1 - Игра угадай число");
        System.out.println("2 - Игра угадай слово");

        Game selectedGame = null;
        int numberGame = ConsoleHelper.inputNumber(1, 2);
        System.out.println();

        switch (numberGame) {
            case 1:
                selectedGame = new NumberPuzzle();
                break;
            case 2:
                selectedGame = new WordPuzzle();
                break;
        }

        selectedGame.game();

        ConsoleHelper.close();
    }

}
