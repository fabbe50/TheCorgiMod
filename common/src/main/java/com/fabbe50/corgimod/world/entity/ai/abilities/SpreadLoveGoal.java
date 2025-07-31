package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SpreadLoveGoal extends Goal {
    private final Corgi corgi;
    private final Level level;
    private final RandomSource random;
    private int cooldown;
    private List<Animal> animals;

    public SpreadLoveGoal(Corgi corgi) {
        this.corgi = corgi;
        this.level = this.corgi.level();
        this.random = this.corgi.getRandom();
        this.cooldown = random.nextInt(10) + 10;
    }

    @Override
    public boolean canUse() {
        if (this.corgi.isTame()) {
            if ((level.getGameTime() % 20) - 1 == 0) {
                cooldown--;
            }
            if (cooldown <= 0 && !this.corgi.isHungry()) {
                int i = ModConfig.<Integer>getValue("spreadLoveAbilityRange").getValue();
                Vec3 position = this.corgi.position();
                AABB bounds = new AABB(position.subtract(i, 2, i), position.add(i, 2, i));
                animals = level.getEntitiesOfClass(Animal.class, bounds);
                return animals.size() < ModConfig.<Integer>getValue("spreadLoveAbilityMaxEntityCount").getValue();
            }
        }
        return false;
    }

    @Override
    public void start() {
        for (Animal animal : animals) {
            if (!(animal instanceof Corgi) && animal.canBreed()) {
                if (animal.getAge() == 0 && animal.canFallInLove()) {
                    animal.setInLove((Player) this.corgi.getOwner());
                }
            }
        }
        cooldown = random.nextInt(10) + 10;
    }
}
