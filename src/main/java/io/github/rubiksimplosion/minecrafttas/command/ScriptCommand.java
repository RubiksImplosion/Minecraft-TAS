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
            .then(literal("load")
                .then(argument("name", string())
                    .suggests(FileCompletion.scriptFileList())
                        .executes(ctx -> loadScript(getString(ctx, "name")))))
            .then(literal("start")
                .executes(ctx -> start()))
            .then(literal("stop")
                .executes(ctx -> stop())));

        addClientSideCommand("savestate");
        dispatcher.register(literal("savestate")
            .then(literal("save")
                .then(argument("name", string())
                    .executes(ctx -> save(getString(ctx, "name")))))
            .then(literal("load")
                .then(argument("name", string())
                    .suggests(FileCompletion.savestateFileList())
                        .executes(ctx -> loadSaveState(getString(ctx, "name"))))));
    }

    public static int loadSaveState(String name) {
        MinecraftTas.savestateManager.loadSoftSavetateFromFile(name);
        return 0;
    }

    public static int test(ServerCommandSource source) {
        InputUtil.getClientSidePlayerEntity().sendMessage(
                new LiteralText(ScriptManager.scriptDirectory.toString()), false);
        return 0;
    }
    public static int save(String name) {
        MinecraftTas.savestateManager.saveSoftSavestateToFile(name);
        return 0;
    }

    public static int loadScript(String name) {
        int status = MinecraftTas.scriptManager.setScript(name);
        if (status == 0) {
            InputUtil.sendFeedback(new TranslatableText("command.load.success", name));
        } else if (status == 1) {
            InputUtil.sendError(new TranslatableText("error.command.load.empty", name));
        } else if (status == 2) {
            InputUtil.sendError(new TranslatableText("error.command.load.fail", name));
        }
        return 0;
    }

    public static int start() {
        InputUtil.sendFeedback(new TranslatableText("command.start"));
        MinecraftTas.scriptManager.start();
        return 0;
    }

    public static int stop() {
        InputUtil.sendFeedback(new TranslatableText("command.stop"));
        MinecraftTas.scriptManager.stop();
        return 0;
    }
}