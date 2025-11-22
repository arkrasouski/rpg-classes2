package org.example.artyom.rpgClasses.eventHandlers;


import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.example.artyom.rpgClasses.RpgClasses;
import org.example.artyom.rpgClasses.customEvents.ChangeLevelOrExpEvent;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.PlayerJobsUtils;

public class JobsEvents implements Listener {


    @EventHandler
    public void onCraftItemEvent(CraftItemEvent e){
        //Если кузнец скрафтит броню, повышаем очки брони для нее
        Player player = (Player) e.getWhoClicked();

        if(PlayerJobsUtils.getPlayerJob(player) != null && PlayerJobsUtils.getPlayerJob(player).equalsIgnoreCase(Jobs.BLACKSMITH.getName())){

            ItemStack item = e.getRecipe().getResult();



            int jobLevel = PlayerJobsUtils.getPlayerJobLevel(player);

            if (item.getType().name().contains("BOOTS")) {
                NamespacedKey key = new NamespacedKey(RpgClasses.getInstance(), "extra_boots");
                PlayerJobsUtils.addArmor(item, 2, EquipmentSlotGroup.FEET, key, jobLevel);
            }
            else if (item.getType().name().contains("CHEST")) {
                NamespacedKey key = new NamespacedKey(RpgClasses.getInstance(), "extra_chest");
                    PlayerJobsUtils.addArmor(item, 2, EquipmentSlotGroup.CHEST, key, jobLevel);
                }

            else if (item.getType().name().contains("LEGGINGS")) {
                NamespacedKey key = new NamespacedKey(RpgClasses.getInstance(), "extra_leggins");
                PlayerJobsUtils.addArmor(item, 2, EquipmentSlotGroup.LEGS, key, jobLevel);
            }
            else if (item.getType().name().contains("HELMET")) {
                NamespacedKey key = new NamespacedKey(RpgClasses.getInstance(), "extra_helmet");
                PlayerJobsUtils.addArmor(item, 2, EquipmentSlotGroup.HEAD, key, jobLevel);
            }


                e.getInventory().setResult(item);

                Bukkit.getPluginManager().callEvent(new ChangeLevelOrExpEvent(player));
//
        }
    }



}
