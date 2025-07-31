package com.fabbe50.corgimod;

import com.fabbe50.corgimod.client.model.geom.ModelLayers;
import com.fabbe50.corgimod.client.renderer.registry.RendererRegistry;
import com.fabbe50.corgimod.world.item.VariantSpawnEggItem;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.registry.client.rendering.ColorHandlerRegistry;
import net.minecraft.util.FastColor;

public class TheCorgiModClient {
    public static void initClient() {
        RendererRegistry.init();
        RendererRegistry.registerScreens();
        ModelLayers.registerDefinitions();

        ClientLifecycleEvent.CLIENT_SETUP.register(minecraft -> {
            VariantSpawnEggItem.EGGS.forEach(egg -> ColorHandlerRegistry.registerItemColors((itemStack, layer) -> FastColor.ARGB32.opaque(egg.getColor(layer)), egg));
        });
    }
}
