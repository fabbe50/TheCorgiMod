package com.fabbe50.corgis.entities.data;

import com.fabbe50.corgis.Reference;
import net.minecraft.util.ResourceLocation;

public enum CorgiType {
    NORMAL("normal", 0, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_normal.png")), //It's just a normal dog... or is it?
    FABBE50("fabbe50", 1, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_fabbe50.png")), //Blind to glass, but can steal abilities at random for a short period of time
    BODYGUARD("bodyguard", 2, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_bodyguard.png")), //Guards you when alive and your dead body
    BUSINESS("business", 3, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_business.png")), //Lowers trading prices and raises luck for enchanting
    MELON("melon", 4, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_melon.png")), //Seeks out melon blocks and destroys them, can spit melon seeds at enemy.
    PIRATE("pirate", 5, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_pirate.png")), //Loot table luck maybe? Make boat go zoom.
    SPY("spy", 6, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_spy.png")), //Make you and it become invisible for a while if you give it something. Maybe some morph type feature?
    SUNGLASSES("sunglasses", 7, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_sunglasses.png")), //Gives you night vision when close. Gives the spectral arrow effect to mobs.
    ANTI("anti", 8, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_anti.png")), //Is a cat.
    LOVE("love", 9, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_love.png")), //Makes animals breed when in proximity
    RADIOACTIVE("radioactive", 10, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_radioactive.png")), //I mean the name pretty much explains it
    HERO("hero", 11, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_hero.png")), //Totem of undying with really long cooldown (maybe like 2 minecraft days?)
    NERD("nerd", 12, new ResourceLocation(Reference.MOD_ID, Reference.TEXTURECORGIDIR + "corgi_nerd.png")); //The nerd is a hacker. Can buff you or other corgis you own for a short period of time.

    //farmer corgi - harvests crops and replants them.
    //fisher corgi - gives random loot from the treasure fishing loot table occasionally while near water. Also boosts the players fishing luck when close.
    //miner corgi - finds ores for you, but only after you've given it a sample
    //mercenary corgi - pay it to help you in fights
    //wandering corgi - travels around with the wandering trader and buffs the traders trades.


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
