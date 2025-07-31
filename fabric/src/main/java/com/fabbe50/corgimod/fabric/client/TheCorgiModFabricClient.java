package com.fabbe50.corgimod.fabric.client;

import com.fabbe50.corgimod.client.renderer.registry.RendererRegistry;
import net.fabricmc.api.ClientModInitializer;

public final class TheCorgiModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        RendererRegistry.init();
    }
}
