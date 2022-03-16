package io.github.rubiksimplosion.minecrafttas.savestate;

import io.github.rubiksimplosion.minecrafttas.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.io.*;

public class PlayerState {
    public NbtCompound serverPlayerState;
    public NbtCompound clientPlayerState;

    public PlayerState() {
    }

    public PlayerState(ClientPlayerEntity clientPlayer, ServerPlayerEntity serverPlayer) {
        init(clientPlayer, serverPlayer);
    }

    public void init(ClientPlayerEntity clientPlayer, ServerPlayerEntity serverPlayer) {
        clientPlayerState = clientPlayer.writeNbt(new NbtCompound());
        serverPlayerState = serverPlayer.writeNbt(new NbtCompound());
    }

    public void save(File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        NbtCompound combined = new NbtCompound();
        combined.put("Client", clientPlayerState);
        combined.put("Server", serverPlayerState);
        NbtIo.write(combined, file);
    }

    public void load(File file) throws IOException {
        NbtCompound state = NbtIo.read(file);

        ServerPlayerEntity serverDummy = InputUtil.getServerSidePlayerEntity();
        serverDummy = new ServerPlayerEntity(serverDummy.server, serverDummy.getWorld(), serverDummy.getGameProfile());
        serverDummy.readNbt(state.getCompound("Server"));

        ClientPlayerEntity clientDummy = InputUtil.getClientSidePlayerEntity();
        clientDummy = MinecraftClient.getInstance().interactionManager.createPlayer(
                clientDummy.clientWorld, clientDummy.getStatHandler(), clientDummy.getRecipeBook());
        clientDummy.readNbt(state.getCompound("Client"));

        init(clientDummy, serverDummy);
    }
}
