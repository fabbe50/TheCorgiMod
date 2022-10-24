package com.fabbe50.corgimod;

import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.registry.RendererRegistry;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import com.mojang.logging.LogUtils;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CorgiMod.MODID)
public class CorgiMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "corgimod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    private static ModConfig config;

    public CorgiMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::setupEntityModelLayers);

        EntityRegistry.DEFERRED_REGISTER.register(modEventBus);
        ItemRegistry.DEFERRED_REGISTER.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModelLayers.register(event);
    }

    public static ModConfig getConfig() {
        return config;
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            new RendererRegistry();
        }
    }
}
