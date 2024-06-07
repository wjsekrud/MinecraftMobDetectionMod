package com.AIGame.mobdetectionmod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;

public class DummyPlayerEntity extends ClientPlayerEntity{

    public DummyPlayerEntity(ClientWorld world, ClientPlayerEntity player) {
        super(
                Minecraft.getInstance(),
                world,
                player.connection,
                player.getStats(),
                player.getRecipeBook(),
                false,
                false
        );
        this.copyPlayerState(player);
    }

    private void copyPlayerState(ClientPlayerEntity player) {
        this.setPos(player.getX(), player.getY(), player.getZ());
        this.yRot = player.yRot;
        this.xRot = player.xRot;
        this.yHeadRot = player.yHeadRot;
        this.yBodyRot = player.yBodyRot;
    }

}
