package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AntiCorgi extends Cat {
    public AntiCorgi(EntityType<? extends Cat> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.ANTI.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    public Cat getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob mob) {
        AntiCorgi corgi = EntityRegistry.CORGI_ANTI.get().create(level);
        if (corgi != null && this.isTame()) {
            corgi.setOwnerUUID(this.getOwnerUUID());
            corgi.setTame(true);
        }
        return corgi;
    }

    @Override
    public boolean canMate(@NotNull Animal animal) {
        if (animal == this) {
            return false;
        } else if (!this.isTame()) {
            return false;
        } else if (!(animal instanceof AntiCorgi corgi)) {
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
