package io.github.rubiksimplosion.minecrafttas.mixin;

import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Mouse.class)
public interface MouseAccessor {
    @Invoker
    void callOnMouseButton(long window, int button, int action, int mods);

    @Invoker
    void callOnMouseScroll(long window, double d, double e);

    @Invoker
    void callOnCursorPos(long window, double x, double y);
}
