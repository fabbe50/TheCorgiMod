package com.fabbe50.corgis.entities.ai;

import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import net.minecraft.entity.EntityPredicate;
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
    private final CorgiEntity corgi;
    private PlayerEntity player;
    private final World worldObject;
    private final float minPlayerDistance;
    private int timeoutCounter;
    private final EntityPredicate predicate;

    public BegDecoyGoal(CorgiEntity corgi, float minDistance) {
        this.corgi = corgi;
        this.worldObject = corgi.world;
        this.minPlayerDistance = minDistance;
        this.predicate = (new EntityPredicate()).setDistance((double)minDistance).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks();
        this.setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
    }

    public boolean shouldExecute() {
        this.player = this.worldObject.getClosestPlayer(this.predicate, this.corgi);
        return this.player != null && this.hasTemptationItemInHand(this.player);
    }

    public boolean shouldContinueExecuting() {
        if (!this.player.isAlive()) {
            return false;
        } else if (this.corgi.getDistanceSq(this.player) > (double)(this.minPlayerDistance * this.minPlayerDistance)) {
            return false;
        } else {
            return this.timeoutCounter > 0 && this.hasTemptationItemInHand(this.player);
        }
    }

    public void startExecuting() {
        this.corgi.setBegging(true);
        this.timeoutCounter = 40 + this.corgi.getRNG().nextInt(40);
    }

    public void resetTask() {
        this.corgi.setBegging(false);
        this.player = null;
    }

    public void tick() {
        this.corgi.getLookController().setLookPosition(this.player.getPosX(), this.player.getPosYEye(), this.player.getPosZ(), 10.0F, (float)this.corgi.getVerticalFaceSpeed());
        --this.timeoutCounter;
    }
    
    private boolean hasTemptationItemInHand(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (itemstack.isEmpty()) {
                if (this.corgi.isTamed() && itemstack.getItem() == Items.BAKED_POTATO) {
                    return true;
                }

                if (this.corgi.isBreedingItem(itemstack)) {
                    return true;
                }
            }
        }

        return false;
    }
}
