package io.github.rubiksimplosion.minecrafttas.input;

import io.github.rubiksimplosion.minecrafttas.mixin.MouseAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class FakeMouse {
    /**
     * Button: 0 is LMB, 1 is RMB, 2 is MMB
     * Action: 1 is press, 0 is release
     * Mods: 1 is shift, 2 is ctrl, 3 is shift and ctrl
     * */
    public static void fakeMouseButton(int button, int action, int mods) {
        ((MouseAccessor)MinecraftClient.getInstance().mouse).callOnMouseButton(MinecraftClient.getInstance().getWindow().getHandle(), button, action, mods);
    }

    /** MWHEELUP is positive dir, MWHEELDOWN is negative dir */
    public static void fakeMouseScroll(double direction) {
        ((MouseAccessor)MinecraftClient.getInstance().mouse).callOnMouseScroll(MinecraftClient.getInstance().getWindow().getHandle(), 0, direction);
    }

    public static void fakeCursorMove(double x, double y) {
        ((MouseAccessor)MinecraftClient.getInstance().mouse).callOnCursorPos(MinecraftClient.getInstance().getWindow().getHandle(), x, y);
    }
}
