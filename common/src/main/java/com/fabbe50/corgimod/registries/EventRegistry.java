package com.fabbe50.corgimod.registries;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import com.fabbe50.corgimod.world.entity.base.FeaturedMonster;
import com.fabbe50.corgimod.world.entity.feature.corgis.HeroCorgiFeaturePack;
import com.fabbe50.corgimod.world.entity.monster.ZombieCorgi;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.event.events.common.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class EventRegistry {
    public static void init() {
        ClientTooltipEvent.ITEM.register((itemStack, list, tooltipContext, tooltipFlag) -> {
            if (itemStack.is(ModRegistries.SUNGLASSES)) {
                int i = 0;
                for (Component tooltip : list) {
                    if (tooltip.getString().equalsIgnoreCase("When on Head:")) {
                        list.add(i + 1, Component.translatable("item.thecorgimod.sunglasses.tooltip.swag").withStyle(ChatFormatting.BLUE));
                        list.add(i + 2, Component.translatable("item.thecorgimod.sunglasses.tooltip.enderman-gaze").withStyle(ChatFormatting.BLUE));
                        break;
                    }
                    i++;
                }
            }
        });
        EntityEvent.LIVING_DEATH.register((livingEntity, damageSource) -> {
            if (livingEntity instanceof Player player) {
                List<Corgi> list = player.level().getNearbyEntities(Corgi.class, HeroCorgiFeaturePack.TARGETING_CONDITIONS, player, player.getBoundingBox().inflate(16.0D));
                for (Corgi corgi : list) {
                    if (corgi.getVariant().is(CorgiVariants.HERO)) {
                        HeroCorgiFeaturePack featurePack = (HeroCorgiFeaturePack) corgi.getFeaturePack();
                        if (corgi.getOwner() != null && corgi.getOwner().is(player) && !corgi.isHungry() && featurePack.getPlayerSavingCooldown(corgi) <= 0){
                            player.setHealth(1);
                            for (int i = 0; i < 8; i++) {
                                player.level().addParticle(ParticleTypes.HEART, player.position().x + player.getRandom().nextDouble() - 0.5D, player.position().y + player.getRandom().nextInt(2) + player.getRandom().nextDouble() - 0.5D, player.position().z + player.getRandom().nextDouble() - 0.5D, 0, 0.1f, 0);
                            }
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, Utilities.ticksFromSecond(20), 2, false, true));
                            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, Utilities.ticksFromSecond(5), 3, false, true));
                            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, Utilities.ticksFromSecond(20), 2, false, true));
                            if (damageSource.is(DamageTypes.ON_FIRE)) {
                                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, Utilities.ticksFromSecond(20), 0, false, true));
                            }
                            if (damageSource.is(DamageTypes.DROWN)) {
                                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, Utilities.ticksFromSecond(20), 0, false, true));
                            }
                            corgi.setHungry(true);
                            featurePack.setPlayerSavingCooldown(corgi, Utilities.ticksFromSecond(corgi.getRandom().nextInt(600) + 2400));
                            return EventResult.interruptFalse();
                        }
                    }
                }
            }
            return EventResult.pass();
        });
        EntityEvent.ADD.register((entity, level) -> {
            RandomSource random = level.getRandom();
            if (entity instanceof Corgi corgi) {
                corgi.getFeaturePack().registerGoals(corgi, corgi.getGoalSelector());
                corgi.getFeaturePack().registerTargets(corgi, corgi.getTargetSelector());
                if (!corgi.hasCustomName() && !ModConfig.<Boolean>getValue("nameOnTame").getValue()) {
                    NameRegistry.nameCorgi(corgi);
                }
            }
            if (entity instanceof Zombie zombie) {
                if (random.nextInt(ModConfig.<Integer>getValue("zombieCorgiSpawnWeight").getValue()) == 0) {
                    ZombieCorgi zombieCorgi = EntityRegistry.ZOMBIE_CORGI.get().create(level);
                    if (zombieCorgi != null) {
                        zombieCorgi.load(zombie.saveWithoutId(new CompoundTag()));
                        level.addFreshEntity(zombieCorgi);
                        zombie.remove(Entity.RemovalReason.DISCARDED);
                    }
                }
            }
            return EventResult.pass();
        });
        InteractionEvent.INTERACT_ENTITY.register((player, entity, interactionHand) -> {
            if (player.isCreative() && player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty()) {
                if (entity.getTags().contains("display_summon")) {
                    entity.remove(Entity.RemovalReason.DISCARDED);
                    return EventResult.interruptTrue();
                }
            }
            return EventResult.pass();
        });
        InteractionEvent.RIGHT_CLICK_BLOCK.register((player, interactionHand, blockPos, direction) -> {
            Level level = player.level();
            if (level instanceof ServerLevel serverLevel) {
                BlockState state = serverLevel.getBlockState(blockPos);
                if (state.is(BlockTags.BEDS)) {
                    List<FeaturedMonster> monsters = level.getEntitiesOfClass(FeaturedMonster.class, player.getBoundingBox().inflate(8, 5, 8), featuredMonster -> featuredMonster.isPreventingPlayerRest(player));
                    if (!monsters.isEmpty()) {
                        return EventResult.interruptFalse();
                    }
                }
            }
            return EventResult.pass();
        });
        ExplosionEvent.DETONATE.register((level, explosion, list) -> {
            if (ModConfig.<Boolean>getValue("allowUraniumTNTBoosting").getValue()) {
                Entity entity = explosion.getDirectSourceEntity();
                if (entity instanceof PrimedTnt tnt) {
                    List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, new AABB(tnt.position().add(-1, -1, -1), tnt.position().add(1, 1, 1)));
                    for (ItemEntity item : items) {
                        if (item.getItem().is(ModRegistries.URANIUM)) {
                            level.explode(null, tnt.getX(), tnt.getY(0.0625f), tnt.getZ(), 30, Level.ExplosionInteraction.TNT);
                            break;
                        }
                    }
                }
            }
        });
    }
}
