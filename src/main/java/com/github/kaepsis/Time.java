package com.github.kaepsis;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {

    private static Time instance = null;

    public static Time getInstance() {
        if (instance == null) {
            instance = new Time();
        }
        return instance;
    }

    private Time() {
    }

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

    public String toFormattedDate(long millis) {
        LocalDateTime localDateTime = LocalDateTime.from(Instant.ofEpochMilli(millis));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

}
