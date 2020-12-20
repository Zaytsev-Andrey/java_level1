package ru.geekbrains.java_level1.home_work1;

public class HomeWork1 {
    public static void main(String[] args) {
        // Приметивные типы
        byte byteValue = -127;
        short shortValue = 512;
        int intValue = 2048;
        long longValue = 4096L;
        float floatValue = 32.16f;
        double doubleValue = 128.64;
        char charValue = 'c';
        boolean booleanValue = true;

        // Ссылочные типы
        String stringValue = "Андрей";

        System.out.println("Задание № 3:");
        System.out.println(calculate(floatValue, byteValue, intValue, shortValue));
        System.out.println(calculate(2.5f, 9.0f, 7.5f, 2.5f));
        System.out.println();

        System.out.println("Задание № 4:");
        System.out.println(sumBetweenRange(shortValue, intValue));
        System.out.println(sumBetweenRange(5, 15));
        System.out.println();

        System.out.println("Задание № 5:");
        signNumber(byteValue);
        signNumber(0);
        signNumber(intValue);
        System.out.println();

        System.out.println("Задание № 6:");
        System.out.println(isNegativeNumber(byteValue));
        System.out.println(isNegativeNumber(0));
        System.out.println(isNegativeNumber(intValue));
        System.out.println();

        System.out.println("Задание № 7:");
        hello(stringValue);
        System.out.println();

        System.out.println("Задание № 8:");
        leapYear(2000);
        leapYear(700);
        leapYear(800);
        leapYear(1990);
        leapYear(2020);
        leapYear(2100);

    }

    // Задание № 3
    private static float calculate(float a, float b, float c, float d) {
        return a * (b + (c / d));
    }

    // Задание № 4
    private static boolean sumBetweenRange(int a, int b) {
        int sum = a + b;
        if (sum >= 10 && sum <=20) {
            return true;
        } else {
            return false;
        }
    }

    // Задание № 5
    private static void signNumber(int number) {
        if (number >= 0) {
            System.out.println("Число положительное");
        } else {
            System.out.println("Число отрицательное");
        }
    }

    // Задание № 6
    private static boolean isNegativeNumber(int number) {
        if (number < 0) {
            return true;
        } else {
            return false;
        }
    }

    // Задание № 7:
    private static void hello(String name) {
        System.out.println("Привет, " + name + "!");
    }

    // Задание № 8:
    private static void leapYear(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            System.out.println("Год " + year + " високосный");
        } else {
            System.out.println("Год " + year + " не високосный");
        }
    }

}
