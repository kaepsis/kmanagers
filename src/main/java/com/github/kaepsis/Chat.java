package com.github.kaepsis;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final char COLOR_CHAR = '&';

    private static final Map<String, String> HEX_COLOR_CACHE = new ConcurrentHashMap<>();

    private static Chat instance = null;

    private Chat() {
    }

    public static Chat getInstance() {
        if (instance == null) {
            instance = new Chat();
        }
        return instance;
    }

    public String color(final String message) {
        if (message == null || message.isEmpty()) return "";
        Matcher matcher = HEX_PATTERN.matcher(message);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            final String hex = matcher.group(1).toLowerCase();
            final String replacement = HEX_COLOR_CACHE.computeIfAbsent(hex, key -> {
                final StringBuilder builder = new StringBuilder(14);
                builder.append(COLOR_CHAR).append('x');
                for (char c : key.toCharArray()) {
                    builder.append(COLOR_CHAR).append(c);
                }
                return builder.toString();
            });
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);
        return ChatColor.translateAlternateColorCodes(COLOR_CHAR, sb.toString());
    }

    public String removeColors(String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replaceAll("&#([A-Fa-f0-9]{6})", "");
        message = message.replaceAll(COLOR_CHAR + "", "");
        return message;
    }

    public void send(CommandSender receiver, String message, Object... placeholders) {
        String modifiedMessage = format(message, placeholders);
        send(receiver, modifiedMessage);
    }

    public void send(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }

    public String format(String message, Object... placeholders) {
        String result = message;
        for (int i = 0; i < placeholders.length; i += 2) {
            String placeholder = String.valueOf(placeholders[i]);
            String replacement = String.valueOf(placeholders[i + 1]);
            result = result.replace(placeholder, replacement);
        }
        return result;
    }

    public void broadcast(String message, @Nullable String permission, @Nullable Object... placeholders) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> permission == null || player.hasPermission(permission))
                .forEach(player -> send(player, message, placeholders));
    }

    public ArrayList<String> formatList(List<String> list, Object... placeholders) {
        return new ArrayList<>(
                list.stream()
                        .map(line -> format(line, placeholders))
                        .toList()
        );
    }

    public ArrayList<String> colorList(List<String> list) {
        return new ArrayList<>(
                list.stream()
                        .map(this::color)
                        .toList()
        );
    }

}
