package io.github.rubiksimplosion.minecrafttas.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Mouse.class)
public class MixinMouse {

    @Inject(method="onMouseButton", at = @At("HEAD"), cancellable = true)
    private void onOnMouseButton(long window, int button, int action, int mods, CallbackInfo ci) {
        if(MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.addChatMessage(new LiteralText("canceled button input"), false);
            ci.cancel();
        }
    }
}
