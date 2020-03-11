package io.github.rubiksimplosion.minecrafttas;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.command.ClientCommandManager;
import io.github.rubiksimplosion.minecrafttas.command.ScriptCommand;
import io.github.rubiksimplosion.minecrafttas.input.InputManager;
import io.github.rubiksimplosion.minecrafttas.input.TasKeyBindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;

@Environment(EnvType.CLIENT)
public class MinecraftTas implements ModInitializer {
    public InputManager inputManager;
    @Override
    public void onInitialize() {
        inputManager = new InputManager();
        TasKeyBindings.registerKeys();
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        ClientCommandManager.clearClientSideCommands();
        ScriptCommand.register(dispatcher);
    }
}
