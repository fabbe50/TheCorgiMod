package com.fabbe50.corgimod.client.renderer.registry;

import com.fabbe50.corgimod.client.renderer.*;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class RendererRegistry {
    public RendererRegistry() {
        registerEntityRenderers();
    }

    public void registerEntityRenderers() {
        EntityRenderers.register(EntityRegistry.CORGI_NORMAL.get(), CorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_ANTI.get(), AntiCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_BODYGUARD.get(), BodyguardCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_BUSINESS.get(), BusinessCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_CREEPER.get(), CreeperCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_FABBE50.get(), Fabbe50CorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_FARMER.get(), FarmerCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_HERO.get(), HeroCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_LOVE.get(), LoveCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_MELON.get(), MelonCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_NERD.get(), NerdCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_PIRATE.get(), PirateCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_RADIOACTIVE.get(), RadioactiveCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_SKELETON.get(), SkeletonCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_SPY.get(), SpyCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_SUNGLASSES.get(), SunglassesCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_ZOMBIE.get(), ZombieCorgiRenderer::new);
    }
}
