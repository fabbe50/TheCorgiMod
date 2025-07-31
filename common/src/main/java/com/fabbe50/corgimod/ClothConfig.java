package com.fabbe50.corgimod;

import com.fabbe50.corgimod.config.IConfigOption;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.IOException;

public class ClothConfig {
    public static Screen getConfigScreen(Screen parent) {
        Component title = Component.translatable("text.thecorgimod.mod_name");

        var builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(title);

        var entryBuilder = builder.entryBuilder();
        var general = builder.getOrCreateCategory(Component.translatable("category.thecorgimod.general"));

        for (IConfigOption<?, ?> configOption : ModConfig.getConfigOptions().values()) {
            general.addEntry(configOption.buildClothEntry(entryBuilder));
        }

        return builder.setSavingRunnable(() -> {
            try {
                ModConfig.save(ModConfig.getConfigFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ModConfig.load(ModConfig.getConfigFile());
        }).build();
    }
}
