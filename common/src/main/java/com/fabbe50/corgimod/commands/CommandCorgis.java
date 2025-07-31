package com.fabbe50.corgimod.commands;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.PetBedBlock;
import com.fabbe50.corgimod.world.block.PetBowlBlock;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import com.fabbe50.corgimod.world.entity.interfaces.IBaby;
import com.fabbe50.corgimod.world.entity.monster.EnderCorgi;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class CommandCorgis {
    public CommandCorgis() {}

    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(
                Commands.literal("corgis").requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("summonDisplay").executes(context -> summonDisplay(context.getSource())))
                        .then(Commands.literal("clearDisplay").executes(context -> clearDisplay(context.getSource())))
                        .then(Commands.literal("bedDisplay").executes(context -> bedDisplay(context.getSource())))
                        .then(Commands.literal("bowlDisplay").executes(context -> bowlDisplay(context.getSource())))
        );
    }

    private static int summonDisplay(CommandSourceStack sourceStack) {
        ServerLevel level = sourceStack.getLevel();
        RegistryAccess registryAccess = level.registryAccess();
        Registry<CorgiVariant> registry = registryAccess.registryOrThrow(CorgiVariants.CORGI_VARIANTS_REGISTRY.key());
        Optional<Holder.Reference<CorgiVariant>> registryHolder = registry.getHolder(CorgiVariants.NORMAL);
        Objects.requireNonNull(registry);
        Vec3 startingPos = sourceStack.getPosition().add(0, 1, 0);
        int spawnedEntities = 0;
        List<Corgis> corgis = Corgis.getCorgis();
        int timesX = 0;
        int timesZ = 0;
        for (Corgis corgi : corgis) {
            Entity toSpawn = corgi.getEntityType().create(level);
            if (toSpawn instanceof Mob mob) {
                Vec3 pos = new Vec3(startingPos.x() + (3 * timesX), startingPos.y(), startingPos.z() + (3 * timesZ));
                timesX++;
                if (timesX > 8) {
                    timesX = 0;
                    timesZ++;
                }

                if (mob instanceof Corgi corgi1) {
                    corgi1.setVariant(registry.getHolder(corgi.getCorgiVariant()).orElse(registryHolder.get()));
                    if (corgi.getEntityType().create(level) instanceof Corgi baby) {
                        baby.setVariant(registry.getHolder(corgi.getCorgiVariant()).orElse(registryHolder.get()));
                        baby.setBaby(true);
                        if (spawnDefaultDisplayEntity(level, baby, pos.add(1.3, 0, 0))) {
                            spawnedEntities++;
                        }
                    }
                    if (corgi.getEntityType().create(level) instanceof Corgi tamed) {
                        tamed.setVariant(registry.getHolder(corgi.getCorgiVariant()).orElse(registryHolder.get()));
                        tamed.setTame(true, false);
                        if (corgi.getEntityType().create(level) instanceof Corgi baby) {
                            baby.setVariant(registry.getHolder(corgi.getCorgiVariant()).orElse(registryHolder.get()));
                            baby.setTame(true, false);
                            baby.setBaby(true);
                            if (spawnDefaultDisplayEntity(level, baby, pos.add(1.3, 3, 0))) {
                                spawnedEntities++;
                            }
                        }
                        if (spawnDefaultDisplayEntity(level, tamed, pos.add(0, 3, 0))) {
                            spawnedEntities++;
                        }
                    }
                    if (corgi.getEntityType().create(level) instanceof Corgi angry) {
                        angry.setVariant(registry.getHolder(corgi.getCorgiVariant()).orElse(registryHolder.get()));
                        angry.setRemainingPersistentAngerTime(-1);
                        if (corgi.getEntityType().create(level) instanceof Corgi baby) {
                            baby.setVariant(registry.getHolder(corgi.getCorgiVariant()).orElse(registryHolder.get()));
                            baby.setRemainingPersistentAngerTime(-1);
                            baby.setBaby(true);
                            if (spawnDefaultDisplayEntity(level, baby, pos.add(1.3, 6, 0))) {
                                spawnedEntities++;
                            }
                        }
                        if (spawnDefaultDisplayEntity(level, angry, pos.add(0, 6, 0))) {
                            spawnedEntities++;
                        }
                    }
                }
                if (mob instanceof IBaby) {
                    Entity babyEntity = corgi.getEntityType().create(level);
                    if (babyEntity instanceof Mob babyMob) {
                        babyMob.setBaby(true);
                        if (babyMob instanceof AbstractSkeleton) {
                            if (babyMob instanceof WitherSkeleton) {
                                babyMob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
                            } else {
                                babyMob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                            }
                        }
                        if (spawnDefaultDisplayEntity(level, babyMob, pos.add(1.3, 0, 0))) {
                            spawnedEntities++;
                        }
                    }
                }
                if (mob instanceof EnderCorgi) {
                    Entity enderCorgiEntity = corgi.getEntityType().create(level);
                    if (enderCorgiEntity instanceof EnderCorgi enderCorgi) {
                        enderCorgi.setHasWings(true);
                        if (spawnDefaultDisplayEntity(level, enderCorgi, pos.add(0, 3, 0))) {
                            spawnedEntities++;
                        }
                    }
                    Entity babyEnderCorgiEntity = corgi.getEntityType().create(level);
                    if (babyEnderCorgiEntity instanceof EnderCorgi babyEnderCorgi) {
                        babyEnderCorgi.setBaby(true);
                        babyEnderCorgi.setHasWings(true);
                        if (spawnDefaultDisplayEntity(level, babyEnderCorgi, pos.add(1.3, 3, 0))) {
                            spawnedEntities++;
                        }
                    }
                }
                if (mob instanceof AbstractSkeleton) {
                    if (mob instanceof WitherSkeleton) {
                        mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.STONE_SWORD));
                    } else {
                        mob.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(Items.BOW));
                    }
                }
                if (spawnDefaultDisplayEntity(level, mob, pos)) {
                    spawnedEntities++;
                }
            }
        }
        if (spawnedEntities > 0) {
            int finalSpawnedEntities = spawnedEntities;
            sourceStack.sendSuccess(() -> Component.literal("Successfully summoned " + finalSpawnedEntities + " entities"), true);
        } else {
            sourceStack.sendFailure(Component.literal("Failed to summon display."));
        }
        return spawnedEntities;
    }

    private static int clearDisplay(CommandSourceStack sourceStack) {
        ServerLevel level = sourceStack.getLevel();
        Vec3 pos = sourceStack.getPosition();
        int boundsSize = 100;
        level.getEntities((Entity) null, AABB.ofSize(pos, boundsSize, boundsSize, boundsSize), entity -> entity.getTags().contains("display_summon")).forEach(entity -> entity.remove(Entity.RemovalReason.DISCARDED));
        sourceStack.sendSuccess(() -> Component.literal("Cleared entity display."), false);
        return 1;
    }

    private static int spawnDefaultDisplayWithBaby(ServerLevel level, Mob mob, Vec3 pos) {
        int count = 0;
        if (mob instanceof IBaby) {
            Entity babyEntity = mob.getType().create(level);
            if (babyEntity instanceof Mob babyMob) {
                babyMob.setBaby(true);
                if (spawnDefaultDisplayEntity(level, babyMob, pos.add(1.3, 0, 0))) {
                    count++;
                }
            }
        }
        return spawnDefaultDisplayEntity(level, mob, pos) ? count + 1 : count;
    }

    private static boolean spawnDefaultDisplayEntity(ServerLevel level, Mob mob, Vec3 pos) {
        mob.setPos(pos);
        mob.setNoAi(true);
        mob.setInvulnerable(true);
        mob.setSilent(true);
        mob.setPersistenceRequired();
        mob.addTag("display_summon");
        return level.addFreshEntity(mob);
    }

    private static int bedDisplay(CommandSourceStack sourceStack) {
        ServerLevel level = sourceStack.getLevel();
        ServerPlayer player = sourceStack.getPlayer();
        if (player != null) {
            BlockPos origin = player.getOnPos();
            BlockPos pos = player.getOnPos();
            for (WoodType woodType : WoodType.values()) {
                for (Color wool : Color.values()) {
                    level.setBlock(pos, ModRegistries.CORGI_BED.get().defaultBlockState().setValue(PetBedBlock.WOOD_TYPE, woodType).setValue(PetBedBlock.WOOL_COLOR, wool), 3);
                    pos = pos.offset(2, 0, 0);
                }
                pos = new BlockPos(origin.getX(), pos.getY(), pos.getZ());
                pos = pos.offset(0, 0, 2);
            }
            return 1;
        }
        return 0;
    }

    private static int bowlDisplay(CommandSourceStack sourceStack) {
        ServerLevel level = sourceStack.getLevel();
        ServerPlayer player = sourceStack.getPlayer();
        if (player != null) {
            BlockPos origin = player.getOnPos();
            BlockPos pos = origin;
            Color[] colors = Color.values();
            int sqrtColors = (int) Math.sqrt(colors.length);
            int index = 0;
            for (Color color : Color.values()) {
                index++;
                level.setBlock(pos, ModRegistries.PET_BOWL.get().defaultBlockState().setValue(PetBowlBlock.COLOR, color), 3);
                pos = pos.offset(1, 0, 0);
                level.setBlock(pos, ModRegistries.PET_BOWL.get().defaultBlockState().setValue(PetBowlBlock.COLOR, color).setValue(PetBowlBlock.FOOD_IN_BOWL, true), 3);
                pos = pos.offset(2, 0, 0);
                if (index == sqrtColors) {
                    pos = new BlockPos(origin.getX(), pos.getY(), pos.getZ());
                    pos = pos.offset(0, 0, 2);
                    index = 0;
                }
            }
            return 1;
        }
        return 0;
    }
}
