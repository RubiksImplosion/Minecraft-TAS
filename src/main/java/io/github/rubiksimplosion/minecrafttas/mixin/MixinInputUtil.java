package io.github.rubiksimplosion.minecrafttas.mixin;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InputUtil.class)
public class MixinInputUtil {
    @Inject(method = "isKeyPressed", at = @At("HEAD"), cancellable = true)
    private static void onIsKeyPressed(long handle, int code, CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftTas.scriptManager.executing) {
            if (code == GLFW.GLFW_KEY_LEFT_SHIFT) {
                cir.setReturnValue(MinecraftTas.scriptManager.modifiers % 2 == 1);
            }
        }
    }
}
