package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.entity.Corgis;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Stream;

public class EntityDrops extends EntityLootSubProvider {
    protected EntityDrops(HolderLookup.Provider provider) {
        super(FeatureFlags.DEFAULT_FLAGS, provider);
    }

    @Override
    public void generate() {
        // Creating a basic loot table with multiple weighted drop options.
        add(EntityRegistry.CORGI.get(), createLootTable().withPool(createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.BONE).applyLooting(this.registries, 0.0f, 1.0f).build(1000)).add(SimpleLootEntryBuilder.create(Items.BAKED_POTATO).makePlayerKillLoot().build(1))));
        add(EntityRegistry.ZOMBIE_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.ZOMBIE.getDefaultLootTable()))));
        add(EntityRegistry.HUSK_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.HUSK.getDefaultLootTable()))));
        add(EntityRegistry.DROWNED_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.DROWNED.getDefaultLootTable()))));
        add(EntityRegistry.SKELETON_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.SKELETON.getDefaultLootTable()))));
        add(EntityRegistry.BOGGED_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.BOGGED.getDefaultLootTable()))));
        add(EntityRegistry.STRAY_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.STRAY.getDefaultLootTable()))));
        add(EntityRegistry.WITHER_SKELETON_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.WITHER_SKELETON.getDefaultLootTable()))));
        add(EntityRegistry.SPIDER_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.SPIDER.getDefaultLootTable()))));
        add(EntityRegistry.CREEPER_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.CREEPER.getDefaultLootTable()))));
        add(EntityRegistry.ENDER_CORGI.get(), createLootTable().withPool(createLootPool(1.0f, NestedLootTable.lootTableReference(EntityType.ENDERMAN.getDefaultLootTable()))));


        // Creating a loot table for a specific location. These tables are
        // Which loot-table that's going to be used is determined in Corgi$getDefaultLootTable();.
        // This table makes a normal corgi always drop a single slime ball when killed by a player.
//        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.NORMAL, createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.SLIME_BALL, 1, 1).makePlayerKillLoot().build()));
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.ANTI, createLootPool(1.0f).add(NestedLootTable.lootTableReference(EntityType.CAT.getDefaultLootTable())));
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.BODYGUARD, createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.SHIELD).build()));
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.BUSINESS,
                createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.PAPER, 2, 4).applyLooting(this.registries, 1, 2).build()),
                createLootPool(1.0f)
                        .add(SimpleLootEntryBuilder.create(Items.BOOK, 0, 1).build(10))
                        .add(SimpleLootEntryBuilder.create(Items.EMERALD, 0, 1).build(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.FABBE50, createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.REDSTONE).makePlayerKillLoot().build()));
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.FARMER,
                createLootPool(1.0f)
                        .add(SimpleLootEntryBuilder.create(Items.WHEAT_SEEDS, 0, 2).applyLooting(this.registries, 0, 2).build(50))
                        .add(SimpleLootEntryBuilder.create(Items.BEETROOT_SEEDS, 0, 2).applyLooting(this.registries, 0, 2).build(20))
                        .add(SimpleLootEntryBuilder.create(Items.POTATO, 0, 2).applyLooting(this.registries, 0, 2).build(15))
                        .add(SimpleLootEntryBuilder.create(Items.CARROT, 0, 2).applyLooting(this.registries, 0, 2).build(15))
                        .add(SimpleLootEntryBuilder.create(Items.MELON_SEEDS, 0, 1).applyLooting(this.registries, 0, 1).build(10))
                        .add(SimpleLootEntryBuilder.create(Items.PUMPKIN_SEEDS, 0, 1).applyLooting(this.registries, 0, 1).build(10))
                        .add(SimpleLootEntryBuilder.create(Items.SUGAR_CANE, 0, 1).applyLooting(this.registries, 0, 1).build(5))
                        .add(SimpleLootEntryBuilder.create(Items.BAMBOO, 0, 1).applyLooting(this.registries, 0, 1).build(5))
                        .add(SimpleLootEntryBuilder.create(Items.CACTUS, 0, 1).applyLooting(this.registries, 0, 1).build(5))
                        .add(SimpleLootEntryBuilder.create(Items.SWEET_BERRIES, 0, 1).applyLooting(this.registries, 0, 1).build(5))
                        .add(SimpleLootEntryBuilder.create(Items.TORCHFLOWER_SEEDS, 0, 1).applyLooting(this.registries, 0, 1).build(1))
                        .add(SimpleLootEntryBuilder.create(Items.PITCHER_POD, 0, 1).applyLooting(this.registries, 0, 1).build(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.HERO,
                createLootPool(1.0f)
                        .add(SimpleLootEntryBuilder.create(PotionContents.createItemStack(Items.POTION, ModRegistries.HERO_ABILITIES).getItem(), 1, 1).build(10000))
                        .add(SimpleLootEntryBuilder.create(Items.NETHER_STAR).build(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.LOVE,
                createLootPool(1.0f)
                        .add(SimpleLootEntryBuilder.create(Items.PINK_PETALS, 1, 1).build(100))
                        .add(SimpleLootEntryBuilder.create(Items.PINK_TULIP, 1, 1).build(50))
                        .add(SimpleLootEntryBuilder.create(Items.PINK_DYE, 1, 1).build(50))
                        .add(SimpleLootEntryBuilder.create(Items.PINK_CANDLE, 1, 1).build(10))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.MELON,
                createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.MELON_SLICE, 2, 4).applyLooting(this.registries, 1, 2).build()),
                createLootPool(1.0f, SimpleLootEntryBuilder.create(Items.MELON, 1, 1).makePlayerKillLoot().build())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.NERD,
                createLootPool(1.0f)
                        .add(SimpleLootEntryBuilder.create(Items.REDSTONE, 1, 3).applyLooting(this.registries, 0, 1).build(1000))
                        .add(SimpleLootEntryBuilder.create(Items.REDSTONE_TORCH, 1, 3).applyLooting(this.registries, 0, 1).build(800))
                        .add(SimpleLootEntryBuilder.create(Items.REPEATER, 1, 2).applyLooting(this.registries, 0, 1).build(500))
                        .add(SimpleLootEntryBuilder.create(Items.COMPARATOR, 1, 2).applyLooting(this.registries, 0, 1).build(500))
                        .add(SimpleLootEntryBuilder.create(Items.REDSTONE_LAMP, 1, 2).applyLooting(this.registries, 0, 1).build(100))
                        .add(SimpleLootEntryBuilder.create(Items.DROPPER, 1, 2).applyLooting(this.registries, 0, 1).build(100))
                        .add(SimpleLootEntryBuilder.create(Items.DISPENSER, 1, 2).applyLooting(this.registries, 0, 1).build(50))
                        .add(SimpleLootEntryBuilder.create(Items.OBSERVER, 1, 1).applyLooting(this.registries, 0, 1).build(50))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.PIRATE,
                createLootPool(1.0f)
                        .add(SimpleLootEntryBuilder.create(Items.GOLD_NUGGET, 2, 5).applyLooting(this.registries, 1, 2).build(1000))
                        .add(SimpleLootEntryBuilder.create(Items.GOLD_INGOT, 1, 2).applyLooting(this.registries, 0, 1).build(200))
                        .add(SimpleLootEntryBuilder.create(Items.GOLD_BLOCK, 1, 1).applyLooting(this.registries, 0, 1).build(100))
                        .add(SimpleLootEntryBuilder.create(Items.DIAMOND, 1, 1).applyLooting(this.registries, 0, 1).build(10))
                        .add(SimpleLootEntryBuilder.create(Items.NETHERITE_INGOT, 1, 1).applyLooting(this.registries, 0, 1).build(1))
                        .when(LootItemKilledByPlayerCondition.killedByPlayer())
        );
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.RADIOACTIVE, createLootPool(1.0f, SimpleLootEntryBuilder.create(ModRegistries.URANIUM.get(), 1, 2).applyLooting(this.registries, 0, 2).makePlayerKillLoot().build()));
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.SPY, createLootPool(1.0f, SimpleLootEntryBuilder.create(PotionContents.createItemStack(Items.POTION, Potions.INVISIBILITY).getItem(), 0, 1).makePlayerKillLoot().build()));
        addCorgiVariantLoot(EntityRegistry.CORGI.get(), Corgis.SUNGLASSES, createLootPool(1.0f, SimpleLootEntryBuilder.create(ModRegistries.SUNGLASSES.get(), 0, 1).makePlayerKillLoot().build()));
    }

    public static class SimpleLootEntryBuilder {
        private final LootPoolSingletonContainer.Builder<?> builder;

        protected SimpleLootEntryBuilder(ItemLike itemLike, float min, float max) {
            builder = LootItem.lootTableItem(itemLike).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)));
        }

        public static SimpleLootEntryBuilder create(ItemLike itemLike) {
            return create(itemLike, 0.0f, 1.0f);
        }

        public static SimpleLootEntryBuilder create(ItemLike itemLike, float min, float max) {
            return new SimpleLootEntryBuilder(itemLike, min, max);
        }

        public SimpleLootEntryBuilder applyLooting(HolderLookup.Provider registries, float minLootingMultiplier, float maxLootingMultiplier) {
            builder.apply(EnchantedCountIncreaseFunction.lootingMultiplier(registries, UniformGenerator.between(minLootingMultiplier, maxLootingMultiplier)));
            return this;
        }

        public SimpleLootEntryBuilder makePlayerKillLoot() {
            builder.when(LootItemKilledByPlayerCondition.killedByPlayer());
            return this;
        }

        public LootPoolSingletonContainer.Builder<?> build() {
            return builder;
        }

        public LootPoolSingletonContainer.Builder<?> build(int weight) {
            return builder.setWeight(weight);
        }
    }

    private void addCorgiVariantLoot(EntityType<Corgi> entityType, Corgis corgiVariant, LootPool.Builder basePool, LootPool.Builder... builders) {
        LootTable.Builder lootTable = createLootTable().withPool(basePool);
        for (LootPool.Builder builder : builders) {
            lootTable.withPool(builder);
        }
        add(entityType, corgiVariant.getDefaultLootTable(), lootTable.withPool(createLootPool(1.0f).add(NestedLootTable.lootTableReference(entityType.getDefaultLootTable()))));
    }

    private LootTable.Builder createLootTable() {
        return LootTable.lootTable();
    }

    private LootPool.Builder createLootPool(float rolls) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(rolls));
    }

    private LootPool.Builder createLootPool(float rolls, LootPoolSingletonContainer.Builder<?> builder) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(rolls)).add(builder);
    }

    @Override
    protected @NotNull Stream<EntityType<?>> getKnownEntityTypes() {
        return EntityRegistry.ENTITY_TYPES.entrySet().stream().filter(entry -> entry.getKey().location().getNamespace().equals(TheCorgiMod.MOD_ID)).map(Map.Entry::getValue);
    }
}
