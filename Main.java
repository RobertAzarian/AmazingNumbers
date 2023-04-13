package numbers;

import java.util.Arrays;
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
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and properties to search for;
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
                String property;
                StringBuilder wrongProperties = new StringBuilder();
                boolean isPropertiesRight = true;

                for (int j = 2; j < inpStrArr.length; j++) {        // 5000 5 duck jumping
                    property = inpStrArr[j].toUpperCase();
                    if (!("BUZZ".equals(property) ||
                            "DUCK".equals(property) ||
                            "PALINDROMIC".equals(property) ||
                            "GAPFUL".equals(property) ||
                            "SPY".equals(property) ||
                            "EVEN".equals(property) ||
                            "ODD".equals(property) ||
                            "SUNNY".equals(property) ||
                            "SQUARE".equals(property) ||
                            "JUMPING".equals(property))) {
                        isPropertiesRight = false;
                        wrongProperties.append(property).append(" ");
                    }
                }
                String[] wrongPropertiesArr = wrongProperties.toString().split(" ");
                if (!isPropertiesRight) {
                    if (wrongPropertiesArr.length == 1) {
                        System.out.printf("The property [%s] is wrong.\n", wrongPropertiesArr[0]);
                        System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SUNNY, SQUARE, JUMPING]\n");
                    } else {
                        System.out.println("The properties " + Arrays.toString(wrongPropertiesArr) + " are wrong.");
                        System.out.println("Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING]\n");
                    }
                    continue;
                }

                boolean isMutualExclusive = false;
                for (int i = 2; i < inpLength - 1; i++) {
                    for (int j = 3; j < inpLength; j++) {
                        String fV = inpStrArr[i];
                        String sV = inpStrArr[j];
                        if (("odd".equalsIgnoreCase(fV) && "even".equalsIgnoreCase(sV)) ||
                                ("even".equalsIgnoreCase(fV) && "odd".equalsIgnoreCase(sV)) ||
                                ("sunny".equalsIgnoreCase(fV) && "square".equalsIgnoreCase(sV)) ||
                                ("square".equalsIgnoreCase(fV) && "sunny".equalsIgnoreCase(sV)) ||
                                ("duck".equalsIgnoreCase(fV) && "spy".equalsIgnoreCase(sV)) ||
                                ("spy".equalsIgnoreCase(fV) && "duck".equalsIgnoreCase(sV))) {
                            System.out.printf("""
                            The request contains mutually exclusive properties: [%s, %s]
                            There are no numbers with these properties.

                            """, inpStrArr[2].toUpperCase(), inpStrArr[3].toUpperCase());
                            isMutualExclusive = true;
                            break;
                        }
                    }
                }
                if (!isMutualExclusive) {
                    int result4 = fourArg(inpStrArr);
                    if (result4 == 0) {
                        break;
                    }
                }
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
        String property = inpStrArr[2].toUpperCase();

        boolean isTrue;
        for (int i = 0; i < count; ) {
            String strN = String.valueOf(number);
            switch (property) {
                case "EVEN" -> isTrue = isEven(strN);
                case "ODD" -> isTrue = isOdd(strN);
                case "BUZZ" -> isTrue = isBuzz(strN);
                case "DUCK" -> isTrue = isDuck(strN);
                case "PALINDROMIC" -> isTrue = isPalindromic(strN);
                case "GAPFUL" -> isTrue = isGapful(strN);
                case "SPY" -> isTrue = isSpy(strN);
                case "SUNNY" -> isTrue = isSunny(strN);
                case "SQUARE" -> isTrue = isSquare(strN);
                case "JUMPING" -> isTrue = isJumping(strN);
                default -> {
                    System.out.printf("The property [%s] is wrong.\n", property);
                    System.out.println("Available properties: [BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, EVEN, ODD, SUNNY, SQUARE, JUMPING]\n");
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


    public static int fourArg(String[] inpStrArr) {
        long number = Long.parseLong(inpStrArr[0]);
        int count = Integer.parseInt(inpStrArr[1]);



        String property;
        int countProperties = inpStrArr.length - 2;
        int countOfTrue = 0;
        for (int i = 0; i < count; ) {
            String strN = String.valueOf(number);

            for (int j = 2; j < inpStrArr.length; j++) {        // 1 2 spy odd sunny even
                property = inpStrArr[j].toUpperCase();
                switch (property) {
                    case "EVEN" -> countOfTrue += isEven(strN) ? 1 : 0;
                    case "ODD" -> countOfTrue += isOdd(strN) ? 1 : 0;
                    case "BUZZ" -> countOfTrue += isBuzz(strN) ? 1 : 0;
                    case "DUCK" -> countOfTrue += isDuck(strN) ? 1 : 0;
                    case "PALINDROMIC" -> countOfTrue += isPalindromic(strN) ? 1 : 0;
                    case "GAPFUL" -> countOfTrue += isGapful(strN) ? 1 : 0;
                    case "SPY" -> countOfTrue += isSpy(strN) ? 1 : 0;
                    case "SUNNY" -> countOfTrue += isSunny(strN) ? 1 : 0;
                    case "SQUARE" -> countOfTrue += isSquare(strN) ? 1 : 0;
                    case "JUMPING" -> countOfTrue += isJumping(strN) ? 1 : 0;
                    default -> System.out.println("error");
                }
            }
            if (countOfTrue == countProperties) {
                twoArgs(new String[]{strN, "1"});
                countOfTrue = 0;
                number++;
                i++;
            } else {
                countOfTrue = 0;
                number++;
            }
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
        boolean isSunny = isSunny(strNum);
        boolean isSquare = isSquare(strNum);
        boolean isJumping = isJumping(strNum);


        map.put("even", isEven);
        map.put("odd", isOdd);
        map.put("buzz", isBuzz);
        map.put("duck", isDuck);
        map.put("palindromic", isPalindromic);
        map.put("gapful", isGapful);
        map.put("spy", isSpy);
        map.put("sunny", isSunny);
        map.put("square", isSquare);
        map.put("jumping", isJumping);
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

    public static boolean isSunny(String strNum) {
        return Math.sqrt((Long.parseLong(strNum) + 1)) % 1 == 0;
    }

    public static boolean isSquare(String strNum) {
        return Math.sqrt((Long.parseLong(strNum))) % 1 == 0;
    }

    public static boolean isJumping(String strNum) {        // 2,101,010,101
        boolean isJumping = true;
        for (int i = 1; i < strNum.length(); i++) {
            int n1 = Character.getNumericValue(strNum.charAt(i - 1));
            int n2 = Character.getNumericValue(strNum.charAt(i));
            isJumping = ((n1 - 1 == n2) || (n1 + 1 == n2));
            if (!isJumping) {
                break;
            }
        }
        return isJumping;
    }
}