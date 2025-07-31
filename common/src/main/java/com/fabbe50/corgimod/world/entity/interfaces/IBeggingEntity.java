package com.fabbe50.corgimod.world.entity.interfaces;

import net.minecraft.world.entity.player.Player;

public interface IBeggingEntity {
    boolean playerHoldingInteresting(Player player);

    void setIsInterested(boolean interested);

    boolean isInterested();
}
