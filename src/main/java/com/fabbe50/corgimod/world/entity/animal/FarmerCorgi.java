package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class FarmerCorgi extends Corgi {
    public FarmerCorgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.FARMER.getFormattedName());
        return super.getDisplayName();
    }
}
