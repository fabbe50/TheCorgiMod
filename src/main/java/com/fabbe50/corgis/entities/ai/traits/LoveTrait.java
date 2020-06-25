package com.fabbe50.corgis.entities.ai.traits;

import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.entities.corgis.CorgiEntity;
import com.fabbe50.corgis.entities.data.CorgiType;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class LoveTrait extends Goal {
    private final CorgiEntity corgi;

    public LoveTrait(CorgiEntity corgi) {
        this.corgi = corgi;
    }

    @Override
    public boolean shouldExecute() {
        return this.corgi.getCorgiType().equals(CorgiType.LOVE) && isAllowedToSpreadLove();
    }

    private List<AnimalEntity> animals;
    private boolean isAllowedToSpreadLove() {
        int i = Corgis.config.getLoveCorgi().getEffectRange();
        AxisAlignedBB bounds = new AxisAlignedBB(this.corgi.getPosition().add(-i, -3, -i), this.corgi.getPosition().add(i, 3, i));
        World world = this.corgi.getEntityWorld();
        animals = world.getEntitiesWithinAABB(AnimalEntity.class, bounds);
        return animals.size() < 16;
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldExecute() && super.shouldContinueExecuting();
    }

    private int cooldown = 10;
    @Override
    public void tick() {
        if (this.corgi.getEntityWorld().getGameTime() % 20 == 0) {
            cooldown--;
        }

        if (cooldown == 0) {
            for (AnimalEntity animal : animals) {
                if (this.corgi.isTamed() && animal.canBreed() && animal.getGrowingAge() == 0 && animal != this.corgi)
                    animal.setInLove((PlayerEntity) this.corgi.getOwner());
                else if (animal.canBreed() && animal.getGrowingAge() == 0 && animal != this.corgi)
                    animal.setInLove(600);
            }
            cooldown = 10;
        }
    }
}
