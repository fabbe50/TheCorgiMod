package com.fabbe50.corgimod.world.block;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.entity.PetBowlBlockEntity;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.inventory.PetBowlMenu;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PetBowlBlock extends HorizontalDirectionalBlock implements EntityBlock {
    public static final MapCodec<PetBowlBlock> CODEC = simpleCodec(PetBowlBlock::new);

    protected static final VoxelShape BASE = Block.box(2, 0, 2, 14, 1, 14);
    protected static final VoxelShape NORTH_WALL = Block.box(4, 1, 3, 12, 4.5, 4);
    protected static final VoxelShape SOUTH_WALL = Block.box(4, 1, 12, 12, 4.5, 13);
    protected static final VoxelShape WEST_WALL = Block.box(3, 1, 3, 4, 4.5, 13);
    protected static final VoxelShape EAST_WALL = Block.box(12, 1, 3, 13, 4.5, 13);
    protected static final VoxelShape FOOD = Block.box(4, 1, 4, 12, 4.2, 12);

    protected static final VoxelShape EMPTY_BOWL = Shapes.or(BASE, NORTH_WALL, SOUTH_WALL, WEST_WALL, EAST_WALL);
    protected static final VoxelShape FULL_BOWL = Shapes.or(EMPTY_BOWL, FOOD);

    public static final EnumProperty<Color> COLOR = EnumProperty.create("color", Color.class);
    public static final BooleanProperty FOOD_IN_BOWL = BooleanProperty.create("food_in_bowl");

    public PetBowlBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(COLOR, Color.RED).setValue(FOOD_IN_BOWL, false));
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = level.getBlockEntity(blockPos);
            if (blockEntity instanceof PetBowlBlockEntity petBowlBlockEntity) {
                MenuRegistry.openMenu((ServerPlayer) player, petBowlBlockEntity);
            }
            return InteractionResult. CONSUME;
        }
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return blockState.getValue(FOOD_IN_BOWL) ? FULL_BOWL : EMPTY_BOWL;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, COLOR, FOOD_IN_BOWL);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        String color = blockState.getValue(COLOR).getSerializedName();
        for (RegistrySupplier<Item> item : ModRegistries.PET_BOWLS) {
            String path = item.getId().getPath();
            if (path.equals("pet_bowl_" + color)) {
                return new ItemStack(item.get());
            }
        }
        return new ItemStack(this);
    }

    @Override
    protected void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof PetBowlBlockEntity petBowlBlockEntity) {
            Containers.dropContents(level, blockPos, petBowlBlockEntity);
        }
    }

    @Override
    protected @NotNull MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new PetBowlBlockEntity(blockPos, blockState);
    }
}
