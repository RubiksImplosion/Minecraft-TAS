package io.github.rubiksimplosion.minecrafttas.savestate;

import io.github.rubiksimplosion.minecrafttas.mixin.MinecraftClientAccessor;
import io.github.rubiksimplosion.minecrafttas.mixin.MinecraftServerAccessor;
import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.level.storage.LevelStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavestateManager {
    public File saveStatesDirectory;
    private final List<PlayerState> softSavestateStack = new ArrayList<>();   // created from key bind
    private final Map<String, PlayerState> namedSoftSavestates = new HashMap<>();   // loaded from file

    public void addQuickSoftSavestate() {
        if (MinecraftClient.getInstance().isInSingleplayer()) {
            PlayerState currentState = new PlayerState(InputUtil.getClientSidePlayerEntity(), InputUtil.getServerSidePlayerEntity());
            softSavestateStack.add(currentState);
            InputUtil.sendFeedback(new TranslatableText("savestate.create", softSavestateStack.size()));
        } else {
            InputUtil.sendFeedback(new TranslatableText("error.savestate.multiplayer"));
        }
    }

    public void loadMostRecentSavestate() {
        if (softSavestateStack.size() > 0) {
            PlayerState state = softSavestateStack.get(softSavestateStack.size() - 1);
            ServerPlayerEntity serverPlayer = InputUtil.getServerSidePlayerEntity();
            ClientPlayerEntity clientPlayer = InputUtil.getClientSidePlayerEntity();
            serverPlayer.readNbt(state.serverPlayerState);
            clientPlayer.readNbt(state.serverPlayerState);
            InputUtil.sendFeedback(new TranslatableText("savestate.load.recent"));
        } else {
            InputUtil.sendError(new TranslatableText("error.savestate.load.empty"));
        }
    }

    public void removeMostRecentSavestate() {
        if (softSavestateStack.size() > 0) {
            softSavestateStack.remove(softSavestateStack.size() - 1);
            InputUtil.sendFeedback(new TranslatableText("savestate.remove.recent"));
        } else {
            InputUtil.sendError(new TranslatableText("error.savestate.remove.empty"));
        }
    }

   // saves most recent save-state to file
    public void saveSoftSavestateToFile(String fileName) {
        try {
            if (softSavestateStack.size() > 0) {
                PlayerState state = softSavestateStack.get(softSavestateStack.size() - 1);
                state.save(getSavestateFile(fileName));
                InputUtil.sendFeedback(new TranslatableText("savestate.file.save", fileName));
            } else {
                InputUtil.sendError(new TranslatableText("error.savestate.file.save.empty"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadNamedSoftSavestate(String name) {
        try {
            PlayerState state = namedSoftSavestates.get(name);
            if (state == null) {
                InputUtil.sendError(new TranslatableText("error.savestate.file.load.notExists", name));
                return;
            }
            ServerPlayerEntity serverPlayer = InputUtil.getServerSidePlayerEntity();
            ClientPlayerEntity clientPlayer = InputUtil.getClientSidePlayerEntity();
            serverPlayer.readNbt(state.serverPlayerState);
            clientPlayer.readNbt(state.serverPlayerState);
            softSavestateStack.add(state);
            InputUtil.sendFeedback(new TranslatableText("savestate.file.load", name));
        }
        catch (Exception e) {
            InputUtil.sendError(new TranslatableText("error.savestate.load"));
        }
    }

    public void loadSoftSavetateFromFile(String fileName) {
        try {
            File saveStateFile = getSavestateFile(fileName);
            if (saveStateFile.exists()) {
                PlayerState state = new PlayerState();
                state.load(getSavestateFile(fileName));
                namedSoftSavestates.put(fileName, state);
                softSavestateStack.add(state);
                ServerPlayerEntity serverPlayer = InputUtil.getServerSidePlayerEntity();
                ClientPlayerEntity clientPlayer = InputUtil.getClientSidePlayerEntity();
                serverPlayer.readNbt(state.serverPlayerState);
                clientPlayer.readNbt(state.serverPlayerState);
                InputUtil.sendFeedback(new TranslatableText("savestate.file.load", fileName));
            }
            else {
                InputUtil.sendError(new TranslatableText("error.savestate.file.notExists", fileName+".savestate"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getSavestateFile(String fileName) {
        String filePath = saveStatesDirectory.getAbsolutePath() + System.getProperty("file.separator") + fileName + ".savestate";
        return new File(filePath);
    }
    // find a better way to get the world name cus this is nasty
    public void initializeSavestateDirectory() {
        LevelStorage.Session session = ((MinecraftServerAccessor)((((MinecraftClientAccessor) MinecraftClient.getInstance()).getServer()))).getSession();
        String worldName = session.getDirectoryName();
        File saveStatesDirectory = new File(
                MinecraftClient.getInstance().runDirectory.getPath()
                        + System.getProperty("file.separator")
                        + "saves"
                        + System.getProperty("file.separator")
                        + worldName
                        + System.getProperty("file.separator")
                        + "savestates");
        if (!saveStatesDirectory.exists()) {
            saveStatesDirectory.mkdir();
        }
        this.saveStatesDirectory = saveStatesDirectory;
    }
}
