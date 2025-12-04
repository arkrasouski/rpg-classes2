package org.example.artyom.rpgClasses.gui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.utils.ItemUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassesGUI {
    //Класс GUi выбора класса игрока
    public static void openGUI(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, "Выбор класса");
        //Перечисляем доступные классы
        Classes wizard = Classes.WIZARD;
        Classes warrior = Classes.WARRIOR;
        Classes sacrifier = Classes.SACRIFIER;

        inv.setItem(0, ItemUtils.create(wizard.getClassMaterial(), 1, Component.text(wizard.getName()), Classes.menuName, List.of(Component.text("Волшебник"))));
        inv.setItem(1, ItemUtils.create(warrior.getClassMaterial(), 1, Component.text(warrior.getName()), Classes.menuName, List.of(Component.text("Воин"))));
        inv.setItem(2, ItemUtils.create(sacrifier.getClassMaterial(), 1, Component.text(sacrifier.getName()), Classes.menuName, List.of(Component.text("Жрец"))));
        inv.setItem(8, ItemUtils.create(Material.OAK_DOOR, 1, Component.text("Выход", NamedTextColor.RED), "Exit"));

        p.openInventory(inv);
    }
}
