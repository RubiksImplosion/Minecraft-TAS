package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class MouseUtil {
    public static int findXFromYaw(double newYaw) {
        float oldYaw = MinecraftClient.getInstance().player.yaw;
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newYaw - oldYaw)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getX());
    }

    public static int findYFromPitch(double newPitch) {
        float oldPitch = MinecraftClient.getInstance().player.pitch;
        double sens = MinecraftClient.getInstance().options.mouseSensitivity * 0.6 + 0.2;
        return (int)(((float)newPitch - oldPitch)/(0.15*8*sens*sens*sens) + MinecraftClient.getInstance().mouse.getY());
    }

    public static void changeYaw(double yaw) {
        FakeMouse.fakeCursorMove(findXFromYaw(yaw), MinecraftClient.getInstance().mouse.getY());
    }

    public static void changePitch(double pitch) {
        FakeMouse.fakeCursorMove(MinecraftClient.getInstance().mouse.getX(), findYFromPitch(pitch));
    }

    public static void clickAttack() {
        FakeMouse.fakeMouseButton(0, 1);
        FakeMouse.fakeMouseButton(0, 0);
    }

    public static void pressAttack() {
        FakeMouse.fakeMouseButton(0, 1);
    }

    public static void releaseAttack() {
        FakeMouse.fakeMouseButton(0, 0);
    }

    public static void clickUse() {
        FakeMouse.fakeMouseButton(1, 1);
        FakeMouse.fakeMouseButton(1, 0);
    }

    public static void pressUse() {
        FakeMouse.fakeMouseButton(1, 1);
    }

    public static void releaseUse() {
        FakeMouse.fakeMouseButton(1, 0);
    }

    public static void clickPickItem() {
        FakeMouse.fakeMouseButton(2, 1);
        FakeMouse.fakeMouseButton(2, 0);
    }

    public static void pressPickItem() {
        FakeMouse.fakeMouseButton(2, 1);
    }

    public static void releasePickItem() {
        FakeMouse.fakeMouseButton(2, 0);
    }

    public static void scrollUp(double amount) {
        FakeMouse.fakeMouseScroll(amount);
    }

    public static void scrollDown(double amount) {
        FakeMouse.fakeMouseScroll(-amount);
    }
}
