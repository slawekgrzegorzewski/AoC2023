package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day5Test {

    static Day5 day5;

    @BeforeAll
    public static void init() throws IOException {
        day5 = new Day5();
    }

    @Test
    void testPart1() {
        long part1Result = day5.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(51752125, part1Result);
    }

    @Test
    @Disabled
    void testPart2() {
        long part2Result = day5.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(12634632, part2Result);
    }
}