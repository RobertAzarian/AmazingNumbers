package numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        write your code here
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a natural number:");
        int number = scanner.nextInt();

        if (number > 0) {
            boolean isEven = number % 2 == 0;
            boolean isBuzz = (number % 7 == 0) || (number % 10 == 7);
            boolean isDuck = false;

            String strNum = String.valueOf(number);
            for (int i = 1; i < strNum.length(); i++) {
                if (strNum.charAt(i) == '0') {
                    isDuck = true;
                    break;
                }
            }

            System.out.printf("""
                    Properties of %d
                            even: %b
                             odd: %b
                            buzz: %b
                            duck: %b
                    """, number, isEven, !isEven, isBuzz, isDuck);
        } else {
            System.out.println("This number is not natural!");
        }
    }
}
