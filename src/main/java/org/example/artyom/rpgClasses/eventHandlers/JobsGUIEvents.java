package org.example.artyom.rpgClasses.eventHandlers;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class JobsGUIEvents implements Listener {
    //обработка выбора профессии игрока
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) { //вызовется когда буду кликать по открытому ранее инвернтарю
        Player p = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equalsIgnoreCase("Выбор профессии")){

            e.setCancelled(true);
            ItemStack item = e.getCurrentItem();
            if (item == null || item.getType() == Material.AIR){
               return;
            }
            String menuItemString = item.getItemMeta().getPersistentDataContainer().get(NamespacedKey.fromString("menu_item"), PersistentDataType.STRING);
            if(item.getType() == Material.OAK_DOOR && menuItemString.equals("Exit")){
                p.closeInventory();
            }
            else if(menuItemString.equals("Jobs")){
                System.out.println(item.getItemMeta().getDisplayName());
                p.performCommand("getjobs " + item.getItemMeta().getDisplayName().toUpperCase());
            }
            p.closeInventory();}

    }
}
