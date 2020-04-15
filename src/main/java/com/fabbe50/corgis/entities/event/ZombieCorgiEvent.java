package com.fabbe50.corgis.entities.event;

import com.fabbe50.corgis.entities.corgis.ZombieCorgiEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent;

public class ZombieCorgiEvent extends EntityEvent {
    public ZombieCorgiEvent(ZombieCorgiEntity entity)
    {
        super(entity);
    }

    public ZombieCorgiEntity getSummoner()
    {
        return (ZombieCorgiEntity) getEntity();
    }

    @HasResult
    public static class SummonAidEvent extends ZombieCorgiEvent {
        private ZombieCorgiEntity customSummonedAid;

        private final World world;
        private final int x;
        private final int y;
        private final int z;
        private final LivingEntity attacker;
        private final double summonChance;

        public SummonAidEvent(ZombieCorgiEntity entity, World world, int x, int y, int z, LivingEntity attacker, double summonChance)
        {
            super(entity);
            this.world = world;
            this.x = x;
            this.y = y;
            this.z = z;
            this.attacker = attacker;
            this.summonChance = summonChance;
        }

        /**
         * Populate this field to have a custom zombie instead of a normal zombie summoned
         */
        public ZombieCorgiEntity getCustomSummonedAid() { return customSummonedAid; }
        public void setCustomSummonedAid(ZombieCorgiEntity customSummonedAid) { this.customSummonedAid = customSummonedAid; }
        public World getWorld() { return world; }
        public int getX() { return x; }
        public int getY() { return y; }
        public int getZ() { return z; }
        public LivingEntity getAttacker() { return attacker; }
        public double getSummonChance() { return summonChance; }
    }
}
