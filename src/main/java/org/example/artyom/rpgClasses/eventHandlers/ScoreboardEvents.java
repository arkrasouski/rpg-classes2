package org.example.artyom.rpgClasses.eventHandlers;

import org.bukkit.Bukkit;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import org.example.artyom.rpgClasses.customEvents.ChangeClassEvent;
import org.example.artyom.rpgClasses.customEvents.ChangeJobEvent;
import org.example.artyom.rpgClasses.customEvents.ChangeLevelOrExpEvent;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.PlayerClassesUtils;
import org.example.artyom.rpgClasses.utils.PlayerJobsUtils;
import org.example.artyom.rpgClasses.utils.ScoreUtils;

public class ScoreboardEvents implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) { //рисуем scoreboard при входе в игру
        Player player = event.getPlayer();
        ScoreUtils score = ScoreUtils.createScore(player);
        ScoreUtils.drawScoreboard(player, score, false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) { //удаляем scoreboard при выходе из игры
        Player player = event.getPlayer();
        if(ScoreUtils.hasScore(player)) {
            ScoreUtils.removeScore(player);
        }
    }

    @EventHandler
    public void onChangeClassEvent(ChangeClassEvent event) { //При смене класса обнуляем профессию и scoreboard.
        Player player = event.getPlayer();
        Classes playerClass = event.getPlayerClass();
        // Обновляем scoreboard
        ScoreUtils score = ScoreUtils.getByPlayer(player);
        //int killed_by_fire_count = score.fired_entity += 1;

        PlayerClassesUtils.setPlayerStats(player, playerClass);

        PlayerJobsUtils.giveJobParametersToPlayer(player, "Null");
        PlayerJobsUtils.setPlayerJobLevel(player, 0);
        PlayerJobsUtils.setPlayerJobExp(player, 0);

        Bukkit.getPluginManager().callEvent(new ChangeJobEvent(player, Jobs.NULL));

        ScoreUtils.drawScoreboard(player, score, true);

    }

    @EventHandler
    public void onChangeJobEvent(ChangeJobEvent event) { // При смене профессии обновляем scoreboard
        Player player = event.getPlayer();
        Jobs playerJob = event.getPlayerJob();
        // Обновляем scoreboard
        ScoreUtils score = ScoreUtils.getByPlayer(player);

        if (score == null) {
            System.out.println("Scoreboard for " + player.getName() + " NOT FOUND!");
            return;
        }
        ScoreUtils.drawScoreboard(player, score, true);
    }

    @EventHandler
    public void onChangeLevelOrExperience(ChangeLevelOrExpEvent event) { //Обновляем прогресс уровня профессии в scoreboard
        Player player = event.getPlayer();
        ScoreUtils score = ScoreUtils.getByPlayer(player);

        if (score == null) {
            System.out.println("Scoreboard for " + player.getName() + " NOT FOUND!");
            return;
        }

        PlayerJobsUtils.changeJobLevelOrExp(player, 5);

        ScoreUtils.drawScoreboard(player, score, true);
    }
}
