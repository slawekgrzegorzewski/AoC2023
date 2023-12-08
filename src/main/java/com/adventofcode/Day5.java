package com.adventofcode;

import com.adventofcode.input.Input;
import com.adventofcode.input.day5.Almanac;
import com.adventofcode.input.day5.Range;

import java.io.IOException;
import java.util.List;
import java.util.stream.LongStream;

public class Day5 {

    private final Almanac almanac;

    public Day5() throws IOException {
        almanac = Input.day5();
    }

    long part1() {
        return LongStream.of(almanac.seeds()).map(this::getLocation).min().orElseThrow();
    }

    private long getLocation(long seed) {
        long soil = map(almanac.seedToSoilMap(), seed);
        long fertilizer = map(almanac.soilToFertilizerMap(), soil);
        long water = map(almanac.fertilizerToWaterMap(), fertilizer);
        long light = map(almanac.waterToLightMap(), water);
        long temperature = map(almanac.lightToTemperatureMap(), light);
        long humidity = map(almanac.temperatureToHumidityMap(), temperature);
        return map(almanac.humidityToLocationMap(), humidity);
    }

    private long map(List<Range> ranges, long source) {
        return ranges.stream().filter(r -> r.fallIntoRange(source)).findAny().map(r -> r.map(source)).orElse(source);
    }

    long part2() {
        long min = Long.MAX_VALUE;
        for (int i = 0; i < almanac.seeds().length; i += 2) {
            for (long j = almanac.seeds()[i]; j < almanac.seeds()[i] + almanac.seeds()[i + 1]; j++){
                long location = getLocation(j);
                if(min > location){
                    min = location;
                    System.out.println("min = " + min);
                }
            }
        }
        return min;
//        List<LongStream> longStreams = new ArrayList<>();
//        for (int i = 0; i < almanac.seeds().length; i += 2) {
//            longStreams.add(LongStream.range(almanac.seeds()[i], almanac.seeds()[i] + almanac.seeds()[i + 1]));
//        }
//        return longStreams.stream()
//                .flatMapToLong(s -> s)
//                .parallel()
//                .map(this::getLocation)
//                .min()
//                .orElseThrow();
    }

}


