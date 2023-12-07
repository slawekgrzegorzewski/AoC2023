package com.adventofcode.input.day2;

public record Draw(int red, int green, int blue) {

    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";

    public static Draw parse(String value) {
        String[] parts = value.replace(" ", "").split(",");
        int red = 0, green = 0, blue = 0;
        for (String part : parts) {
            if (part.contains(RED)) {
                red = Integer.parseInt(part.replace(RED, ""));
            }
            if (part.contains(GREEN)) {
                green = Integer.parseInt(part.replace(GREEN, ""));
            }
            if (part.contains(BLUE)) {
                blue = Integer.parseInt(part.replace(BLUE, ""));
            }
        }
        return new Draw(red, green, blue);
    }

    public int power() {
        return red * green * blue;
    }
}
