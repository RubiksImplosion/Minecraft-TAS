package io.github.rubiksimplosion.minecrafttas.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

//TODO: Health, Hunger, XP
public class PlayerState {
    public Vec3d position;
    public Vec3d velocity;
    public float pitch;
    public float yaw;
    public ItemStack[] inventory_main;
    public ItemStack[] inventory_armor;
    public ItemStack inventory_offhand;
    public ServerWorld world;

    public PlayerState(ServerPlayerEntity player) {
        this(player.getPos(),
                player.getVelocity(),
                player.getYaw(),
                player.getPitch(),
                player.getInventory(),
                player.getWorld());
    }

    public PlayerState(Vec3d position, Vec3d velocity, float yaw, float pitch, PlayerInventory inventory, ServerWorld world) {
        this.position = position;
        this.velocity = velocity;
        this.yaw = yaw;
        this.pitch = pitch;
        this.inventory_main = new ItemStack[inventory.main.size()];
        for (int i = 0; i < inventory_main.length; i++) {
            this.inventory_main[i] = inventory.main.get(i).copy();
        }
        this.inventory_armor = new ItemStack[inventory.armor.size()];
        for (int i = 0; i < inventory_armor.length; i++) {
            this.inventory_armor[i] = inventory.armor.get(i).copy();
        }
        this.inventory_offhand = inventory.offHand.get(0).copy();
        this.world = world;
    }
}
