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
    private static final Registrar<MenuType<?>> MENU_TYPES = TheCorgiMod.MANAGER.get().get(Registries.MENU);

    private static final MenuType<PetBowlMenu> PET_BOWL_MENU = MenuRegistry.of(PetBowlMenu::new);
    public static final RegistrySupplier<MenuType<PetBowlMenu>> PET_BOWL_MENU_SUPPLIER = MENU_TYPES.register(TheCorgiMod.location("pet_bowl_menu"), () -> PET_BOWL_MENU);

    public static void init() {
        EntityRendererRegistry.register(EntityRegistry.CORGI, CorgiRenderer::new);

        EntityRendererRegistry.register(EntityRegistry.BOGGED_CORGI, SkeletonCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.CREEPER_CORGI, CreeperCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ENDER_CORGI, EnderCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SKELETON_CORGI, SkeletonCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.SPIDER_CORGI, SpiderCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.STRAY_CORGI, SkeletonCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.WITHER_SKELETON_CORGI, WitherSkeletonCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.ZOMBIE_CORGI, ZombieCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.DROWNED_CORGI, ZombieCorgiRenderer::new);
        EntityRendererRegistry.register(EntityRegistry.HUSK_CORGI, ZombieCorgiRenderer::new);
    }

    public static void registerScreens() {
        MenuRegistry.registerScreenFactory(PET_BOWL_MENU, PetBowlScreen::new);
    }
}
