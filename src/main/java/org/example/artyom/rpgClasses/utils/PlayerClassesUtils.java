package org.example.artyom.rpgClasses.utils;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.example.artyom.rpgClasses.customEvents.ChangeClassEvent;
import org.example.artyom.rpgClasses.plugins.Classes;

public class PlayerClassesUtils {
    //Вспомогательный класс для работы с кастомными классами игрока
    public static void setPlayerClass(Player player, String className) {
        //Устанавливаем класс игроку
        player.getPersistentDataContainer().set(
                NamespacedKey.fromString( "player_class"),
                PersistentDataType.STRING,
                className
        );
    }

    public static String getPlayerClass(Player player) {
        //Получаем класс игрока
        return player.getPersistentDataContainer().get(
                NamespacedKey.fromString( "player_class"),
                PersistentDataType.STRING
        );
    }

    public static void giveClassParametersToPlayer(Player player, String className) {
        //Устанавливаем класс и вызываем ивент обновления класса (возможно стоит объединить с просто установкой класса)
        player.sendMessage("I`m " + className + "!");
        PlayerClassesUtils.setPlayerClass(player, className);
        Bukkit.getPluginManager().callEvent(new ChangeClassEvent(player, Classes.valueOf(className.toUpperCase())));
    }

    public static void setPlayerStats(Player player, Classes playerClass) {
        //Выдача базовых параметров для класса
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(playerClass.getHP());
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue());

        player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(playerClass.getStrength() * 0.25);
        player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(playerClass.getAgility() * 0.1);
    }
}
