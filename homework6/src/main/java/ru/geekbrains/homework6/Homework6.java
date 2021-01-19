package ru.geekbrains.homework6;

import java.util.ArrayList;
import java.util.List;
// Для хранения создаваемых объектов производных от класса Animal используется динамический массив класса ArrayList.
// Это сделано дя удобства демонстрации работы методов run() и swim() у объектов наследуемых от класса Animal. Методы
// для каждого объекта вызываются в цикле, а за одно перехватываюся возможные исключения UnsupportedOperationException
// и IllegalArgumentException
public class Homework6 {
    private static List<Animal> animals = new ArrayList<>();

    public static void main(String[] args) {
        // Создаем животных
        animals.add(new Cat("Барсик"));
        animals.add(new Dog("Рекс"));
        animals.add(new Dog("Барон"));
        animals.add(new Cat("Маркиз"));
        animals.add(new Dog("Хатико"));

        // Животные бегут
        animalsRun(200);
        animalsRun(500);
        animalsRun(1000);
        animalsRun(0);

        // Животные плывут
        animalsSwim(10);
        animalsSwim(20);
        animalsSwim(0);

        // Статистика созданных объектов
        System.out.printf("Всего было создано %d животных.\n", Animal.getCount());
        System.out.printf("Из них котов - %d и собак - %d\n", Cat.getCount(), Dog.getCount());
    }

    private static void animalsRun(int distance) {
        System.out.printf("Животные бегут дистанцию %d м.\n", distance);
        for (Animal animal : animals) {
            try {
                System.out.print("\t");
                animal.run(distance);
            } catch (UnsupportedOperationException e) {
                System.out.printf("Операция не выполнима для %s: %s\n", animal.getName(), e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.printf("Операция не выполнима: %s\n", e.getMessage());
            }
        }
        System.out.println();
    }

    private static void animalsSwim(int distance) {
        System.out.printf("Животные плывут дистанцию %d м.\n", distance);
        for (Animal animal : animals) {
            try {
                System.out.print("\t");
                animal.swim(distance);
            } catch (UnsupportedOperationException e) {
                System.out.printf("Операция не выполнима для %s: %s\n", animal.getName(), e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.printf("Операция не выполнима: %s\n", e.getMessage());
            }
        }
        System.out.println();
    }
}
