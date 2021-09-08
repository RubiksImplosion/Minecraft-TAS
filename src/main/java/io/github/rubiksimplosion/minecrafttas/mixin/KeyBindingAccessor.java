package io.github.rubiksimplosion.minecrafttas.mixin;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    @Accessor("KEYS_BY_ID")
    static Map<String, KeyBinding> getKeysById() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    InputUtil.Key getBoundKey();
}
