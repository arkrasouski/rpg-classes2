package org.example.artyom.rpgClasses.plugins;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

@Getter
public enum Classes {
    //Перечисление классов игрока
    WIZARD(20, 10, 2, 40, "Wizard", Material.TURTLE_HELMET),
    WARRIOR(30, 25, 1, 20, "Warrior", Material.CHAINMAIL_HELMET),
    SACRIFIER(15, 10, 3, 50, "Sacrifier", Material.GOLDEN_HELMET);

    private final int HP;
    private final int strength;
    private final int agility;
    private final int mana;
    private final String name;
    private final Material classMaterial;
    public static final String menuName = "Classes";

    Classes(int HP, int strength, int agility, int mana, String name, Material classMaterial) {
        this.HP = HP;
        this.strength = strength;
        this.agility = agility;
        this.mana = mana;

        this.name = name;
        this.classMaterial = classMaterial;
    }

    public static Classes getClassFromItem(ItemStack itemStack) {
        ItemMeta meta = itemStack.getItemMeta();

        String name = meta.getPersistentDataContainer().get(NamespacedKey.fromString("class_name"), PersistentDataType.STRING);
        if (name == null) {
            return null;
        }

        return valueOf(name);
    }
}
