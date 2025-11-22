package org.example.artyom.rpgClasses.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.plugins.Jobs;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ScoreUtils {
    //Вспомогательный класс отрисовки scoreboard
    private static HashMap<UUID, ScoreUtils> players = new HashMap<>();

    public static boolean hasScore(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public static ScoreUtils createScore(Player player) {
        return new ScoreUtils(player);
    }

    public static ScoreUtils getByPlayer(Player player) {
        return players.get(player.getUniqueId());
    }

    public static ScoreUtils removeScore(Player player) {
        return players.remove(player.getUniqueId());
    }

    private Scoreboard scoreboard;
    private Objective sidebar;

    private ScoreUtils(Player player) {
        //this.fired_entity = 0;
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        sidebar = scoreboard.registerNewObjective("sidebar", "dummy");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);
        // Create Teams
        for(int i=1; i<=15; i++) {
            Team team = scoreboard.registerNewTeam("SLOT_" + i);
            team.addEntry(genEntry(i));
        }
        player.setScoreboard(scoreboard);
        players.put(player.getUniqueId(), this);
    }

    public void setTitle(String title) {
        title = ChatColor.translateAlternateColorCodes('&', title);
        sidebar.setDisplayName(title.length()>32 ? title.substring(0, 32) : title);
    }

    public void setSlot(int slot, String text) {
        Team team = scoreboard.getTeam("SLOT_" + slot);
        String entry = genEntry(slot);
        if(!scoreboard.getEntries().contains(entry)) {
            sidebar.getScore(entry).setScore(slot); //если нет ключа, добавляем
        }

        text = ChatColor.translateAlternateColorCodes('&', text);
        //System.out.println(text);
        String pre = getFirstSplit(text);
        String suf = getFirstSplit(ChatColor.getLastColors(pre) + getSecondSplit(text));
        team.setPrefix(pre);
        team.setSuffix(suf);
    }

    public void removeSlot(int slot) {
        String entry = genEntry(slot);
        if(scoreboard.getEntries().contains(entry)) {
            scoreboard.resetScores(entry);
        }
    }

    public void setSlotsFromList(List<String> list) {
        while(list.size()>15) {
            list.remove(list.size()-1);
        }

        int slot = list.size();

        if(slot<15) {
            for(int i=(slot +1); i<=15; i++) {
                removeSlot(i);
            }
        }

        for(String line : list) {
            setSlot(slot, line);
            slot--;
        }
    }

    private String genEntry(int slot) {
        return ChatColor.values()[slot].toString();
    }

    private String getFirstSplit(String s) {
        return s.length()>16 ? s.substring(0, 16) : s;
    }

    private String getSecondSplit(String s) {
        if(s.length()>32) {
            s = s.substring(0, 32);
        }
        return s.length()>16 ? s.substring(16) : "";
    }
    
    public static void drawScoreboard(Player player, ScoreUtils score, boolean onChange) {
        //Базовая отрисовка scoreboard с изменениями параметров
        if(!onChange) {
        score.setTitle("&aNitrum &eProject");
        score.setSlot(3, "&7&m--------------------------------");
        score.setSlot(2, "&aPlayer&f: " + player.getName());
        score.setSlot(1, "&7&m--------------------------------");
        score.setSlot(4, "&cMaxHealth = " + player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        }

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

        score.setSlot(5, "&9Mana = " + (playerClass == null ? 2000 : playerClass.getMana()));
        score.setSlot(6, (playerJob == Jobs.NULL ? "" : "&4Your job is &5" + playerJob.getName()));

        int currentExp = PlayerJobsUtils.getPlayerJobExp(player);

        score.setSlot(7, "Job level: " +  PlayerJobsUtils.getPlayerJobLevel(player));
        score.setSlot(8, "Level experience: " +  currentExp);
        Scoreboard scoreboard = player.getScoreboard();
        //System.out.println(scoreboard.getEntries());
        //System.out.println(scoreboard.getTeams());
        int maxProgress = PlayerJobsUtils.changeLevelOrExp(player);
        int currentProgress =  (int) Math.round((double) 10 * currentExp / maxProgress );

        player.sendMessage(maxProgress + " " + currentProgress + " " + currentExp);
        score.setSlot(9, "Job Progress: &a" + "-".repeat(currentProgress) + "&7" + "-".repeat(10 - currentProgress)); //16 + 16


    }
}
