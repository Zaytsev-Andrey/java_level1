package ru.geekbrains.homework6;

public class Cat extends Animal {
    // Переменная для подсчета количества созданных экземпляров сласса Cat, скрыват статическую переменную родительского
    // класса Animal
    private static int count = 0;

    public Cat(String name) {
        super(name, 200);
        count++;
    }

    @Override
    public void run(int distance) {
        if (distance > 0) {
            if (distance <= getDistanceRun()) {
                System.out.printf("Кот %s пробежал %d м.\n", getName(), distance);
            } else {
                String message = String.format("Коты не могут бегать больше %d м.", getDistanceRun());
                throw new UnsupportedOperationException(message);
            }
        } else {
            throw new IllegalArgumentException("Дистанция должна быть больше нуля.");
        }
    }

    // Т.к. по условию задания 2 все животные могут бежать и плыть, в родительском классе Animal добавлены абстрактные
    // методы run() и swim(). Согласно заданию 3 кот не умеет плавать, поэтому в реализации метода swim() бросаю
    // исключение UnsupportedOperationException() (по аналогии с интерфейсом Iterator, в котором таким образом
    // по умолчанию реализован метод remove()).
    @Override
    public void swim(int distance) {
        throw new UnsupportedOperationException("Коты не умеют плавать");
    }

    /**
     * Возвращает зачение переменной count для подсчета количества созданных экземпляров сласса Cat. Метод скрывает
     * метод getCount() родительского класса Animal.
     * @return количество созданных экземпляров сласса Cat
     */
    public static int getCount() {
        return count;
    }
}
