import java.util.InputMismatchException;
import java.util.Scanner;

class Calculator{
    public static void main(String[] args) {
        Double number1 = 0.0;
        Double number2 = 0.0;
        Character expression = 'x';
        do {
            number1 = getNumber(number1);
            expression = getExpression(expression);
            number2 = getNumber(number2);
            Double result;
            switch(expression) {
                case ('+'):
                    result = number1 + number2;
                    System.out.println("Result = " + result);
                    break;
                case ('-'):
                    result = number1 - number2;
                    System.out.println("Result = " + result);
                    break;
                case ('/'):
                    result = number1 / number2;
                    System.out.println("Result = " + result);
                    break;
                case ('*'):
                    result = number1 * number2;
                    System.out.println("Result = " + result);
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
                if(expression.toString().matches("[-+/*]"))
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
