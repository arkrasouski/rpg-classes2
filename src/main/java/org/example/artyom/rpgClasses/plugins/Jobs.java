package org.example.artyom.rpgClasses.plugins;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

@Getter
public enum Jobs {
    //Класс выбора профессии
    //возможно тут стоит перечислять сразу классы доступные для профессии
    BLACKSMITH("Blacksmith", Material.ANVIL),
    ALCHEMIST("Alchemist", Material.BREWING_STAND),
    ENCHANTER("Enchanter", Material.ENCHANTED_BOOK),

    NULL("Null", null); //Безработный

    private final String name;
    private final Material classMaterial;
    public static final String menuName = "Jobs";

    Jobs(String name, Material classMaterial) {
        this.name = name;
        this.classMaterial = classMaterial;
    }

    public static Jobs getJobFromItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        String name = meta.getPersistentDataContainer().get(NamespacedKey.fromString("job_name"), PersistentDataType.STRING);
        if (name == null) {
            return null;
        }
        return valueOf(name);
    }
}
