package ru.geekbrains.homework4;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;

public class Homework4 {
    private static final char DOT_EMPTY = '.';
    private static final char DOT_X = 'x';
    private static final char DOT_O = 'o';
    private static int size = 5;
    private static int dotsToWin = 4;           // Количество последовательных символов для победы
    private static char[][] map;
    private static boolean isWin = false;
    private static int humanTurnToWin[];

    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();

    public static void main(String[] args) {
        initGameOption();

        isSizePositive();
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (isWin) {
                System.out.println("Победил человек");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
            aiTurn();
            printMap();
            if (isWin) {
                System.out.println("Победил компьютер");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья");
                break;
            }
        }

        System.out.println("Игра окончена");
    }

    private static void initGameOption() {
        System.out.println("Выберите вариант игры:\n" +
                "1 - Игровое поле 3х3 до трех фишек подряд\n" +
                "2 - Игровое поле 5х5 до четырех фишек подряд");

        int value = 0;

        do {
            try {
                value = scanner.nextInt();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Введите числа");
                scanner = new Scanner(System.in);
            }
        } while (value < 1 || value > 2);

        if (value == 1) {
            size = 3;
            dotsToWin = 3;
        } else {
            size = 5;
            dotsToWin = 4;
        }
    }

    private static boolean isMapFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    return false;
                }
            }
        }

        return true;
    }

    private static void isSizePositive() {
        if (size < 3) {
            System.out.println("Минимальный размер поля 3х3");
            System.exit(1);
        }
    }

    private static void aiTurn() {
        int x;
        int y;

        System.out.println("Ход компьютера ");
        // Если человек может победить при следующем ходе блокируем эту возможность
        // Иначе ход компьютера выбирается произвольно
        if (humanTurnToWin != null) {
            x = humanTurnToWin[1];
            y = humanTurnToWin[0];
            humanTurnToWin = null;
        } else {
            do {
                x = random.nextInt(size);
                y = random.nextInt(size);
            } while (!isValid(x, y));
        }

        map[y][x] = DOT_O;
        checkMap(x, y, DOT_O);
    }

    private static void humanTurn() {
        int x = -1;
        int y = -1;
        System.out.println("Ходит человек");
        do {
            System.out.println("Введите координаты X и Y");
            try {
                x = scanner.nextInt() - 1;
                y = scanner.nextInt() - 1;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("Введите числа");
                scanner = new Scanner(System.in);
            }
        } while (!isValid(x, y));

        map[y][x] = DOT_X;
        checkMap(x, y, DOT_X);
    }

    private static void checkMap(int x, int y, char symbol) {
        String sequence;
        String sequenceForWin = getSequenceDots(symbol, dotsToWin);
        String sequenceForPossibleWin = getSequenceDots(symbol,dotsToWin - 1);
        int index;
        int row;
        int col;
        int possibleRow;
        int possibleCol;

        // Проверка строки
        // Получаем строку в которой был добавлен последний элемент
        // Если последовательное количество символов текущего игрока достигло количества необходимого для
        // победы устанавливаем флаг isWin
        sequence = getRowFromMap(y);
        index = sequence.indexOf(sequenceForWin);
        if (index >= 0) {
            isWin = true;
            // В случае победы приводим выигрышную последовательность символов в строке к верхнему регистру
            while (index < size && map[y][index] == symbol) {
                map[y][index] = (char) (symbol - 32);
                index++;
            }
            return;
        }
        // Если текущий ход делеал человек, проверяем строку на наличие возможности выиграть в следующем ходе
        if (symbol == DOT_X) {
            for (int i = 0; i < dotsToWin; i++) {
                index = getStartIndexForPossibleSequence(sequence, sequenceForPossibleWin, i);
                if (index >= 0) {
                    possibleRow = y;
                    possibleCol = index + i;
                    setHumanTurnToWin(possibleRow, possibleCol);
                    break;
                }
            }
        }

        // Проверка столбца
        // Получаем столбец в котором был добавлен последний элемент
        // Если последовательное количество символов текущего игрока достигло количества необходимого для
        // победы устанавливаем флаг isWin
        sequence = getColFromMap(x);
        index = sequence.indexOf(sequenceForWin);
        if (index >= 0) {
            isWin = true;
            // В случае победы приводим выигрышную последовательность символов в столбце к верхнему регистру
            while (index < size && map[index][x] == symbol) {
                map[index][x] = (char) (symbol - 32);
                index++;
            }
            return;
        }
        // Если текущий ход делеал человек, проверяем строку на наличие возможности выиграть в следующем ходе
        if (symbol == DOT_X) {
            for (int i = 0; i < dotsToWin; i++) {
                index = getStartIndexForPossibleSequence(sequence, sequenceForPossibleWin, i);
                if (index >= 0) {
                    possibleRow = index + i;
                    possibleCol = x;
                    setHumanTurnToWin(possibleRow, possibleCol);
                    break;
                }
            }
        }

        // Проверка главной диагонали
        // Устанавливаем row и col в начало главной диагонали в котором был добавлен последний элемент
        row  = y;
        col = x;
        while (row > 0 && col > 0) {
            row--;
            col--;
        }
        // Получаем главную диагональ в котором был добавлен последний элемент
        // Если последовательное количество символов текущего игрока достигло количества необходимого для
        // победы устанавливаем флаг isWin
        sequence = getMainDiagonalFromMap(row, col);
        index = sequence.indexOf(sequenceForWin);
        if (index >= 0) {
            isWin = true;
            // В случае победы приводим выигрышную последовательность символов в главной диагонале к верхнему
            // регистру
            possibleRow = row + index;
            possibleCol = col + index;
            while (possibleRow < size && possibleCol < size && map[possibleRow][possibleCol] == symbol) {
                map[possibleRow][possibleCol] = (char) (symbol - 32);
                possibleRow++;
                possibleCol++;
            }
            return;
        }
        // Если текущий ход делеал человек, проверяем строку на наличие возможности выиграть в следующем ходе
        if (symbol == DOT_X) {
            for (int i = 0; i < dotsToWin; i++) {
                index = getStartIndexForPossibleSequence(sequence, sequenceForPossibleWin, i);
                if (index >= 0) {
                    possibleRow = row + index + i;
                    possibleCol = col + index + i;
                    setHumanTurnToWin(possibleRow, possibleCol);
                    break;
                }
            }
        }

        // Проверка побочной диагонали
        // Устанавливаем row и col в начало побочной диагонали в котором был добавлен последний элемент
        row = y;
        col = x;
        while (row < size - 1 && col > 0) {
            row++;
            col--;
        }
        // Получаем побочную диагональ в котором был добавлен последний элемент
        // Если последовательное количество символов текущего игрока достигло количества необходимого для
        // победы устанавливаем флаг isWin
        sequence = getDiagonalFromMap(row, col);
        index = sequence.indexOf(sequenceForWin);
        if (index >= 0) {
            isWin = true;
            // В случае победы приводим выигрышную последовательность символов в побочной диагонале к верхнему
            // регистру
            row -= index;
            col += index;
            while (row >= 0 && col < size && map[row][col] == symbol) {
                map[row][col] = (char) (symbol - 32);
                row--;
                col++;
            }
            return;
        }
        // Если текущий ход делеал человек, проверяем строку на наличие возможности выиграть в следующем ходе
        if (symbol == DOT_X) {
            for (int i = 0; i < dotsToWin; i++) {
                index = getStartIndexForPossibleSequence(sequence, sequenceForPossibleWin, i);
                if (index >= 0) {
                    possibleRow = row - index - i;
                    possibleCol = col + index + i;
                    setHumanTurnToWin(possibleRow, possibleCol);
                    break;
                }
            }
        }
    }

    private static String getRowFromMap(int row) {
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sequence.append(map[row][i]);
        }
        return sequence.toString();
    }

    private static String getColFromMap(int col) {
        StringBuilder sequence = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sequence.append(map[i][col]);
        }
        return sequence.toString();
    }

    private static String getMainDiagonalFromMap(int row, int col) {
        StringBuilder sequence = new StringBuilder();
        for (int i = row, j = col; i < size && j < size; i++, j++) {
            sequence.append(map[i][j]);
        }
        return sequence.toString();
    }

    private static String getDiagonalFromMap(int row, int col) {
        StringBuilder sequence = new StringBuilder();
        for (int i = row, j = col; i >= 0 && j < size; i--, j++) {
            sequence.append(map[i][j]);
        }
        return sequence.toString();
    }

    private static int getStartIndexForPossibleSequence(String sequence, String sequenceForPossibleWin, int i) {
        StringBuilder possibleSequence = new StringBuilder(sequenceForPossibleWin);
        possibleSequence.insert(i, DOT_EMPTY);
        return sequence.indexOf(possibleSequence.toString());
    }

    private static void setHumanTurnToWin(int row, int col) {
        if (humanTurnToWin == null) {
            humanTurnToWin = new int[2];
        }
        humanTurnToWin[0] = row;
        humanTurnToWin[1] = col;
    }

    private static String getSequenceDots(char symbol, int count) {
        StringBuilder dots = new StringBuilder();
        for (int i = 0; i < count; i++) {
            dots.append(symbol);
        }

        return dots.toString();
    }

    private static boolean isValid(int x, int y) {
        return x >= 0 && x < size
                && y >= 0 && y < size
                && map[y][x] == DOT_EMPTY;
    }

    private static void initMap() {
        map = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        System.out.println();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
