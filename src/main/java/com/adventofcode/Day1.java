package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

public class Day1 {
    private final static String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private final static String[] numberStrings = new String[]{"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
    private final static String[] both = Stream.of(numbers, numberStrings)
            .flatMap(Stream::of)
            .toArray(String[]::new);

    private final List<String> lines;

    public Day1() throws IOException {
        this.lines = Input.day1();
    }

    int part1() {
        return calculateCalibration(numbers);
    }

    int part2() {
        return calculateCalibration(both);
    }

    private int calculateCalibration(String[] set) {
        int sum = 0;
        for (String line : lines) {
            sum += calibration(line, set);
        }
        return sum;
    }

    private int calibration(String line, String[] numbers) {
        int firstIndex = line.length();
        int lastIndex = -1;
        int firstValue = 0;
        int lastValue = 0;
        for (String number : numbers) {
            int index = line.indexOf(number);
            int lastIndexOf = line.lastIndexOf(number);
            if (index > -1 && index < firstIndex) {
                firstIndex = index;
                firstValue = parse(number);
            }
            if (lastIndexOf > -1 && lastIndexOf > lastIndex) {
                lastIndex = lastIndexOf;
                lastValue = parse(number);
            }
        }
        return 10 * firstValue + lastValue;
    }

    private int parse(String number) {
        int i = find(number, numbers);
        if (i > -1) return i;
        i = find(number, numberStrings);
        if (i > -1) return i + 1;
        throw new RuntimeException("Number " + number + " can not be parsed.");
    }

    private int find(String search, String[] array) {
        for (int i = 0; i < array.length; i++) {
            String element = array[i];
            if (element.equals(search))
                return i;
        }
        return -1;
    }

}


