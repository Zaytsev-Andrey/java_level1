package ru.geekbrains.homework8;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator extends JFrame {
    JTextField textField;

    String regexPow = "x" + '\u00B2';
    String regexEndsPow = ".*\\^\\s2$";
    String regexSeparator = ",";
    String regexOperations = "[-*/+]";
    String regexDigit = "\\d";
    String regexEndsOperation = ".*[*/+-]\\s$";
    String regexEndsDigit = ".*\\d$";
    String regexEndsFractionalNumber = ".*\\d+,\\d+$";
    String regexEndsSeparator = ".*,$";

    /**
     * Переопределяет метод keyPressed() класса KeyAdapter для ввода цифр и операций с клавиатуры в запрещенное для
     * редактирование поле textField
     */
    private class CalculatorKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();

            // Числа и операции
            if (code == 111 || code == 47                                                               // Деление
                    || code == 106 || e.isShiftDown() && code == 56                                         // Умножение
                    || code == 107 || e.isShiftDown() && code == 61                                         // Сложение
                    || code == 109 || code == 45                                                        // Вычитание
                    || code >= 96 && code <= 105 || !e.isShiftDown() && code >= 48 && code <= 57) {     // Числа
                checkDigitOrOperationSymbol("" + e.getKeyChar());
            }

            // Enter
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                calculate();
            }

            // Квадрат
            if (e.isShiftDown() && code == 54) {
                checkDigitOrOperationSymbol("x" + '\u00B2');
            }

            // Разделитель разрядов числа
            if (code == 110 || code == 44 || code == 46) {
                checkDigitOrOperationSymbol(",");
            }

            // Backspace
            if (code == 8) {
                deleteSymbol();
            }

            // Esc
            if (code == 27) {
                clear();
            }
        }
    }

    public Calculator() throws HeadlessException {
        setTitle("Калькулятор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 300, 300, 300);
        setResizable(false);

        // Создаем панель для текста
        JPanel textPanel = new JPanel();
        textPanel.setFocusable(false);
        textPanel.setPreferredSize(new Dimension(1, 60));
        textPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        textPanel.setLayout(new BorderLayout());

        // Создаем панель для операций
        JPanel operationPanel = new JPanel();
        operationPanel.setFocusable(false);
        operationPanel.setBorder(BorderFactory.createEmptyBorder(5,5,10,5));
        GridLayout gridLayout = new GridLayout(5,4);
        gridLayout.setVgap(10);
        gridLayout.setHgap(10);
        operationPanel.setLayout(gridLayout);

        // Создаем текстовое поле и помещаем на панель
        textField = new JTextField();
        textField.setFocusable(false);
        textField.setFont(textField.getFont().deriveFont(18f));
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setEditable(false);
        textPanel.add(textField);

        // Создаем и размещаем кнопки цифр и операций
        operationPanel.add(createButtonClearingText());
        operationPanel.add(createButtonDeletingSymbol());
        operationPanel.add(createButtonSettingText("x" + '\u00B2'));
        operationPanel.add(createButtonSettingText("/"));
        int i = 9;
        for (int j = i - 2; j <= i; j++) {
            operationPanel.add(createButtonSettingText("" + j));
        }
        operationPanel.add(createButtonSettingText("*"));
        i -= 3;
        for (int j = i - 2; j <= i; j++) {
            operationPanel.add(createButtonSettingText("" + j));
        }
        operationPanel.add(createButtonSettingText("-"));
        i -= 3;
        for (int j = i - 2; j <= i; j++) {
            operationPanel.add(createButtonSettingText("" + j));
        }
        operationPanel.add(createButtonSettingText("+"));
        operationPanel.add(createButtonSettingText(","));
        operationPanel.add(createButtonSettingText("0"));
        JButton emptyButton = new JButton();
        emptyButton.setVisible(false);
        operationPanel.add(emptyButton);
        operationPanel.add(createButtonCalculate());

        // Размещаем панели на форме
        add(BorderLayout.NORTH, textPanel);
        add(BorderLayout.CENTER, operationPanel);

        addKeyListener(new CalculatorKeyListener());
        setFocusable(true);
        setVisible(true);
    }

    /**
     * Создает объект класса JButton и добавляет ему ActionListener, реализованный в виде лямбда выражения, вызывающий
     * метод контроллируещий введеный символ
     * @param name символ
     * @return новый объект JButton
     */
    private JButton createButtonSettingText(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);

        button.addActionListener((e) -> {
            String symbol = ((JButton) e.getSource()).getText();
            checkDigitOrOperationSymbol(symbol);
        });

        return button;
    }

    /**
     * Создает объект класса JButton и добавляет ему ActionListener, реализованный в виде лямбда выражения, вызывающий
     * метод очищающий поле ввода textField
     * @return новый объект JButton
     */
    private JButton createButtonClearingText() {
        JButton button = new JButton("C");
        button.setFocusable(false);

        button.addActionListener((e) -> clear());

        return button;
    }

    /**
     * Создает объект класса JButton и добавляет ему ActionListener, реализованный в виде лямбда выражения, вызывающий
     * метод удаляющий последний символ в поле ввода textField
     * @return новый объект JButton
     */
    private JButton createButtonDeletingSymbol() {
        JButton button = new JButton("" + '\u2190');
        button.setFocusable(false);

        button.addActionListener((e) -> deleteSymbol());

        return button;
    }

    /**
     * Создает объект класса JButton и добавляет ему ActionListener, реализованный в виде лямбда выражения, вызывающий
     * метод вычисляющий выражения из поля ввода textField
     * @return новый объект JButton
     */
    private JButton createButtonCalculate() {
        JButton button = new JButton("=");
        button.setFocusable(false);

        button.addActionListener((e) -> calculate());

        return button;
    }

    /**
     * Проверяет допустимость введенного символа и в случае успеха добавляет его к выразению
     * @param symbol введенный символ
     */
    private void checkDigitOrOperationSymbol(String symbol) {
        String value = textField.getText();

        // Если нажата кнопка операции "+", "-", "*", "/":
        //      1) если выражение не пустое и не заканчивается на чило завершаем работу метода
        //      2) если выражение пустое и операция не равна "-" завершаем работу метода
        // Иначе если была нажата кнопка ",":
        //      1) если выражение пустое или заканчивается операцией "+", "-", "*", "/" тогда заменяем ","
        //      на "0,"
        //      2) иначе если выражение заканчивается на дробное число или на операцию возведения в квадрат
        //      или заканчивается на символ не являющийся числом, тогда завершаем работу метода
        // Иначе если нажата числовая кнопка:
        //      1) и выражение заканчивается на операцию возведения в квадрат, тогда завершаем работу метода
        // Иначе если нажата кнопка "возведение в квадрат":
        //      1) если выражение заканчивается на операцию возведения в квадрат или не заканчивается на число,
        //      тогда завершаем работу метода
        if (symbol.matches(regexOperations)) {
            if (!value.isEmpty()) {
                if (!value.matches(regexEndsDigit)) {
                    return;
                }
                symbol = " " + symbol + " ";
            } else if (!symbol.equals("-")) {
                return;
            }
        } else if (symbol.matches(regexSeparator)) {
            if (value.isEmpty() || value.matches(regexEndsOperation)) {
                symbol = 0 + symbol;
            } else if (value.matches(regexEndsFractionalNumber) || value.matches(regexEndsPow)
                    || !value.matches(regexEndsDigit)) {
                return;
            }
        } else if (symbol.matches(regexDigit) && value.matches(regexEndsPow)) {
            return;
        } else if (symbol.matches(regexPow)) {
            if (value.matches(regexEndsPow) || !value.matches(regexEndsDigit)) {
                return;
            }
            symbol = " ^ 2";
        }

        textField.setText(textField.getText() + symbol);
    }

    /**
     * Удаляет последний символ в числе или последнюю операцию целиком
     */
    private void deleteSymbol() {
        String value = textField.getText();
        int countDeletingSymbols = 0;

        if (value.matches(regexEndsPow)) {
            countDeletingSymbols = 4;
        } else if (value.matches(regexEndsOperation)) {
            countDeletingSymbols = 3;
        } else if (value.matches(regexEndsDigit) || value.matches(regexEndsSeparator) || value.equals("-")) {
            countDeletingSymbols = 1;
        }

        textField.setText(value.substring(0, value.length() - countDeletingSymbols));
    }

    /**
     * Очищает поле ввода
     */
    private void clear() {
        textField.setText("");
    }

    /**
     * Вычисляет выражение
     */
    private void calculate() {
        String value = textField.getText();
        Pattern patternPow = Pattern.compile("(?<Op1>(^-)?\\d+([,.]\\d+)?)\\^2");
        Pattern patternMulOrDiv = Pattern.compile("(?<Op1>\\d+([,.]\\d+)?)(?<Opr>[/*])(?<Op2>\\d+([,.]\\d+)?)");
        Pattern patternSumOrSub = Pattern.compile("(?<Op1>-?\\d+([.]\\d+)?)(?<Opr>[-+])(?<Op2>\\d+([,.]\\d+)?)");
        Matcher matcher;
        BigDecimal operand1;
        BigDecimal operand2;
        BigDecimal result;

        // Если выражение пустое или заканчивается на "," или заканчивается на символ операции [/*-+] завершаем работу
        // метода
        if (value.isEmpty() || value.matches(regexEndsSeparator) || value.matches(regexEndsOperation)) {
            return;
        }

        // Удаляем все лишние пробелы из выражения
        value = value.replaceAll(" ", "");
        // Заменяем символы "," на символ "."
        value = value.replaceAll(",", ".");

        // Последовательно ищем в строке выражения все операции возведения в квадрат и заменяем их вычисленным значением
        matcher = patternPow.matcher(value);
        while (matcher.find()) {
            operand1 = new BigDecimal(matcher.group("Op1"));
            result = operand1.pow(2);
            value = matcher.replaceFirst(result.toString());
            matcher = patternPow.matcher(value);
        }

        // Последовательно ищем в строке выражения все операции умножения или деления и заменяем их вычисленным
        // значением
        matcher = patternMulOrDiv.matcher(value);
        while (matcher.find()) {
            operand1 = new BigDecimal(matcher.group("Op1"));
            operand2 = new BigDecimal(matcher.group("Op2"));
            if (matcher.group("Opr").equals("*")) {
                result = operand1.multiply(operand2);
            } else {
                result = operand1.divide(operand2, 15, RoundingMode.CEILING);
            }
            value = matcher.replaceFirst(result.toString());
            matcher = patternMulOrDiv.matcher(value);
        }

        // Последовательно ищем в строке выражения все операции сложения или вычитания и заменяем их вычисленным
        // значением
        matcher = patternSumOrSub.matcher(value);
        while (matcher.find()) {
            operand1 = new BigDecimal(matcher.group("Op1"));
            operand2 = new BigDecimal(matcher.group("Op2"));
            if (matcher.group("Opr").equals("+")) {
                result = operand1.add(operand2);
            } else {
                result = operand1.subtract(operand2);
            }
            value = matcher.replaceFirst(result.toString());
            matcher = patternSumOrSub.matcher(value);
        }

        // Заменяем символы "." на символ ","
        value = value.replaceAll("\\.", ",");
        // Удаляем все незначемые нули в результате
        value = value.replaceAll("(\\d+,[1-9]+)0+\\b", "$1");
        value = value.replaceAll("(\\d+),0+\\b", "$1");
        textField.setText(value);
    }
}

