package com.fabbe50.corgis.proxy;

import com.fabbe50.client.renderer.CorgiRenderer;
import com.fabbe50.corgis.Corgis;
import com.fabbe50.corgis.entities.CorgiEntity;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy implements IProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        Corgis.getLogger().info("Running preInit on Client");

        registerEntity(CorgiEntity.class, CorgiRenderer::new);

        Corgis.getLogger().info("Finished preInit on Client");
    }

    private <T extends Entity> void registerEntity(Class<T> entityClass, IRenderFactory<? super T> renderFactory) {
        Corgis.getLogger().info("Registered renderer for: " + entityClass.getName());
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderFactory);
    }
}
