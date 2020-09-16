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
    public static boolean autoJumpEnabled = false;

    public static void enableAutoJump() {
        autoJumpEnabled = true;
    }

    public static void disableAutoJump() {
        autoJumpEnabled = false;
    }

    public static void pressJump() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseJump() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressSneak() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseSneak() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressSprint() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseSprint() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressForward() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseForward() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressBack() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseBack() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressLeft() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseLeft() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressRight() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }

    public static void releaseRight() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressDrop() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.drop")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }
    public static void releaseDrop() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.drop")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressInventory() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.inventory")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }
    public static void releaseInventory() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.inventory")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void pressSwapHand() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.swapOffhand")).getBoundKey().getCode();
        updateModifiers(key, true);
        FakeKeyboard.fakeOnKey(key, 1);
    }
    public static void releaseSwapHand() {
        int key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.swapOffhand")).getBoundKey().getCode();
        updateModifiers(key, false);
        FakeKeyboard.fakeOnKey(key, 0);
    }

    public static void updateModifiers(int key, boolean pressed) {
        //shift
        if (key == 340 && pressed) {
            MinecraftTas.scriptManager.modifiers += 1;
        }
        else if (key == 340) {
            MinecraftTas.scriptManager.modifiers -= 1;
        }

        //control
        else if (key == 341 && pressed) {
            MinecraftTas.scriptManager.modifiers += 2;
        }
        else if (key == 341) {
            MinecraftTas.scriptManager.modifiers -= 2;
        }

        //alt
        else if (key == 342 && pressed) {
            MinecraftTas.scriptManager.modifiers += 4;
        }
        else if (key == 342) {
            MinecraftTas.scriptManager.modifiers -= 4;
        }
    }
}
