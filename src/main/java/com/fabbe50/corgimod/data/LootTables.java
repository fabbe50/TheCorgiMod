package com.fabbe50.corgimod.data;

import com.fabbe50.corgimod.CorgiMod;
import com.google.common.collect.Sets;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.Set;

public class LootTables {
    private static final Set<ResourceLocation> LOCATIONS = Sets.newHashSet();
    private static final Set<ResourceLocation> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public static final ResourceLocation MELON_CORGI_GIFT = register("gameplay/melon_corgi_gift");

    private static ResourceLocation register(String location) {
        return register(new ResourceLocation(CorgiMod.MODID, location));
    }

    private static ResourceLocation register(ResourceLocation resourceLocation) {
        if (LOCATIONS.add(resourceLocation)) {
            return resourceLocation;
        } else {
            throw new IllegalArgumentException(resourceLocation + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceLocation> all() {
        return IMMUTABLE_LOCATIONS;
    }
}
