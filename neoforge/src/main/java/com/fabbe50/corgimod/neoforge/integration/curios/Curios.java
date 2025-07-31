package com.fabbe50.corgimod.neoforge.integration.curios;

import com.fabbe50.corgimod.registries.ModRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.Optional;

public class Curios {
    public static void registerCurios() {
        CuriosApi.registerCurio(ModRegistries.SUNGLASSES.get(), new SunglassesCurioItem());
    }

    public static void registerRendering() {
        CuriosRendererRegistry.register(ModRegistries.SUNGLASSES.get(), SunglassesRenderer::new);
    }

    public static boolean isPlayerWearingSunglasses(LivingEntity livingEntity) {
        ItemStack curioStack = getCurio(livingEntity, ModRegistries.SUNGLASSES.get());
        if (curioStack != null) {
            return curioStack.is(ModRegistries.SUNGLASSES);
        }
        return false;
    }

    public static boolean damageCurio(LivingEntity livingEntity, Item item) {
        SlotContext slotContext = getSlotContext(livingEntity, item);
        if (slotContext != null) {
            ItemStack stack = getCurio(livingEntity, item);
            if (stack != null) {
                stack.hurtAndBreak(1, (ServerLevel) livingEntity.level(), livingEntity, item1 -> {
                    CuriosApi.getCurio(stack).ifPresent(curio -> curio.curioBreak(slotContext));
                });
                return true;
            }
        }
        return false;
    }

    private static SlotContext getSlotContext(LivingEntity livingEntity, Item item) {
        SlotResult slotResult = getSlotResult(livingEntity, item).orElse(null);
        if (slotResult != null) {
            return slotResult.slotContext();
        }
        return null;
    }

    private static ItemStack getCurio(SlotContext slotContext, Item item) {
        return getCurio(slotContext.entity(), item);
    }

    private static ItemStack getCurio(LivingEntity livingEntity, Item item) {
        return CuriosApi.getCuriosInventory(livingEntity).flatMap(itemHandler -> getSlotResult(livingEntity, item).map(SlotResult::stack)).orElse(null);
    }

    private static Optional<SlotResult> getSlotResult(LivingEntity livingEntity, Item item) {
        Optional<ICuriosItemHandler> optionalInventory = CuriosApi.getCuriosInventory(livingEntity);
        if (optionalInventory.isPresent()) {
            ICuriosItemHandler inventory = optionalInventory.get();
            return inventory.findFirstCurio(stack -> stack.is(item));
        }
        return Optional.empty();
    }
}
