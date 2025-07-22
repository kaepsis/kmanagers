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

/**
 * Utility class for managing chat formatting, color codes, and sending messages in a Bukkit-based environment.
 * Supports both legacy color codes and modern hex colors using the format <code>&amp;#RRGGBB</code>.
 * Implemented as a singleton.
 */
public class Chat {

    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");
    private static final char COLOR_CHAR = '&';
    private static final Map<String, String> HEX_COLOR_CACHE = new ConcurrentHashMap<>();

    private static Chat instance = null;

    /**
     * Private constructor to enforce singleton pattern.
     */
    private Chat() {
    }

    /**
     * Gets the singleton instance of this class.
     *
     * @return the singleton instance of {@code Chat}.
     */
    public static Chat getInstance() {
        if (instance == null) {
            instance = new Chat();
        }
        return instance;
    }

    /**
     * Translates legacy color codes (&amp;) and hex color codes (e.g., &amp;#FF00FF) in a given message string.
     *
     * @param message The message to colorize.
     * @return The colored message ready to be sent to players.
     */
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

    /**
     * Removes all legacy and hex color codes from the given message.
     *
     * @param message The message to sanitize.
     * @return The message without any color formatting.
     */
    public String removeColors(String message) {
        if (message == null || message.isEmpty()) return "";
        message = message.replaceAll("&#([A-Fa-f0-9]{6})", "");
        message = message.replaceAll(COLOR_CHAR + "", "");
        return message;
    }

    /**
     * Sends a formatted message to the given {@link CommandSender}, applying placeholder replacements.
     *
     * @param receiver     The command sender (player, console, etc.)
     * @param message      The message to send.
     * @param placeholders Optional placeholder-value pairs for formatting.
     */
    public void send(CommandSender receiver, String message, Object... placeholders) {
        if (message == null || message.isEmpty()) return;
        String modifiedMessage = format(message, placeholders);
        send(receiver, modifiedMessage);
    }

    /**
     * Sends a message to the given {@link CommandSender} after applying color codes.
     *
     * @param sender  The command sender to send the message to.
     * @param message The message to send.
     */
    public void send(CommandSender sender, String message) {
        if (message == null || message.isEmpty()) return;
        sender.sendMessage(color(message));
    }

    /**
     * Replaces placeholders in a string. Expects pairs of [placeholder, replacement, ...].
     *
     * @param message      The message containing placeholders.
     * @param placeholders An array of placeholder-value pairs.
     * @return The formatted message with replacements applied.
     */
    public String format(String message, Object... placeholders) {
        String result = message;
        for (int i = 0; i < placeholders.length; i += 2) {
            String placeholder = String.valueOf(placeholders[i]);
            String replacement = String.valueOf(placeholders[i + 1]);
            result = result.replace(placeholder, replacement);
        }
        return result;
    }

    /**
     * Broadcasts a message to all online players, optionally filtered by permission.
     *
     * @param message     The message to broadcast.
     * @param permission  The permission required to receive the message, or {@code null} to send to all players.
     * @param placeholders Optional placeholder-value pairs for formatting the message.
     */
    public void broadcast(String message, @Nullable String permission, @Nullable Object... placeholders) {
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> permission == null || player.hasPermission(permission))
                .forEach(player -> send(player, message, placeholders));
    }

    /**
     * Formats a list of strings by applying placeholder replacements.
     *
     * @param list         The list of strings to format.
     * @param placeholders Placeholder-value pairs.
     * @return A new list with all lines formatted.
     */
    public ArrayList<String> formatList(List<String> list, Object... placeholders) {
        return new ArrayList<>(
                list.stream()
                        .map(line -> format(line, placeholders))
                        .toList()
        );
    }

    /**
     * Applies color codes to all strings in the given list.
     *
     * @param list The list of strings to colorize.
     * @return A new list with all lines colorized.
     */
    public ArrayList<String> colorList(List<String> list) {
        return new ArrayList<>(
                list.stream()
                        .map(this::color)
                        .toList()
        );
    }
}
