package ru.geekbrains.java_level1.homework2;

import java.util.Arrays;

public class Homework2 {
    public static void main(String[] args) {
        // Задание 1
        int[] array1 = {1, 0, 0, 1, 0, 1, 1, 1, 0, 0};
        System.out.println("Задание 1");
        System.out.println("Исходный массив: " + Arrays.toString(array1));
        for (int i = 0; i < array1.length; i++) {
            if (array1[i] == 0) {
                array1[i] = 1;
            } else if (array1[i] == 1) {
                array1[i] = 0;
            }
        }
        System.out.println("Измененный массив: " + Arrays.toString(array1));
        System.out.println();

        // Задание 2
        System.out.println("Задание 2");
        int[] array2 = new int[8];
        for (int i = 0, j = 0; i < array2.length; i++, j += 3) {
            array2[i] = j;
        }
        System.out.println("Заполненный массив: " + Arrays.toString(array2));
        System.out.println();

        // Задание 3
        int[] array3 = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        System.out.println("Задание 3");
        System.out.println("Исходный массив: " + Arrays.toString(array3));
        for (int i = 0; i < array3.length; i++) {
            if (array3[i] < 6) {
                array3[i] *= 2;
            }
        }
        System.out.println("Измененный массив: " + Arrays.toString(array3));
        System.out.println();

        // Задание 4
        System.out.println("Задание 4");
        int[][] array4 = new int[9][9];
        for (int i = 0, j = array4.length - 1; i < array4.length; i++, j--) {
            array4[i][i] = 1;
            array4[i][j] = 1;
        }
        System.out.println("Заполненный массив: ");
        printMultiArray(array4);
        System.out.println();

        // Задание 5
        int[] array5 = {18, 8, 12, 71, 38, 16, 57, 23, 10, 2};
        System.out.println("Задание 5");
        System.out.println("Исходный массив: " + Arrays.toString(array5));
        int maxElement = array5[0];
        int minElement = array5[0];
        for (int i = 1; i < array5.length; i++) {
            if (array5[i] < minElement) {
                minElement = array5[i];
            }
            if (array5[i] > maxElement) {
                maxElement = array5[i];
            }
        }
        System.out.println("Минимальный элемент массива: " + minElement);
        System.out.println("Максимальный элемент массива: " + maxElement);
        System.out.println();

        // Задание 6
        System.out.println("Задание 6");
        int[] array6 = {2, 2, 2, 1, 2, 2, 10, 1};
        System.out.println("Исходный массив: " + Arrays.toString(array6));
        System.out.println(checkBalance(array6));
        array6 = new int[] {1, 1, 1, 2, 1};
        System.out.println("Исходный массив: " + Arrays.toString(array6));
        System.out.println(checkBalance(array6));
        array6 = new int[] {4, 1, 1, 2, 5};
        System.out.println("Исходный массив: " + Arrays.toString(array6));
        System.out.println(checkBalance(array6));
        System.out.println();

        // Задание 7
        System.out.println("Задание 7");
        int[] array7 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        System.out.println("Исходный массив: " + Arrays.toString(array7));
        for (int i = 20; i >= -20; i--) {
            // Дополнительный массив (использование которого запрещает условие задачи), создается исключительно
            // для того чтобы показать результаты работы функции shiftElements() относительно исходного массива
            int[] testArray = array7.clone();
            shiftElements(testArray, i);
            System.out.println("Массив циклически сдвинутый на " + i + ":\t" + Arrays.toString(testArray));
        }

    }

    private static void shiftElements(int[] array, int n) {
        // Если количество операций сдвига больше длины массива, вычисляем действительное число сдвигов
        n %= array.length;

        // i - начальная позиция
        // countOperation - счетчик операций сдвигов (общее число сдвигов должно быть равным количеству элементов в массиве)
        // В цикле for за текушую позицию берется i, следующую позицую на которую необходимо сдвинуть текущий элемент
        // вычисляет функция getNextPosition(). Далее во вложенно цикле все значения последовательно переносятся на свои
        // новые позиции (с шагом n), пока текущая позиция не станет равна i. В этом случае, если шаг n не был кратен
        // числу элементов в массиве, количество операций сдвигов станет равным количеству элементов в массиве и
        // цикл for завершится. Если шаг n был кратен количеству элемементов в массиве, тогда увеличиваем i на
        // единицу и  повторяем цикл for.
        for (int i = 0, countOperation = 0; n != 0 && countOperation < array.length; i++) {
            int currentPosition = i;
            int nextPosition = getNextPosition(currentPosition, array.length, n);
            int tempCurrentElement = array[currentPosition];
            int tempNextElement;
            do {
                tempNextElement = array[nextPosition];
                array[nextPosition] = tempCurrentElement;
                countOperation++;
                currentPosition = nextPosition;
                nextPosition = getNextPosition(currentPosition, array.length, n);
                tempCurrentElement = tempNextElement;
            } while (currentPosition != i);
        }
    }
    private static int getNextPosition(int currentPosition, int length, int n) {
        int nextPosition = currentPosition + n;
        if (nextPosition >= 0) {
            return nextPosition % length;
        } else {
            return nextPosition + length;
        }
    }
    private static boolean checkBalance(int[] array) {
        int[] leftArray;
        int[] rightArray;
        for (int i = 1; i < array.length; i++) {
            leftArray = Arrays.copyOfRange(array, 0, i);
            rightArray = Arrays.copyOfRange(array, i, array.length);
            if (sumElementsArray(leftArray) == sumElementsArray(rightArray)) {
                return true;
            }
        }
        return false;
    }

    private static int sumElementsArray(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }

    private static void printMultiArray(int[][] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                System.out.print(array[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
