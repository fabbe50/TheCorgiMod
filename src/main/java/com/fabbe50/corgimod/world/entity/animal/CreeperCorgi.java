package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CreeperCorgi extends Creeper {
    public CreeperCorgi(EntityType<? extends Creeper> p_32278_, Level p_32279_) {
        super(p_32278_, p_32279_);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.33D);
    }

    public float getTailAngle() {
        return ((float)Math.PI / 5F);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.showCorgiDefaultNames)
            return Component.literal(Corgis.CREEPER.getFormattedName());
        return super.getDisplayName();
    }
}
