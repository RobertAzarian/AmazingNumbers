package numbers;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static String supRequests = """
            Supported requests:
            - enter a natural number to know its properties;
            - enter two natural numbers to obtain the properties of the list:
              * the first parameter represents a starting number;
              * the second parameter shows how many consecutive numbers are to be printed;
            - two natural numbers and properties to search for;
            - a property preceded by minus must not be present in numbers;
            - separate the parameters with one space;
            - enter 0 to exit.
            """;


    public static void main(String[] args) {

        System.out.println("Welcome to Amazing Numbers!\n");
        System.out.println(supRequests);

        while (true) {
            System.out.print("Enter a request: ");
            String inStr = scanner.nextLine();
            String[] inpStrArr = inStr.split(" ");
            System.out.println();

            if (checkingNumbers(inStr) == 1) {
                continue;
            } else if (checkingNumbers(inStr) == 0) {
                break;
            }

            if (inpStrArr.length > 2) {
                if (!isPropertiesCorrect(inpStrArr)) {
                    continue;
                }
            }

            processing(inpStrArr);  // MAYBE BETTER PASS STRING
        }
    }


    public static int checkingNumbers(String inStr) {
        String[] inpStrArr = inStr.split(" ");

        if ("".equals(inStr)) {     // if str is empty
            System.out.println(supRequests);
            return 1;

        } else if ("0".equals(inpStrArr[0])) { // if first is 0
            System.out.println("Goodbye!");
            return 0;

        } else {
            long number;
            long count;
            try {
                number = Long.parseLong(inpStrArr[0]);
                if (number < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                System.out.println("The first parameter should be a natural number or zero.\n");
                return 1;
            }
            if (inpStrArr.length >= 2) {
                try {
                    count = Integer.parseInt(inpStrArr[1]);
                    if (count <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    System.out.println("The second parameter should be a natural number.\n");
                    return 1;
                }
            }
        }
        return 2;
    }


    public static boolean isPropertiesCorrect(String[] inpStrArr) {
        boolean isExists = false;
        StringBuilder wrongProperties = new StringBuilder();

        for (int i = 2; i < inpStrArr.length; i++) {
            String inpProperty = inpStrArr[i].toUpperCase();
            for (Property property : Property.values()) {
                if (inpProperty.matches("-?" + property.name())) {
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                wrongProperties.append(inpProperty).append(" ");
            }
            isExists = false;
        }
        if (!wrongProperties.isEmpty()) {
            ArrayList<String> propNamesArr = new ArrayList<>(Property.values().length);
            for (Property property : Property.values()) {
                propNamesArr.add(property.name());
            }
            String[] wrongPropertiesArr = wrongProperties.toString().split(" ");
            if (wrongPropertiesArr.length == 1) {
                System.out.printf("The property [%s] is wrong.\n", wrongPropertiesArr[0]);
                System.out.println("Available properties:" + propNamesArr);
                System.out.println();
            } else {
                System.out.println("The properties " + Arrays.toString(wrongPropertiesArr) + " are wrong.");
                System.out.println("Available properties: " + propNamesArr);
                System.out.println();
            }
            return false;
        }

        for (int i = 2; i < inpStrArr.length - 1; i++) {
            for (int j = 3; j < inpStrArr.length; j++) {
                String fV = inpStrArr[i];
                String sV = inpStrArr[j];
                if (("odd".equalsIgnoreCase(fV) && "even".equalsIgnoreCase(sV)) ||
                        ("even".equalsIgnoreCase(fV) && "odd".equalsIgnoreCase(sV)) ||
                        ("-odd".equalsIgnoreCase(fV) && "-even".equalsIgnoreCase(sV)) ||
                        ("sunny".equalsIgnoreCase(fV) && "square".equalsIgnoreCase(sV)) ||
                        ("square".equalsIgnoreCase(fV) && "sunny".equalsIgnoreCase(sV)) ||
                        ("duck".equalsIgnoreCase(fV) && "spy".equalsIgnoreCase(sV)) ||
                        ("spy".equalsIgnoreCase(fV) && "duck".equalsIgnoreCase(sV)) ||
                        ("happy".equalsIgnoreCase(fV) && "sad".equalsIgnoreCase(sV)) ||
                        ("-happy".equalsIgnoreCase(fV) && "-sad".equalsIgnoreCase(sV)) ||
                        ("sad".equalsIgnoreCase(fV) && "happy".equalsIgnoreCase(sV)) ||
                        ((("-" + fV).equalsIgnoreCase(sV)) || (("-" + sV).equalsIgnoreCase(fV)))) {
                    System.out.printf("""
                            The request contains mutually exclusive properties: [%s, %s]
                            There are no numbers with these properties.
                            
                            """, fV.toUpperCase(), sV.toUpperCase());
                    return false;
                }
            }
        }
        return true;
    }


    public static void processing(String[] inpStrArr) {
        int inpLength = inpStrArr.length;

        switch (inpLength) {
            case 1 -> {
                oneArg(inpStrArr[0]);
                System.out.println();
            }
            case 2 -> {
                twoArgs(inpStrArr);
                System.out.println();
            }
            default -> {
                multiArgs(inpStrArr);
                System.out.println();
            }
        }
    }



    public static void oneArg(String inpStr) {
        System.out.printf(Locale.US, "Properties of %,d\n", Long.parseLong(inpStr));
        for (Property property : Property.values()) {
            System.out.println("\t\t" + property.name().toLowerCase() + ": " + property.realization(inpStr)); // Property.EVEN.realization(strN)
        }
    }


    public static void twoArgs(String[] inpStrArr) {
        long number = Long.parseLong(inpStrArr[0]);
        int count = Integer.parseInt(inpStrArr[1]);

        for (int i = 0; i < count; i++) {
            ArrayList<String> listOfTrueProperties = new ArrayList<>();
            for (Property property : Property.values()) {
                if (property.realization(String.valueOf(number + i))) {
                    listOfTrueProperties.add(property.name().toLowerCase());
                }
            }
            System.out.printf(Locale.US, "\t\t%,d is ", number + i);
            System.out.println(listOfTrueProperties.toString().replace("[", "").replace("]", ""));
        }
    }


    public static void multiArgs(String[] inpStrArr) {
        boolean isNumTrue = true;

        long number = Long.parseLong(inpStrArr[0]);
        int count = Integer.parseInt(inpStrArr[1]);

        for (int i = 0; i < count; ) {
            String strNum = String.valueOf(number);
            for (int j = 2; j < inpStrArr.length; j++) {
                String propertyName = inpStrArr[j];
                boolean isMinus = propertyName.charAt(0) == '-';
                Property property = Property.valueOf((isMinus ? propertyName.substring(1) : propertyName).toUpperCase());
                boolean isNumTrueIgnoreMinus = property.realization(strNum);
                isNumTrue = isMinus ^ isNumTrueIgnoreMinus;
                if (!isNumTrue) {
                    break;
                }
            }
            if (isNumTrue) {
                twoArgs(new String[]{strNum, "1"});
                i++;
            }
            number++;
        }
    }
}