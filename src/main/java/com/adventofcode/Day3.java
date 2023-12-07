package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Day3 {

    private static final List<Character> numbers = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
    private final char[][] enginePlan;
    private final int[][] adjatementPlan;
    private final int[][] gearRatio;

    public Day3() throws IOException {
        enginePlan = Input.day3();
        adjatementPlan = new int[enginePlan.length][enginePlan[0].length];
        gearRatio = new int[enginePlan.length][enginePlan[0].length];
    }

    private void init() {
        for (int i = 0; i < enginePlan.length; i++) {
            Arrays.fill(adjatementPlan[i], 0);
            Arrays.fill(gearRatio[i], 1);
        }
    }

    int part1() {
        return analyze();
    }

    long part2() {
        analyze();
        long sum = 0;
        for (int i = 0; i < enginePlan.length; i++) {
            for (int j = 0; j < enginePlan[i].length; j++) {
                if (enginePlan[i][j] == '*' && adjatementPlan[i][j] == 2) {
                    sum += gearRatio[i][j];
                }
            }
        }
        return sum;
    }

    private int analyze() {
        init();
        int sum = 0;
        for (int x = 0; x < enginePlan.length; x++) {
            char[] line = enginePlan[x];
            int numberStartIndex = -1;
            int numberEndIndex = -1;
            for (int y = 0; y < line.length; y++) {
                boolean isCurrentCharNumber = numbers.contains(line[y]);
                if (numberStartIndex == -1 && isCurrentCharNumber) {
                    numberStartIndex = y;
                }
                if (isCurrentCharNumber) {
                    numberEndIndex = y;
                }
                if (numberStartIndex != -1 && (!isCurrentCharNumber || y == line.length - 1)) {
                    if (isPartNumber(x, numberStartIndex, numberEndIndex)) {
                        int number = getNumber(x, numberStartIndex, numberEndIndex);
                        sum += number;
                        fillUpAdditionalArrays(x, numberStartIndex, numberEndIndex, number);
                    }
                    numberStartIndex = -1;
                    numberEndIndex = -1;
                }
            }
        }
        return sum;
    }

    private int getNumber(int rowIndex, int numberStartIndex, int numberEndIndex) {
        int number = 0;
        for (int i = numberStartIndex; i <= numberEndIndex; i++) {
            number = number * 10 + Integer.parseInt(String.valueOf(this.enginePlan[rowIndex][i]));
        }
        return number;
    }

    private boolean isPartNumber(int rowIndex, int numberStartIndex, int numberEndIndex) {
        int fromInclusive = Math.max(0, numberStartIndex - 1);
        int toInclusive = Math.min(this.enginePlan[rowIndex].length - 1, numberEndIndex + 1);
        return rowIndex > 0 && sectionContainsSymbol(rowIndex - 1, fromInclusive, toInclusive)
                || sectionContainsSymbol(rowIndex, fromInclusive, toInclusive)
                || rowIndex < this.enginePlan.length - 1 && sectionContainsSymbol(rowIndex + 1, fromInclusive, toInclusive);
    }

    private void fillUpAdditionalArrays(int rowIndex, int numberStartIndex, int numberEndIndex, int number) {
        int fromInclusive = Math.max(0, numberStartIndex - 1);
        int toInclusive = Math.min(this.enginePlan[rowIndex].length - 1, numberEndIndex + 1);
        if (rowIndex > 0) {
            fillUpAdditionalArraysRow(rowIndex - 1, fromInclusive, toInclusive, number);
        }
        fillUpAdditionalArraysRow(rowIndex, fromInclusive, toInclusive, number);
        if (rowIndex < this.enginePlan.length - 1) {
            fillUpAdditionalArraysRow(rowIndex + 1, fromInclusive, toInclusive, number);
        }
    }

    private void fillUpAdditionalArraysRow(int rowIndex, int fromInclusive, int toInclusive, int number) {
        for (int i = fromInclusive; i <= toInclusive; i++) {
            this.adjatementPlan[rowIndex][i]++;
            this.gearRatio[rowIndex][i] *= number;
        }
    }

    private boolean sectionContainsSymbol(int rowIndex, int fromInclusive, int toInclusive) {
        String scope = String.valueOf(Arrays.copyOfRange(this.enginePlan[rowIndex], fromInclusive, toInclusive + 1));
        Pattern noSymbol = Pattern.compile("^[0-9.]+$");
        return !noSymbol.matcher(scope).find();
    }

}


