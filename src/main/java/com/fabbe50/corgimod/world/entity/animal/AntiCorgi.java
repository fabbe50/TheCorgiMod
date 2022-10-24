package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.data.Corgis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class AntiCorgi extends Cat {
    public AntiCorgi(EntityType<? extends Cat> p_28114_, Level p_28115_) {
        super(p_28114_, p_28115_);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(Corgis.ANTI.getFormattedName());
    }
}
