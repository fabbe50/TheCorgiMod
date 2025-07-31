package com.fabbe50.corgimod.world.block;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DogDoorBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<DogDoorBlock> CODEC = simpleCodec(DogDoorBlock::new);

    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<WoodType> WOOD_TYPE = EnumProperty.create("wood_type", WoodType.class);

    protected static final VoxelShape EAST_AABB = Block.box(0, 0, 0, 2, 16, 16);
    protected static final VoxelShape WEST_AABB = Block.box(14, 0, 0, 16, 16, 16);
    protected static final VoxelShape SOUTH_AABB = Block.box(0, 0, 0, 16, 16, 2);
    protected static final VoxelShape NORTH_AABB = Block.box(0, 0, 14, 16, 16, 16);
    protected static final VoxelShape EAST_OPEN_AABB = Block.box(0, 15, 0, 2, 16, 16);
    protected static final VoxelShape WEST_OPEN_AABB = Block.box(14, 15, 0, 16, 16, 16);
    protected static final VoxelShape SOUTH_OPEN_AABB = Block.box(0, 15, 0, 16, 16, 2);
    protected static final VoxelShape NORTH_OPEN_AABB = Block.box(0, 15, 14, 16, 16, 16);
    protected static final AABB INTERACTION_SHAPE = new AABB(0, 0, 0, 1, 1, 1);

    public DogDoorBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(OPEN, false).setValue(POWERED, false).setValue(WOOD_TYPE, WoodType.OAK));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        return getNonChangeableShape(state);
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return getChangeableShape(state);
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        float xOffset = 0;
        float zOffset = 0;
        switch (state.getValue(FACING)) {
            case NORTH -> zOffset = -0.0625f;
            case SOUTH -> zOffset = 0.0625f;
            case WEST -> xOffset = -0.0625f;
            case EAST -> xOffset = 0.0625f;
        }

        return getChangeableShape(state).move(xOffset, 0, zOffset);
    }

    private VoxelShape getChangeableShape(BlockState state) {
        boolean open = state.getValue(OPEN);
        return switch (state.getValue(FACING)) {
            case NORTH -> open ? NORTH_OPEN_AABB : NORTH_AABB;
            case SOUTH -> open ? SOUTH_OPEN_AABB : SOUTH_AABB;
            case WEST -> open ? WEST_OPEN_AABB : WEST_AABB;
            case EAST -> open ? EAST_OPEN_AABB : EAST_AABB;
            default -> NORTH_AABB;
        };
    }

    private VoxelShape getNonChangeableShape(BlockState state) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> SOUTH_AABB;
            case WEST -> WEST_AABB;
            case EAST -> EAST_AABB;
            default -> NORTH_AABB;
        };
    }

    @Override
    protected boolean isPathfindable(BlockState blockState, PathComputationType pathComputationType) {
        switch (pathComputationType) {
            case LAND, AIR -> {
                return true;
            }
            default -> {
                return false;
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        boolean flag = level.hasNeighborSignal(pos);
        Direction direction = context.getHorizontalDirection();
        return this.defaultBlockState().setValue(FACING, direction).setValue(OPEN, flag).setValue(POWERED, flag);
    }

    private int getOpenTime() {
        return 30;
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide) {
            if (!this.isOpen(state) && entity instanceof TamableAnimal) {
                this.checkState(entity, level, pos, state, false);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel serverLevel, BlockPos pos, RandomSource randomSource) {
        if (this.isOpen(state)) {
            this.checkState(null, serverLevel, pos, state, true);
        }
    }

    private void checkState(@Nullable Entity entity, Level level, BlockPos pos, BlockState state, boolean isOpen) {
        boolean isInside = this.isEntityInsideDoor(level, pos);
        if (isOpen != isInside) {
            this.setOpen(entity, level, state, pos, isInside);
        }
        if (isInside) {
            level.scheduleTick(new BlockPos(pos), this, this.getOpenTime());
        }
    }

    private boolean isEntityInsideDoor(Level level, BlockPos pos) {
        AABB aabb = INTERACTION_SHAPE.move(pos);
        List<TamableAnimal> entities = level.getEntitiesOfClass(TamableAnimal.class, aabb);
        if (!entities.isEmpty()) {
            for (TamableAnimal tamableAnimal : entities) {
                if (!tamableAnimal.isIgnoringBlockTriggers()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public @NotNull InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (state.getValue(OPEN)) {
            state = state.setValue(OPEN, false);
            level.setBlock(pos, state, 10);
        } else {
            state = state.setValue(OPEN, true);
            level.setBlock(pos, state, 10);
        }

        boolean flag = state.getValue(OPEN);
        this.playSound(null, level, pos, flag);
        level.gameEvent(player, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos blockPos, boolean b) {
        if (!level.isClientSide) {
            boolean flag = level.hasNeighborSignal(pos);
            if (state.getValue(POWERED) != flag) {
                level.setBlock(pos, state.setValue(POWERED, flag).setValue(OPEN, flag), 2);
                if (state.getValue(OPEN) != flag) {
                    this.playSound(null, level, pos, flag);
                    level.gameEvent(null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
            }
        }
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open) {
        BlockState newState = state.setValue(OPEN, open);
        level.setBlock(pos, newState, 10);
        level.setBlocksDirty(pos, state, newState);
        this.playSound(entity, level, pos, open);
        level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
    }

    public boolean isOpen(BlockState state) {
        return state.getValue(OPEN);
    }

    private void playSound(@Nullable Entity entity, Level level, BlockPos pos, boolean open) {
        level.playSound(entity, pos, getSound(level.getBlockState(pos), open), SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    private SoundEvent getSound(BlockState state, boolean open) {
        if (this.getSoundType(state).equals(SoundType.BAMBOO_WOOD)) {
            return open ? SoundEvents.BAMBOO_WOOD_FENCE_GATE_OPEN : SoundEvents.BAMBOO_WOOD_FENCE_GATE_CLOSE;
        }
        if (this.getSoundType(state).equals(SoundType.CHERRY_WOOD)) {
            return open ? SoundEvents.CHERRY_WOOD_FENCE_GATE_OPEN : SoundEvents.CHERRY_WOOD_FENCE_GATE_CLOSE;
        }
        if (this.getSoundType(state).equals(SoundType.NETHER_WOOD)) {
            return open ? SoundEvents.NETHER_WOOD_FENCE_GATE_OPEN : SoundEvents.NETHER_WOOD_FENCE_GATE_CLOSE;
        }
        return open ? SoundEvents.FENCE_GATE_OPEN : SoundEvents.FENCE_GATE_CLOSE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, OPEN, POWERED, WOOD_TYPE);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        for (RegistrySupplier<Item> item : ModRegistries.DOG_DOORS) {
            String path = item.getId().getPath();
            if (path.equals("dog_door_" + blockState.getValue(WOOD_TYPE).getSerializedName())) {
                return new ItemStack(item.get());
            }
        }
        return new ItemStack(this);
    }

    @Override
    protected @NotNull MapCodec<DogDoorBlock> codec() {
        return CODEC;
    }
}
