package io.github.rubiksimplosion.minecrafttas.mixin;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Keyboard.class)
public class MixinKeyboard {

    @Inject(method = "onKey", at = @At("HEAD"), cancellable = true)
    private void onOnKey(long window, int key, int scancode, int i, int j, CallbackInfo ci) {
//        if (MinecraftTas.scriptManager.executing) {
//            if (!MinecraftTas.scriptManager.fakeInput) {
//                ci.cancel();
//            }
//        }
    }

    @Inject(method = "onChar", at = @At("HEAD"))
    private void onOnChar(long window, int i, int j, CallbackInfo ci) {
//        if (MinecraftTas.scriptManager.executing) {
//            if (!MinecraftTas.scriptManager.fakeInput) {
//                ci.cancel();
//            }
//        }
    }
}
