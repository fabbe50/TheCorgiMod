package com.fabbe50.corgimod.neoforge.datagen;

import com.fabbe50.corgimod.TheCorgiMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Collections;
import java.util.List;

@EventBusSubscriber(modid = TheCorgiMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();

        generator.addProvider(event.includeServer(), new Recipes(packOutput, event.getLookupProvider()));
        BlockTags blockTags = new BlockTags(packOutput, event.getLookupProvider(), event.getExistingFileHelper());
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ItemTags(packOutput, event.getLookupProvider(), blockTags.contentsGetter(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new BiomeTags(packOutput, event.getLookupProvider(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new EntityTags(packOutput, event.getLookupProvider(), event.getExistingFileHelper()));
        generator.addProvider(event.includeServer(), new LootTableProvider(packOutput, Collections.emptySet(),
                List.of(
                        new LootTableProvider.SubProviderEntry(BlockDrops::new, LootContextParamSets.BLOCK),
                        new LootTableProvider.SubProviderEntry(EntityDrops::new, LootContextParamSets.ENTITY)
                ), event.getLookupProvider()));

        generator.addProvider(event.includeClient(), new Models(packOutput, event.getExistingFileHelper()));
        generator.addProvider(event.includeClient(), new ItemModels(packOutput, event.getExistingFileHelper()));
    }
}
