package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.world.entity.animal.BusinessCorgi;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillagerMixin {
    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "updateSpecialPrices", at = @At("TAIL"))
    public void injectUpdateSpecialPrices(Player player, CallbackInfo ci) {
        LOGGER.debug("Attempting to add business corgi discount...");
        List<BusinessCorgi> corgis = player.level().getEntitiesOfClass(BusinessCorgi.class, new AABB(player.getOnPos().offset(-5, -5, -5), player.getOnPos().offset(5, 5, 5)));
        for (BusinessCorgi corgi : corgis) {
            if (corgi.isOwnedBy(player)) {
                for (MerchantOffer merchantOffer : this.getOffers()) {
                    int cost = merchantOffer.getCostA().getCount();
                    int discount = (int)(cost * (CorgiMod.config.corgiAbilities.businessCorgiVillagerDiscount / 100d));

                    merchantOffer.setSpecialPriceDiff(-discount);
                    LOGGER.debug("Merchant Offer Update: { Original Cost: {}, Discount: {}, New Cost: {} }", cost, discount, cost - discount);
                }
            }
        }
    }
}
