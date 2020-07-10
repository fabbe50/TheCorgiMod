package com.fabbe50.corgis.entities.data;

import com.fabbe50.corgis.Reference;
import net.minecraft.util.ResourceLocation;

public enum CorgiType {
    NORMAL("normal", 0, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_normal.png")),
    FABBE50("fabbe50", 1, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_fabbe50.png")),
    BODYGUARD("bodyguard", 2, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_bodyguard.png")),
    BUSINESS("business", 3, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_business.png")),
    MELON("melon", 4, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_melon.png")),
    PIRATE("pirate", 5, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_pirate.png")),
    SPY("spy", 6, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_spy.png")),
    SUNGLASSES("sunglasses", 7, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_sunglasses.png")),
    ANTI("anti", 8, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_anti.png")),
    LOVE("love", 9, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_love.png")),
    RADIOACTIVE("radioactive", 10, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_radioactive.png")),
    HERO("hero", 11, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_hero.png")),
    NERD("nerd", 12, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_nerd.png"));
    /*Farmer Corgi
    * */


    private static final CorgiType[] DMG_LOOKUP = new CorgiType[values().length];
    private final String name;
    private final int ID;
    private final ResourceLocation resourceLocation;

    CorgiType(String name, int ID, ResourceLocation resourceLocation) {
        this.name = name;
        this.ID = ID;
        this.resourceLocation = resourceLocation;
    }

    public static CorgiType byDamage(int damage) {
        if (damage < 0 || damage >= DMG_LOOKUP.length) {
            damage = 0;
        }

        return DMG_LOOKUP[damage];
    }

    public static CorgiType byName(String name) {
        for (int i = 0; i < CorgiType.count(); i++) {
            if (CorgiType.byDamage(i).getName().equals(name.toLowerCase())) {
                return CorgiType.byDamage(i);
            }
        }
        return null;
    }

    public String getName () {
        return name;
    }

    public int getID () {
        return ID;
    }

    public ResourceLocation getResourceLocation() {
        return resourceLocation;
    }

    public static int count() {
        return values().length;
    }

    static {
        for (CorgiType enumCorgiType : values()) {
            DMG_LOOKUP[enumCorgiType.getID()] = enumCorgiType;
        }
    }
}
