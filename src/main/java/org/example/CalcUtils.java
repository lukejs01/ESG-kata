package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CalcUtils {

    public int addStrings(String numbers) {
        int result = 0;
        List<String> negativeNumbers = new ArrayList<>();

        List<String> deliminator = getDelimiter(numbers);
        numbers = removeDelimiterSyntax(numbers);
        numbers = removeNewLineOperator(numbers);

        List<String> strings = splitStrings(numbers, deliminator);

        if (strings.size() == 1 && strings.get(0).equals("")) {
            return 0;
        }

        for (String num : strings) {
            if (num.startsWith("-")) {
                negativeNumbers.add(num);
            }
            result += greaterThan1000(num);
        }

        if (!negativeNumbers.isEmpty()) {
            throw new RuntimeException("You cannot use negative numbers: " + String.join(" ", negativeNumbers));
        }

        return result;
    }

    public String removeNewLineOperator(String numbers) {
        return numbers.replace("\n", ",").replace("\r", ",");
    }

    public List<String> getDelimiter(String numbers) {

        List<String> delimiter = new ArrayList<>();

        if (numbers.startsWith("//[")) {
            String anyLength = numbers.substring(2);
            int indexOfLastIdentifier = 0;

            char[] charAnyLength = anyLength.toCharArray();
            for (int i = 0; i < anyLength.length(); i++) {
                if (charAnyLength[i] == '\n') {
                    indexOfLastIdentifier = i;
                }
            }

            anyLength = anyLength.substring(0, indexOfLastIdentifier);

            return getListOfDelimiters(anyLength);
        }


        if (numbers.startsWith("//")) {
            delimiter.add(numbers.substring(2, 3));
            return delimiter;

        }
        delimiter.add(",");
        return delimiter;
    }

    public List<String> getListOfDelimiters(String prefix) {
        List<String> delimiters = new ArrayList<>();
        boolean delimState = false;
        StringBuilder delimiter = new StringBuilder();

        char[] prefixAsChar = prefix.toCharArray();
        for (int i = 0; i < prefix.length(); i++) {

            if (prefixAsChar[i] == ']') {
                delimState = false;
                delimiters.add(delimiter.toString());
                delimiter.setLength(0);
            }

            if (delimState) {
                delimiter.append(prefixAsChar[i]);
            }

            if (prefixAsChar[i] == '[') {
                delimState = true;
            }


        }
        return delimiters;
    }

    public String removeDelimiterSyntax(String numbers) {
        if (numbers.startsWith("//[")) {
            char[] numbersChar = numbers.toCharArray();
            int index = 0;

            for (int i = 0; i < numbersChar.length; i++) {
                if(numbersChar[i] == '\n') {
                    index = i + 1;
                }
            }

            return numbers.substring(index);
        }

        if (numbers.startsWith("//")) {
            return numbers.substring(4);
        }
        return numbers;
    }

    public int greaterThan1000(String number) {
        int numToInt = Integer.parseInt(number);

        if (numToInt > 1000) {
            return 0;
        }

        return numToInt;
    }

    public List<String> splitStrings(String input, List<String> splitStrings) {
        List<String> result = new ArrayList<>();
        input = removeDelimiterSyntax(input);
        result.add(input);

        for (String splitStr : splitStrings) {
            List<String> tempResult = new ArrayList<>();

            for (String str : result) {
                String[] splitArr = str.split(splitStr);
                tempResult.addAll(Arrays.asList(splitArr));
            }

            result = tempResult;
        }

        return result;
    }
}
