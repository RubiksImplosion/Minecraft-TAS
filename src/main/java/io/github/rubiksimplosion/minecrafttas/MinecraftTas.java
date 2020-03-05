package io.github.rubiksimplosion.minecrafttas;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.command.*;
import io.github.rubiksimplosion.minecrafttas.input.InputManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

@Environment(EnvType.CLIENT)
public class MinecraftTas implements ModInitializer {
    InputManager manager;
    @Override
    public void onInitialize() {
        manager = new InputManager();
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        ClientCommandManager.clearClientSideCommands();
        ScriptCommand.register(dispatcher);
    }
}
