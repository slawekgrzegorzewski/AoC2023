package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day2.Draw;
import com.adventofcode.input.day2.Game;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Day2 {

    private final List<Game> games;

    public Day2() throws IOException {
        games = Input.day2();
    }

    int part1() {
        return games.stream()
                .filter(game -> isGamePossibleWith(game, 12, 13, 14))
                .mapToInt(Game::id)
                .sum();
    }

    private boolean isGamePossibleWith(Game game, int numberOfReds, int numberOfGreens, int numberOfBlues) {
        return Arrays.stream(game.draws())
                .allMatch(draw ->
                        draw.red() <= numberOfReds
                                && draw.green() <= numberOfGreens
                                && draw.blue() <= numberOfBlues);
    }

    int part2() {
        return games.stream()
                .map(Game::findMinSetOfCubes)
                .mapToInt(Draw::power)
                .sum();
    }

}


