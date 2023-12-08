package com.adventofcode.input.day5;

import java.util.Arrays;
import java.util.function.Predicate;

public record Range(long sourceFrom, long length, long destinationDiff) {
    public static Range parse(String line) {
        long[] range = Arrays.stream(line.trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
        return new Range(range[1], range[2], range[0] - range[1]);
    }

    public boolean fallIntoRange(long number) {
        return number >= sourceFrom && number < sourceFrom + length;
    }

    public long map(long number) {
        if (fallIntoRange(number)) {
            return number + destinationDiff;
        }
        throw new RuntimeException(number + " not in scope of this range " + this);
    }
}
