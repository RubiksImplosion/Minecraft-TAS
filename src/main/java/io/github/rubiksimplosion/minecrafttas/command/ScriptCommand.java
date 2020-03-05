package io.github.rubiksimplosion.minecrafttas.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.input.InputManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static net.minecraft.server.command.CommandManager.literal;

@Environment(EnvType.CLIENT)
public class ScriptCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        ClientCommandManager.addClientSideCommand("script");

        dispatcher.register(literal("script")
            .then(literal("test")
                .executes(ctx -> test(ctx.getSource()))));
    }

    public static int test(ServerCommandSource source) {
        source.sendFeedback(new LiteralText("testing"), false);
        InputManager.getInstance().setExecutingStatus(!InputManager.getInstance().executing);
        return 0;
    }
}