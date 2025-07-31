package com.fabbe50.corgimod.world.entity.base;

import com.fabbe50.corgimod.world.entity.feature.IFeaturePack.HurtType;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePackEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class FeaturedEntity extends PathfinderMob implements IFeaturePackEntity<FeaturedEntity> {
    protected FeaturedEntity(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isAlive()) {
            this.getFeaturePack().onHandleParticles(this, this.getRandom());
            this.getFeaturePack().onTick(this);
        }
    }

    @Override
    protected @NotNull InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
        InteractionResult featureResult = this.getFeaturePack().onInteractWith(this, player, interactionHand);
        if (featureResult.consumesAction()) {
            return featureResult;
        }
        return super.mobInteract(player, interactionHand);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float f) {
        HurtType hurtType = this.getFeaturePack().onHurt(this, damageSource, f);
        if (hurtType != HurtType.PASS) {
            return hurtType == HurtType.HURT;
        }
        return super.hurt(damageSource, f);
    }

    @Override
    public void die(DamageSource damageSource) {
        this.getFeaturePack().onDeath(this, damageSource);
        super.die(damageSource);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean ignoreDropChance) {
        this.getFeaturePack().dropCustomDeathLoot(this, serverLevel, damageSource, ignoreDropChance);
        super.dropCustomDeathLoot(serverLevel, damageSource, ignoreDropChance);
    }

    @Override
    protected void pickUpItem(ItemEntity itemEntity) {
        this.getFeaturePack().onItemPickup(this, itemEntity);
    }
}
