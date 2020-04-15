package com.fabbe50.corgis.entities.corgis;

import com.fabbe50.corgis.entities.interfaces.ICorgi;
import com.fabbe50.corgis.entities.registry.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.world.World;

public class CreeperCorgiEntity extends MonsterEntity implements ICorgi {
    public CreeperCorgiEntity(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public CreeperCorgiEntity(World world) {
        this(EntityRegistry.CREEPER_CORGI, world);
    }
}
