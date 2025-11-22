package org.example.artyom.rpgClasses.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.utils.PlayerClassesUtils;

public class HandleClasses implements CommandExecutor {
    //Обработка команды выдачи класса
    //возможно тут стоит сбрасывать профессию
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage("Укажи имя класса");
            return true;
        }

        try {
            Classes gameClass = Classes.valueOf(strings[0]);
            //ArmsHandler.handle(scroll, player);

            if (gameClass.getName().equalsIgnoreCase("wizard")){
                PlayerClassesUtils.giveClassParametersToPlayer(player, "wizard");
            } else if (gameClass.getName().equalsIgnoreCase("warrior")){
                PlayerClassesUtils.giveClassParametersToPlayer(player, "warrior");

            }   else if (gameClass.getName().equalsIgnoreCase("sacrifier")){
                PlayerClassesUtils.giveClassParametersToPlayer(player, "sacrifier");
            }
            return true;
        }
        catch (IllegalArgumentException e) {
            player.sendMessage("Неверное имя класса!");
            return true;
        }
        //return false;
    }
}
