package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.ModConfig;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static com.mojang.text2speech.Narrator.LOGGER;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillagerMixin {
    @Inject(method = "updateSpecialPrices", at = @At("TAIL"))
    public void injectUpdateSpecialPrices(Player player, CallbackInfo ci) {
        LOGGER.debug("Attempting to add business corgi discount...");
        List<Corgi> corgis = player.level().getEntitiesOfClass(Corgi.class, new AABB(player.position().add(-5, -5, -5), player.position().add(5, 5, 5)));
        for (Corgi corgi : corgis) {
            if (corgi.getVariant().is(CorgiVariants.BUSINESS) && corgi.isOwnedBy(player)) {
                for (MerchantOffer merchantOffer : this.getOffers()) {
                    int cost = merchantOffer.getCostA().getCount();
                    int discount = (int)(cost * (ModConfig.<Integer>getValue("businessCorgiVillagerDiscount").getValue() / 100d));

                    merchantOffer.setSpecialPriceDiff(-discount);
                    LOGGER.debug("Merchant Offer Update: { Original Cost: {}, Discount: {}, New Cost: {} }", cost, discount, cost - discount);
                }
            }
        }
    }
}
