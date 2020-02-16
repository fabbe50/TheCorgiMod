package com.fabbe50.corgis;

import net.minecraft.util.ResourceLocation;

public class Reference {
    public static final String MOD_ID = "corgis";
    public static final String MOD_NAME = "Corgis";
    public static final String VERSION = "1.0";

    public static final String CLIENT_PROXY = "com.fabbe50.corgis.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.fabbe50.corgis.proxy.ServerProxy";

    public static final String TEXTURECORGIDIR = "textures/entity/corgi/";

    public static ResourceLocation modResourceLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
