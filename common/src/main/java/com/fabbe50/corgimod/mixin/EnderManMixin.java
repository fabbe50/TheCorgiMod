package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.Platform;
import com.fabbe50.corgimod.registries.ModTags;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EnderManMixin {
    @Inject(at = @At("HEAD"), method = "isLookingAtMe", cancellable = true)
    public void injectIsLookingAtMe(Player player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack stack = player.getInventory().armor.get(3);
        if (stack.is(ModTags.ENDERMAN_SAFE_ITEMS)) {
            cir.setReturnValue(false);
        }
    }
}
