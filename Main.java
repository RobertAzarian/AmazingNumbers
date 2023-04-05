package numbers;

import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("""
                Welcome to Amazing Numbers!
                                
                Supported requests:
                - enter a natural number to know its properties;
                - enter 0 to exit.
                """);

        while (true) {
            System.out.print("Enter a request: ");
            long number = scanner.nextLong();
            System.out.println();

            if (number > 0) {
                System.out.printf(Locale.US, "Properties of %,d\n", number);

                    HashMap<String, Boolean> map = getMap(number);

                    for (String key : map.keySet()) {
                        System.out.println("\t\t" + key + ": " + map.get(key));
                    }
                    System.out.println();
            } else if (number == 0) {
                System.out.print("Goodbye!");
                break;
            } else {
                System.out.println("The first parameter should be a natural number or zero.\n");
            }
        }
    }

    public static HashMap<String, Boolean> getMap(long number) {
        HashMap<String, Boolean> map = new HashMap<>();

        boolean isEven = number % 2 == 0;
        boolean isOdd = !isEven;
        boolean isBuzz = (number % 7 == 0) || (number % 10 == 7);
        boolean isDuck = false;
        boolean isPalindromic = true;

        String strNum = String.valueOf(number);

        // isDuck
        for (int i = 1; i < strNum.length(); i++) {
            if (strNum.charAt(i) == '0') {
                isDuck = true;
                break;
            }
        }

        // isPalindromic
        int count = strNum.length();
        for (int i = 0, j = count - 1; i < (count / 2) || j > (count / 2) - 1; i++, j--) { // 123456789    1234.5678
            if (strNum.charAt(i) != strNum.charAt(j)) {
                isPalindromic = false;
                break;
            }
        }

        map.put("even", isEven);
        map.put("odd", isOdd);
        map.put("buzz", isBuzz);
        map.put("duck", isDuck);
        map.put("palindromic", isPalindromic);
                return map;
    }
}