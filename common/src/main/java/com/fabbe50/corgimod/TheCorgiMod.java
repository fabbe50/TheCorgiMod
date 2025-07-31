package com.fabbe50.corgimod;

import com.fabbe50.corgimod.commands.CommandCorgis;
import com.fabbe50.corgimod.registries.ArmorRegistry;
import com.fabbe50.corgimod.registries.*;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import com.google.common.base.Suppliers;
import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.registry.registries.RegistrarManager;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public final class TheCorgiMod {
    public static final String MOD_ID = "thecorgimod";

    public static final Supplier<RegistrarManager> MANAGER = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static void init() {
        ModConfig.register();
        EventRegistry.init();
        NameRegistry.init();
        EntityRegistry.init();
        CorgiVariants.init();
        ArmorRegistry.init();
        ModRegistries.init();
        CreativeTabRegistry.init();
        CommandRegistrationEvent.EVENT.register((commandDispatcher, commandBuildContext, commandSelection) -> {
            CommandCorgis.register(commandDispatcher);
        });
    }

    public static void initClient() {
        TheCorgiModClient.initClient();
    }

    public static ResourceLocation location(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public static ResourceLocation location(String namespace, String id) {
        return ResourceLocation.fromNamespaceAndPath(namespace, id);
    }
}
