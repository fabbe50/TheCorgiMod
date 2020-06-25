package com.fabbe50.corgis;

import com.fabbe50.corgis.entities.registry.EntityRegistry;
import com.fabbe50.corgis.event.TooltipRenderEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("corgis")
public class Corgis {
    public static final Config config = new Config();

    private static final Logger LOGGER = LogManager.getLogger();

    public Corgis() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config.getSpec(), "Corgis.toml");

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupCommon);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
    }

    private void setupCommon(final FMLCommonSetupEvent event) {
        EntityRegistry.addSpawningConditions();
        DeferredWorkQueue.runLater(EntityRegistry::addSpawns);
        EntityRegistry.registerBetaMobs();
    }

    private void setupClient(final FMLClientSetupEvent event) {
        EntityRegistry.registerRenderers();
        EVENT_BUS.register(new TooltipRenderEvent());
    }
}
