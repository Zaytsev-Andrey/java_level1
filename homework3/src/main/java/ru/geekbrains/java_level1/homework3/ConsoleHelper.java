package ru.geekbrains.java_level1.homework3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int inputNumber(int startRange, int endRange) {
        int number;
        while (true) {
            try {
                number = Integer.parseInt(reader.readLine());
                if (number < startRange || number > endRange) {
                    System.out.printf("Введенное значение должно быть в диапозоне от %d до %d, попробуйте снова\n", startRange, endRange);
                } else {
                    break;
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения из потока. Программа будет завершена.");
            } catch (NumberFormatException e) {
                System.out.println("Введенное значение должно быть числом, попробуйте снова");
            }
        }

        return number;
    }

    public static String inputString() {
        String word = "";
        while (true) {
            try {
                word = reader.readLine();
                if (word.equals("")) {
                    System.out.println("Вы  ничего не ввели, повторите попытку");
                } else {
                    break;
                }
            } catch (IOException e) {
                System.out.println("Ошибка чтения из потока. Программа будет завершена.");
            }
        }

        return word.toLowerCase();
    }



    public static void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("Ошибка закрытия потока. Программа будет завершена.");
        }
    }
}
