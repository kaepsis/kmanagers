package com.github.kaepsis;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemFactory {

    private ItemStack item;
    private ItemMeta meta;

    private static ItemFactory instance = null;

    private ItemFactory() {
    }

    public static ItemFactory getInstance() {
        if (instance == null) {
            instance = new ItemFactory();
        }
        return instance;
    }

    public ItemFactory(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemFactory name(String name, Object... placeholders) {
        meta.setDisplayName(Chat.getInstance().format(name, placeholders));
        return this;
    }

    public ItemFactory material(Material material) {
        item.setType(material);
        return this;
    }

    public ItemFactory unbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    public ItemFactory lore(List<String> lore, Object... placeholders) {
        meta.setLore(Chat.getInstance().colorList(Chat.getInstance().formatList(lore, placeholders)));
        return this;
    }

    public ItemFactory addItemFlag(ItemFlag flag) {
        meta.addItemFlags(flag);
        return this;
    }

    public ItemFactory removeItemFlag(ItemFlag flag) {
        meta.removeItemFlags(flag);
        return this;
    }

    public ItemFactory enchant(Enchantment enchant, int level, boolean ignoreLevelRestriction) {
        meta.addEnchant(enchant, level, ignoreLevelRestriction);
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

}
