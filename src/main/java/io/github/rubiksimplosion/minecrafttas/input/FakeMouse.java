package io.github.rubiksimplosion.minecrafttas.input;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

public class FakeMouse extends Mouse {
    public FakeMouse(MinecraftClient client) {
        super(client);
    }
}
