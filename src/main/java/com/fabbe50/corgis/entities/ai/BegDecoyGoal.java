package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.CorgiEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import java.util.EnumSet;

/**
 * Created by fabbe50 on 23/09/2016.
 */
public class BegDecoyGoal extends Goal {
    private final CorgiEntity theCorgi;
    private PlayerEntity thePlayer;
    private final World worldObject;
    private final float minPlayerDistance;
    private int timeoutCounter;

    public BegDecoyGoal(CorgiEntity corgi, float minDistance) {
        this.theCorgi = corgi;
        this.worldObject = corgi.world;
        this.minPlayerDistance = minDistance;
        this.setMutexFlags(EnumSet.of(Flag.LOOK));
    }

    public boolean shouldExecute() {
        this.thePlayer = this.worldObject.getClosestPlayer(this.theCorgi, (double)this.minPlayerDistance);
        return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
    }

    public boolean shouldContinueExecuting() {
        return !this.thePlayer.isAlive() ? false : (this.theCorgi.getDistance(this.thePlayer) > (double)(this.minPlayerDistance * this.minPlayerDistance) ? false : this.timeoutCounter > 0 && this.hasPlayerGotBoneInHand(this.thePlayer));
    }

    public void startExecuting() {
        this.theCorgi.setBegging(true);
        this.timeoutCounter = 40 + this.theCorgi.getRNG().nextInt(40);
    }

    public void resetTask() {
        this.theCorgi.setBegging(false);
        this.thePlayer = null;
    }

    public void tick() {
        this.theCorgi.getLookController().setLookPosition(this.thePlayer.getPosition().getX(), this.thePlayer.getPosition().getY() + (double)this.thePlayer.getEyeHeight(), this.thePlayer.getPosition().getZ(), 10.0F, (float)this.theCorgi.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }
    
    private boolean hasPlayerGotBoneInHand(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack != null) {
                if (this.theCorgi.isTamed() && itemstack.getItem() == Items.BONE) {
                    return true;
                }

                if (this.theCorgi.isBreedingItem(itemstack)) {
                    return true;
                }
            }
        }

        return false;
    }
}
