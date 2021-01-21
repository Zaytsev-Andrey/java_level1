package ru.geekbrains.homework7.enums;

/**
 * Перечисление хранит наименование и сытность доступных видов еды
 */
public enum Food {
    FISH ("Рыба", 1),
    CHICKEN ("Курица", 3),
    RABBIT ("Кролик", 2),
    LAMB ("Ягненок", 4);

    private final String title;
    private final int satiety;            // сытность

    Food(String title, int satiety) {
        this.title = title;
        this.satiety = satiety;
    }

    /**
     * @return строковое представление блюда
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return сытность блюда
     */
    public int getSatiety() {
        return satiety;
    }
}
