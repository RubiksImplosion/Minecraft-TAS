package io.github.rubiksimplosion.minecrafttas.mixin;

import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Keyboard.class)
public interface KeyboardAccessor {
    @Invoker
    void callOnChar(long window, int i, int j);
}
