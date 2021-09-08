package io.github.rubiksimplosion.minecrafttas.util;

import net.minecraft.util.math.Vec3d;

public class PlayerState {
    public Vec3d position;
    public Vec3d velocity;
    public float pitch;
    public float yaw;

    public PlayerState(Vec3d position, Vec3d velocity, float yaw, float pitch) {
        this.position = position;
        this.velocity = velocity;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
