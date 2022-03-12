package io.github.rubiksimplosion.minecrafttas.mixin;

import io.github.rubiksimplosion.minecrafttas.MinecraftTas;
import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTickStart(CallbackInfo ci) {
        if (MinecraftTas.scriptManager.executing) {
            //updates mouse without actually moving it, allows for yaw/pitch changes after closing inventories
            InputUtil.updateCursorPos();
            MinecraftTas.scriptManager.setupTick();
            if (InputUtil.autoJumpEnabled) {
                if (InputUtil.getClientSidePlayerEntity().isOnGround() && !MinecraftClient.getInstance().options.jumpKey.isPressed()) {
                    InputUtil.pressJump();
                } else if (MinecraftClient.getInstance().options.jumpKey.isPressed()) {
                    InputUtil.releaseJump();
                }
            }
            MinecraftTas.scriptManager.executeTick();
        }
    }

//    @Inject(method = "tick", at = @At("RETURN"))
//    private void onTickEnd(CallbackInfo ci) {
//        if (MinecraftTas.scriptManager.executing) {
////            Synchronizer.clientExecuting = false;
//        }
//        long end = Util.getMeasuringTimeMs();
//        System.out.print("Client: ");
//        System.out.println(end - Synchronizer.clientTickStart);
//    }

//    @Inject(method = "render", at = @At("HEAD"))
//    private void printDelay(boolean tick, CallbackInfo ci) {
////        Synchronizer.printDelay();
//    }
}
