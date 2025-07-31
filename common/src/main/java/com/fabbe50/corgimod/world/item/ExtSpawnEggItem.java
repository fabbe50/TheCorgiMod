package com.fabbe50.corgimod.world.item;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ExtSpawnEggItem extends ArchitecturySpawnEggItem {
    public ExtSpawnEggItem(RegistrySupplier<? extends EntityType<? extends Mob>> entityType, int backgroundColor, int highlightColor, Properties properties) {
        super(entityType, backgroundColor, highlightColor, properties);
    }

    @Override
    public @NotNull Optional<Mob> spawnOffspringFromSpawnEgg(Player player, Mob mob, EntityType<? extends Mob> entityType, ServerLevel level, Vec3 vec3, ItemStack stack) {
        if (!this.spawnsEntity(stack, entityType)) {
            return Optional.empty();
        } else {
            Mob clone;
            if (mob instanceof AgeableMob ageableMob) {
                clone = ageableMob.getBreedOffspring(level, ageableMob);
            } else {
                clone = entityType.create(level, EntityType.createDefaultStackConfig(level, stack, player), mob.blockPosition(), MobSpawnType.SPAWN_EGG, false, false);
            }
            if (clone == null) {
                return Optional.empty();
            } else {
                clone.setBaby(true);
                if (!clone.isBaby()) {
                    return Optional.empty();
                } else {
                    clone.moveTo(mob.position());
                    level.addFreshEntityWithPassengers(clone);
                    clone.setCustomName(stack.get(DataComponents.CUSTOM_NAME));
                    stack.consume(1, player);
                    return Optional.of(clone);
                }
            }
        }
    }
}
