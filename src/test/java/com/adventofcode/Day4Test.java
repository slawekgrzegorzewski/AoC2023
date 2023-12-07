package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    static Day4 day4;

    @BeforeAll
    public static void init() throws IOException {
        day4 = new Day4();
    }

    @Test
    void testPart1() {
        int part1Result = day4.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(0, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day4.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(0, part2Result);
    }
}