package io.github.rubiksimplosion.minecrafttas.savestate;

import io.github.rubiksimplosion.minecrafttas.util.PlayerState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;

public class SavestateManager {
    public List<PlayerState> softSavestates = new ArrayList<>();

    public void addSoftSavestate(PlayerState state) {
        if (MinecraftClient.getInstance().isInSingleplayer()) {
            softSavestates.add(state);
            MinecraftClient.getInstance().player.sendMessage(
                    new LiteralText(String.format("Save-state #%s created.", softSavestates.size())), false);
        } else {
            MinecraftClient.getInstance().player.sendMessage(
                    new LiteralText("Cannot create save-states in multiplayer"), false);
        }
    }

    public void loadSoftSavestate() {
        if (softSavestates.size() > 0) {
            PlayerState state = softSavestates.get(softSavestates.size()-1);
            PlayerEntity player = MinecraftClient.getInstance().player;
            player.setPosition(state.position);
            player.setVelocity(state.velocity);
            player.setPitch(state.pitch);
            player.setYaw(state.yaw);
            MinecraftClient.getInstance().player.sendMessage(
                    new LiteralText(String.format("Save-state #%s loaded.", softSavestates.size())), false);
        } else {
            MinecraftClient.getInstance().player.sendMessage(
                    new LiteralText(String.format("No save-states to load.", softSavestates.size() + 1)), false);
        }
    }

    public void removeSoftSavestate() {
        if (softSavestates.size() > 0) {
            softSavestates.remove(softSavestates.size() - 1);
            MinecraftClient.getInstance().player.sendMessage(
                    new LiteralText(String.format("Save-state #%s deleted.", softSavestates.size() + 1)), false);
        } else {
            MinecraftClient.getInstance().player.sendMessage(
                    new LiteralText(String.format("No save-states to delete.", softSavestates.size() + 1)), false);
        }
    }

}
