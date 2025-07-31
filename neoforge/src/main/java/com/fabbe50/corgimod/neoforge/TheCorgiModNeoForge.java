package com.fabbe50.corgimod.neoforge;

import com.fabbe50.corgimod.TheCorgiMod;
import com.fabbe50.corgimod.neoforge.integration.curios.Curios;
import com.fabbe50.corgimod.neoforge.registry.Serializers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(TheCorgiMod.MOD_ID)
public final class TheCorgiModNeoForge {
    public TheCorgiModNeoForge(IEventBus eventBus) {
        Serializers.SERIALIZERS.register(eventBus);
        eventBus.addListener(this::setup);

        TheCorgiMod.init();
    }

    private void setup(final FMLCommonSetupEvent event) {
        if (isCuriosLoaded()) {
            Curios.registerCurios();
        }
    }

    public static boolean isCuriosLoaded() {
        return ModList.get().isLoaded("curios");
    }
}
