package ru.geekbrains.homework5;

public class Homework5 {
    public static void main(String[] args) {
        Employee[] employees = new Employee[5];

        employees[0] = createEmployee("Мельникова Ксения Витальевна", "PHP-разработчик", "melnikova1@mail.ru", "+79261234567", 90000, 28);
        employees[1] = createEmployee("   пименов максим евгеньевич", "Водитель", "pimenov@mail.ru", "79259876543", 60000.555, 48);
        employees[2] = createEmployee("Миронова Елизавета Валерьевна", "Программист 1C", "mironova_e@mail.ru", "+7(916)1234567", 40000, 22);
        employees[3] = createEmployee("Лебедева Екатерина Сергеевна", "Старший продавец-консультант", "lebedeva@domain.mail.ru", "89169876543", 40000, 54);
        employees[4] = createEmployee("Прокопенко         Иван Алексеевич", "Торговый представитель", "i.prokopenko@mail.ru", "+79031234567", 50000, 41);

        System.out.println();

        for (Employee employee : employees) {
            // Если сотрудник был создан и его возраст более 40 лет (включительно) выводим о нем информацию
            if (employee != null && employee.getAge() >= 40) {
                employee.printInfo();
                System.out.println();
            }
        }
    }

    /**
     * Создает нового сотрудника, в случае исключения выводит информацию для какого сотрудника на удалось создать объект
     * @return возвращает нового сотрудника, в случае не корректных данных возвращает null
     */
    private static Employee createEmployee(String fullName, String position, String email, String phone, double salary, int age) {
        Employee employee = null;

        try {
            employee = new Employee(fullName, position, email, phone, salary, age);
        } catch (IllegalArgumentException e) {
            System.out.println("LOG: " + e.getMessage());
        }

        return employee;
    }
}
