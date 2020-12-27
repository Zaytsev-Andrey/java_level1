package ru.geekbrains.java_level1.homework3;

abstract public class Game {
    abstract protected String getSecret();

    public void game() {
        do {
            restartGame();

            boolean resultGame = runGame();


            if (resultGame) {
                System.out.println("Поздравляем, вы выиграли!");
            } else {
                System.out.println("Вы проиграли, правильный ответ: " + getSecret());
            }

        } while (isContinue());
    }

    protected static boolean isContinue() {
        System.out.println("Повторить игру еще раз? 1 – да / 0 – нет");
        return ConsoleHelper.inputNumber(0, 1) == 1;
    }

    abstract protected boolean runGame();

    abstract protected void restartGame();
}
