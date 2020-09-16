package io.github.rubiksimplosion.minecrafttas.mixin;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.util.KeyboardUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "tick", at = @At("HEAD"))
    private void executeTickHead(CallbackInfo ci) {
        if (MinecraftTas.scriptManager.executing) {
            MinecraftTas.scriptManager.setupTick();
            if (KeyboardUtil.autoJumpEnabled) {
                if (MinecraftClient.getInstance().player.isOnGround() && !MinecraftClient.getInstance().options.keyJump.isPressed()) {
                    KeyboardUtil.pressJump();
                } else if (MinecraftClient.getInstance().options.keyJump.isPressed()) {
                    KeyboardUtil.releaseJump();
                }
            }
            MinecraftTas.scriptManager.executeTick();
        }
    }
}
