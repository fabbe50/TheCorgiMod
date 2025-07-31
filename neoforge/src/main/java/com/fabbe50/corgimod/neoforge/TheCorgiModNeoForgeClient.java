package com.fabbe50.corgimod.neoforge;

import com.fabbe50.corgimod.ClothConfig;
import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.client.renderer.registry.RendererRegistry;
import com.fabbe50.corgimod.neoforge.integration.curios.Curios;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = TheCorgiMod.MOD_ID, dist = Dist.CLIENT)
public class TheCorgiModNeoForgeClient {
    public TheCorgiModNeoForgeClient(IEventBus eventBus, ModContainer modContainer) {
        eventBus.addListener(this::setupClient);

        TheCorgiMod.initClient();

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (modContainer1, screen) -> ClothConfig.getConfigScreen(screen));
    }

    public void setupClient(FMLClientSetupEvent event) {
        if (TheCorgiModNeoForge.isCuriosLoaded()) {
            Curios.registerRendering();
        }
    }
}
