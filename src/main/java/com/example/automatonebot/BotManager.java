package com.example.automatonebot;

import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

import java.util.UUID;

public class BotManager {

    private static ServerPlayerEntity bot;

    public static ServerPlayerEntity createBot(MinecraftServer server) {
        ServerWorld world = server.getOverworld();

        GameProfile profile = new GameProfile(UUID.randomUUID(), "Bot");

        bot = new ServerPlayerEntity(server, world, profile);
        world.spawnEntity(bot);

        return bot;
    }

    public static ServerPlayerEntity getBot() {
        return bot;
    }
}
