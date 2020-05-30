package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class TasKeyBindings {
    public static FabricKeyBinding keyTasTest = FabricKeyBinding.Builder.create(new Identifier("minecrafttas", "test"),
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            "tas").build();
    public static FabricKeyBinding keyScriptStop = FabricKeyBinding.Builder.create(new Identifier("minecrafttas", "stop"),
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "tas").build();
    public static FabricKeyBinding keyScriptStart = FabricKeyBinding.Builder.create(new Identifier("minecrafttas", "start"),
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "tas").build();

    public static void registerKeys() {
        KeyBindingRegistry.INSTANCE.addCategory("tas");
        KeyBindingRegistry.INSTANCE.register(keyTasTest);
        KeyBindingRegistry.INSTANCE.register(keyScriptStop);
        KeyBindingRegistry.INSTANCE.register(keyScriptStart);

        ClientTickCallback.EVENT.register(e ->
        {
            if(keyTasTest.wasPressed()) {
                onKeyTasTestPressed();
            }
            else if (keyScriptStop.wasPressed()) {
                onKeyTasStopPressed();
            }
            else if (keyScriptStart.wasPressed()) {
                onKeyTasStartPressed();
            }
        });
    }

    public static void onKeyTasTestPressed() {
        FakeMouse.fakeMouseButton(1, 1);
        FakeMouse.fakeMouseButton(1,0);
    }

    public static void onKeyTasStopPressed() {
        MinecraftClient.getInstance().player.addChatMessage(new LiteralText("Stopped executing script"), false);
        MinecraftTas.scriptManager.stop();
    }

    public static void onKeyTasStartPressed() {
        MinecraftClient.getInstance().player.addChatMessage(new LiteralText("Started executing script"), false);
        MinecraftTas.scriptManager.start();
    }
}
