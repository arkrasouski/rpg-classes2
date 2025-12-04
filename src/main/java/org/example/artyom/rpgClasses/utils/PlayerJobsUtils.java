package org.example.artyom.rpgClasses.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.example.artyom.rpgClasses.RpgClasses;
import org.example.artyom.rpgClasses.customEvents.ChangeJobEvent;

import org.example.artyom.rpgClasses.plugins.Jobs;


public class PlayerJobsUtils {
    //Вспомогательный класс для работы с профессиями
    public static void setPlayerJob(Player player, String className) {
        //Установка профессии игроку
        player.getPersistentDataContainer().set(
                new NamespacedKey(RpgClasses.getInstance(), "player_job"),
                PersistentDataType.STRING,
                className
        );
    }

    public static void setPlayerJobLevel(Player player, int level) {
        //Установка уровня профессии игрока
        player.getPersistentDataContainer().set(
                new NamespacedKey(RpgClasses.getInstance(),getPlayerJob(player) + "_level" ),
                PersistentDataType.INTEGER,
                level
        );

    }

    public static void setPlayerJobExp(Player player, int exp) {
        //Установка уровня прогресса опыта профессии игрока
        player.getPersistentDataContainer().set(
                new NamespacedKey(RpgClasses.getInstance(), getPlayerJob(player) + "_exp"),
                PersistentDataType.INTEGER,
                exp
        );

    }

    public static String getPlayerJob(Player player) {
        //Получение профессии игрока
        return player.getPersistentDataContainer().get(
                new NamespacedKey(RpgClasses.getInstance(),"player_job"),
                PersistentDataType.STRING
        );
    }

    public static int getPlayerJobLevel(Player player) {
        //Получение уровня профессии игрока
        String job = getPlayerJob(player);

        if (job == null || job.equalsIgnoreCase("NULL")) {
            return 0;
        }
        return player.getPersistentDataContainer().get(
                new NamespacedKey(RpgClasses.getInstance(),getPlayerJob(player) + "_level"),
                PersistentDataType.INTEGER
        ) == null ? 0 : player.getPersistentDataContainer().get(new NamespacedKey(RpgClasses.getInstance(),getPlayerJob(player) + "_level"),
                PersistentDataType.INTEGER);

    }

    public static int getPlayerJobExp(Player player) {
        //Получение прогресса опыта профессии игрока
        String job = getPlayerJob(player);

        if (job == null || job.equalsIgnoreCase("NULL")) {
            return 0;
        }
        return player.getPersistentDataContainer().get(
                new NamespacedKey(RpgClasses.getInstance(),getPlayerJob(player) + "_exp"),
                PersistentDataType.INTEGER
        ) == null ? 0 : player.getPersistentDataContainer().get(new NamespacedKey(RpgClasses.getInstance(),getPlayerJob(player) + "_exp"),
                PersistentDataType.INTEGER);
    }

    public static void giveJobParametersToPlayer(Player player, Jobs job) {
        //Установка профессии и вызов ивента изменения профессии для scoreboard
        // player.sendMessage("I`m " + className + "!");
        PlayerJobsUtils.setPlayerJob(player, job.toString().toLowerCase());
        Bukkit.getPluginManager().callEvent(new ChangeJobEvent(player, job));
    }

    public static void addArmor(ItemStack item, double addAmount, EquipmentSlotGroup slot, NamespacedKey key, int level) {
        //Добавление очков брони для брони созданной кузнецом
        if (item == null || item.getType().isAir()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
//        double currentArmor = 0;
//        // Получаем существующие модификаторы (если их нет — будет null)
//        Multimap<Attribute, AttributeModifier> map = meta.getAttributeModifiers(); //мультимап - объект где
////        // одному ключу соответствует НЕ одно, а сразу несколько значений.
//        if (map != null) {
//            Collection<AttributeModifier> armorMods = map.get(Attribute.GENERIC_ARMOR);
//            if (armorMods != null) {
//                for (AttributeModifier mod : armorMods) {
//                    // Суммируем ВСЕ модификаторы ADD_NUMBER (обычные цифры)
//                    if (mod.getOperation() == AttributeModifier.Operation.ADD_NUMBER &&
//                            mod.getSlotGroup() == slot) {
//
//                        currentArmor += mod.getAmount();
//                    }
//                }
//
//                // Удаляем старые модификаторы брони (иначе они удвоятся)
//                //meta.removeAttributeModifier(Attribute.GENERIC_ARMOR);
//            }
//
//
//        }
        ;

        // Новый итог
        double newArmor = (PlayerJobsUtils.getVanillaArmorValue(item.getType()) + addAmount ) + (double) level / 10; // + currentArmor;

        AttributeModifier newMod = new AttributeModifier(
                key,
                newArmor, // количество брони
                AttributeModifier.Operation.ADD_NUMBER,
                slot
        );
        //System.out.println(meta.getAttributeModifiers(Attribute.GENERIC_ARMOR).toString());

        meta.addAttributeModifier(Attribute.ARMOR, newMod);
        item.setItemMeta(meta);
    }

    public static int changeJobLevelOrExp(Player player, int levelThreshold, boolean getThreshold) { //пороговое значение
        //Обновление уровней профессии

        int level = PlayerJobsUtils.getPlayerJobLevel(player);
        int maxLevelThreshold = levelThreshold + level * 2;

        if(getThreshold) {
            return maxLevelThreshold;
        }
        int exp = PlayerJobsUtils.getPlayerJobExp(player);
        exp += 1;


        if (exp >= maxLevelThreshold) {

            level += 1;
            PlayerJobsUtils.setPlayerJobLevel(player, level);
            PlayerJobsUtils.setPlayerJobExp(player, exp - maxLevelThreshold);

        } else {
            PlayerJobsUtils.setPlayerJobExp(player, exp);
        }
        return  maxLevelThreshold;
    }

    public static int changeJobLevelOrExp(Player player, int levelThreshold) {
        return changeJobLevelOrExp(player, levelThreshold, false); // Перегрузка для получения максимума прогресса уровня
    }

    public static int changeLevelOrExp(Player player) {
        return changeJobLevelOrExp(player, 5, true);
    }
    private static int getVanillaArmorValue(Material mat) {
        //Перечисление базовых игровых очков брони
        // ноги (boots)
        switch (mat) {
            case LEATHER_BOOTS: return 1;
            case GOLDEN_BOOTS: return 1;
            case CHAINMAIL_BOOTS: return 1;
            case IRON_BOOTS: return 2;
            case DIAMOND_BOOTS: return 3;
            case NETHERITE_BOOTS: return 3;

            // legs (leggings)
            case LEATHER_LEGGINGS: return 2;
            case GOLDEN_LEGGINGS: return 2;
            case CHAINMAIL_LEGGINGS: return 4;
            case IRON_LEGGINGS: return 5;
            case DIAMOND_LEGGINGS: return 6;
            case NETHERITE_LEGGINGS: return 6;

            // chest (chestplate)
            case LEATHER_CHESTPLATE: return 3;
            case GOLDEN_CHESTPLATE: return 5;
            case CHAINMAIL_CHESTPLATE: return 6;
            case IRON_CHESTPLATE: return 8;
            case DIAMOND_CHESTPLATE: return 8;
            case NETHERITE_CHESTPLATE: return 8;

            // head (helmet)
            case LEATHER_HELMET: return 1;
            case GOLDEN_HELMET: return 1;
            case CHAINMAIL_HELMET: return 1;
            case IRON_HELMET: return 2;
            case DIAMOND_HELMET: return 3;
            case NETHERITE_HELMET: return 3;

            default:
                return 0;
        }
    }
}
