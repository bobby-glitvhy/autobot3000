package com.example.automatonebot;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class BotTicker {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var bot = BotManager.getBot();
            if (bot != null) {
                try {
                    // Tick the bot every server tick
                    bot.tick();

                    // Keep chunks around bot loaded
                    bot.getWorld().getChunkManager().addTicket(
                            net.minecraft.server.world.ChunkTicketType.PLAYER,
                            bot.getChunkPos(),
                            1,
                            bot.getId()
                    );
                } catch (Exception ignored) {}
            }
        });
    }
}
