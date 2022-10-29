package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.ai.LoveCorgiGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LoveCorgi extends Corgi {
    public LoveCorgi(EntityType<? extends Wolf> p_30369_, Level p_30370_) {
        super(p_30369_, p_30370_);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.LOVE.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(10, new LoveCorgiGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if (random.nextInt(8) == 0) {
            if (CorgiMod.config.general.corgiParticleEffects) {
                this.level.addParticle(ParticleTypes.HEART, this.getX() + random.nextDouble() - 0.5D, this.getY() + random.nextDouble(), this.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
            }
        }
    }
}
