package io.github.rubiksimplosion.minecrafttas.mixin;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MixinMouse {

    @Inject(method="onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (MinecraftTas.scriptManager.executing) {
            if (!MinecraftTas.scriptManager.fakeInput) {
                ci.cancel();
            }
        }
    }

    @Inject(method="onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double d, double e, CallbackInfo ci) {
        if (MinecraftTas.scriptManager.executing) {
            if (!MinecraftTas.scriptManager.fakeInput) {
                ci.cancel();
            }
        }
    }


    @Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
    private void onOnCursorPos(long window, double x, double y, CallbackInfo ci) {
//        System.out.println("x: " + x + ", y: " + y);
        if (MinecraftTas.scriptManager.executing) {
            if (!MinecraftTas.scriptManager.fakeInput) {
                ci.cancel();
            }
        }
    }

//    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
//    private void onUpdateMouse(CallbackInfo ci) {
////        if (InputManager.getInstance().executing) {
////            ci.cancel();
////        }
//    }
}
