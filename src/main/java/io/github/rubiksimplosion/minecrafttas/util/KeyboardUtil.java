package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeKeyboard;
import io.github.rubiksimplosion.minecrafttas.mixin.KeyBindingAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyboardUtil {

    public static void holdJump() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseJump() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdSneak() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getBoundKey().getCode();
        MinecraftTas.scriptManager.modifiers += 1;
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseSneak() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getBoundKey().getCode();
        MinecraftTas.scriptManager.modifiers -= 1;
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdSprint() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getBoundKey().getCode();
        MinecraftTas.scriptManager.modifiers += 2;
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseSprint() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getBoundKey().getCode();
        MinecraftTas.scriptManager.modifiers -= 2;
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdForward() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseForward() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdBack() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseBack() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdLeft() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseLeft() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void holdRight() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseRight() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getBoundKey().getCode();
        FakeKeyboard.fakeOnKey(key, 0);
    }
}
