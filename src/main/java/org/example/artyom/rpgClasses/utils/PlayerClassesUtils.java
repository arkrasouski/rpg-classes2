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
        player.getPersistentDataContainer().set(
                NamespacedKey.fromString("player_class"),
                PersistentDataType.STRING,
                className
        );
    }

    public static String getPlayerClass(Player player) {
        return player.getPersistentDataContainer().get(
                NamespacedKey.fromString("player_class"),
                PersistentDataType.STRING
        );
    }

    public static void giveClassParametersToPlayer(Player player, Classes classe) {
        //Устанавливаем класс и вызываем ивент обновления класса (возможно стоит объединить с просто установкой класса)
        // player.sendMessage("I`m " + className + "!"); Вот это не надо наверное - mikinol

        setPlayerStats(player, classe);
        PlayerClassesUtils.setPlayerClass(player, classe.getName().toLowerCase());
        Bukkit.getPluginManager().callEvent(new ChangeClassEvent(player, classe));
    }

    @SuppressWarnings("DataFlowIssue")
    public static void setPlayerStats(Player player, Classes playerClass) {
        //Выдача базовых параметров для класса
        player.getAttribute(Attribute.MAX_HEALTH).setBaseValue(playerClass.getHP());
        player.getAttribute(Attribute.ATTACK_DAMAGE).setBaseValue(playerClass.getStrength() * 0.25);
        player.getAttribute(Attribute.MOVEMENT_SPEED).setBaseValue(playerClass.getAgility() * 0.1);
    }
}
