package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.ai.LoveCorgiGoal;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class LoveCorgi extends Corgi {
    public LoveCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES))
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
        if (CorgiMod.config.general.corgiParticleEffects && this.hasBeenFed()) {
            if (random.nextInt(8) == 0) {
                this.level.addParticle(ParticleTypes.HEART, this.getX() + random.nextDouble() - 0.5D, this.getY() + random.nextDouble(), this.getZ() + random.nextDouble() - 0.5D, 0, 0.1D, 0);
            }
        }
    }
}
