package numbers;

import java.util.Locale;
import java.util.Scanner;
import java.util.HashMap;

public class Main {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String supRequests = """
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameters show how many consecutive numbers are to be processed;
                - two natural numbers and a property to search for;
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
            String[] inpStrArr = inStr.split(" ");

            int inpLength = inpStrArr.length;


            if (inpLength == 0) {
                continue;
            } else if (inpLength == 1) {
                int result1 = oneArg(inpStrArr[0]);
                if (result1 == 0) {
                    break;
                } else if (result1 == 1) {
                    continue;
                }
            } else if (inpLength == 2) {
                int result2 = twoArgs(inpStrArr);
                System.out.println();
                if (result2 == 0) {
                    break;
                } else if (result2 == 1) {
                    continue;
                }
            } else if (inpLength == 3) {
                int result3 = threeArg(inpStrArr);
                if (result3 == 0) {
                    break;
                } else if (result3 == 1) {
                    continue;
                }
            } else {
                System.out.println("error");
            }
        } while (true);
    }


    public static int oneArg(String inpStr) {
        long number;
        try {
            number = Long.parseLong(inpStr);
            if (number < 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            return 1;
        }
        if (number == 0) {
            System.out.println("Goodbye!");
            return 0;
        }

        HashMap<String, Boolean> map = getMap(number);      // output properties
        System.out.printf(Locale.US, "Properties of %,d\n", number);
        for (String key : map.keySet()) {
            System.out.println("\t\t" + key + ": " + map.get(key));
        }
        System.out.println();
        return 2;
    }


    public static int twoArgs(String[] inpStrArr) {
        long number;
        int count;

        try {
            number = Long.parseLong(inpStrArr[0]);
            if (number < 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            System.out.println("The first parameter should be a natural number or zero.\n");
            return 1;
        }
        if (number == 0) {
            return 0;
        }

        try {
            count = Integer.parseInt(inpStrArr[1]); // 1
            if (count <= 0) {
                throw new NumberFormatException();
            }
        } catch(NumberFormatException e) {
            System.out.println("The second parameter should be a natural number.\n");
            return 1;
        }

        for (int i = 0; i < count; i++) {       // output properties
            int countTrue = 0;
            HashMap<String, Boolean> map = getMap(number + i);
            for (String key : map.keySet()) {
                if (map.get(key)) {
                    countTrue++;        // count of true properties
                }
            }
            System.out.printf(Locale.US, "\t\t%,d is ", number + i);
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
        return 2;
    }


    public static int threeArg(String[] inpStrArr) {
        long number = Long.parseLong(inpStrArr[0]);
        int count = Integer.parseInt(inpStrArr[1]);
        String property = inpStrArr[2];

        boolean isTrue;
        for (int i = 0; i < count; ) {
            String strN = String.valueOf(number);
            switch (property.toLowerCase()) {
                case "even" -> isTrue = isEven(strN);
                case "odd" -> isTrue = isOdd(strN);
                case "buzz" -> isTrue = isBuzz(strN);
                case "duck" -> isTrue = isDuck(strN);
                case "palindromic" -> isTrue = isPalindromic(strN);
                case "gapful" -> isTrue = isGapful(strN);
                case "spy" -> isTrue = isSpy(strN);
                default -> {
                    System.out.printf("The property [%s] is wrong.\n", property.toUpperCase());
                    System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD]\n");
                    return 1;
                }
            }
            if (isTrue) {
                twoArgs(new String[]{strN, "1"});
                i++;
            }
            number++;
        }
        System.out.println();
        return 2;
    }



    public static HashMap<String, Boolean> getMap(long number) {
        HashMap<String, Boolean> map = new HashMap<>();

        String strNum = String.valueOf(number);

        boolean isEven = isEven(strNum);
        boolean isOdd = isOdd(strNum);
        boolean isBuzz = isBuzz(strNum);
        boolean isDuck = isDuck(strNum);
        boolean isPalindromic = isPalindromic(strNum);
        boolean isGapful = isGapful(strNum);
        boolean isSpy = isSpy(strNum);


        map.put("even", isEven);
        map.put("odd", isOdd);
        map.put("buzz", isBuzz);
        map.put("duck", isDuck);
        map.put("palindromic", isPalindromic);
        map.put("gapful", isGapful);
        map.put("spy", isSpy);
        return map;
    }



    public static boolean isEven(String strNum) {
        return Long.parseLong(strNum) % 2 == 0;
    }

    public static boolean isOdd(String strNum) {
        return Long.parseLong(strNum) % 2 == 1;
    }

    public static boolean isBuzz(String strNum) {
        long number = Long.parseLong(strNum);
        return (number % 7 == 0) || (number % 10 == 7);
    }

    public static boolean isDuck(String strNum) {
        for (int i = 1; i < strNum.length(); i++) {
            if (strNum.charAt(i) == '0') {
                return true;
            }
        }
        return false;
    }

    public static boolean isPalindromic(String strNum) {
        int countNumbs = strNum.length();
        for (int i = 0, j = countNumbs - 1; i < (countNumbs / 2) || j > (countNumbs / 2) - 1; i++, j--) {
            if (strNum.charAt(i) != strNum.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isGapful(String strNum) {
        if (strNum.length() >= 3) {
            int firstAndEnd = Integer.parseInt(String.valueOf(strNum.charAt(0)) + strNum.charAt(strNum.length() - 1));
            return Long.parseLong(strNum) % firstAndEnd == 0;
        }
        return false;
    }

    public static boolean isSpy(String strNum) {
        long sum = 0;
        long multi = 1;

        String[] strArrNumbs = strNum.split("");
        for (String strArrNumb : strArrNumbs) {
            sum += Long.parseLong(strArrNumb);
            multi *= Long.parseLong(strArrNumb);
        }
        return sum == multi;
    }
}