package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeKeyboard;
import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import io.github.rubiksimplosion.minecrafttas.mixin.HandledScreenAccessor;
import io.github.rubiksimplosion.minecrafttas.mixin.KeyBindingAccessor;
import io.github.rubiksimplosion.minecrafttas.mixin.MouseAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.BaseText;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import org.lwjgl.glfw.GLFW;

import static net.minecraft.client.util.InputUtil.*;

public class InputUtil {
    public static boolean autoJumpEnabled = false;

    public static ServerPlayerEntity getServerSidePlayerEntity() {
        return MinecraftClient.getInstance().getServer().getPlayerManager().getPlayerList().get(0);
    }

    public static ClientPlayerEntity getClientSidePlayerEntity() {
        return MinecraftClient.getInstance().player;
    }

    public static void sendError(BaseText text) {
        getClientSidePlayerEntity().sendSystemMessage(text.formatted(Formatting.RED), Util.NIL_UUID);
    }

    public static void sendFeedback(BaseText text) {
        getClientSidePlayerEntity().sendSystemMessage(text, Util.NIL_UUID);
    }
    //derived from Mouse.updateMouse();
    public static int findXFromYaw(double newYaw) {
        float oldYaw = InputUtil.getClientSidePlayerEntity().getYaw();
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newYaw - oldYaw)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getX());
    }

    //derived from Mouse.updateMouse();
    public static int findYFromPitch(double newPitch) {
        float oldPitch = InputUtil.getClientSidePlayerEntity().getPitch();
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newPitch - oldPitch)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getY());
    }

    public static void changeYaw(double yaw) {
        FakeMouse.fakeCursorMove(findXFromYaw(yaw), MinecraftClient.getInstance().mouse.getY());
    }

    public static void changePitch(double pitch) {
        FakeMouse.fakeCursorMove(MinecraftClient.getInstance().mouse.getX(), findYFromPitch(pitch));
    }

    public static void updateCursorPos() {
        FakeMouse.fakeCursorMove(MinecraftClient.getInstance().mouse.getX(), MinecraftClient.getInstance().mouse.getY());
    }

    public static void moveMouseToSlot(int slotId) {
        try {
            Slot slot = ((HandledScreen) MinecraftClient.getInstance().currentScreen).getScreenHandler().getSlot(slotId);

            int x = ((HandledScreenAccessor) MinecraftClient.getInstance().currentScreen).getX() + slot.x;
            x = x * MinecraftClient.getInstance().getWindow().getWidth() / MinecraftClient.getInstance().getWindow().getScaledWidth();

            int y = ((HandledScreenAccessor) MinecraftClient.getInstance().currentScreen).getY() + slot.y;
            y = y * MinecraftClient.getInstance().getWindow().getHeight() / MinecraftClient.getInstance().getWindow().getScaledHeight();

            FakeMouse.fakeCursorMove(x, y);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    // moves cursor to a creative tabs
    public static void moveMouseToTab(ItemGroup group) {
        if (MinecraftClient.getInstance().currentScreen instanceof CreativeInventoryScreen) {
            int i = group.getColumn();
            int x = 28 * i;
            int y = 0;
            int backgroundWidth = 195; // mystery constant yoinked from CreativeInventoryScreen
            int backgroundHeight = 136; // mystery constant yoinked from CreativeInventoryScreen
            if (group.isSpecial()) {
                x = backgroundWidth - 28 * (6 - i) + 2;
            } else if (i > 0) {
                x += i;
            }
            y = group.isTopRow() ? (y -= 32) : (y += backgroundHeight);

            x++;    // make sure coordinates inside tab
            x += ((HandledScreenAccessor) MinecraftClient.getInstance().currentScreen).getX();
            x *= (double) MinecraftClient.getInstance().getWindow().getWidth() / MinecraftClient.getInstance().getWindow().getScaledWidth();

            y++;
            y += ((HandledScreenAccessor) MinecraftClient.getInstance().currentScreen).getY();
            y *= (double) MinecraftClient.getInstance().getWindow().getHeight() / MinecraftClient.getInstance().getWindow().getScaledHeight();

            FakeMouse.fakeCursorMove(x, y);
        } else {
            // error
        }
    }

    public static void pressMouseButton(int button) {
        FakeMouse.fakeMouseButton(button, 1);
    }

    public static void releaseMouseButton(int button) {
        FakeMouse.fakeMouseButton(button, 0);
    }

    public static void pressLeftShift() {
        if (MinecraftTas.scriptManager.modifiers % 2 == 0) {
            MinecraftTas.scriptManager.modifiers += 1;
            FakeKeyboard.fakeOnKey(GLFW.GLFW_KEY_LEFT_SHIFT, 1);
        }
    }
    public static void releaseLeftShift() {
        if (MinecraftTas.scriptManager.modifiers % 2 == 1) {
            MinecraftTas.scriptManager.modifiers -= 1;
            FakeKeyboard.fakeOnKey(GLFW.GLFW_KEY_LEFT_SHIFT, 0);
        }
    }

    public static void pressLeftControl() {
        FakeKeyboard.fakeOnKey(GLFW.GLFW_KEY_LEFT_CONTROL, 1);
    }
    public static void releaseLeftControl() {
        FakeKeyboard.fakeOnKey(GLFW.GLFW_KEY_LEFT_CONTROL, 0);
    }

    public static void enableAutoJump() {
        autoJumpEnabled = true;
    }

    public static void disableAutoJump() {
        autoJumpEnabled = false;
        if (MinecraftClient.getInstance().options.jumpKey.isPressed()) {
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

    public static void pressEnter() {
        FakeKeyboard.fakeOnKey(GLFW.GLFW_KEY_ENTER, 1);
    }

    public static void releaseEnter() {
        FakeKeyboard.fakeOnKey(GLFW.GLFW_KEY_ENTER, 0);
    }
    public static void pressEscape() {
        pressKey(fromTranslationKey("key.keyboard.escape"), true);
    }

    public static void releaseEscape() {
        pressKey(fromTranslationKey("key.keyboard.escape"), false);
    }

    public static void typeLiteralChar(char key) {
        FakeKeyboard.fakeOnChar(key);
    }

    private static void pressKey(Key key, boolean pressed) {
        updateModifiers(key, pressed);
        switch (key.getCategory()) {
            case KEYSYM -> FakeKeyboard.fakeOnKey(key.getCode(), pressed ? 1 : 0);
            case MOUSE -> FakeMouse.fakeMouseButton(key.getCode(), pressed ? 1 : 0);
        }
    }

    public static void updateModifiers(Key key, boolean pressed) {
        if (key.getCode() == GLFW.GLFW_KEY_LEFT_SHIFT) {
            MinecraftTas.scriptManager.modifiers += pressed ? 1 : -1;
        }
        else if (key.getCode() == GLFW.GLFW_KEY_LEFT_CONTROL) {
            MinecraftTas.scriptManager.modifiers += pressed ? 2 : -2;
        }
        else if (key.getCode() == GLFW.GLFW_KEY_LEFT_ALT) {
            MinecraftTas.scriptManager.modifiers += pressed ? 4 : -4;
        }
    }
}