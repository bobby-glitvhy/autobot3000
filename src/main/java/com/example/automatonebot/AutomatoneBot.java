package com.example.automatonebot;

import net.fabricmc.api.ModInitializer;

public class AutomatoneBot implements ModInitializer {
    @Override
    public void onInitialize() {
        BotCommand.register();
    }
}
