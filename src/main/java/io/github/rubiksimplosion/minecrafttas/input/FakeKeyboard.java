package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.mixin.KeyboardAccessor;
import io.github.rubiksimplosion.minecrafttas.mixin.MouseAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class FakeKeyboard {
    /**
     *
     * @param key       the GLFW keycode
     * @param action    0 is released, 1 is pressed, 2 is held
     */
    public static void fakeOnKey(int key, int action) {
        MinecraftTas.scriptManager.fakeInput = true;
        if (MinecraftClient.getInstance().currentScreen == null) {
            MinecraftClient.getInstance().keyboard.onKey(
                    MinecraftClient.getInstance().getWindow().getHandle(),
                    key,
                    GLFW.glfwGetKeyScancode(key),
                    action,
                    MinecraftTas.scriptManager.modifiers);
        }
        else {
            MinecraftClient.getInstance().currentScreen.keyPressed(
                    key,
                    GLFW.glfwGetKeyScancode(key),
                    MinecraftTas.scriptManager.modifiers);
        }
        MinecraftTas.scriptManager.fakeInput = false;
    }
    public static void fakeOnChar(int key) {
        ((KeyboardAccessor) MinecraftClient.getInstance().keyboard).callOnChar(
                MinecraftClient.getInstance().getWindow().getHandle(),
                key,
                MinecraftTas.scriptManager.modifiers);
    }
}
