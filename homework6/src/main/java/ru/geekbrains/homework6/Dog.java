package ru.geekbrains.homework6;

public class Dog extends Animal {
    // Переменная для подсчета количества созданных экземпляров сласса Dog, скрыват статическую переменную count
    // родительского класса Animal
    private static int count = 0;

    public Dog(String name) {
        super(name, 500, 10);
        count++;
    }

    @Override
    public void run(int distance) {
        if (distance > 0) {
            if (distance <= getDistanceRun()) {
                System.out.printf("Собака %s пробежала %d м.\n", getName(), distance);
            } else {
                String message = String.format("Собаки не могут бегать больше %d м.", getDistanceRun());
                throw new UnsupportedOperationException(message);
            }
        } else {
            throw new IllegalArgumentException("Дистанция должна быть больше нуля.");
        }
    }

    @Override
    public void swim(int distance) {
        if (distance > 0) {
            if (distance <= getDistanceSwim()) {
                System.out.printf("Собака %s проплыла %d м.\n", getName(), distance);
            } else {
                String message = String.format("Собаки не могут плавать больше %d м.", getDistanceSwim());
                throw new UnsupportedOperationException(message);
            }
        } else {
            throw new IllegalArgumentException("Дистанция должна быть больше нуля.");
        }
    }

    /**
     * Возвращает зачение переменной count для подсчета количества созданных экземпляров сласса Dog. Метод скрывает
     * метод getCount() родительского класса Animal.
     * @return количество созданных экземпляров сласса Dog
     */
    public static int getCount() {
        return count;
    }
}
