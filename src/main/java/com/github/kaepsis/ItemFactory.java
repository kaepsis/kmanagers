package com.github.kaepsis;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

/**
 * A utility class for building custom {@link ItemStack} objects fluently.
 * Provides chainable methods to configure various item properties using the Bukkit API.
 */
public class ItemFactory {

    private ItemStack item;
    private ItemMeta meta;

    private static ItemFactory instance = null;

    /**
     * Gets the singleton instance of this class.
     *
     * @return the singleton instance of {@code ItemFactory}.
     */
    public static ItemFactory getInstance() {
        if (instance == null) {
            instance = new ItemFactory();
        }
        return instance;
    }

    private ItemFactory() {}

    /**
     * Creates a new {@code ItemFactory} for the given {@link ItemStack}.
     *
     * @param item The item to customize.
     */
    public ItemFactory(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    /**
     * Sets the display name of the item, with optional placeholders.
     *
     * @param name         The name to set.
     * @param placeholders Optional placeholders to format within the name.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory name(String name, Object... placeholders) {
        meta.setDisplayName(Chat.getInstance().format(name, placeholders));
        return this;
    }

    /**
     * Sets the item's material type.
     *
     * @param material The material to assign.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory material(Material material) {
        item.setType(material);
        return this;
    }

    /**
     * Sets whether the item is unbreakable.
     *
     * @param unbreakable {@code true} if the item should be unbreakable.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Sets the lore (description) of the item, with optional placeholders.
     *
     * @param lore         A list of strings to use as the lore.
     * @param placeholders Optional placeholders to format within the lore.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory lore(List<String> lore, Object... placeholders) {
        meta.setLore(Chat.getInstance().colorList(Chat.getInstance().formatList(lore, placeholders)));
        return this;
    }

    /**
     * Sets the break sound of the item.
     *
     * @param sound The {@link Sound} to play when the item breaks.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory breakSound(Sound sound) {
        meta.setBreakSound(sound);
        return this;
    }

    /**
     * Hides or shows the item tooltip.
     *
     * @param tooltip {@code true} to hide the tooltip, {@code false} to show it.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory hideTooltip(boolean tooltip) {
        meta.setHideTooltip(tooltip);
        return this;
    }

    /**
     * Sets a value in the item's persistent data container.
     *
     * @param key   The {@link NamespacedKey} used to identify the data.
     * @param type  The {@link PersistentDataType} used to store the data.
     * @param value The value to store.
     * @param <T>   The {@link PersistentDataType} type.
     * @param <Z>   The value type.
     * @return This {@code ItemFactory} instance.
     */
    public <T, Z> ItemFactory persistentDataContainer(NamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        meta.getPersistentDataContainer().set(key, type, value);
        return this;
    }

    /**
     * Adds an item flag to the item.
     *
     * @param flag The {@link ItemFlag} to add.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    /**
     * Adds an item flag to the item.
     *
     * @param flags A list of {@link ItemFlag} to add.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory addItemFlags(ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Removes an item flag from the item.
     *
     * @param flag The {@link ItemFlag} to remove.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory removeItemFlag(ItemFlag flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    /**
     * Adds an enchantment to the item.
     *
     * @param enchantment             The {@link Enchantment} to add.
     * @param level                   The level of the enchantment.
     * @param ignoreLevelRestriction {@code true} to ignore level restrictions.
     * @return This {@code ItemFactory} instance.
     */
    public ItemFactory enchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    /**
     * Finalizes the item customization and returns the modified {@link ItemStack}.
     *
     * @return The customized item.
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}
