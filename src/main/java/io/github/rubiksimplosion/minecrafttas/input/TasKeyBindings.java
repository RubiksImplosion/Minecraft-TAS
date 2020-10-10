package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.script.ScriptManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TasKeyBindings {
    public static KeyBinding keyTasTest = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.test",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "category.minecrafttas.tas"));
    public static KeyBinding keyScriptStop = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.script.stop",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "category.minecrafttas.tas"));
    public static KeyBinding keyScriptStart = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.minecrafttas.script.start",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "category.minecrafttas.tas"));

    public static void registerKeys() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyTasTest.wasPressed()) {
                onKeyTasTestPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyScriptStart.wasPressed()) {
                onKeyTasStartPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyScriptStop.wasPressed()) {
                onKeyTasStopPressed();
            }
        });
    }

    public static void onKeyTasTestPressed() {
        MinecraftClient.getInstance().player.sendMessage(new LiteralText(ScriptManager.scriptDirectory.getPath()), false);
    }

    public static void onKeyTasStopPressed() {
        if (MinecraftTas.scriptManager.executing) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("Stopped executing script"), false);
            MinecraftTas.scriptManager.stop();
        }
    }

    public static void onKeyTasStartPressed() {
        if (!MinecraftTas.scriptManager.executing) {
            MinecraftClient.getInstance().player.sendMessage(new LiteralText("Started executing script"), false);
            MinecraftTas.scriptManager.start();
        }
    }
}
