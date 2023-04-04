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
            boolean isDivisibleSev = number % 7 == 0;
            boolean isEndSeven = number % 10 == 7;
            System.out.println("This number is " + (isEven ? "Even." : "Odd."));
            System.out.println("It is " + (isDivisibleSev || isEndSeven ? "a Buzz number." : "not a Buzz number."));
            System.out.println("Explanation:");

            if (isDivisibleSev || isEndSeven) {
                if (isDivisibleSev) {
                    System.out.print(number + " is divisible by 7");
                    if (isEndSeven) {
                        System.out.print(" and ends with 7");
                    }
                } else {
                    System.out.print(number + " ends with 7");
                }
                System.out.println(".");
            } else {
                System.out.println(number + " is neither divisible by 7 nor does it end with 7.");
            }
        } else {
            System.out.println("This number is not natural!");
        }
    }
}
