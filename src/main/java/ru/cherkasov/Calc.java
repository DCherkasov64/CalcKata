package ru.cherkasov;

import java.util.Scanner;

public class Calc {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите два одинаковых числа (арабских или римских): ");
        String expression = scanner.nextLine();
        System.out.println(parse(expression));
    }

    public static String parse(String expression) throws Exception {
        int num1;
        int num2;
        String operation;
        String result;
        boolean isRoman;
        String[] operands = expression.split("[+\\-*/]");
        if (operands.length != 2) throw new Exception("Должно быть два операнда");
        operation = detectOperation(expression);
        if (operation == null) throw new Exception("Неподдерживаемая математическая операция");
        //Если оба числа римские:
        if (Roman.isRoman(operands[0]) && Roman.isRoman(operands[1])) {
            //Конвертируем оба числа в арабские для вычисления действия
            num1 = Roman.convertToArabian(operands[0]);
            num2 = Roman.convertToArabian(operands[1]);
            isRoman = true;
        }
        //Если оба числа арабские:
        else if (!Roman.isRoman(operands[0]) && !Roman.isRoman(operands[1])) {
            num1 = Integer.parseInt(operands[0]);
            num2 = Integer.parseInt(operands[1]);
            isRoman = false;
        }
        //Если одно число римское, а другое - арабское:
        else {
            throw new Exception("Числа должны быть одного формата (римские или арабские)");
        }
        if (num1 > 10 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10");
        }
        int arabian = calc(num1, num2, operation);
        if (isRoman) {
            //Если римское число получилось меньше либо равно нулю, выбивает ошибку:
            if (arabian <= 0) {
                throw new Exception("Римское число должно быть больше нуля");
            }
            //Конвертер результата операции из арабского в римское:
            result = Roman.convertToRoman(arabian);
        } else {
            //Конвертер арабского числа в тип String
            result = String.valueOf(arabian);
        }
        //Возвращаем результат:
        return result;
    }

    static String detectOperation(String expression) {
        if (expression.contains("+")) return "+";
        else if (expression.contains("-")) return "-";
        else if (expression.contains("*")) return "*";
        else if (expression.contains("/")) return "/";
        else return null;
    }

    static int calc(int a, int b, String oper) {

        return switch (oper) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> a / b;
        };
    }

}