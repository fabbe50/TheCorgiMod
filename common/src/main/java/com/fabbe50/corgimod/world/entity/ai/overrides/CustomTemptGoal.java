package com.fabbe50.corgimod.world.entity.ai.overrides;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class CustomTemptGoal extends TemptGoal {
    @Nullable
    private Player selectedPlayer;
    private final TamableAnimal tamableAnimal;

    public CustomTemptGoal(TamableAnimal tamableAnimal, double speedModifier, Predicate<ItemStack> predicate, boolean canScare) {
        super(tamableAnimal, speedModifier, predicate, canScare);
        this.tamableAnimal = tamableAnimal;
    }

    public void tick() {
        super.tick();
        if (this.selectedPlayer == null && this.mob.getRandom().nextInt(this.adjustedTickDelay(600)) == 0) {
            this.selectedPlayer = this.player;
        } else if (this.mob.getRandom().nextInt(this.adjustedTickDelay(500)) == 0) {
            this.selectedPlayer = null;
        }

    }

    protected boolean canScare() {
        return (this.selectedPlayer == null || !this.selectedPlayer.equals(this.player)) && super.canScare();
    }

    public boolean canUse() {
        return super.canUse() && !this.tamableAnimal.isTame();
    }
}
