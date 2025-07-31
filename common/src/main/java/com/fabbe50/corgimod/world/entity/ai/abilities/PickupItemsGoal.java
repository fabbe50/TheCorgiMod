package com.fabbe50.corgimod.world.entity.ai.abilities;

import com.fabbe50.corgimod.world.entity.animal.TamableAnimalExtension;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class PickupItemsGoal<T extends PathfinderMob> extends Goal {
    private final T entity;
    private final Predicate<ItemEntity> interestingItems;
    private final double speedModifier;
    private final int horizontalSearchRange;
    private final int verticalSearchRange;

    public PickupItemsGoal(T entity, double speedModifier, int horizontalSearchRange, int verticalSearchRange, Predicate<ItemEntity> interestingItems) {
        this.setFlags(EnumSet.of(Flag.MOVE));
        this.entity = entity;
        this.speedModifier = speedModifier;
        this.horizontalSearchRange = horizontalSearchRange;
        this.verticalSearchRange = verticalSearchRange;
        this.interestingItems = interestingItems;
    }

    public boolean canUse() {
        if (!this.entity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()) {
            return false;
        } else if (this.entity.getTarget() == null && this.entity.getLastHurtByMob() == null) {
            if (this.entity instanceof TamableAnimalExtension tamable) {
                if (!tamable.canMove()) {
                    return false;
                }
            }
            if (this.entity.getRandom().nextInt(reducedTickDelay(10)) != 0) {
                return false;
            } else {
                List<ItemEntity> list = this.entity.level().getEntitiesOfClass(ItemEntity.class, this.entity.getBoundingBox().inflate(horizontalSearchRange, verticalSearchRange, horizontalSearchRange), this.interestingItems);
                return !list.isEmpty() && this.entity.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty();
            }
        } else {
            return false;
        }
    }

    public void tick() {
        List<ItemEntity> list = this.entity.level().getEntitiesOfClass(ItemEntity.class, this.entity.getBoundingBox().inflate(horizontalSearchRange, verticalSearchRange, horizontalSearchRange), this.interestingItems);
        ItemStack itemStack = this.entity.getItemBySlot(EquipmentSlot.MAINHAND);
        if (itemStack.isEmpty() && !list.isEmpty()) {
            this.entity.getNavigation().moveTo(list.getFirst(), speedModifier);
        }
    }

    public void start() {
        List<ItemEntity> list = this.entity.level().getEntitiesOfClass(ItemEntity.class, this.entity.getBoundingBox().inflate(horizontalSearchRange, verticalSearchRange, horizontalSearchRange), this.interestingItems);
        if (!list.isEmpty()) {
            this.entity.getNavigation().moveTo(list.getFirst(), speedModifier);
        }
    }
}
