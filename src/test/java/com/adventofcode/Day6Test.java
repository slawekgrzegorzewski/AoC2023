package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day6Test {

    static Day6 day6;

    @BeforeAll
    public static void init() throws IOException {
        day6 = new Day6();
    }

    @Test
    void testPart1() {
        long part1Result = day6.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(2756160, part1Result);
    }

    @Test
    void testPart2() {
        long part2Result = day6.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(34788142, part2Result);
    }
}