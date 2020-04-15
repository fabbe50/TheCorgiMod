package com.fabbe50.corgis.event;

import com.fabbe50.corgis.entities.corgis.ZombieCorgiEntity;
import com.fabbe50.corgis.entities.event.ZombieCorgiEvent.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

public class ModEventFactory {
    public static SummonAidEvent fireZombieSummonAid(ZombieCorgiEntity zombie, World world, int x, int y, int z, LivingEntity attacker, double summonChance) {
        SummonAidEvent summonEvent = new SummonAidEvent(zombie, world, x, y, z, attacker, summonChance);
        MinecraftForge.EVENT_BUS.post(summonEvent);
        return summonEvent;
    }
}
