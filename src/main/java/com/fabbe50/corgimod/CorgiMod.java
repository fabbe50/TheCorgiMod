package com.fabbe50.corgimod;

import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.curios.SunglassesCurioRenderer;
import com.fabbe50.corgimod.client.renderer.registry.RendererRegistry;
import com.fabbe50.corgimod.handlers.EventHandler;
import com.fabbe50.corgimod.handlers.NameHandler;
import com.fabbe50.corgimod.misc.CorgiModTabs;
import com.fabbe50.corgimod.world.CorgiSpawnBiomeModifier;
import com.fabbe50.corgimod.world.CorgiSpawnStructureModifier;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.HeroCorgi;
import com.fabbe50.corgimod.world.entity.monster.ZombieCorgi;
import com.fabbe50.corgimod.world.item.ItemCorgiSoul;
import com.fabbe50.corgimod.world.item.ItemRegistry;
import com.fabbe50.corgimod.world.level.block.BlockRegistry;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.StructureModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CorgiMod.MODID)
public class CorgiMod {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "corgimod";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ModConfig config;

    private static final NameHandler nameHandler = new NameHandler();

    public CorgiMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        AutoConfig.register(ModConfig.class, JanksonConfigSerializer::new);
        config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        nameHandler.init();

        modEventBus.addListener(this::setupEntityModelLayers);

        EntityRegistry.DEFERRED_REGISTER.register(modEventBus);
        BlockRegistry.DEFERRED_REGISTER.register(modEventBus);
        ItemRegistry.DEFERRED_REGISTER.register(modEventBus);
        CorgiModTabs.DEFERRED_REGISTER.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new ZombieCorgi.ZombieEvents());
        MinecraftForge.EVENT_BUS.register(new Corgi.CorgiEvents());
        MinecraftForge.EVENT_BUS.register(new HeroCorgi.HeroCorgiEvents());
        MinecraftForge.EVENT_BUS.register(new EventHandler());

        final DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MODID);
        biomeModifiers.register(modEventBus);
        biomeModifiers.register("corgi_mod_spawns", CorgiSpawnBiomeModifier::createCodec);

        final DeferredRegister<Codec<? extends StructureModifier>> structureModifiers = DeferredRegister.create(ForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, MODID);
        structureModifiers.register(modEventBus);
        structureModifiers.register("corgi_mod_structure_spawns", CorgiSpawnStructureModifier::createCodec);
    }

    private void setupEntityModelLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        ModelLayers.registerDefinitions(event);
    }

    public static NameHandler getNameHandler() {
        return nameHandler;
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            new RendererRegistry();
            CuriosRendererRegistry.register(ItemRegistry.SUNGLASSES.get(), SunglassesCurioRenderer::new);
            ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, screen) -> AutoConfig.getConfigScreen(ModConfig.class, screen).get()));
        }
    }
}
