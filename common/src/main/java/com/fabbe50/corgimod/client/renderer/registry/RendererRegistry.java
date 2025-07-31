package com.fabbe50.corgimod.client.renderer.registry;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.client.gui.screen.container.PetBowlScreen;
import com.fabbe50.corgimod.client.renderer.*;
import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.world.inventory.PetBowlMenu;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;

public class RendererRegistry {
    public static void init() {
        EntityRendererRegistry.register(EntityRegistry.CORGI, CorgiRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.BOGGED_CORGI, SkeletonCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CREEPER_CORGI, CreeperCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SKELETON_CORGI, SkeletonCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ZOMBIE_CORGI, ZombieCorgiRenderer::new);
    }

    }
}
