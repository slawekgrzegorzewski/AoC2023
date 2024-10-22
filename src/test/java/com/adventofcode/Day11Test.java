package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    static Day11 day11;

    @BeforeAll
    public static void init() throws IOException {
        day11 = new Day11();
    }

    @Test
    void testPart1() {
        long part1Result = day11.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(10_292_708, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day11.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(790_194_712_336L, part2Result);
    }
}