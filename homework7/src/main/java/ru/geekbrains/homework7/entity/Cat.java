package ru.geekbrains.homework7.entity;

import java.util.ArrayList;
import java.util.List;

// Классу Cat добавил поле вес (weight) на основании его расчитывается аппетит
// Добавил поле тарелка (plate) из которой он будет есть
// Добавил поле сытость (satiety), которое будет заполняться после съедания каждого блюда и уменьшатся после ожидания
// или сна
// Добавил поле catService которое ссылается на поток выполняющий кормление кота
// Класс реализующий интерфейс Runnable (CatService) поместил внутрь класса Cat
public class Cat {
    private final static List<Cat> cats = new ArrayList<>();

    private String name;
    private double weight;
    private int appetite;
    private int satiety;            // сытость
    private Plate plate;

    private Thread catService;

    public Cat(String name, double weight, Plate plate) {
        this.name = name;
        this.weight = weight;
        this.plate = plate;
        calculateAppetite();
        // По умолчанию сытость равна половине аппетита
        this.satiety = this.appetite / 2;

        cats.add(this);

        catService = new Thread(new CatService());
        catService.start();
    }

    public String getName() {
        return name;
    }

    public int getAppetite() {
        return appetite;
    }

    public int getSatiety() {
        return satiety;
    }

    public void setSatiety(int satiety) {
        this.satiety = satiety;
    }

    /**
     * Внутренни класс - реализация интерфейса Runnable для создания потока кормления кота
     */
    private class CatService implements Runnable {
        @Override
        public void run() {
            try {
                System.out.printf("%S пришел (сытость %d из %d)\n", name, satiety, appetite);

                while (!Thread.currentThread().isInterrupted()) {
                    // Если кот сыт он ложится спать, проснувшись теряет примерно половину своей сытости
                    // Иначе кот ест
                    if (satiety == appetite) {
                        System.out.printf("%S лег спать (сытость %d из %d)\n", name, satiety, appetite);
                        Thread.sleep(10000);
                        satiety /= 2;
                        System.out.printf("%S проснулся (сытость %d из %d)\n", name, satiety, appetite);
                    } else {
                        plate.eatFood(Cat.this);
                    }

                    // Советуем передать управление другому потоку
                    Thread.yield();
                }

            } catch (InterruptedException e) {

            }

            System.out.printf("%S ушел\n", name);
        }
    }

    /**
     * Останавливает работу потока кормления кота
     */
    public static void stopService() {
        for (Cat cat : cats) {
            cat.catService.interrupt();
        }
    }

    /**
     * Вычисление аппетита
     */
    private void calculateAppetite() {
        appetite = (int) (weight * 1.5);
    }

    /**
     * Вывод сообщения о всех имеющихся котах и их сытости
     */
    public static void printInfo() {
        for (Cat cat : cats) {
            System.out.printf("%S сытость %d из %d\n", cat.getName(), cat.satiety, cat.getAppetite());
        }
    }
}
