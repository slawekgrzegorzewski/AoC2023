package com.adventofcode.input.day4;

import java.util.Arrays;

import static java.util.function.Predicate.not;

public record Card(int id, int[] winningNumbers, int[] ownNumbers) {
    public static Card parse(String line) {
        String[] cardId = line.split(":");
        String[] numbers = cardId[1].split("\\|");
        return new Card(
                Integer.parseInt(cardId[0].replace("Card", "").trim()),
                Arrays.stream(numbers[0].trim().split(" ")).filter(not(String::isBlank)).mapToInt(Integer::parseInt).toArray(),
                Arrays.stream(numbers[1].trim().split(" ")).filter(not(String::isBlank)).mapToInt(Integer::parseInt).toArray()
        );
    }

    public int matches() {
        int matches = 0;
        for (int ownNumber : ownNumbers) {
            if (contains(winningNumbers, ownNumber)) matches++;
        }
        return matches;
    }

    public int score() {
        int matches = matches();
        return matches == 0 ? 0 : (int) Math.pow(2, matches - 1);
    }

    private boolean contains(int[] array, int value) {
        for (int i : array) {
            if (i == value) return true;
        }
        return false;
    }
}
