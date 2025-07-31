package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.Utilities;
import com.fabbe50.corgimod.registries.EntityRegistry;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import com.fabbe50.corgimod.world.entity.animal.CorgiVariants;
import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CorgiHolderItem extends Item {
    private static final MapCodec<EntityType<?>> ENTITY_TYPE_FIELD_CODEC = BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("id");

    public CorgiHolderItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public ItemStack addCorgi(ItemStack stack, Corgi corgi) {
        CompoundTag tag = new CompoundTag();
        corgi.save(tag);
        CustomData data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        data.update(tag1 -> stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag1.merge(tag))));
        return stack;
    }

    public Corgi getCorgi(Level level, ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        EntityType<Corgi> entityType = !data.isEmpty() ? (EntityType<Corgi>) data.read(ENTITY_TYPE_FIELD_CODEC).result().orElse(EntityRegistry.CORGI.get()) : EntityRegistry.CORGI.get();
        Corgi corgi = entityType.create(level);
        if (corgi != null) {
            corgi.load(data.copyTag());
        }
        return corgi;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack itemStack, Player player, LivingEntity livingEntity, InteractionHand interactionHand) {
        if (livingEntity != null && livingEntity.isAlive() && livingEntity instanceof Corgi corgi) {
            player.setItemInHand(interactionHand, this.addCorgi(itemStack, corgi));
            livingEntity.remove(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
            return InteractionResult.SUCCESS;
        }
        return super.interactLivingEntity(itemStack, player, livingEntity, interactionHand);
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

            spawnEntity(level, blockPos, blockState, direction, itemStack, useOnContext.getPlayer());

            return InteractionResult.CONSUME;
        }
    }

    public void spawnEntity(Level level, BlockPos blockPos, BlockState blockState, Direction direction, ItemStack itemStack, Player player) {
        BlockPos blockPos2;
        if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
            blockPos2 = blockPos;
        } else {
            blockPos2 = blockPos.relative(direction);
        }

        Corgi entity = getCorgi(level, itemStack);
        if (entity != null) {
            if (entity.getOwner() != player) {
                entity.setTame(false, true);
                entity.setOwnerUUID(null);
            }

            entity.setPos(blockPos2.getCenter());
            if (level instanceof ServerLevel serverLevel) {
                Utilities.fixPos(entity, serverLevel, blockPos2, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP);
            }
            level.addFreshEntity(entity);
            player.setItemInHand(player.getUsedItemHand(), new ItemStack(this));
            level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
        }
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        CustomData data = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        return tag.contains("variant");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        CustomData data = itemStack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
        CompoundTag tag = data.copyTag();
        if (tag.contains("CustomName")) {
            String name = tag.getString("CustomName");
            list.add(Component.translatable("item.thecorgimod.corgi_soul.tooltip", Component.literal(name).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
        } else if (tag.contains("variant")) {
            String nameKey = Optional.ofNullable(ResourceLocation.tryParse(tag.getString("variant"))).orElse(CorgiVariants.NORMAL.location()).toLanguageKey();
            list.add(Component.translatable("item.thecorgimod.corgi_soul.tooltip", Component.translatable(nameKey).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
        } else {
            list.add(Component.translatable("item.thecorgimod.corgi_soul.tooltip", Component.literal("NULL").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }
        if (tooltipFlag.isAdvanced()) {
            list.add(Component.empty());
            if (tag.contains("UUID")) {
                list.add(Component.literal("UUID: " + tag.getUUID("UUID")).withStyle(ChatFormatting.DARK_GRAY));
            } else {
                list.add(Component.literal("UUID: NULL").withStyle(ChatFormatting.DARK_GRAY));
            }
        }
    }
}
