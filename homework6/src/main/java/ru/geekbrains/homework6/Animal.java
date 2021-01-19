package ru.geekbrains.homework6;

// Задания с 1 по 3 не подразумевают создания объектов класса Animal. В задании 4* необходимо подсчитать количество
// созданных котов, собак и животных. Но т.к. создание объектов базового класса Animal выглядит не логичным, сделал
// класс Animal абстрактным (без реализации методов run и swim), а для подсчета добавил статический счетчит для подсчета
// когда либо созданных животных, в том числе котов, собак и прочих животных унаследованных от класса Animal.
public abstract class Animal {
    private static int count = 0;

    private String name;
    private int distanceRun;
    private int distanceSwim;

    public Animal(String name, int runDistance) {
        this.name = name;
        this.distanceRun = runDistance;
        count++;
    }

    public Animal(String name, int distanceRun, int distanceSwim) {
        this.name = name;
        this.distanceRun = distanceRun;
        this.distanceSwim = distanceSwim;
        count++;
    }

    public String getName() {
        return name;
    }

    public int getDistanceRun() {
        return distanceRun;
    }

    public int getDistanceSwim() {
        return distanceSwim;
    }

    public static int getCount() {
        return count;
    }

    public abstract void run(int distance);

    public abstract void swim(int distance);
}
