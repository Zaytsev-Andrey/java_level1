package ru.geekbrains.java_level1.homework3_plus;

import java.util.Scanner;

public class Homework3Plus {
    public static void main(String[] args) {
        /* В третьем вебинаре было дополнительное задание переписать следующую конструкцию используюя только два
            оператора if

        Scanner scanner = new Scanner(System.in);

        int intExampleMultiply = scanner.nextInt();
        if (intExampleMultiply % 3 == 0 && intExampleMultiply % 5 == 0) {
            System.out.println("Число успешно делится на 3");
            System.out.println("Число успешно делится на 5");
        } else if (intExampleMultiply % 3 == 0) {
            System.out.println("Число успешно делится на 3");
        } else if (intExampleMultiply % 5 == 0) {
            System.out.println("Число успешно делится на 5");
        }
        */



        /* Вероятно не верно понял задание, потому что аналогичный результат можно получить используя два
            последовательных оператора if
         */
        Scanner scanner = new Scanner(System.in);

        int intExampleMultiply = scanner.nextInt();
        if (intExampleMultiply % 3 == 0) {
            System.out.println("Число успешно делится на 3");
        }
        if (intExampleMultiply % 5 == 0) {
            System.out.println("Число успешно делится на 5");
        }
    }
}
