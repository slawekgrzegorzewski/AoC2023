package com.adventofcode;

import com.adventofcode.input.Input;

import java.io.IOException;
import java.util.List;
import java.util.logging.LogManager;

import static com.adventofcode.input.Utils.extractInts;
import static com.adventofcode.input.Utils.extractLongs;

public class Day6 {

    private final long[] times;
    private final long[] distances;
    private final long time;
    private final long distance;

    public Day6() throws IOException {
        List<String> lines = Input.day6();
        times = extractLongs(lines.get(0).replace("Time:", ""));
        distances = extractLongs(lines.get(1).replace("Distance:", ""));
        time = Long.parseLong(lines.get(0).replace("Time:", "").replace(" ", ""));
        distance = Long.parseLong(lines.get(1).replace("Distance:", "").replace(" ", ""));

    }

    long part1() {
        int result = 1;
        for (int i = 0; i < times.length; i++) {
            result *= findNumberOfPossibilitiesToBreakRecord(times[i], distances[i]);
        }
        return result;
    }

    long part2() {
        return findNumberOfPossibilitiesToBreakRecord(time, distance);
    }

    private int findNumberOfPossibilitiesToBreakRecord(long time, long distance) {
        double fromReal = (time - Math.sqrt(time * time - 4 * distance)) / 2;
        double toReal = (time + Math.sqrt(time * time - 4 * distance)) / 2;
        int from = (int) Math.ceil(fromReal);
        if (from == fromReal) from++;
        int to = (int) Math.floor(toReal);
        if (to == toReal) to--;
        return to - from + 1;
    }

}


