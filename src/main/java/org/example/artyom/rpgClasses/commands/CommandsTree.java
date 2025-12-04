package org.example.artyom.rpgClasses.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.example.artyom.rpgClasses.gui.ClassesGUI;
import org.example.artyom.rpgClasses.gui.JobGUI;
import org.example.artyom.rpgClasses.plugins.Classes;
import org.example.artyom.rpgClasses.plugins.Jobs;
import org.example.artyom.rpgClasses.utils.PlayerClassesUtils;
import org.example.artyom.rpgClasses.utils.PlayerJobsUtils;

import java.util.concurrent.CompletableFuture;

public class CommandsTree {
    public static LiteralCommandNode<CommandSourceStack> guiCommand = Commands.literal("gui")
            .requires(source -> source.getSender() instanceof Player)
            .executes(CommandsTree::guiCommandExecutor)
            .build();
    public static LiteralCommandNode<CommandSourceStack> jobsCommand = Commands.literal("jobs")
            .requires(source -> source.getSender() instanceof Player)
            .executes(CommandsTree::jobsCommandExecutor)
            .build();

    public static LiteralCommandNode<CommandSourceStack> getClassCommand = Commands.literal("getclass")
            .requires(source -> source.getSender() instanceof Player)

            .then(
                    Commands.argument("class", StringArgumentType.word())
                            .suggests(CommandsTree::getClassSuggest)
                            .executes(CommandsTree::getClassCommandExecutor)
            )
            .build();
    public static LiteralCommandNode<CommandSourceStack> getJobsCommand = Commands.literal("getjobs")
            .requires(source -> source.getSender() instanceof Player)
            .then(
                    Commands.argument("job", StringArgumentType.word())
                            .suggests(CommandsTree::getJobsSuggest)
                            .executes(CommandsTree::getJobsCommandExecutor)
            )
            .build();

    // Вообще всё это наверное должно быть в отдельных классах, а не просто всё в одном (типо всё что снизу) - mikinol
    public static int guiCommandExecutor(CommandContext<CommandSourceStack> ctx) {
        ClassesGUI.openGUI((Player) ctx.getSource().getSender());
        return Command.SINGLE_SUCCESS;
    }

    public static int jobsCommandExecutor(CommandContext<CommandSourceStack> ctx) {
        JobGUI.openGUI((Player) ctx.getSource().getSender());
        return Command.SINGLE_SUCCESS;
    }

    public static CompletableFuture<Suggestions> getClassSuggest(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        for (Classes value : Classes.values())
            if (value.name().toLowerCase().startsWith(builder.getRemaining().toLowerCase()))
                builder.suggest(value.name());
        return builder.buildFuture();
    }

    public static int getClassCommandExecutor(CommandContext<CommandSourceStack> ctx) {
        String newClass = ctx.getArgument("class", String.class).toUpperCase();
        try {
            //ArmsHandler.handle(scroll, player); хз что это просто оставлю - mikinol
            PlayerClassesUtils.giveClassParametersToPlayer((Player) ctx.getSource().getSender(), Classes.valueOf(newClass));
        } catch (IllegalArgumentException e) {
            ctx.getSource().getSender().sendMessage("Неверное имя класса!");
        }
        return Command.SINGLE_SUCCESS;
    }

    public static CompletableFuture<Suggestions> getJobsSuggest(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
        for (Jobs value : Jobs.values())
            if (value.name().toLowerCase().startsWith(builder.getRemaining().toLowerCase()))
                builder.suggest(value.name());
        return builder.buildFuture();
    }

    public static int getJobsCommandExecutor(CommandContext<CommandSourceStack> ctx) {
        String newJob = ctx.getArgument("job", String.class).toUpperCase();
        try {
            PlayerJobsUtils.giveJobParametersToPlayer((Player) ctx.getSource().getSender(), Jobs.valueOf(newJob));
        } catch (IllegalArgumentException e) {
            ctx.getSource().getSender().sendMessage("Неверное имя класса!");
        }
        return Command.SINGLE_SUCCESS;
    }
}
