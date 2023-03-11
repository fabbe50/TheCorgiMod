package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.ability.IAbility;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class RadioactiveCorgi extends Corgi implements IAbility {
    public int uraniumDropTime = this.random.nextInt(6000) + 6000;

    public RadioactiveCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void runAbilityWhileFed() {
        if (!this.level.isClientSide && this.isAlive() && !this.isBaby() && --this.uraniumDropTime <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.spawnAtLocation(ItemRegistry.URANIUM.get());
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.uraniumDropTime = this.random.nextInt(6000) + 6000;
            this.setHasBeenFed(false);
        }
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES))
            return Component.literal(Corgis.RADIOACTIVE.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (entity instanceof LivingEntity) {
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.POISON, 10, 3));
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.HUNGER, 10, 3));
            ((LivingEntity) entity).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10, 3));
        }
        return super.doHurtTarget(entity);
    }
}
