package io.github.rubiksimplosion.minecrafttas.util;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.input.FakeMouse;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

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
}
