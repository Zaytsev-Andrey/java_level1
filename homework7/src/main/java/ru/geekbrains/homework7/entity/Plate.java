package ru.geekbrains.homework7.entity;

import ru.geekbrains.homework7.enums.Food;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Plate {
    private final int size;
    private List<Food> foods;
    private boolean isEmptyPlate;

    private Thread plateService;

    public Plate(int size) {
        this.size = size;
        foods = new LinkedList<>(getRandomFoods());
        this.isEmptyPlate = false;
        System.out.println("Наполняем миску: " + getMenu());
        System.out.println();

        plateService = new Thread(new PlateService());
        plateService.start();
    }

    /**
     * Внутренни класс - реализация интерфейса Runnable для создания потока наполнения миски
     */
    private class PlateService implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    fillPlate();
                }
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }

            Cat.stopService();
        }
    }

    /**
     * Формирует список произвольной еды.
     * @return список произвольной еды.
     */
    private List<Food> getRandomFoods() {
        List<Food> randomFoods = new LinkedList<>();
        int countFood = Food.values().length;
        int index;

        for (int i = 0; i < size; i++) {
            index = ThreadLocalRandom.current().nextInt(countFood);
            Food currentFood = Food.values()[index];
            randomFoods.add(currentFood);
        }

        return randomFoods;
    }

    /**
     * Формирует меню
     * @return строку меню
     */
    private String getMenu() {
        StringBuilder menu = new StringBuilder();
        for (Food food : foods) {
            menu.append(food.getTitle());
            menu.append(", ");
        }

        // Восвращаем строку без последних символов ", "
        return menu.substring(0,menu.length() - 2);
    }

    /**
     * Наполняет миску едой
     * @throws InterruptedException
     */
    private synchronized void fillPlate() throws InterruptedException {
        // Ждем пока миска не станет пустой
        while (!foods.isEmpty()) {
            wait(3000);
        }

        // Наполняем миску
        foods.addAll(getRandomFoods());
        isEmptyPlate = false;
        System.out.println();
        System.out.println("Наполняем миску: " + getMenu());
        Cat.printInfo();
        System.out.println();

        // Возобновляем работу потоков всех ожидающих котов
        notifyAll();
    }

    /**
     * Метод находит максимально подходящую по сытности еду и удаляет ее из миски. Если миска пуста, поток ижидает ее
     * наполнение освобождая монитор. Если поток не нашел подходящей для кота еды его выполнение приостанавливается и
     * освобождается монитор.
     * @param cat - объект кот
     * @throws InterruptedException
     */
    public synchronized void eatFood(Cat cat) throws InterruptedException {
        int maxSatiety;
        Food maxSatietyFood = null;

        // Пока нет доступной еды, останавливаем поток и освобождаем монитор
        while (foods.isEmpty()) {
            showMessageEmptyPlate();
            waitFood(cat);
        }

        maxSatiety = cat.getAppetite() - cat.getSatiety();

        Iterator<Food> iterator = foods.iterator();
        while (iterator.hasNext()) {
            Food currentFood = iterator.next();

            // Если текущая сытность больше требуемой, то переходим к следующей итерации цикла
            if (currentFood.getSatiety() > maxSatiety) {
                continue;
            }

            // Если текущая сытность равна требуемой, тогда сохраняем ее и выходим из цикла
            if (currentFood.getSatiety() == maxSatiety) {
                maxSatietyFood = currentFood;
                break;
            }

            // Если оптимальная еда (maxSatietyFood) = null присваиваем ей текущую еду и переходим к следующей итерации
            // цикла
            if (maxSatietyFood == null) {
                maxSatietyFood = currentFood;
                continue;
            }

            // Если текущая сытность больше сытности оптимальной еды (maxSatietyFood), тогдп присваиваем оптимальной еде
            // текущую еду
            int currentSatiety = currentFood.getSatiety();
            if (currentSatiety > maxSatietyFood.getSatiety()) {
                maxSatietyFood = currentFood;
            }
        }

        // Если оптимальная еда была найдена кормим кота и удаляем текущую еду из списка
        // Иначе ждем освобождая монитор
        if (maxSatietyFood != null) {
            cat.setSatiety(cat.getSatiety() + maxSatietyFood.getSatiety());
            foods.remove(maxSatietyFood);
            System.out.printf("%S съел %s (сытость %d из %d)\n",
                    cat.getName(), maxSatietyFood.getTitle(), cat.getSatiety(), cat.getAppetite());
        } else {
            System.out.printf("Для %S вся еда слишком сытная(сытость %d из %d)\n",
                    cat.getName(), cat.getSatiety(), cat.getAppetite());
            waitFood(cat);
        }
    }

    /**
     * Останавливает выполнение текущего потока на 3 сек. и освобождает монитор. После возобновления потока считается,
     * что кот проглодался и его сытость уменьшается на 1, либо не изменяется если она была равна нулю.
     * @param cat - объект кот
     * @throws InterruptedException
     */
    private void waitFood(Cat cat) throws InterruptedException {
        System.out.printf("%S ждет возле миски (сытость %d из %d)\n",
                cat.getName(), cat.getSatiety(), cat.getAppetite());
        wait(3000);

        cat.setSatiety(Math.max(cat.getSatiety() - 1, 0));
        System.out.printf("%S приступил к еде (сытость %d из %d)\n",
                cat.getName(), cat.getSatiety(), cat.getAppetite());
    }

    /**
     * Проверяет флаг о том что миска не пуста, в случае успеха выводит сообщение и устанавливает флаг. Сообщение
     * отображается только при первом обнаружении пустой миски.
     */
    private void showMessageEmptyPlate() {
        if (!isEmptyPlate) {
            isEmptyPlate = true;
            System.out.println("Миска пуста!");
        }
    }

    /**
     * Останавлиывает работу потока наполнения миски
     */
    public void stopService() {
        plateService.interrupt();
    }
}
