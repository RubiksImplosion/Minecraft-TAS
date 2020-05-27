package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeKeyboard;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyboardUtil {

    public static void holdJump() {
        int key = MinecraftClient.getInstance().options.keyJump.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseJump() {
        int key = MinecraftClient.getInstance().options.keyJump.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdSneak() {
        int key = MinecraftClient.getInstance().options.keySneak.getDefaultKeyCode().getKeyCode();
        MinecraftTas.scriptManager.modifiers += 1;
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseSneak() {
        int key = MinecraftClient.getInstance().options.keySneak.getDefaultKeyCode().getKeyCode();
        MinecraftTas.scriptManager.modifiers -= 1;
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdSprint() {
        int key = MinecraftClient.getInstance().options.keySprint.getDefaultKeyCode().getKeyCode();
        MinecraftTas.scriptManager.modifiers += 2;
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseSprint() {
        int key = MinecraftClient.getInstance().options.keySprint.getDefaultKeyCode().getKeyCode();
        MinecraftTas.scriptManager.modifiers -= 2;
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdForward() {
        int key = MinecraftClient.getInstance().options.keyForward.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseForward() {
        int key = MinecraftClient.getInstance().options.keyForward.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdBack() {
        int key = MinecraftClient.getInstance().options.keyBack.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseBack() {
        int key = MinecraftClient.getInstance().options.keyBack.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdLeft() {
        int key = MinecraftClient.getInstance().options.keyLeft.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseLeft() {
        int key = MinecraftClient.getInstance().options.keyLeft.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdRight() {
        int key = MinecraftClient.getInstance().options.keyRight.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseRight() {
        int key = MinecraftClient.getInstance().options.keyRight.getDefaultKeyCode().getKeyCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }
}
