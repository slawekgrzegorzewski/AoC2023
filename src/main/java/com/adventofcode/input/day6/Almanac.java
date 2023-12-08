package com.adventofcode.input.day6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public record Almanac(long[] seeds,
                      List<Range> seedToSoilMap,
                      List<Range> soilToFertilizerMap,
                      List<Range> fertilizerToWaterMap,
                      List<Range> waterToLightMap,
                      List<Range> lightToTemperatureMap,
                      List<Range> temperatureToHumidityMap,
                      List<Range> humidityToLocationMap
) {
    public static Almanac parse(List<String> lines) {
        Almanac result = null;
        List<Range> listToAdd = null;
        for (String line : lines) {
            if (result == null) {
                if (line.startsWith("seeds:")) {
                    result = new Almanac(
                            extractLongs(line.replace("seeds:", "")),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>(),
                            new ArrayList<>());
                }
            } else {
                if (line.isBlank()) continue;
                switch (line) {
                    case "seed-to-soil map:":
                        listToAdd = result.seedToSoilMap;
                        break;
                    case "soil-to-fertilizer map:":
                        listToAdd = result.soilToFertilizerMap;
                        break;
                    case "fertilizer-to-water map:":
                        listToAdd = result.fertilizerToWaterMap;
                        break;
                    case "water-to-light map:":
                        listToAdd = result.waterToLightMap;
                        break;
                    case "light-to-temperature map:":
                        listToAdd = result.lightToTemperatureMap;
                        break;
                    case "temperature-to-humidity map:":
                        listToAdd = result.temperatureToHumidityMap;
                        break;
                    case "humidity-to-location map:":
                        listToAdd = result.humidityToLocationMap;
                        break;
                    default:
                        listToAdd.add(Range.parse(line));
                }
            }
        }
        return result;
    }

    private static long[] extractLongs(String longsString) {
        return Arrays.stream(longsString.trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .map(String::trim)
                .mapToLong(Long::parseLong)
                .toArray();
    }
}