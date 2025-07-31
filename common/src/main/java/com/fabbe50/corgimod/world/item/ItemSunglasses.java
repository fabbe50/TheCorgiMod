package com.fabbe50.corgimod.world.item;

import net.minecraft.core.Holder;
import net.minecraft.world.item.*;

public class ItemSunglasses extends ArmorItem implements Equipable {
    public ItemSunglasses(Holder<ArmorMaterial> holder, Type type, Properties properties) {
        super(holder, type, properties.stacksTo(1).durability(Type.HELMET.getDurability(6)));
    }
}
