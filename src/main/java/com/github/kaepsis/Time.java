package com.github.kaepsis;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for working with time conversions, formatting, and Minecraft-style time expressions.
 * Implemented as a singleton.
 */
public class Time {

    private static Time instance = null;

    /**
     * Returns the singleton instance of the {@code Time} utility class.
     *
     * @return the singleton {@code Time} instance.
     */
    public static Time getInstance() {
        if (instance == null) {
            instance = new Time();
        }
        return instance;
    }

    /**
     * Private constructor to enforce singleton pattern.
     */
    private Time() {
    }

    /**
     * Formats the given time in milliseconds into a human-readable duration string (e.g., "1h 20m 15s").
     *
     * @param millis Time duration in milliseconds.
     * @return A formatted duration string.
     */
    public String strftime(long millis) {
        long seconds = millis / 1000;
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;

        StringBuilder sb = new StringBuilder();
        if (hours > 0) sb.append(hours).append("h ");
        if (minutes > 0) sb.append(minutes).append("m ");
        if (seconds > 0 || sb.isEmpty()) sb.append(seconds).append("s");

        return sb.toString().trim();
    }

    /**
     * Converts a custom Minecraft-style duration string (e.g., "10s", "5m", "2h", "1d")
     * into an epoch millisecond timestamp relative to the provided {@link Instant}.
     *
     * @param now The base instant (typically the current time).
     * @param str The Minecraft-style duration string.
     * @return The resulting time as epoch milliseconds, or {@code -1} if invalid format.
     */
    public long minecraftTimeToInstant(Instant now, String str) {
        char lastChar = str.charAt(str.length() - 1);
        long temporalAmount = Long.parseLong(str.substring(0, str.length() - 1));
        return switch (lastChar) {
            case 's' -> now.plusSeconds(temporalAmount).toEpochMilli();
            case 'm' -> now.plusSeconds(temporalAmount * 60).toEpochMilli();
            case 'h' -> now.plusSeconds(temporalAmount * 3600).toEpochMilli();
            case 'd' -> now.plusSeconds(temporalAmount * 86400).toEpochMilli();
            default -> -1L;
        };
    }

    /**
     * Converts a timestamp in milliseconds to a formatted date string.
     * Format: <code>yyyy-MM-dd HH:mm:ss</code>
     *
     * @param millis The timestamp in milliseconds.
     * @return A formatted date string.
     */
    public String toFormattedDate(long millis) {
        LocalDateTime localDateTime = Instant.ofEpochMilli(millis)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }
}
