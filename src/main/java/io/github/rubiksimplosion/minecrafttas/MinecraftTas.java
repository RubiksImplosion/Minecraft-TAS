package io.github.rubiksimplosion.minecrafttas;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.command.ClientCommandManager;
import io.github.rubiksimplosion.minecrafttas.command.ScriptCommand;
import io.github.rubiksimplosion.minecrafttas.input.TasKeyBindings;
import io.github.rubiksimplosion.minecrafttas.savestate.SavestateManager;
import io.github.rubiksimplosion.minecrafttas.script.ScriptManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.command.ServerCommandSource;
import org.lwjgl.system.CallbackI;

@Environment(EnvType.CLIENT)
public class MinecraftTas implements ModInitializer {
    public static ScriptManager scriptManager;
    public static SavestateManager savestateManager;

    @Override
    public void onInitialize() {
        scriptManager = new ScriptManager();
        savestateManager = new SavestateManager();
        TasKeyBindings.registerKeys();
    }

    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher) {
        ClientCommandManager.clearClientSideCommands();
        ScriptCommand.register(dispatcher);
    }
}
