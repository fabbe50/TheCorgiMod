package com.fabbe50.corgimod.world.item;

import com.fabbe50.corgimod.CorgiMod;
import com.fabbe50.corgimod.data.Corgis;
import com.fabbe50.corgimod.utils.Utilities;
import com.fabbe50.corgimod.world.entity.animal.Corgi;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class ItemCorgiSoul extends Item {
    public ItemCorgiSoul(Properties properties) {
        super(properties.stacksTo(1));
    }

    public ItemStack addCorgi(ItemStack stack, Corgi corgi) {
        CompoundTag tag = new CompoundTag();
        corgi.save(tag);
        CompoundTag data = stack.getOrCreateTag();
        data.putInt("corgiVariant", Corgis.getVariantFromCorgi(corgi).getId());
        data.putBoolean("soul_active", false);
        data.putBoolean("consumed_nether_wart_block", false);
        data.putBoolean("consumed_blaze", false);
        stack.setTag(data.merge(tag));
        return stack;
    }

    public Corgi getCorgi(Level level, ItemStack stack) {
        CompoundTag data = stack.getOrCreateTag();
        EntityType<? extends Mob> entityType = Corgis.getCorgiFromID(data.getInt("corgiVariant")).getCorgiType();
        Corgi corgi = (Corgi) entityType.create(level);
        if (corgi != null) {
            corgi.load(data.copy());
            corgi.setHealth(corgi.getMaxHealth());
            corgi.setOrderedToSit(false);
            corgi.setAskedToStay(false);
        }
        return corgi;
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(@NotNull ItemStack itemStack, @NotNull Player player, @NotNull LivingEntity livingEntity, @NotNull InteractionHand interactionHand) {
        if (!(player.level() instanceof ServerLevel)) {
            return InteractionResult.SUCCESS;
        }
        CompoundTag tag = itemStack.getOrCreateTag();
        if (livingEntity.isAlive() && livingEntity instanceof Blaze && (!tag.contains("consumed_blaze") || !tag.getBoolean("consumed_blaze"))) {
            tag.putBoolean("consumed_blaze", true);
            livingEntity.remove(Entity.RemovalReason.KILLED);
            this.checkAndUpdateSoulCondition(tag);
            itemStack.setTag(tag);
            player.setItemInHand(interactionHand, itemStack);
            for (int i = 0; i < 16; i++) {
                ((ServerLevel) player.level()).sendParticles(ParticleTypes.SOUL.getType(), Utilities.getPartialPos(livingEntity.getX(), -0.5), Utilities.getPartialPosY(livingEntity.getY(), livingEntity.getDimensions(livingEntity.getPose()).height), Utilities.getPartialPos(livingEntity.getZ(), -0.5), 1, 0, 0.05, 0, 0);
            }
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
            Player player = useOnContext.getPlayer();
            if (player == null) {
                return InteractionResult.FAIL;
            }

            CompoundTag tag = itemStack.getOrCreateTag();
            if (tag.contains("soul_active") && tag.getBoolean("soul_active")) {
                spawnEntity(level, blockPos, blockState, direction, itemStack, useOnContext.getPlayer());
                return InteractionResult.SUCCESS;
            } else if (blockState.is(Blocks.NETHER_WART_BLOCK) && (!tag.contains("consume_nether_wart_block") || !tag.getBoolean("consume_nether_wart_block"))) {
                tag.putBoolean("consume_nether_wart_block", true);
                checkAndUpdateSoulCondition(tag);
                itemStack.setTag(tag);
                player.setItemInHand(useOnContext.getHand(), itemStack);
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), 3);
                for (int i = 0; i < 8; i++) {
                    ((ServerLevel) level).sendParticles(ParticleTypes.SOUL.getType(), Utilities.getPartialPos(blockPos.getX()), Utilities.getPartialPosY(blockPos.getY()), Utilities.getPartialPos(blockPos.getZ()), 1, 0, 0.05, 0, 0);
                }
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.CONSUME;
        }
    }

    private void checkAndUpdateSoulCondition(CompoundTag tag) {
        if (tag.contains("consume_nether_wart_block") && tag.getBoolean("consume_nether_wart_block") && tag.contains("consumed_blaze") && tag.getBoolean("consumed_blaze")) {
            tag.putBoolean("soul_active", true);
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
                entity.setTame(false);
                entity.setOwnerUUID(null);
            }

            entity.setPos(blockPos2.getCenter());
            if (level instanceof ServerLevel serverLevel) {
                Utilities.fixPos(entity, serverLevel, blockPos2, true, !Objects.equals(blockPos, blockPos2) && direction == Direction.UP);
            }
            level.addFreshEntity(entity);
            player.setItemInHand(player.getUsedItemHand(), ItemStack.EMPTY);
            level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);
        }
    }

    @Override
    public boolean isEnchantable(@NotNull ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack itemStack) {
        CompoundTag tag = itemStack.getOrCreateTag();
        return tag.contains("soul_active") && tag.getBoolean("soul_active");
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.contains("CustomName")) {
            String name = tag.getString("CustomName");
            try {
                name = (Objects.requireNonNull(Component.Serializer.fromJson(name))).getString();
            } catch (Exception exception) {
                CorgiMod.LOGGER.warn("Failed to parse entity custom name {}", name, exception);
            }

            list.add(Component.translatable("item.corgimod.corgi_soul.tooltip", Component.literal(name).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
        } else if (tag.contains("corgiVariant")) {
            String nameKey = "entity.corgimod.corgi_" + Corgis.getCorgiFromID(tag.getInt("corgiVariant")).getName();
            list.add(Component.translatable("item.corgimod.corgi_soul.tooltip", Component.translatable(nameKey).withStyle(ChatFormatting.GOLD)).withStyle(ChatFormatting.GRAY));
        } else {
            list.add(Component.translatable("item.corgimod.corgi_soul.tooltip", Component.literal("NULL").withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
        }
        if (tag.contains("soul_active")) {
            if (tag.getBoolean("soul_active")) {
                list.add(Component.translatable("item.corgimod.corgi_soul.tooltip-active").withStyle(ChatFormatting.GREEN));
            } else {
                list.add(Component.translatable("item.corgimod.corgi_soul.tooltip-inactive",
                        Component.literal("Blaze").withStyle(tag.getBoolean("consumed_blaze") ? ChatFormatting.GREEN : ChatFormatting.RED),
                        Component.literal("Nether Wart Block").withStyle(tag.getBoolean("consume_nether_wart_block") ? ChatFormatting.GREEN : ChatFormatting.RED)
                ).withStyle(ChatFormatting.GRAY));
            }
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
