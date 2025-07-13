package com.github.kaepsis;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder name(String name, Object[] placeholders) {
        meta.setDisplayName(Chat.getInstance().format(name, placeholders));
        return this;
    }

    public ItemBuilder unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder lore(List<String> lore, Object[] placeholders) {
        meta.setLore(Chat.getInstance().colorList(Chat.getInstance().formatList(lore, placeholders)));
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemBuilder removeItemFlag(ItemFlag flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchant, int level, boolean ignoreLevelRestriction) {
        meta.addEnchant(enchant, level, ignoreLevelRestriction);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
