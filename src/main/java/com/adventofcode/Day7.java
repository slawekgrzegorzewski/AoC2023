package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day7 {
    private enum HandType {
        FIVE_OF_A_KIND(7), FOUR_OF_A_KIND(6), FULL_HOUSE(5), THREE_OF_A_KIND(4), TWO_PAIR(3), ONE_PAIR(2), HIGH_CARD(1);
        private final int order;

        HandType(int order) {
            this.order = order;
        }

        public int getOrder() {
            return order;
        }
    }

    private final Map<String, Integer> listOfHands;

    public Day7() throws IOException {
        listOfHands = Input.day7().stream()
                .map(line -> line.split(" "))
                .collect(Collectors.toMap(
                        pair -> pair[0],
                        pair -> Integer.parseInt(pair[1])
                ));
    }

    int part1() {
        return calculateBids(false);
    }

    int part2() {
        return calculateBids(true);
    }

    private int calculateBids(boolean withJoker) {
        Comparator<String> comparator = Comparator.<String, Integer>comparing(hand -> determineType(hand, withJoker).order).thenComparing((h1, h2) -> this.compareHands(h1, h2, withJoker));
        List<String> ordered = listOfHands
                .keySet()
                .stream()
                .sorted(comparator)
                .toList();
        int sum = 0;
        for (int i = 0; i < ordered.size(); i++) {
            String hand = ordered.get(i);
//            System.out.println(hand + ";" + determineType(hand, false) + ";" + determineType(hand, true));
            sum += (i + 1) * listOfHands.get(hand);
        }
        return sum;
    }

    private HandType determineType(String hand, boolean withJoker) {
        Map<Character, Integer> stats = new HashMap<>();
        for (char c : hand.toCharArray()) {
            stats.compute(c, (card, counter) -> counter == null ? 1 : counter + 1);
        }
        return switch (stats.size()) {
            case 1 -> HandType.FIVE_OF_A_KIND;
            case 2 -> stats.containsValue(4) ? checkFourOfAKind(stats, withJoker) : checkFullHouse(stats, withJoker);
            case 3 -> stats.containsValue(3) ? checkThreeOfAKind(stats, withJoker) : checkTwoPairs(stats, withJoker);
            case 4 -> checkPair(stats, withJoker);
            default -> checkHighCard(stats, withJoker);
        };
    }

    private HandType checkHighCard(Map<Character, Integer> stats, boolean withJoker) {
        if (withJoker && stats.containsKey('J'))
            return HandType.ONE_PAIR;
        return HandType.HIGH_CARD;
    }

    private HandType checkPair(Map<Character, Integer> stats, boolean withJoker) {
        if (withJoker && stats.containsKey('J'))
            return HandType.THREE_OF_A_KIND;
        return HandType.ONE_PAIR;
    }

    private HandType checkTwoPairs(Map<Character, Integer> stats, boolean withJoker) {
        if (withJoker) {
            Integer jokers = stats.getOrDefault('J', 0);
            if (jokers == 1)
                return HandType.FULL_HOUSE;
            if (jokers == 2)
                return HandType.FOUR_OF_A_KIND;
        }
        return HandType.TWO_PAIR;
    }

    private HandType checkThreeOfAKind(Map<Character, Integer> stats, boolean withJoker) {
        if (withJoker && stats.containsKey('J'))
            return HandType.FOUR_OF_A_KIND;
        return HandType.THREE_OF_A_KIND;
    }

    private HandType checkFullHouse(Map<Character, Integer> stats, boolean withJoker) {
        if (withJoker && stats.containsKey('J'))
            return HandType.FIVE_OF_A_KIND;
        return HandType.FULL_HOUSE;
    }

    private HandType checkFourOfAKind(Map<Character, Integer> stats, boolean withJoker) {
        if (withJoker && stats.containsKey('J'))
            return HandType.FIVE_OF_A_KIND;
        return HandType.FOUR_OF_A_KIND;
    }

    private int compareHands(String hand1, String hand2, boolean withJoker) {
        char[] hand1Array = hand1.toCharArray();
        char[] hand2Array = hand2.toCharArray();
        for (int i = 0; i < hand1Array.length; i++) {
            char hand1Card = hand1Array[i];
            char hand2Card = hand2Array[i];
            int diff = orderOfCard(hand1Card, withJoker) - orderOfCard(hand2Card, withJoker);
            if (diff != 0)
                return diff;
        }
        return 0;
    }

    private int orderOfCard(char card, boolean withJoker) {
        switch (card) {
            case 'A':
                return 13;
            case 'K':
                return 12;
            case 'Q':
                return 11;
            case 'J':
                return withJoker ? 0 : 10;
            case 'T':
                return 9;
            case '9':
                return 8;
            case '8':
                return 7;
            case '7':
                return 6;
            case '6':
                return 5;
            case '5':
                return 4;
            case '4':
                return 3;
            case '3':
                return 2;
            case '2':
                return 1;
        }
        throw new RuntimeException();
    }

}


