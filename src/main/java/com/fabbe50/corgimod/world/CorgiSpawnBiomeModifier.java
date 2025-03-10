package com.fabbe50.corgimod.world;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.misc.CorgiModTags;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.random.Weight;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CorgiSpawnBiomeModifier implements BiomeModifier {
    private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(CorgiMod.MODID, "corgi_mod_spawns"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, CorgiMod.MODID);

    @Override
    public void modify(Holder<Biome> holder, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.ADD) {
            if (holder.is(CorgiModTags.CORGI_SPAWN_BIOMES)) {
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_NORMAL.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_ANTI.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_BODYGUARD.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_BUSINESS.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_FABBE50.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_HERO.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_LOVE.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_MELON.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_NERD.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_PIRATE.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_RADIOACTIVE.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_SPY.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
                builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_SUNGLASSES.get(), Weight.of(CorgiMod.config.general.passiveCorgiSpawnWeight), 3, 6));
            }
            if (holder.is(BiomeTags.IS_OVERWORLD)) {
                builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_CREEPER.get(), Weight.of(CorgiMod.config.general.creeperCorgiSpawnWeight), 1, 3));
                builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_ZOMBIE.get(), Weight.of(CorgiMod.config.general.zombieCorgiSpawnWeight), 2, 4));
                builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_SKELETON.get(), Weight.of(CorgiMod.config.general.skeletonCorgiSpawnWeight), 1, 2));
            }
            if (holder.is(Biomes.SOUL_SAND_VALLEY)) {
                builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_SKELETON.get(), Weight.of(CorgiMod.config.general.skeletonCorgiSpawnWeight), 3, 4));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<CorgiSpawnBiomeModifier> createCodec() {
        return Codec.unit(CorgiSpawnBiomeModifier::new);
    }
}
