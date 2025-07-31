package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.world.entity.ai.overrides.*;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.feature.IFeaturePack;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class AntiCorgiFeaturePack extends BaseCorgiFeaturePack {
    private CustomAvoidEntityGoal<Corgi, Player> avoidPlayersGoal;
    private CustomTemptGoal temptGoal;

    @Override
    public void registerGoals(Corgi entity, GoalSelector goalSelector) {
        this.temptGoal = new CustomTemptGoal(entity, 0.6, stack -> stack.is(ItemTags.CAT_FOOD), true);
        goalSelector.addGoal(3, new CustomRelaxOnOwnerGoal(entity));
        goalSelector.addGoal(4, this.temptGoal);
        goalSelector.addGoal(5, new CustomLieOnBedGoal(entity, 1.1, 8));

        goalSelector.addGoal(7, new CustomSitOnBlockGoal(entity, 0.8));
        goalSelector.addGoal(8, new LeapAtTargetGoal(entity, 0.3f));
        goalSelector.addGoal(9, new OcelotAttackGoal(entity));
        goalSelector.addGoal(20, new BreedGoal(entity, 0.8));
        goalSelector.addGoal(21, new CustomWaterAvoidingRandomStrollGoal<>(entity, 0.8, ModConfig.<Integer>getValue("maxWanderingDistance").getValue()));
        goalSelector.addGoal(23, new LookAtPlayerGoal(entity, Player.class, 10.0F));
    }

    @Override
    public void registerTargets(Corgi entity, GoalSelector targetSelector) {
        targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(entity, Rabbit.class, false, null));
        targetSelector.addGoal(1, new NonTameRandomTargetGoal<>(entity, Turtle.class, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ItemTags.CAT_FOOD);
    }

    @Override
    public boolean isTamingItem(ItemStack stack) {
        return stack.is(ItemTags.CAT_FOOD);
    }

    @Override
    public void onTick(Corgi entity) {
        if (this.temptGoal != null && this.temptGoal.isRunning() && !entity.isTame() && entity.tickCount % 100 == 0) {
            entity.playSound(Sounds.BEG_FOR_FOOD.getSound(), 1, 1);
        }

        if ((entity.isLying() || entity.isRelaxStateOne()) && entity.tickCount % 5 == 0) {
            entity.playSound(Sounds.PURR.getSound(), 1, 1);
        }
        entity.lieDownAmountO = entity.lieDownAmount;
        entity.lieDownAmountOTail = entity.lieDownAmountTail;
        if (entity.isLying()) {
            entity.lieDownAmount = Math.min(1.0F, entity.lieDownAmount + 0.15F);
            entity.lieDownAmountTail = Math.min(1.0F, entity.lieDownAmountTail + 0.08F);
        } else {
            entity.lieDownAmount = Math.max(0.0F, entity.lieDownAmount - 0.22F);
            entity.lieDownAmountTail = Math.max(0.0F, entity.lieDownAmountTail - 0.13F);
        }
        entity.relaxStateOneAmountO = entity.relaxStateOneAmount;
        if (entity.isRelaxStateOne()) {
            entity.relaxStateOneAmount = Math.min(1.0F, entity.relaxStateOneAmount + 0.1F);
        } else {
            entity.relaxStateOneAmount = Math.max(0.0F, entity.relaxStateOneAmount - 0.13F);
        }
    }

    @Override
    public void onTame(Corgi entity, GoalSelector goalSelector) {
        super.onTame(entity, goalSelector);
        if (this.avoidPlayersGoal == null) {
            this.avoidPlayersGoal = new CustomAvoidEntityGoal<>(entity, Player.class, 16f, 0.8, 1.33);
        }
        goalSelector.removeGoal(this.avoidPlayersGoal);
        if (!entity.isTame()) {
            goalSelector.addGoal(4, this.avoidPlayersGoal);
        }
    }

    @Override
    public SoundEvent onAmbientSound(Corgi entity) {
        if (entity.isTame()) {
            if (entity.isInLove()) {
                return Sounds.PURR.getSound();
            } else {
                return entity.getRandom().nextInt(4) == 0 ? Sounds.PURREOW.getSound() : Sounds.AMBIENT.getSound();
            }
        } else {
            return Sounds.STRAY_AMBIENT.getSound();
        }
    }

    public enum Sounds implements IFeaturePack.ISounds {
        BEG_FOR_FOOD {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_BEG_FOR_FOOD;
            }
        },
        HURT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_HURT;
            }
        },
        DEATH {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_DEATH;
            }
        },
        STRAY_AMBIENT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_STRAY_AMBIENT;
            }
        },
        AMBIENT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_AMBIENT;
            }
        },
        PURREOW {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_PURREOW;
            }
        },
        PURR {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_PURR;
            }
        },
        HISS {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_HISS;
            }
        },
        EAT {
            @Override
            public SoundEvent getSound() {
                return SoundEvents.CAT_EAT;
            }
        };

        @Override
        public SoundEvent getSound() {
            return SoundEvents.EMPTY;
        }
    }
}
