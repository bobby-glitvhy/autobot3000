package com.example.automatonebot;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.GameMode;

import java.util.UUID;

public class BotManager {

    private static ServerPlayerEntity bot;

    public static ServerPlayerEntity createBot(MinecraftServer server, String name) {
        ServerWorld world = server.getOverworld();

        GameProfile profile = new GameProfile(UUID.randomUUID(), name);

        // Apply skin
        applySkin(server, profile);

        ServerPlayerInteractionManager interactionManager =
                new ServerPlayerInteractionManager(world);

        bot = new ServerPlayerEntity(server, world, profile, interactionManager);

        // 🔥 CRITICAL: fake network connection
        ClientConnection connection = new ClientConnection(net.minecraft.network.NetworkSide.SERVERBOUND);

        bot.networkHandler = new ServerPlayNetworkHandler(
                server,
                connection,
                bot
        );

        // Set gamemode (important for movement)
        interactionManager.changeGameMode(GameMode.SURVIVAL);

        // Spawn correctly
        bot.refreshPositionAndAngles(
                world.getSpawnPos().getX(),
                world.getSpawnPos().getY(),
                world.getSpawnPos().getZ(),
                0,
                0
        );

        world.spawnEntity(bot);

        return bot;
    }

    public static ServerPlayerEntity getBot() {
        return bot;
    }

    // ✅ Skin support
    private static void applySkin(MinecraftServer server, GameProfile profile) {
        try {
            server.getSessionService().fillProfileProperties(profile, true);
        } catch (Exception e) {
            System.out.println("Skin fetch failed: " + e.getMessage());
        }
    }
}
