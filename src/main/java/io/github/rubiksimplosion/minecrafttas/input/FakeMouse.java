package io.github.rubiksimplosion.minecrafttas.input;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Environment(EnvType.CLIENT)
public class FakeMouse {
    public static void fakeMouseButton(int button, int action, int mods) {
        try {
            Method onMouseButton = Mouse.class.getDeclaredMethod("onMouseButton", long.class, int.class, int.class, int.class);
            onMouseButton.setAccessible(true);
            onMouseButton.invoke(MinecraftClient.getInstance().mouse, MinecraftClient.getInstance().getWindow().getHandle(), button, action, mods);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
