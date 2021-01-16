package ru.geekbrains.homework5;

import java.math.BigDecimal;

// 1. Все поля класса сделал закрытыми
// 2. Сделал один конструктор с пятью параметрами, для заполнения всех полей класса. Такое представление полностью
//      соответствует примеру из задания 4. Конструктор по умолчани создавать не стал, чтобы избежать возможность
//      создания сотрудников с незаполненными полями.
// 3. По заданию требуется метод который выводит информацию в консоль. Поэтому создал метод printInfo(), а не
//      переотпределил метод toString() который бы возвращал строковое представление объекта.
//
// Вне класса требуется доступ к полю age, поэтому для него создал геттер
// Т.к. условие домашнего задания не подразумевает получение значений и установку значений для остальных полей, для них
// геттеры и сеттеры создавать не стал.
public class Employee {

    private String fullName;        // ФИО
    private String position;        // Должность
    private String email;           // E-mail
    private String phone;           // Номер телефона
    private BigDecimal salary;      // Зарплата. Т.к. денежный формат подразумевает использование рублей и копеек,
                                    // для хранения выбрал вещественный тип (больше для того чтобы по практиковаться с
                                    // BigDecimal)
    private int age;                // Возраст

    public Employee(String fullName, String position, String email, String phone, double salary, int age) {
        // Преобразование параметров fullName, position, email и phone в формат удобный для хранения
        fullName = StringUtils.convertStringForStorage(fullName);
        position = StringUtils.convertStringForStorage(position);
        email = StringUtils.convertStringForStorage(email);
        phone = StringUtils.convertPhoneForStorage(phone);


        // Проверка переданных значений
        // Методы для проверки парамотров "зарплата" и "возраст" реализованы в нутри текущего класса (т.к. их реализация
        // может меняться в зависимости от регламента) в виде статических методов потому что проверка входных значений
        // должна выполняться до того как будут изменены значения полей класса.
        if (!isValidSalary(salary) || !isValidAge(age) || !isValidEmail(email) || !isValidPhone(phone)) {
            throw new IllegalArgumentException("Некорректные данные для сотрудника " + StringUtils.capitalizeAll(fullName));
        }

        this.fullName = fullName;
        this.position = position;
        this.email = email;
        this.phone = phone;
        // Значение параметра salary округляем до сотых
        this.salary = new BigDecimal(salary).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void printInfo() {
        System.out.printf("ФИО:\t\t\t%s\n", StringUtils.capitalizeAll(fullName));
        System.out.printf("Должность:\t\t%s\n", position);
        System.out.printf("E-mail:\t\t\t%s\n", email);
        System.out.printf("Номер телефона:\t%s\n", StringUtils.formatPhone(phone));

        BigDecimal ruble = salary.setScale(0, BigDecimal.ROUND_DOWN);
        BigDecimal fraction = salary.subtract(ruble);
        BigDecimal rank = new BigDecimal(100);
        BigDecimal kopeck = fraction.multiply(rank);
        System.out.printf("Зарплата:\t\t%,d руб. %02d коп.\n", ruble.intValue(), kopeck.intValue());

        System.out.printf("Возраст:\t\t%d\n", age);
    }

    /**
     * @return  Возвращает true если параметр salary >= 0 (учитывая что сотрудник может работать бесплатно),
     *          иначе возвращает false
     */
    private static boolean isValidSalary(double salary) {
        return salary >= 0;
    }

    /**
     * @return  Возвращает true если параметр age >= 15 (т.к официально можно работать в России с 16 лет,
     *          в соответствии со ст. 63 ТК РФ, однако трудовые отношения можно начать с 15 лет, если подросток уже
     *          закончил учебу в школе (например, 9 классов) либо продолжает получать общее образование на вечернем
     *          отделении либо по заочной форме) иначе возвращается false
     */
    private static boolean isValidAge(int age) {
        return age >= 15;
    }

    /**
     * @return  Возвращает true если параметр email сответствует регулярному выражению: имя и домен начинаются с буквы и
     * содержат только буквы цифры и символы "-", "_" и ".". Доменых имен может быть несколько, разделяются они символом
     * "." и доменное имя всегда заканчивается буквой. Иначе возвращается false
     */
    private static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z]+[a-zA-Z0-9-_.]+@([a-zA-Z]+[a-zA-Z0-9-_]+[.])+[a-zA-Z]+$");
    }

    /**
     * @return  Возвращает true если параметр phone состоит только из 11 цифр, иначе возвращается false
     */
    private static boolean isValidPhone(String phone) {
        return phone.matches("\\d{11}");
    }
}
