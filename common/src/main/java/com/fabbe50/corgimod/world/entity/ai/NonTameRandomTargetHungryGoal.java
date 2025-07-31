package com.fabbe50.corgimod.world.entity.ai;

import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class NonTameRandomTargetHungryGoal<T extends LivingEntity> extends NonTameRandomTargetGoal<T> {
    private final TamableAnimalExtension animal;

    public NonTameRandomTargetHungryGoal(TamableAnimalExtension animal, Class<T> class_, boolean bl, @Nullable Predicate<LivingEntity> predicate) {
        super(animal, class_, bl, predicate);
        this.animal = animal;
    }

    @Override
    public boolean canUse() {
        return this.animal.isHungry() && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return this.animal.isHungry() && super.canContinueToUse();
    }
}
