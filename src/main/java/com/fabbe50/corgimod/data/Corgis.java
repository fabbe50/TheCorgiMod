package com.fabbe50.corgimod.data;

import net.minecraft.resources.ResourceLocation;

public enum Corgis {
    NORMAL(0, "normal", "Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_normal.png"), false),
    ANTI(1, "anti", "Anti Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_anti.png"), false),
    BODYGUARD(2, "bodyguard", "Bodyguard Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_bodyguard.png"), false),
    BUSINESS(3, "business", "Business Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_business.png"), false),
    CREEPER(4, "creeper", "Creeper Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_creeper.png"), true),
    FABBE50(5, "fabbe50", "fabbe50 Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_fabbe50.png"), false),
    HERO(6, "hero", "Hero Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_hero.png"), false),
    LOVE(7, "love", "Love Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_love.png"), false),
    MELON(8, "melon", "Melon Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_melon.png"), false),
    NERD(9, "nerd", "Nerd Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_nerd.png"), false),
    PIRATE(10, "pirate", "Pirate Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_pirate.png"), false),
    RADIOACTIVE(11, "radioactive", "Radioactive Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_radioactive.png"), false),
    SPY(12, "spy", "Spy Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_spy.png"), false),
    SUNGLASSES(13, "sunglasses", "Sunglasses Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_sunglasses.png"), false),
    ZOMBIE(14, "zombie", "Zombie Corgi", new ResourceLocation("corgimod", "textures/entity/corgi/corgi_zombie.png"), true);

    private int id;
    private String name;
    private String formattedName;
    private ResourceLocation textureLocation;
    private boolean hostile;
    Corgis(int id, String name, String formattedName, ResourceLocation textureLocation, boolean hostile) {
        this.id = id;
        this.name = name;
        this.formattedName = formattedName;
        this.textureLocation = textureLocation;
        this.hostile = hostile;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public ResourceLocation getTextureLocation() {
        return textureLocation;
    }
}
