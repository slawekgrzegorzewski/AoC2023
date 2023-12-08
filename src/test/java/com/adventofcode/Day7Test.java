package com.adventofcode;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    static Day7 day7;

    @BeforeAll
    public static void init() throws IOException {
        day7 = new Day7();
    }

    @Test
    void testPart1() {
        int part1Result = day7.part1();
        System.out.println("part1 = " + part1Result);
        assertEquals(0, part1Result);
    }

    @Test
    void testPart2() {
        int part2Result = day7.part2();
        System.out.println("part2 = " + part2Result);
        assertEquals(0, part2Result);
    }
}