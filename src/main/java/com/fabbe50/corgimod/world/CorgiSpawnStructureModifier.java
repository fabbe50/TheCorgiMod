package com.fabbe50.corgimod.world;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.common.world.ModifiableStructureInfo;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CorgiSpawnStructureModifier implements StructureModifier {
    private static final RegistryObject<Codec<? extends StructureModifier>> SERIALIZER = RegistryObject.create(new ResourceLocation(CorgiMod.MODID, "corgi_mod_structure_spawns"), ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, CorgiMod.MODID);

    @Override
    public void modify(Holder<Structure> holder, Phase phase, ModifiableStructureInfo.StructureInfo.Builder builder) {
        if (phase == StructureModifier.Phase.ADD) {
            if (holder.is(BuiltinStructures.FORTRESS)) {
                builder.getStructureSettings().getSpawnOverrides(MobCategory.MONSTER).addSpawn(new MobSpawnSettings.SpawnerData(EntityRegistry.CORGI_SKELETON.get(), 2, 1, 2));
            }
        }
    }

    @Override
    public Codec<? extends StructureModifier> codec() {
        return SERIALIZER.get();
    }

    public static Codec<CorgiSpawnStructureModifier> createCodec() {
        return Codec.unit(CorgiSpawnStructureModifier::new);
    }
}
