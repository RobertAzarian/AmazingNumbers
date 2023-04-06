package numbers;

import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        long number;
        int count;
        String supRequests = """
                    Supported requests:
                    - enter a natural number to know its properties;
                    - enter two natural numbers to obtain the properties of the list:
                      * the first parameter represents a starting number;
                      * the second parameter shows how many consecutive numbers are to be processed;
                    - separate the parameters with one space;
                    - enter 0 to exit.
                    """;

        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println(supRequests);

        do {
            System.out.print("Enter a request: ");
            String inStr = scanner.nextLine();     // reading line
            System.out.println();

            if ("".equals(inStr)) {     // if str is empty
                System.out.println(supRequests);
                continue;
            }
            String[] inStrArr = inStr.split(" ");


            if (inStrArr.length == 1) {        // checking Number valid (number > 0)
                try {
                    number = Long.parseLong(inStrArr[0]);
                    if (number < 0) {
                        throw new NumberFormatException();
                    }
                } catch(NumberFormatException e) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }
                if (number == 0) {
                    System.out.println("Goodbye!");
                    break;
                }

                HashMap<String, Boolean> map = getMap(number);      // output properties
                System.out.printf(Locale.US, "Properties of %,d\n", number);
                for (String key : map.keySet()) {
                    System.out.println("\t\t" + key + ": " + map.get(key));
                }
                System.out.println();


            } else if (inStrArr.length == 2) {     // checking Number and Count valid (number > 0 && count > 0)
                try {
                    number = Long.parseLong(inStrArr[0]);
                    if (number < 0) {
                        throw new NumberFormatException();
                    }
                } catch(NumberFormatException e) {
                    System.out.println("The first parameter should be a natural number or zero.\n");
                    continue;
                }
                try {
                    count = Integer.parseInt(inStrArr[1]);
                    if (count <= 0) {
                        throw new NumberFormatException();
                    }
                } catch(NumberFormatException e) {
                    System.out.println("The second parameter should be a natural number.\n");
                    continue;
                }

                for (int i = 0; i < count; i++) {       // output properties
                    int countTrue = 0;
                    HashMap<String, Boolean> map = getMap(number + i);
                    for (String key : map.keySet()) {
                        if (map.get(key)) {
                            countTrue++;
                        }
                    }
                    System.out.print((number + i) + " is ");
                    if (countTrue == 0) {
                        System.out.println("does not have true properties");
                    } else {
                        for (String key : map.keySet()) {
                            if (map.get(key)) {
                                System.out.print(key);
                                countTrue--;
                                if (countTrue != 0) {
                                    System.out.print(", ");
                                }
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println();
            }
        } while (true);
    }


    public static HashMap<String, Boolean> getMap(long number) {
        HashMap<String, Boolean> map = new HashMap<>();

        boolean isEven = number % 2 == 0;
        boolean isOdd = !isEven;
        boolean isBuzz = (number % 7 == 0) || (number % 10 == 7);
        boolean isDuck = false;
        boolean isPalindromic = true;
        boolean isGapful = false;

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

        // isGapful
        if (strNum.length() >= 3) {
            int firstAndEnd = Integer.parseInt(String.valueOf(strNum.charAt(0)) + strNum.charAt(strNum.length() - 1));
            if (number % firstAndEnd == 0) {
                isGapful = true;
            }
        }

        map.put("even", isEven);
        map.put("odd", isOdd);
        map.put("buzz", isBuzz);
        map.put("duck", isDuck);
        map.put("palindromic", isPalindromic);
        map.put("gapful", isGapful);
        return map;
    }
}