package com.fabbe50.corgimod.fabric;

import com.fabbe50.corgimod.fabric.integration.Patchouli;
import com.fabbe50.corgimod.fabric.integration.Trinkets;
import com.fabbe50.corgimod.fabric.registry.Serializers;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.nio.file.Path;

public class PlatformImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static EntityDataSerializer<Holder<CorgiVariant>> getCorgiVariantSerializer() {
        return Serializers.CORGI_VARIANTS;
    }

    public static boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static boolean isWearingSunglassesTrinketOrCurio(Player player) {
        if (isModLoaded("trinkets")) {
            return Trinkets.isPlayerWearingSunglasses(player);
        }
        return false;
    }
}
