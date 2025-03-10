package com.fabbe50.corgimod.world.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public class ItemSunglasses extends ArmorItem implements Equipable {
    public ItemSunglasses(ArmorMaterial armorMaterial, Type equipmentSlot, Properties properties) {
        super(armorMaterial, equipmentSlot, properties.stacksTo(1));
    }

    @Override
    public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
        return true;
    }

    @Override
    public @Nullable ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return CuriosApi.createCurioProvider(new ICurio() {
            @Override
            public ItemStack getStack() {
                return stack;
            }

            @Override
            public boolean isEnderMask(SlotContext slotContext, EnderMan enderMan) {
                return true;
            }
        });
    }
}
