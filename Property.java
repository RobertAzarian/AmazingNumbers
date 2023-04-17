package numbers;

import java.util.ArrayList;

public enum Property {
    EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SUNNY, SQUARE, JUMPING, HAPPY, SAD;


    boolean realization(String strNum) {
        boolean isTrue;
        switch (this) {

            case EVEN -> isTrue = Long.parseLong(strNum) % 2 == 0;

            case ODD -> isTrue = Long.parseLong(strNum) % 2 == 1;

            case BUZZ -> {
                long number = Long.parseLong(strNum);
                isTrue = (number % 7 == 0) || (number % 10 == 7);
            }

            case DUCK -> {
                isTrue = false;
                for (int i = 1; i < strNum.length(); i++) {
                    if (strNum.charAt(i) == '0') {
                        isTrue = true;
                        break;
                    }
                }
            }

            case PALINDROMIC -> {
                isTrue = true;
                int countNumbs = strNum.length();
                for (int i = 0, j = countNumbs - 1; i < (countNumbs / 2) || j > (countNumbs / 2) - 1; i++, j--) {
                    if (strNum.charAt(i) != strNum.charAt(j)) {
                        isTrue = false;
                        break;
                    }
                }
            }

            case GAPFUL -> {
                isTrue = false;
                if (strNum.length() >= 3) {
                    int firstAndEnd = Integer.parseInt(String.valueOf(strNum.charAt(0)) + strNum.charAt(strNum.length() - 1));
                    isTrue = Long.parseLong(strNum) % firstAndEnd == 0;
                }
            }

            case SPY -> {
                long sum = 0;
                long multi = 1;
                String[] strArrNumbs = strNum.split("");
                for (String strArrNumb : strArrNumbs) {
                    sum += Long.parseLong(strArrNumb);
                    multi *= Long.parseLong(strArrNumb);
                }
                isTrue = sum == multi;
            }

            case SUNNY -> isTrue = Math.sqrt((Long.parseLong(strNum) + 1)) % 1 == 0;

            case SQUARE -> isTrue = Math.sqrt((Long.parseLong(strNum))) % 1 == 0;

            case JUMPING -> {
                boolean isJumping = true;
                for (int i = 1; i < strNum.length(); i++) {
                    int n1 = Character.getNumericValue(strNum.charAt(i - 1));
                    int n2 = Character.getNumericValue(strNum.charAt(i));
                    isJumping = ((n1 - 1 == n2) || (n1 + 1 == n2));
                    if (!isJumping) {
                        break;
                    }
                }
                isTrue = isJumping;
            }

            case HAPPY, SAD -> {
                ArrayList<Long> numbersWas = new ArrayList<>();
                numbersWas.add(Long.parseLong(strNum));

                String numb = strNum;
                long result = 0;
                while (true) {
                    for (char ch : numb.toCharArray()) {
                        result += Math.pow(Character.getNumericValue(ch), 2);
                    }
                    if (result == 1) {
                        isTrue = (this == HAPPY);
                        break;
                    }
                    for (long n : numbersWas) {
                        if (result == n) {
                            isTrue = (this == SAD);
                            return isTrue;      // --
                        }
                    }
                    numbersWas.add(result);
                    numb = String.valueOf(result);
                    result = 0;
                }
            }

            default -> isTrue = false;
        }
        return isTrue;
    }
}
