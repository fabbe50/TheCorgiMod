package com.fabbe50.corgimod.mixin;

import com.fabbe50.corgimod.world.entity.animal.BusinessCorgi;
import net.minecraft.world.entity.ai.gossip.GossipContainer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Villager.class)
public class VillagerMixin {
    @Final
    @Shadow
    private GossipContainer gossips;

    @Inject(method = "getPlayerReputation", at = @At("HEAD"), cancellable = true)
    public void getPlayerReputationInject(Player player, CallbackInfoReturnable<Integer> cir) {
        System.out.println("Mixin ran");
        List<BusinessCorgi> businessCorgis = player.getLevel().getNearbyEntities(BusinessCorgi.class, TargetingConditions.forNonCombat(), player, new AABB(player.getOnPos().offset(-5, -2, -5), player.getOnPos().offset(5, 2, 5)));
        if (!businessCorgis.isEmpty()) {
            for (BusinessCorgi corgi : businessCorgis) {
                if (corgi.isOwnedBy(player)) {
                    cir.setReturnValue(20);
                }
            }
        }
        cir.setReturnValue(gossips.getReputation(player.getUUID(), gossipType -> {
            return true;
        }));
    }
}
