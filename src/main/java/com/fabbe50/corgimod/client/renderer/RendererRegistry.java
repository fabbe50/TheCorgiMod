package com.fabbe50.corgimod.client.renderer;

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
    }
}
