package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeKeyboard;
import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.mixin.KeyBindingAccessor;
import net.minecraft.client.MinecraftClient;

import static net.minecraft.client.util.InputUtil.*;

public class InputUtil {
    public static boolean autoJumpEnabled = false;

    public static int findXFromYaw(double newYaw) {
        float oldYaw = MinecraftClient.getInstance().player.getYaw();
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newYaw - oldYaw)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getX());
    }

    public static int findYFromPitch(double newPitch) {
        float oldPitch = MinecraftClient.getInstance().player.getPitch();
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newPitch - oldPitch)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getY());
    }

    public static void changeYaw(double yaw) {
        FakeMouse.fakeCursorMove(findXFromYaw(yaw), MinecraftClient.getInstance().mouse.getY());
    }

    public static void changePitch(double pitch) {
        FakeMouse.fakeCursorMove(MinecraftClient.getInstance().mouse.getX(), findYFromPitch(pitch));
    }

    public static void enableAutoJump() {
        autoJumpEnabled = true;
    }

    public static void disableAutoJump() {
        autoJumpEnabled = false;
        if (MinecraftClient.getInstance().options.keyJump.isPressed()) {
            InputUtil.releaseJump();
        }
    }

    public static void pressHotbar(int slot) {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.hotbar." + slot)).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseHotbar(int slot) {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.hotbar." + slot)).getBoundKey();
        pressKey(key, false);
    }

    public static void pressAttack() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.attack")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseAttack() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.attack")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressUse() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.use")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseUse() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.use")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressPickItem() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.pickItem")).getBoundKey();
        pressKey(key, true);
    }

    public static void releasePickItem() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.pickItem")).getBoundKey();
        pressKey(key, false);
    }



    public static void pressJump() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseJump() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.jump")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressSneak() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseSneak() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sneak")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressSprint() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseSprint() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.sprint")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressForward() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseForward() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.forward")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressBack() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseBack() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.back")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressLeft() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseLeft() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.left")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressRight() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseRight() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.right")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressDrop() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.drop")).getBoundKey();
        pressKey(key, true);
    }
    public static void releaseDrop() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.drop")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressInventory() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.inventory")).getBoundKey();
        pressKey(key, true);
    }
    public static void releaseInventory() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.inventory")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressSwapHand() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.swapOffhand")).getBoundKey();
        pressKey(key, true);
    }
    public static void releaseSwapHand() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.swapOffhand")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressChat() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.chat")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseChat() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.chat")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressCommand() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.command")).getBoundKey();
        pressKey(key, true);
    }

    public static void releaseCommand() {
        Key key = ((KeyBindingAccessor)KeyBindingAccessor.getKeysById().get("key.command")).getBoundKey();
        pressKey(key, false);
    }

    public static void pressEscape() {
        pressKey(fromTranslationKey("key.keyboard.escape"), true);
    }

    public static void releaseEscape() {
        pressKey(fromTranslationKey("key.keyboard.escape"), false);
    }

    private static void pressKey(Key key, boolean pressed) {
        updateModifiers(key, pressed);
        switch(key.getCategory()) {
            case KEYSYM:
                FakeKeyboard.fakeOnKey(key.getCode(), pressed ? 1 : 0);
                break;
            case MOUSE:
                FakeMouse.fakeMouseButton(key.getCode(), pressed ? 1 : 0);
                break;
        }
    }

    public static void updateModifiers(Key key, boolean pressed) {
        //shift
        if (key.getCode() == 340) {
            MinecraftTas.scriptManager.modifiers += pressed ? 1 : -1;
        }

        //control
        else if (key.getCode() == 341) {
            MinecraftTas.scriptManager.modifiers += pressed ? 2 : -2;
        }

        //alt
        else if (key.getCode() == 342) {
            MinecraftTas.scriptManager.modifiers += pressed ? 4 : -4;
        }
    }
}