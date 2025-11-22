package org.example.artyom.rpgClasses.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.PlayerClassesUtils;
import org.example.artyom.rpgClasses.utils.PlayerJobsUtils;

public class HandleJobs implements CommandExecutor {
    //Обработка команды выдачи Профессии
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage("Укажи имя профессии!");
            return true;
        }
        if (PlayerClassesUtils.getPlayerClass(player) != null) {
        try {
            Jobs job = Jobs.valueOf(strings[0]);

            if (job.getName().equalsIgnoreCase("blacksmith")){
                PlayerJobsUtils.giveJobParametersToPlayer(player, "blacksmith");
            } else if (job.getName().equalsIgnoreCase("alchemist")){
                PlayerJobsUtils.giveJobParametersToPlayer(player, "alchemist");

            }   else if (job.getName().equalsIgnoreCase("enchanter")){
                PlayerJobsUtils.giveJobParametersToPlayer(player, "enchanter");
            }
            return true;
        }
        catch (IllegalArgumentException e) {
            player.sendMessage("Неверное имя профессии!");
            return true;
        }}
        else {
            player.sendMessage("Надо выбрать класс!");
            return true;
            }
    }
}
