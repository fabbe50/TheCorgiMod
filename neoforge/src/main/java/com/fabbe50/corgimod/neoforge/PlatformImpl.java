package com.fabbe50.corgimod.neoforge;

import com.fabbe50.corgimod.neoforge.integration.curios.Curios;
import com.fabbe50.corgimod.neoforge.integration.patchouli.Patchouli;
import com.fabbe50.corgimod.neoforge.registry.Serializers;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariant;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformImpl {
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static EntityDataSerializer<Holder<CorgiVariant>> getCorgiVariantSerializer() {
        return Serializers.CORGI_VARIANT.get();
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public static boolean isWearingSunglassesTrinketOrCurio(Player player) {
        if (TheCorgiModNeoForge.isCuriosLoaded()) {
            return Curios.isPlayerWearingSunglasses(player);
        }
        return false;
    }
}
