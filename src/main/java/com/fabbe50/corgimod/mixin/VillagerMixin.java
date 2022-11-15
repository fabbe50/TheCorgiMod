package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.world.entity.animal.BusinessCorgi;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Villager.class)
public abstract class VillagerMixin extends AbstractVillager {
    public VillagerMixin(EntityType<? extends AbstractVillager> p_35267_, Level p_35268_) {
        super(p_35267_, p_35268_);
    }

    @Inject(method = "updateSpecialPrices", at = @At("HEAD"), cancellable = true)
    public void injectUpdateSpecialPrices(Player player, CallbackInfo ci) {
        List<BusinessCorgi> corgis = player.getLevel().getEntitiesOfClass(BusinessCorgi.class, new AABB(player.getOnPos().offset(-5, -5, -5), player.getOnPos().offset(5, 5, 5)));
        for (BusinessCorgi corgi : corgis) {
            if (corgi.isOwnedBy(player)) {
                for (MerchantOffer merchantOffer : this.getOffers()) {
                    merchantOffer.setSpecialPriceDiff(-(merchantOffer.getCostA().getCount() / 2));
                }
                ci.cancel();
            }
        }
    }
}
