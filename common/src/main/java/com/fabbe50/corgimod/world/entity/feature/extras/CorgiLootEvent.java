package com.fabbe50.corgimod.world.entity.feature.extras;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.Random;

public class CorgiLootEvent {
    private final Random random = new Random();

    private final Item item;
    private final int chance;
    private final IEvent event;

    public CorgiLootEvent(Item item, int chance, IEvent event) {
        this.item = item;
        if (chance <= 0) {
            chance = 1;
        }
        this.chance = chance;
        this.event = event;
    }

    public boolean run(ItemLike itemLike, LivingEntity entity, ServerLevel level) {
        if (shouldRunEvent(itemLike) && rollChance()) {
            return handleEvent(entity, level);
        }
        return false;
    }

    private boolean rollChance() {
        return random.nextInt(chance) == 0;
    }

    private boolean shouldRunEvent(ItemLike itemLike) {
        return itemLike == item;
    }

    private boolean handleEvent(LivingEntity entity, ServerLevel level) {
        return event.handle(entity, level);
    }
}
