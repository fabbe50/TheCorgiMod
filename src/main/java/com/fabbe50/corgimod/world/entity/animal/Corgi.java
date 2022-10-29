package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class Corgi extends Wolf {
    public Corgi(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.MAX_HEALTH, 12.0D).add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20.0D);
            this.setHealth(20.0F);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(12.0D);
        }

        Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(8.0D);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.NORMAL.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    public Wolf getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob ageableMob) {
        EntityType<Corgi> corgi = (EntityType<Corgi>) Corgis.getTameWolfBasedCorgis().get(new Random().nextInt(Corgis.getNonHostileCorgis().size())).getCorgiType();
        Corgi corgi1 = corgi.create(level);
        UUID uuid1 = this.getOwnerUUID();
        if (corgi1 != null && uuid1 != null) {
            corgi1.setOwnerUUID(uuid1);
            corgi1.setTame(true);
        }
        return corgi1;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof Corgi corgi)) {
            return false;
        } else {
            if (!corgi.isTame()) {
                return false;
            } else if (corgi.isInSittingPose()) {
                return false;
            } else {
                return this.isInLove() && corgi.isInLove();
            }
        }
    }
}
