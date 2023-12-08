package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day4.Card;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Day4 {

    private final List<com.adventofcode.input.day4.Card> cards;

    public Day4() throws IOException {
        cards = Input.day4();
    }

    int part1() {
        return cards.stream().mapToInt(Card::score).sum();
    }

    int part2() {
        int[] cardsCopies = new int[cards.size()];
        Arrays.fill(cardsCopies, 1);
        for (int i = 0; i < cards.size(); i++) {
            Card c = cards.get(i);
            for (int j = i + 1; j < i + 1 + c.matches() && j < cardsCopies.length; j++) {
                cardsCopies[j] += cardsCopies[i];
            }
        }
        return IntStream.of(cardsCopies).sum();
    }

}


