package com.fabbe50.corgimod.client.renderer.registry;

import com.fabbe50.corgimod.client.renderer.*;
import com.fabbe50.corgimod.world.entity.EntityRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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
        EntityRenderers.register(EntityRegistry.CORGI_LOVE.get(), LoveCorgiRenderer::new);
        EntityRenderers.register(EntityRegistry.CORGI_ZOMBIE.get(), ZombieCorgiRenderer::new);
    }
}
