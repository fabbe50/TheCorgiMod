package com.fabbe50.corgimod.client.renderer.registry;

import com.fabbe50.corgimod.client.renderer.AntiCorgiRenderer;
import com.fabbe50.corgimod.client.renderer.BodyguardCorgiRenderer;
import com.fabbe50.corgimod.client.renderer.CorgiRenderer;
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
    }
}
