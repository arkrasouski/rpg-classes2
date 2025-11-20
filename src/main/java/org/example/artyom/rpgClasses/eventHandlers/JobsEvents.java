package org.example.artyom.rpgClasses.eventHandlers;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.PlayerJobsUtils;

public class JobsEvents implements Listener {

    JavaPlugin plugin;
    public JobsEvents(JavaPlugin plugin) {

        this.plugin = plugin;
    }
    @EventHandler
    public void onCraftItemEvent(CraftItemEvent e){
        Player player = (Player) e.getWhoClicked();

        if(PlayerJobsUtils.getPlayerJob(player) != null && PlayerJobsUtils.getPlayerJob(player).equalsIgnoreCase(Jobs.BLACKSMITH.getName())){
            player.sendMessage("dasdasd");
            ItemStack item = e.getRecipe().getResult();
            if (item.getType().name().contains("IRON")) {
                player.sendMessage("Iron!");
                ItemMeta meta = item.getItemMeta();

                NamespacedKey key = new NamespacedKey(this.plugin, "extra_armor");

                AttributeModifier newMod = new AttributeModifier(
                        key,
                        5.0, // количество брони
                        AttributeModifier.Operation.ADD_NUMBER,
                        EquipmentSlotGroup.FEET
                );
                //System.out.println(meta.getAttributeModifiers(Attribute.GENERIC_ARMOR).toString());
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, newMod);
                System.out.println(meta.getAttributeModifiers(Attribute.GENERIC_ARMOR).toString());
                item.setItemMeta(meta);

                e.getInventory().setResult(item);
            }

        }
    }

}
