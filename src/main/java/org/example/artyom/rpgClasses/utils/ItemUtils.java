package org.example.artyom.rpgClasses.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;
import java.util.List;

public class ItemUtils {
    //Вспомогательный класс для работы с предметами
    public static ItemStack create(Material material, int amount, Component displayName, String menu_class, @Nullable List<Component> lore) {
        //Создаем кастомный предмет
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.displayName(displayName);
        meta.lore(lore);
        meta.getPersistentDataContainer().set(
                NamespacedKey.fromString("menu_item"),
                PersistentDataType.STRING,
                menu_class
        );
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack create(Material material, int amount, Component displayName, String menu_class) {
        return create(material, amount, displayName, menu_class, null);
    }
}
