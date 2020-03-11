package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.mixin.MouseAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class FakeMouse {
    public static void fakeMouseButton(int button, int action, int mods) {
        ((MouseAccessor)MinecraftClient.getInstance().mouse).callOnMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), button, action, mods);
    }
}
