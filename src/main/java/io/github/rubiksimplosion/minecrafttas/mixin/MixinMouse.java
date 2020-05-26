package io.github.rubiksimplosion.minecrafttas.mixin;

import io.github.rubiksimplosion.minecrafttas.input.InputManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MixinMouse {

    @Inject(method="onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            if (InputManager.getInstance().executing) {
                ci.cancel();
            }
//            MinecraftClient.getInstance().player.addChatMessage(new TranslatableText("mouse button: button: %s, action: %s, mods: %s", button, action, mods), false);
        }
    }

    @Inject(method="onMouseScroll", at = @At("HEAD"), cancellable = true)
    private void onMouseScroll(long window, double d, double e, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            if (InputManager.getInstance().executing) {
                ci.cancel();
            }
//            MinecraftClient.getInstance().player.addChatMessage(new TranslatableText("mouse scroll: d: %s, e: %s", d, e), false);
        }
    }


    @Inject(method = "onCursorPos", at = @At("HEAD"), cancellable = true)
    private void onOnCursorPos(long window, double x, double y, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            if (InputManager.getInstance().executing) {
                ci.cancel();
            }
//            MinecraftClient.getInstance().player.addChatMessage(new TranslatableText("cursor pos: x: %s, y: %s", x, y), false);
        }
    }
    @Inject(method = "updateMouse", at = @At("HEAD"), cancellable = true)
    private void onUpdateMouse(CallbackInfo ci) {
//        if (InputManager.getInstance().executing) {
//            ci.cancel();
//        }
    }
}
