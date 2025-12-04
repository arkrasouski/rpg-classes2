package org.example.artyom.rpgClasses;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.artyom.rpgClasses.commands.CommandsTree;
import org.example.artyom.rpgClasses.eventHandlers.ClassesGuiEvents;
import org.example.artyom.rpgClasses.eventHandlers.JobsEvents;
import org.example.artyom.rpgClasses.eventHandlers.JobsGUIEvents;

public final class RpgClasses extends JavaPlugin {
    @Getter
    private static RpgClasses instance;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new ClassesGuiEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JobsGUIEvents(), this);
        Bukkit.getPluginManager().registerEvents(new JobsEvents(), this);

        this.getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> {
            commands.registrar().register(CommandsTree.guiCommand);
            commands.registrar().register(CommandsTree.jobsCommand);
            commands.registrar().register(CommandsTree.getClassCommand);
            commands.registrar().register(CommandsTree.getJobsCommand);
        });
    }
}
