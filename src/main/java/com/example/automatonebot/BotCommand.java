package com.example.automatonebot;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class BotCommand {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("bot")

                // Spawn bot with custom name
                .then(CommandManager.literal("spawn")
                    .then(CommandManager.argument("name", StringArgumentType.word())
                        .executes(ctx -> {
                            String name = StringArgumentType.getString(ctx, "name");
                            BotManager.createBot(ctx.getSource().getServer(), name);
                            ctx.getSource().sendMessage(Text.literal("Bot spawned: " + name));
                            return 1;
                        })))

                // Execute full Baritone commands
                .then(CommandManager.argument("cmd", StringArgumentType.greedyString())
                    .executes(ctx -> {
                        String input = StringArgumentType.getString(ctx, "cmd");
                        handle(ctx.getSource(), input);
                        return 1;
                    }))
            );
        });
    }

    private static void handle(ServerCommandSource source, String input) {
        var bot = BotManager.getBot();

        if (bot == null) {
            source.sendMessage(Text.literal("Spawn bot first"));
            return;
        }

                IBaritone baritone = BaritoneAPI.getProvider().getBaritone(bot);

        try {
            // Execute real Baritone commands
            baritone.getCommandManager().execute(input);
            source.sendMessage(Text.literal("Executed: " + input));
        } catch (Exception e) {
            source.sendMessage(Text.literal("Error executing command: " + e.getMessage()));
        }
    }
}
