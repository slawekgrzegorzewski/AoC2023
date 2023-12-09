package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.Utils;

import java.io.IOException;
import java.util.List;

public class Day9 {
    private enum Direction {
        FORWARD, BACKWARD
    }

    private final List<int[]> oasisReport;

    public Day9() throws IOException {
        oasisReport = Input.day9().stream().map(Utils::extractInts).toList();
    }

    int part1() {
        return oasisReport.stream().mapToInt(reading -> findNextExtrapolation(reading, Direction.FORWARD)).sum();
    }

    int part2() {
        return oasisReport.stream().mapToInt(reading -> findNextExtrapolation(reading, Direction.BACKWARD)).sum();
    }

    private int findNextExtrapolation(int[] reading, Direction direction) {
        int index = direction == Direction.FORWARD ? reading.length - 1 : 0;
        int sign = direction == Direction.FORWARD ? 1 : -1;
        int[] diffs = reduce(reading);
        if (isReadyForExtrapolation(diffs)) {
            return reading[index];
        }
        return reading[index] + sign * findNextExtrapolation(diffs, direction);
    }

    private int[] reduce(int[] reading) {
        int[] reduce = new int[reading.length - 1];
        for (int i = 1; i < reading.length; i++) {
            reduce[i - 1] = reading[i] - reading[i - 1];
        }
        return reduce;
    }

    private boolean isReadyForExtrapolation(int[] reading) {
        for (int i : reading) {
            if (i != 0) return false;
        }
        return true;
    }

}


