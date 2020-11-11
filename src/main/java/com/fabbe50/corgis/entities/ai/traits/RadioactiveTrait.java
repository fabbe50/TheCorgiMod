package com.fabbe50.corgis.entities.ai.traits;

import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.fabbe50.corgis.entities.data.CorgiType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class RadioactiveTrait extends Goal {
    private final CorgiEntity corgi;

    public RadioactiveTrait(CorgiEntity corgi) {
        this.corgi = corgi;
    }

    @Override
    public boolean shouldExecute() {
        return this.corgi.getCorgiType().equals(CorgiType.RADIOACTIVE);
    }

    private int ticks;
    @Override
    public void tick() {
        if (this.corgi.getEntityWorld().getGameTime() % 20 == 0) {
            int i = Corgis.config.getRadioactiveCorgi().getEffectRange();
            List<LivingEntity> entities = this.corgi.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(this.corgi.getPosition().add(-i, -i, -i), this.corgi.getPosition().add(i, i, i)));
            for (LivingEntity entity : entities) {
                if (this.corgi.isTamed() && entity != this.corgi) {
                    if (!(entity instanceof CorgiEntity && ((CorgiEntity) entity).getCorgiType().equals(CorgiType.RADIOACTIVE))) {
                        if (!(entity instanceof PlayerEntity)) {
                            if (!(entity instanceof TameableEntity) || !((TameableEntity) entity).isTamed()) {
                                entity.addPotionEffect(new EffectInstance(Effects.POISON, 160));
                                entity.addPotionEffect(new EffectInstance(Effects.HUNGER, 160));
                            }
                        }
                    }
                } else {
                    if (!(entity instanceof CorgiEntity && ((CorgiEntity) entity).getCorgiType().equals(CorgiType.RADIOACTIVE) && entity == this.corgi)) {
                        entity.addPotionEffect(new EffectInstance(Effects.POISON, 160));
                        entity.addPotionEffect(new EffectInstance(Effects.HUNGER, 160));
                    }
                }
            }
        }
    }
}
