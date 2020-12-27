package ru.geekbrains.java_level1.homework3;

import java.util.concurrent.ThreadLocalRandom;

public class NumberPuzzle extends Game{
    private int startRange;
    private int endRange;
    private int secret;

    private int stepCount;
    private int step;

    public NumberPuzzle() {
        this.startRange = 0;
        this.endRange = 9;
        this.stepCount = 3;
    }

    @Override
    protected String getSecret() {
        return Integer.toString(secret);
    }

    @Override
    protected void restartGame() {
        secret = ThreadLocalRandom.current().nextInt(startRange, endRange);
        step = stepCount;
    }

    @Override
    protected boolean runGame() {
        int answer;
        while (step > 0) {
            System.out.printf("Угадайте число от %d до %d\n", startRange, endRange);

            answer = ConsoleHelper.inputNumber(startRange, endRange);
            if (answer == secret) {
                return true;
            } else if (answer < secret) {
                System.out.println("Введенное значение меньше");
            } else {
                System.out.println("Введенное значение больше");
            }
            step--;
        }
        return false;
    }

}
