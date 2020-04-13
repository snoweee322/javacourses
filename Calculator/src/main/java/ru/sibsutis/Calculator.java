package ru.sibsutis;

import java.util.InputMismatchException;
import java.util.Scanner;

class MyException extends Exception {
    public MyException(String message)
    {
        super (message);
    }
}

class Fact {
    public static int factorial(Integer n) {
        try {
            if (n > 12)
                throw new MyException("Integer range doesn't include " + n + "!");
        } catch (MyException e) {
            System.err.println(e.getMessage());
            return -1;
        }
        if (n.equals(0))
            return 1;
        return n * factorial(n-1);
    }
}

class Generic { // simple

    public <T extends Number> double add (T one, T two) {
        return one.doubleValue() + two.doubleValue();
    }
    public <T extends Number> double multiply (T one, T two) {
        return one.doubleValue() * two.doubleValue();
    }
}

class Calculator{
    public static void main(String[] args) {
        Generic gen = new Generic();
        Double number1 = 0.0;
        Double number2 = 0.0;
        Character expression = 'x';
        do {
            number1 = getNumber(number1);
            expression = getExpression(expression);
            if (!expression.equals('!'))
                number2 = getNumber(number2);
            switch(expression) {
                case ('+'):
                    System.out.println("Result = " + gen.add(number1, number2));
                    break;
                case ('-'):
                    System.out.println("Result = " + (number1 - number2));
                    break;
                case ('/'):
                    System.out.println("Result = " + (number1 / number2));
                    break;
                case ('*'):
                    System.out.println("Result = " + gen.multiply(number1, number2));
                    break;
                case ('!'):
                    System.out.println("Result = " + Fact.factorial(number1.intValue()));
                    break;
                default:
                    System.out.println("Error");
                    break;
            }
        } while(true);
    }
    public static Double getNumber(Double number) {
        System.out.println("Print number:");
        do {
            Scanner input = new Scanner(System.in);
            try {
                number = input.nextDouble();
                return number;
            } catch (InputMismatchException e)
            {
                System.err.println("Try again!");
            }
        } while (true);
    }
    public static Character getExpression(Character expression) {
        System.out.println("Print expression:");
        do {
            Scanner input = new Scanner(System.in);
            try {
                expression = input.next().charAt(0);;
                if(expression.toString().matches("[-+/*!]"))
                    return expression;
                else
                    System.out.println("Try again!");
            } catch (InputMismatchException e)
            {
                System.err.println("Try again!");
            }
        } while (true);
    }
}
