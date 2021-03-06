package io.github.rubiksimplosion.minecrafttas.command;

import com.mojang.brigadier.CommandDispatcher;
import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.script.ScriptManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.util.Arrays;

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
            .then(literal("set")
                .then(argument("name", string())
                    .suggests(FileCompletion.fileList())
                        .executes(ctx -> set(ctx.getSource(), getString(ctx, "name")))))
            .then(literal("start")
                .executes(ctx -> start(ctx.getSource())))
            .then(literal("stop")
                .executes(ctx -> stop(ctx.getSource()))));
    }

    public static int test(ServerCommandSource source) {
        return 0;
    }

    public static int set(ServerCommandSource source, String name) {
        source.sendFeedback(new LiteralText("Settting script"), false);
        MinecraftTas.scriptManager.setScript(name);
        return 0;
    }

    public static int start(ServerCommandSource source) {
        source.sendFeedback(new LiteralText("Started executing script"), false);
        MinecraftTas.scriptManager.start();
        return 0;
    }

    public static int stop(ServerCommandSource source) {
        source.sendFeedback(new LiteralText("Stopped executing script"), false);
        MinecraftTas.scriptManager.stop();
        return 0;
    }
}