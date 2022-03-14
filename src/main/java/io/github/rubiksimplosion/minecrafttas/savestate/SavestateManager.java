package io.github.rubiksimplosion.minecrafttas.savestate;

import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import io.github.rubiksimplosion.minecrafttas.util.PlayerState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SavestateManager {
    public List<PlayerState> softSavestates = new ArrayList<>();

    public void addSoftSavestate(PlayerState state) {
        if (MinecraftClient.getInstance().isInSingleplayer()) {
            softSavestates.add(state);
            InputUtil.sendFeedback(new TranslatableText("savestate.create", softSavestates.size()));
        } else {
            InputUtil.sendFeedback(new TranslatableText("error.savestate.multiplayer"));
        }
    }

    //TODO: make loading savestate in different dimensions consistent
    //TODO: make "hard" savestates
    public void loadSoftSavestate() {
        if (softSavestates.size() > 0) {
            PlayerState state = softSavestates.get(softSavestates.size()-1);
            ServerPlayerEntity player = InputUtil.getServerSidePlayerEntity();
            player.teleport(state.world, state.position.x, state.position.y, state.position.z, state.yaw, state.pitch);
            player.setVelocity(state.velocity);
            player.setVelocityClient(state.velocity.x, state.velocity.y, state.velocity.z);
            player.velocityModified = true;
            player.velocityDirty = true;

            // inventory = 0..35
            for (int i = 0; i < state.inventory_main.length; i++) {
                player.getStackReference(i).set(state.inventory_main[i].copy());
            }
            // head,chest,leg,feet = 103,102,101,100
            for (int i = 0; i < state.inventory_armor.length; i++) {
                player.getStackReference(100+i).set(state.inventory_armor[i].copy());
            }
            //offhand = 99
            player.getStackReference(99).set(state.inventory_offhand.copy());
            player.currentScreenHandler.sendContentUpdates();

            InputUtil.sendFeedback(new TranslatableText("savestate.load", softSavestates.size()));
        } else {
            InputUtil.sendError(new TranslatableText("error.savestate.load.empty"));
        }
    }

    public void removeSoftSavestate() {
        if (softSavestates.size() > 0) {
            softSavestates.remove(softSavestates.size() - 1);
            InputUtil.sendFeedback(new TranslatableText("savestate.remove", softSavestates.size() + 1));
        } else {
            InputUtil.sendError(new TranslatableText("error.savestate.remove.empty"));
        }
    }
}
