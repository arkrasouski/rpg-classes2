package org.example.artyom.rpgClasses.eventHandlers;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.persistence.PersistentDataType;
import org.example.artyom.rpgClasses.customEvents.ChangeClassEvent;
import org.example.artyom.rpgClasses.customEvents.ChangeJobEvent;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.PlayerClassesUtils;
import org.example.artyom.rpgClasses.utils.PlayerJobsUtils;
import org.example.artyom.rpgClasses.utils.ScoreUtils;

public class ScoreboardEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ScoreUtils helper = ScoreUtils.createScore(player);
        helper.setTitle("&aNitrum &eProject");
        helper.setSlot(3, "&7&m--------------------------------");
        helper.setSlot(2, "&aPlayer&f: " + player.getName());
        helper.setSlot(1, "&7&m--------------------------------");
        helper.setSlot(4, "&cMaxHealth = " + player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        String className = player.getPersistentDataContainer().get(NamespacedKey.fromString("player_class"), PersistentDataType.STRING);
        String jobName = player.getPersistentDataContainer().get(NamespacedKey.fromString("player_job"), PersistentDataType.STRING);
        Classes playerClass = null;
        Jobs playerJob = Jobs.NULL;
        if ( className != null) {
            playerClass = Classes.valueOf(className.toUpperCase());
            PlayerClassesUtils.setPlayerStats(player, playerClass);

        }
        if(jobName != null) {
            playerJob = Jobs.valueOf(jobName.toUpperCase());
        }
        helper.setSlot(5, "&9Mana = " + (playerClass == null ? 2000 : playerClass.getMana()));
        helper.setSlot(6, (playerJob == Jobs.NULL ? "" : "&4Your job is &5" + playerJob.getName()));
        //helper.setSlot(4, "&a Убил огнем существ:&f " + helper.fired_entity);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(ScoreUtils.hasScore(player)) {
            ScoreUtils.removeScore(player);
        }
    }

    @EventHandler
    public void onChangeClassEvent(ChangeClassEvent event) {
        Player player = event.getPlayer();
        Classes playerClass = event.getPlayerClass();
        // Обновляем scoreboard
        ScoreUtils score = ScoreUtils.getByPlayer(player);
        //int killed_by_fire_count = score.fired_entity += 1;

        PlayerClassesUtils.setPlayerStats(player, playerClass);

        PlayerJobsUtils.giveJobParametersToPlayer(player, "Null");
        Bukkit.getPluginManager().callEvent(new ChangeJobEvent(player, Jobs.NULL));

        score.setSlot(4, "&cMaxHealth = " + playerClass.getHP());
        score.setSlot(5, "&9Mana = " +  playerClass.getMana());
    }

    @EventHandler
    public void onChangeJobEvent(ChangeJobEvent event) {
        Player player = event.getPlayer();
        Jobs playerJob = event.getPlayerJob();
        // Обновляем scoreboard
        ScoreUtils score = ScoreUtils.getByPlayer(player);

        if (score == null) {
            System.out.println("Scoreboard for " + player.getName() + " NOT FOUND!");
            return;
        }
        score.setSlot(6, (playerJob == Jobs.NULL ? "" : "&4Your job is &5" + playerJob.getName()));
    }
}
