package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.world.entity.interfaces.IVariant;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Spawner;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class VariantSpawnEggItem<T extends Mob & VariantHolder<Holder<V>>, V extends IVariant> extends Item {
    public static final Map<ResourceKey<? extends IVariant>, EggColor> EGG_COLORS = new HashMap<>();
    public static final List<VariantSpawnEggItem<?, ?>> EGGS = new ArrayList<>();
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");
    private final RegistrySupplier<EntityType<T>> entityType;
    private final ResourceKey<Registry<V>> variantRegistry;
    private final ResourceKey<V> variantKey;

    public VariantSpawnEggItem(RegistrySupplier<EntityType<T>> entityType, int backgroundColor, int highlightColor, Properties properties, Registrar<V> variantRegistry, ResourceKey<V> variantKey) {
        super(properties);
        this.entityType = entityType;
        this.variantRegistry = (ResourceKey<Registry<V>>) variantRegistry.key();
        this.variantKey = variantKey;
        EGGS.add(this);
        EGG_COLORS.put(variantKey, new EggColor(backgroundColor, highlightColor));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack itemStack = useOnContext.getItemInHand();
            BlockPos blockPos = useOnContext.getClickedPos();
            Direction direction = useOnContext.getClickedFace();
            BlockState blockState = level.getBlockState(blockPos);
            BlockEntity var8 = level.getBlockEntity(blockPos);
            if (var8 instanceof Spawner spawner) {
                EntityType<?> entityType = this.getType(itemStack);
                spawner.setEntityId(entityType, level.getRandom());
                level.sendBlockUpdated(blockPos, blockState, blockState, 3);
                level.gameEvent(useOnContext.getPlayer(), GameEvent.BLOCK_CHANGE, blockPos);
                itemStack.shrink(1);
            } else {
                spawnEntity(level, blockPos, blockState, direction, itemStack, useOnContext.getPlayer());
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack itemStack = player.getItemInHand(interactionHand);
        BlockHitResult blockHitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.SOURCE_ONLY);
        if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(itemStack);
        } else if (!(level instanceof ServerLevel)) {
            return InteractionResultHolder.success(itemStack);
        } else {
            BlockPos blockPos = blockHitResult.getBlockPos();
            if (!(level.getBlockState(blockPos).getBlock() instanceof LiquidBlock)) {
                return InteractionResultHolder.pass(itemStack);
            } else if (level.mayInteract(player, blockPos) && player.mayUseItemAt(blockPos, blockHitResult.getDirection(), itemStack)) {
                EntityType<?> entityType = this.getType(itemStack);
                Entity entity = entityType.spawn((ServerLevel)level, itemStack, player, blockPos, MobSpawnType.SPAWN_EGG, false, false);
                if (entity == null) {
                    return InteractionResultHolder.pass(itemStack);
                } else {
                    player.awardStat(Stats.ITEM_USED.get(this));
                    level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.position());
                    return InteractionResultHolder.consume(itemStack);
                }
            } else {
                return InteractionResultHolder.fail(itemStack);
            }
        }
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (player.level() instanceof ServerLevel level) {
            if (livingEntity instanceof Mob mob) {
                EntityType<? extends Mob> mobType = (EntityType<? extends Mob>) mob.getType();
                Mob clone;
                if (mob instanceof AgeableMob ageableMob) {
                    clone = ageableMob.getBreedOffspring(level, ageableMob);
                } else {
                    clone = mobType.create(level, EntityType.createDefaultStackConfig(level, stack, player), mob.blockPosition(), MobSpawnType.SPAWN_EGG, false, false);
                }
                if (clone == null) {
                    return InteractionResult.FAIL;
                } else {
                    clone.setBaby(true);
                    if (!clone.isBaby()) {
                        return InteractionResult.FAIL;
                    } else {
                        clone.moveTo(mob.position());
                        level.addFreshEntityWithPassengers(clone);
                        clone.setCustomName(stack.get(DataComponents.CUSTOM_NAME));
                        stack.consume(1, player);
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.CONSUME;
    }

    public void spawnEntity(Level level, BlockPos blockPos, BlockState blockState, Direction direction, ItemStack itemStack, Player player) {
        BlockPos blockPos2;
        if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
            blockPos2 = blockPos;
        } else {
            blockPos2 = blockPos.relative(direction);
        }

        T entity = this.getType(itemStack).create(level);
        if (entity != null) {
            entity.setPos(blockPos2.getCenter());
            if (level instanceof ServerLevel serverLevel) {
                Utilities.fixPos(entity, serverLevel, blockPos2, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP);
            }
            entity.setVariant(getVariant(entity));
            level.addFreshEntity(entity);
            itemStack.shrink(1);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
        }
    }

    public Holder.Reference<V> getVariant(T entity) {
        return getRegistry(entity).getHolderOrThrow(this.variantKey);
    }

    public Registry<V> getRegistry(T entity) {
        return entity.registryAccess().registryOrThrow(variantRegistry);
    }

    public int getColor(int i) {
        EggColor eggColor = EGG_COLORS.get(variantKey);
        return i == 0 ? eggColor.backgroundColor : eggColor.highlightColor;
    }

    public EntityType<T> getType(ItemStack itemStack) {
        CustomData customData = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        return !customData.isEmpty() ? (EntityType<T>) customData.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(this.entityType.get()) : this.entityType.get();
    }

    public record EggColor(int backgroundColor, int highlightColor) {
    }
}
