package com.fabbe50.corgimod.world.entity.animal;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Fabbe50Corgi extends Corgi {
    public Fabbe50Corgi(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public @NotNull Component getDisplayName() {
        if (CorgiMod.config.general.namingMode.equals(ModConfig.NamingMode.DEFAULT_NAMES))
            return Component.literal(Corgis.FABBE50.getFormattedName());
        return super.getDisplayName();
    }

    @Override
    protected void dropCustomDeathLoot(@NotNull DamageSource source, int i, boolean b) {
        if (CorgiMod.config.corgiAbilities.fabbe50CorgiDoRandomDrops) {
            ItemLike dropItem = fabbe50RandomCorgiDrops.get(random.nextInt(0, fabbe50RandomCorgiDrops.size()));
            if (dropItem != null) {
                boolean doDrop = true;
                if (CorgiMod.config.corgiAbilities.fabbe50CorgiDoRandomDropEvents && source.getEntity() instanceof Player) {
                    if (this.level().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                        if (dropItem == Items.TNT) {
                            PrimedTnt tnt = new PrimedTnt(this.level(), this.getX(), this.getY(), this.getZ(), this);
                            tnt.setFuse(30);
                            this.level().addFreshEntity(tnt);
                            doDrop = false;
                        }
                        if (dropItem == Items.FIRE_CHARGE) {
                            this.setAreaOnFire(this.random.nextInt(2, 5));
                            doDrop = false;
                        }
                    }
                    if (dropItem == Items.GUNPOWDER) {
                        this.summonMultipleNearby(EntityType.CREEPER, EntityRegistry.CORGI_CREEPER.get());
                        doDrop = false;
                    }
                    if (dropItem == Items.BONE) {
                        this.summonMultipleNearby(EntityType.SKELETON, EntityRegistry.CORGI_SKELETON.get());
                        doDrop = false;
                    }
                    if (dropItem == Items.ROTTEN_FLESH) {
                        this.summonMultipleNearby(EntityType.ZOMBIE, EntityRegistry.CORGI_ZOMBIE.get());
                        doDrop = false;
                    }
                    if (dropItem == Items.ENDER_PEARL) {
                        this.summonMultipleNearby(EntityType.ENDERMAN, EntityType.ENDERMITE);
                        doDrop = false;
                    }
                    if (dropItem == Items.GHAST_TEAR) {
                        this.summonMultipleNearby(EntityType.GHAST);
                        doDrop = false;
                    }
                    if (dropItem == Items.FEATHER) {
                        this.summonMultipleNearby(EntityType.CHICKEN);
                        doDrop = false;
                    }
                    if (dropItem == Items.LEATHER) {
                        this.summonMultipleNearby(EntityType.COW);
                        doDrop = false;
                    }
                    if (dropItem == Items.SNOWBALL) {
                        this.summonMultipleNearby(EntityType.SNOW_GOLEM);
                        doDrop = false;
                    }
                }
                if (doDrop) {
                    this.dropItem(dropItem);
                }
            }
        }
        super.dropCustomDeathLoot(source, i, b);
    }

    private void dropItem(ItemLike item) {
        ItemStack dropStack = new ItemStack(item, random.nextInt(1, 4));
        this.spawnAtLocation(dropStack);
    }

    private void setAreaOnFire(int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos posToSpawnFire = this.blockPosition().offset(x, y, z);
                    if (level().getBlockState(posToSpawnFire).is(Blocks.AIR) && !level().getBlockState(posToSpawnFire.below()).is(Blocks.AIR)) {
                        this.level().setBlock(posToSpawnFire, Blocks.FIRE.defaultBlockState(), 10);
                    }
                }
            }
        }
    }

    private void summonMultipleNearby(EntityType<?> entityType, EntityType<?> entityTypeVariant) {
        for (int a = 0; a < random.nextInt(5, 15); a++) {
            if (random.nextInt(5) == 0) {
                Entity entity = entityTypeVariant.create(this.level());
                if (entity != null) {
                    this.summonEntityRandomNearby(entity);
                }
            } else {
                Entity entity = entityType.create(this.level());
                if (entity != null) {
                    this.summonEntityRandomNearby(entity);
                }
            }
        }
    }

    private void summonMultipleNearby(EntityType<?> entityType) {
        for (int a = 0; a < random.nextInt(5, 15); a++) {
            Entity entity = entityType.create(this.level());
            if (entity != null) {
                this.summonEntityRandomNearby(entity);
            }
        }
    }

    private void summonEntityRandomNearby(Entity entity) {
        Vec3 position = this.position().offsetRandom(this.random, 5);
        position = position.add(0, 5, 0);
        entity.setPos(position);
        this.level().addFreshEntity(entity);
    }

    private static final List<ItemLike> fabbe50RandomCorgiDrops = new ArrayList<>();
    static {
        fabbe50RandomCorgiDrops.add(Items.RAW_IRON);
        fabbe50RandomCorgiDrops.add(Items.RAW_GOLD);
        fabbe50RandomCorgiDrops.add(Items.DIAMOND);
        fabbe50RandomCorgiDrops.add(Items.DIRT);
        fabbe50RandomCorgiDrops.add(Items.TNT);
        fabbe50RandomCorgiDrops.add(Items.REDSTONE);
        fabbe50RandomCorgiDrops.add(Items.PRISMARINE_SHARD);
        fabbe50RandomCorgiDrops.add(Items.STONE);
        fabbe50RandomCorgiDrops.add(Items.COBBLESTONE);
        fabbe50RandomCorgiDrops.add(Items.GUNPOWDER);
        fabbe50RandomCorgiDrops.add(Items.GLOWSTONE_DUST);
        fabbe50RandomCorgiDrops.add(Items.COAL);
        fabbe50RandomCorgiDrops.add(Items.RAW_COPPER);
        fabbe50RandomCorgiDrops.add(Items.NAUTILUS_SHELL);
        fabbe50RandomCorgiDrops.add(Items.CLAY_BALL);
        fabbe50RandomCorgiDrops.add(Items.FEATHER);
        fabbe50RandomCorgiDrops.add(Items.LEATHER);
        fabbe50RandomCorgiDrops.add(Items.SNOWBALL);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_WOOL);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_CONCRETE);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_BANNER);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_BED);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_CANDLE);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_DYE);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_STAINED_GLASS);
        fabbe50RandomCorgiDrops.add(Items.MAGENTA_GLAZED_TERRACOTTA);
        fabbe50RandomCorgiDrops.add(Items.FIRE_CHARGE);
        fabbe50RandomCorgiDrops.add(ItemRegistry.URANIUM.get());
    }
}
