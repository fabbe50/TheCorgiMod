package com.fabbe50.corgis.entities.interfaces;

import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public interface ICorgi {
    @OnlyIn(Dist.CLIENT)
    public default float getTailRotation() {
        return (float)Math.PI / 5F;
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {return null;}
}
