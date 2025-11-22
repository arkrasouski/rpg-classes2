package org.example.artyom.rpgClasses;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.artyom.rpgClasses.commands.GUICommand;
import org.example.artyom.rpgClasses.commands.HandleClasses;
import org.example.artyom.rpgClasses.commands.HandleJobs;
import org.example.artyom.rpgClasses.eventHandlers.ClassesGuiEvents;
import org.example.artyom.rpgClasses.eventHandlers.JobsEvents;
import org.example.artyom.rpgClasses.eventHandlers.JobsGUIEvents;
import org.example.artyom.rpgClasses.eventHandlers.ScoreboardEvents;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.ScoreUtils;

public final class RpgClasses extends JavaPlugin {
    private static RpgClasses instance;

    @Override
    public void onEnable() {
        instance = this;
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new ClassesGuiEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JobsGUIEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JobsEvents(), this);
        Bukkit.getPluginManager().registerEvents(new ScoreboardEvents(), this);

        getCommand("getclass").setExecutor(new HandleClasses());
        getCommand("getjobs").setExecutor(new HandleJobs());
        getCommand("gui").setExecutor(new GUICommand());
        getCommand("jobs").setExecutor(new GUICommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static RpgClasses getInstance() {
        return instance;
    }
}
