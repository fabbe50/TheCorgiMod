package com.fabbe50.corgimod.world.entity.feature.corgis;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.feature.extras.CorgiLootEvent;
import com.fabbe50.corgimod.world.entity.feature.extras.SetAreaOnFireEvent;
import com.fabbe50.corgimod.world.entity.feature.extras.SpawnMobEvent;
import com.fabbe50.corgimod.world.entity.feature.extras.SpawnTNTEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.List;

public class fabbe50CorgiFeaturePack extends BaseCorgiFeaturePack {
    @Override
    public void dropCustomDeathLoot(Corgi entity, ServerLevel level, DamageSource damageSource, boolean ignoreDropChance) {
        if (ModConfig.<Boolean>getValue("fabbe50CorgiDoRandomDrops").getValue()) {
            ItemLike dropItem = fabbe50RandomCorgiDrops.get(entity.getRandom().nextInt(0, fabbe50RandomCorgiDrops.size()));
            if (dropItem != null) {
                boolean doDrop = true;
                if (ModConfig.<Boolean>getValue("fabbe50CorgiDoRandomDropEvents").getValue() && damageSource.getEntity() instanceof Player) {
                    for (CorgiLootEvent lootEvent : lootEvents) {
                        doDrop = !lootEvent.run(dropItem, entity, level);
                    }
                }
                if (doDrop) {
                    this.dropItem(entity, dropItem);
                }
            }
        }
        super.dropCustomDeathLoot(entity, level, damageSource, ignoreDropChance);
    }

    private static final List<CorgiLootEvent> lootEvents = new ArrayList<>();
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
        fabbe50RandomCorgiDrops.add(ModRegistries.URANIUM.get());

        lootEvents.add(new CorgiLootEvent(Items.TNT,            1, new SpawnTNTEvent()));
        lootEvents.add(new CorgiLootEvent(Items.FIRE_CHARGE,    1, new SetAreaOnFireEvent()));
        lootEvents.add(new CorgiLootEvent(Items.GUNPOWDER,      1, new SpawnMobEvent(EntityType.CREEPER,    EntityRegistry.CREEPER_CORGI.get())));
        lootEvents.add(new CorgiLootEvent(Items.BONE,           1, new SpawnMobEvent(EntityType.SKELETON,   EntityRegistry.SKELETON_CORGI.get())));
        lootEvents.add(new CorgiLootEvent(Items.ROTTEN_FLESH,   1, new SpawnMobEvent(EntityType.ZOMBIE,     EntityRegistry.ZOMBIE_CORGI.get())));
        lootEvents.add(new CorgiLootEvent(Items.ENDER_PEARL,    1, new SpawnMobEvent(EntityType.ENDERMAN,   EntityRegistry.ENDER_CORGI.get())));
        lootEvents.add(new CorgiLootEvent(Items.GHAST_TEAR,     1, new SpawnMobEvent(EntityType.GHAST)));
        lootEvents.add(new CorgiLootEvent(Items.FEATHER,        1, new SpawnMobEvent(EntityType.CHICKEN)));
        lootEvents.add(new CorgiLootEvent(Items.LEATHER,        1, new SpawnMobEvent(EntityType.COW)));
        lootEvents.add(new CorgiLootEvent(Items.SNOWBALL,       1, new SpawnMobEvent(EntityType.SNOW_GOLEM)));
    }
}
