package com.adventofcode.input;

import java.util.Arrays;
import java.util.function.Predicate;

public class Utils {

    public static int[] extractInts(String intsString) {
        return Arrays.stream(intsString.trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(String::trim)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public static long[] extractLongs(String longsString) {
        return Arrays.stream(longsString.trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
    }
}
