package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.CorgiEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * Created by fabbe50 on 23/09/2016.
 */
public class EntityAIBegDecoy extends EntityAIBase {
    private final CorgiEntity theCorgi;
    private EntityPlayer thePlayer;
    private final World worldObject;
    private final float minPlayerDistance;
    private int timeoutCounter;

    public EntityAIBegDecoy(CorgiEntity corgi, float minDistance) {
        this.theCorgi = corgi;
        this.worldObject = corgi.world;
        this.minPlayerDistance = minDistance;
        this.setMutexBits(2);
    }

    public boolean shouldExecute() {
        this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theCorgi, (double)this.minPlayerDistance);
        return this.thePlayer != null && this.hasPlayerGotBoneInHand(this.thePlayer);
    }

    public boolean shouldContinueExecuting() {
        return this.thePlayer.isEntityAlive() && (!(this.theCorgi.getDistance(this.thePlayer) > (double) (this.minPlayerDistance * this.minPlayerDistance)) && (this.timeoutCounter > 0 && this.hasPlayerGotBoneInHand(this.thePlayer)));
    }

    public void startExecuting() {
        this.theCorgi.setBegging(true);
        this.timeoutCounter = 40 + this.theCorgi.getRNG().nextInt(40);
    }

    public void resetTask() {
        this.theCorgi.setBegging(false);
        this.thePlayer = null;
    }

    public void updateTask() {
        this.theCorgi.getLookHelper().setLookPosition(this.thePlayer.getPosition().getX(), this.thePlayer.getPosition().getY() + (double)this.thePlayer.getEyeHeight(), this.thePlayer.getPosition().getZ(), 10.0F, (float)this.theCorgi.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }
    
    private boolean hasPlayerGotBoneInHand(EntityPlayer player) {
        for (EnumHand hand : EnumHand.values()) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (!itemstack.isEmpty()) {
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
