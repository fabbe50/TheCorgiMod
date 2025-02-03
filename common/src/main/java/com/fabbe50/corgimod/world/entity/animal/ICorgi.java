package com.fabbe50.corgimod.world.entity.animal;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public interface ICorgi extends IPet, IAbility, IBeggingEntity {
    /*public default EntityType<Corgi> getCorgiFromBreeding(ICorgi parent1, ICorgi parent2) {
        return ;
    }*/

    default List<EntityType<?>> getPreyTargets() {
        List<EntityType<?>> prey = new ArrayList<>();
        prey.add(EntityType.SHEEP);
        prey.add(EntityType.RABBIT);
        prey.add(EntityType.FOX);
        return prey;
    }

    boolean isTamingItem(ItemStack stack);
}
