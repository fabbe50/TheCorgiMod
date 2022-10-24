package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.data.Corgis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BodyguardCorgi extends Corgi {
    public BodyguardCorgi(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, (double)0.3F).add(Attributes.MAX_HEALTH, 16.0D).add(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    public void setTame(boolean p_30443_) {
        super.setTame(p_30443_);
        if (p_30443_) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(60.0D);
            this.setHealth(60.0F);
        } else {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(16.0D);
        }

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(Corgis.BODYGUARD.getFormattedName());
    }
}