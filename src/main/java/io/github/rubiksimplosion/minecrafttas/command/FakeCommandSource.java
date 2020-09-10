package io.github.rubiksimplosion.minecrafttas.command;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;

import java.util.Collection;
import java.util.stream.Collectors;

@Environment(EnvType.CLIENT)
public class FakeCommandSource extends ServerCommandSource {
    public FakeCommandSource(ClientPlayerEntity player) {
        super(player, player.getPos(), player.getRotationClient(), null, 0, player.getEntityName(), player.getName(), null, player);
    }

    @Override
    public Collection<String> getPlayerNames() {
        return MinecraftClient.getInstance().getNetworkHandler().getPlayerList()
                .stream().map(e -> e.getProfile().getName()).collect(Collectors.toList());
    }
}
