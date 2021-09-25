package io.github.rubiksimplosion.minecrafttas.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.script.ScriptManager;
import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.string;
import static io.github.rubiksimplosion.minecrafttas.command.ClientCommandManager.*;
import static net.minecraft.server.command.CommandManager.*;

@Environment(EnvType.CLIENT)
public class ScriptCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        addClientSideCommand("script");

        dispatcher.register(literal("script")
            .then(literal("test")
                .executes(ctx -> test(ctx.getSource())))
            .then(literal("load")
                .then(argument("name", string())
                    .suggests(FileCompletion.fileList())
                        .executes(ctx -> load(ctx.getSource(), getString(ctx, "name")))))
            .then(literal("start")
                .executes(ctx -> start(ctx.getSource())))
            .then(literal("stop")
                .executes(ctx -> stop(ctx.getSource()))));
    }

    public static int test(ServerCommandSource source) {
        InputUtil.getClientSidePlayerEntity().sendMessage(
                new LiteralText(ScriptManager.scriptDirectory.toString()), false);
        return 0;
    }

    public static int load(ServerCommandSource source, String name) {
        int status = MinecraftTas.scriptManager.setScript(name);
        if (status == 0) {
            source.sendFeedback(new TranslatableText("commands.script.load.success", name), false);
        } else if (status == 1) {
            source.sendFeedback(new TranslatableText("commands.script.load.empty", name), false);
        } else if (status == 2) {
            source.sendFeedback(new TranslatableText("commands.script.load.fail", name), false);
        }
        return 0;
    }

    public static int start(ServerCommandSource source) {
        source.sendFeedback(new TranslatableText("commands.script.start"), false);
        MinecraftTas.scriptManager.start();
        return 0;
    }

    public static int stop(ServerCommandSource source) {
        source.sendFeedback(new TranslatableText("commands.script.stop"), false);
        MinecraftTas.scriptManager.stop();
        return 0;
    }
}