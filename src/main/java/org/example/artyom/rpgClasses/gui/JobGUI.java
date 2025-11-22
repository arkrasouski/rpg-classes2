package org.example.artyom.rpgClasses.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.ItemUtils;
import org.example.artyom.rpgClasses.utils.PlayerClassesUtils;

public class JobGUI {
    //Класс GUI для выбора професии
    public static void openGUI(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, "Выбор профессии");
        //Перечисляю профессии
        Jobs blacksmith = Jobs.BLACKSMITH;
        Jobs alchemist = Jobs.ALCHEMIST;
        Jobs enchanter = Jobs.ENCHANTER;
        //если профессия доступна классу, добавляем в GUI
        if(PlayerClassesUtils.getPlayerClass(p).equalsIgnoreCase("wizard")) {
            inv.setItem(0, ItemUtils.create(enchanter.getClassMaterial(), 1, enchanter.getName(), Jobs.menuName, "Чародей"));

        } else if (PlayerClassesUtils.getPlayerClass(p).equalsIgnoreCase("sacrifier")) {
            inv.setItem(0, ItemUtils.create(alchemist.getClassMaterial(), 1, alchemist.getName(), Jobs.menuName, "Алхимик"));

        } else if (PlayerClassesUtils.getPlayerClass(p).equalsIgnoreCase("warrior")) {
            inv.setItem(0, ItemUtils.create(blacksmith.getClassMaterial(), 1, blacksmith.getName(), Jobs.menuName, "Кузнец"));

        }
        //Выход из GUI
        inv.setItem(8, ItemUtils.create(Material.OAK_DOOR, ChatColor.RED + "Выход", "Exit"));

        p.openInventory(inv);
    }
}
