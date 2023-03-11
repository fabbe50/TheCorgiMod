package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.LoveCorgi;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class LoveCorgiGoal extends Goal {
    private final LoveCorgi corgi;
    private int cooldown;
    private List<Animal> animals;

    public LoveCorgiGoal(LoveCorgi corgi) {
        this.corgi = corgi;
        this.cooldown = corgi.getRandom().nextInt(10) + 10;
    }

    @Override
    public boolean canUse() {
        if (this.corgi.isTame()) {
            if ((this.corgi.getLevel().getGameTime() % 20) - 1 == 0) {
                cooldown--;
            }
            if (cooldown <= 0 && this.corgi.hasBeenFed()) {
                int i = CorgiMod.config.corgiAbilities.loveCorgiEffectRange;
                AABB bounds = new AABB(this.corgi.getOnPos().offset(-i, -2, -i), this.corgi.getOnPos().offset(i, 2, i));
                Level level = this.corgi.getLevel();
                animals = level.getEntitiesOfClass(Animal.class, bounds);
                return animals.size() < CorgiMod.config.corgiAbilities.loveCorgiMaxEntityCount;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return canUse() && super.canContinueToUse();
    }

    @Override
    public void tick() {
        if (cooldown <= 0) {
            for (Animal animal : animals) {
                if (animal.getAge() == 0 && animal.canFallInLove() && animal != this.corgi) {
                    animal.setInLove((Player) this.corgi.getOwner());
                }
            }
            cooldown = this.corgi.getRandom().nextInt(10) + 10;
        }
    }
}
