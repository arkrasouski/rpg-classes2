package org.example.artyom.rpgClasses.plugins;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public enum Classes {
    //Перечисление классов игрока
    WIZARD(20, 10, 2, 40, "Wizard", Material.TURTLE_HELMET),
    WARRIOR(30, 25, 1, 20, "Warrior", Material.CHAINMAIL_HELMET),
    SACRIFIER(15, 10, 3, 50, "Sacrifier", Material.GOLDEN_HELMET);

        private int HP;
        private int strength;
        private int agility;
        private int mana;
        private String name;
        private Material classMaterial;
        public static String menuName = "Classes";

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

    public int getHP() {
        return HP;
    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getMana() {
        return mana;
    }

    public String getName() {
        return name;
    }

    public Material getClassMaterial() {
        return classMaterial;
    }



    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClassMaterial(Material classMaterial) {
        this.classMaterial = classMaterial;
    }


}
