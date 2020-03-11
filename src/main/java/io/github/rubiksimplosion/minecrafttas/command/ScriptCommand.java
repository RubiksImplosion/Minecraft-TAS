package io.github.rubiksimplosion.minecrafttas.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.input.InputManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import static io.github.rubiksimplosion.minecrafttas.command.ClientCommandManager.*;
import static net.minecraft.server.command.CommandManager.*;

@Environment(EnvType.CLIENT)
public class ScriptCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        addClientSideCommand("script");

        dispatcher.register(literal("script")
            .then(literal("test")
                .executes(ctx -> test(ctx.getSource()))));
    }

    public static int test(ServerCommandSource source) {
        source.sendFeedback(new LiteralText("testing fake mouse"), false);
        FakeMouse.fakeMouseButton(0, 1, 0);
        return 0;
    }
}