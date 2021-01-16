package ru.geekbrains.homework5;

/**
 * Утилитный класс со статическими методами для работы со строками
 */
public class StringUtils {
    /**
     * Преобразует строку из параметра value к строке для хранения. Для этого удаляются пробельные символы в начале и
     * в коце строки, все пробельные символы между словами заменяются на один пробельный символ, все буквы приводятся
     * к нижнему регистру.
     * @return  если value != null и длина value > 0 возвращается преобразованная строка, иначе возвращается
     *          исходная строка.
     */
    public static String convertStringForStorage(String value) {
        if (value != null && !value.isEmpty()) {
            value = value.trim();
            value = value.replaceAll("\\s{2,}", " ");
            value = value.toLowerCase();
        }

        return value;
    }

    /**
     * Преобразует номер телефона из параметра value к номеру телефона для хранения. Для этого удаляются пробельные
     * символы в начале и в коце строки, если номер на чинается на символ '+', тогда он удаляется, если номер начинается
     * на символ '8' тогда он заменяется на символ '7'.
     * @return  если value != null и длина value > 0 возвращается преобразованная строка, иначе возвращается
     *          исходная строка.
     */
    public static String convertPhoneForStorage(String value) {
        if (value != null && !value.isEmpty()) {
            value = value.trim();
            if (value.charAt(0) == '+') {
                value = value.substring(1);
            } else if (value.charAt(0) == '8') {
                value = value.replaceFirst("8", "7");
            }
        }

        return value;
    }

    /**
     * Преобразует строку из параметра value к строке в которой каждое новое слово начинается с заглавной буквы.
     * @return  если value != null и длина value > 0 возвращается преобразованная строка, иначе возвращается
     *          исходная строка.
     */
    public static String capitalizeAll(String value) {
        if (value != null && !value.isEmpty()) {
            StringBuilder capitalizeName = new StringBuilder();
            String[] names = value.split(" ");
            for (String name : names) {
                char[] chars = name.toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                if (capitalizeName.length() > 0) {
                    capitalizeName.append(" ");
                }
                capitalizeName.append(chars);
            }
            return capitalizeName.toString();
        } else {
            return value;
        }
    }

    /**
     * Преобразует строку из параметра value к строке формата +Х(ХХХ)ХХХ-ХХ-ХХ
     * @return  если value != null и длина value > 0 возвращается преобразованная строка, иначе возвращается
     *          исходная строка.
     */
    public static String formatPhone(String value) {
        if (value != null && !value.isEmpty()) {

            StringBuilder phone = new StringBuilder("+");
            phone.append(value.substring(0, 1));
            phone.append("(");
            phone.append(value.substring(1, 4));
            phone.append(")");
            phone.append(value.substring(4, 7));
            phone.append("-");
            phone.append(value.substring(7, 9));
            phone.append("-");
            phone.append(value.substring(9, 11));

            return phone.toString();
        } else {
            return value;
        }
    }
}
