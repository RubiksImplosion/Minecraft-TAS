package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.TranslatableText;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TasKeyBindings {
    public static KeyBinding keyTasTest = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecrafttas.test",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "key.category.tas"));
    public static KeyBinding keyScriptStop = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecrafttas.script.stop",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "key.category.tas"));
    public static KeyBinding keyScriptStart = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecrafttas.script.start",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_I,
            "key.category.tas"));
    public static KeyBinding keyCreateSavestate = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecrafttas.savestate.create",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_J,
            "key.category.tas"));
    public static KeyBinding keyLoadSavestate = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecrafttas.savestate.load",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "key.category.tas"));
    public static KeyBinding keyDeleteSavestate = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.minecrafttas.savestate.delete",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_APOSTROPHE,
            "key.category.tas"));

    public static void registerKeys() {
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            while (keyTasTest.wasPressed()) {
//                onKeyTasTestPressed();
//            }
//        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyScriptStart.wasPressed()) {
                onKeyScriptStartPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyScriptStop.wasPressed()) {
                onKeyScriptStopPressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyCreateSavestate.wasPressed()) {
                onKeyCreateSavestatePressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyLoadSavestate.wasPressed()) {
                onKeyLoadSavestatePressed();
            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyDeleteSavestate.wasPressed()) {
                onKeyDeleteSavestatePressed();
            }
        });
    }

    public static void onKeyDeleteSavestatePressed() {
        MinecraftTas.savestateManager.removeMostRecentSavestate();
//        MinecraftTas.savestateManager.loadSoftSavetateFromFile("temp");
    }

    public static void onKeyCreateSavestatePressed() {
        MinecraftTas.savestateManager.addQuickSoftSavestate();
    }

    public static void onKeyLoadSavestatePressed() {
        MinecraftTas.savestateManager.loadMostRecentSavestate();
    }


    public static void onKeyTasTestPressed() {
        MinecraftTas.savestateManager.saveSoftSavestateToFile("temp");
    }

    public static void onKeyScriptStopPressed() {
        if (MinecraftTas.scriptManager.executing) {
            io.github.rubiksimplosion.minecrafttas.util.InputUtil.sendFeedback(new TranslatableText("execution.stop"));
            MinecraftTas.scriptManager.stop();
        }
    }

    public static void onKeyScriptStartPressed() {
        if (!MinecraftTas.scriptManager.executing) {
            if (MinecraftTas.scriptManager.isScriptLoaded()) {
                io.github.rubiksimplosion.minecrafttas.util.InputUtil.sendFeedback(new TranslatableText("execution.start"));
                MinecraftTas.scriptManager.start();
            } else {
                io.github.rubiksimplosion.minecrafttas.util.InputUtil.sendError(new TranslatableText("error.scriptNotLoaded"));
            }
        }
    }
}
