package com.fabbe50.corgimod.world.level.block;

import com.fabbe50.corgimod.world.level.block.state.properties.AlignablePos;
import com.fabbe50.corgimod.world.level.block.state.properties.ModBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DogDoorBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final EnumProperty<AlignablePos> ALIGN_POS = ModBlockStateProperties.ALIGN_POS;

    protected static final VoxelShape FRAME_SHAPE_FRONT_X = Block.box(0, 0, 0, 2, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_FRONT_X_OPEN = Block.box(0, 15, 0, 2, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_FRONT_Z = Block.box(0, 0, 0, 16, 16, 2);
    protected static final VoxelShape FRAME_SHAPE_FRONT_Z_OPEN = Block.box(0, 15, 0, 16, 16, 2);
    
    protected static final VoxelShape FRAME_SHAPE_BACK_X = Block.box(14, 0, 0, 16, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_BACK_X_OPEN = Block.box(14, 15, 0, 16, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_BACK_Z = Block.box(0, 0, 14, 16, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_BACK_Z_OPEN = Block.box(0, 15, 14, 16, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_CENTER_X = Block.box(7, 0, 0, 9, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_CENTER_X_OPEN = Block.box(7, 15, 0, 9, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_CENTER_Z = Block.box(0, 0, 7, 16, 16, 9);
    protected static final VoxelShape FRAME_SHAPE_CENTER_Z_OPEN = Block.box(0, 15, 7, 16, 16, 9);
    protected static final VoxelShape FRAME_SHAPE_FULL = Block.box(0, 0, 0, 16, 16, 16);
    protected static final VoxelShape FRAME_SHAPE_FULL_OPEN = Block.box(0, 15, 0, 16, 16, 16);

    private final SoundEvent closeSound;
    private final SoundEvent openSound;

    protected DogDoorBlock(Properties properties, SoundEvent closeSound, SoundEvent openSound) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(OPEN, false).setValue(POWERED, false).setValue(ALIGN_POS, AlignablePos.CENTER));
        this.closeSound = closeSound;
        this.openSound = openSound;
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos, @NotNull CollisionContext context) {
        if (state.getValue(ALIGN_POS).equals(AlignablePos.FRONT)) {
            return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X : FRAME_SHAPE_FRONT_Z;
        } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
            return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X : FRAME_SHAPE_BACK_Z;
        } else {
            return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X : FRAME_SHAPE_CENTER_Z;
        }
    }

    @Override
    public @NotNull VoxelShape getBlockSupportShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos blockPos) {
        if (state.getValue(OPEN)) {
            if (state.getValue(ALIGN_POS).equals(AlignablePos.CENTER)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X_OPEN : FRAME_SHAPE_CENTER_Z_OPEN;
            } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X_OPEN : FRAME_SHAPE_BACK_Z_OPEN;
            } else {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X_OPEN : FRAME_SHAPE_FRONT_Z_OPEN;
            }
        } else {
            if (state.getValue(ALIGN_POS).equals(AlignablePos.CENTER)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X : FRAME_SHAPE_CENTER_Z;
            } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X : FRAME_SHAPE_BACK_Z;
            } else {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X : FRAME_SHAPE_FRONT_Z;
            }
        }
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(OPEN)) {
            if (state.getValue(ALIGN_POS).equals(AlignablePos.CENTER)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X_OPEN : FRAME_SHAPE_CENTER_Z_OPEN;
            } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X_OPEN : FRAME_SHAPE_BACK_Z_OPEN;
            } else {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X_OPEN : FRAME_SHAPE_FRONT_Z_OPEN;
            }
        } else {
            if (state.getValue(ALIGN_POS).equals(AlignablePos.CENTER)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X : FRAME_SHAPE_CENTER_Z;
            } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X : FRAME_SHAPE_CENTER_Z;
            } else {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X : FRAME_SHAPE_FRONT_Z;
            }
        }
    }

    @Override
    public @NotNull VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        if (state.getValue(OPEN)) {
            if (state.getValue(ALIGN_POS).equals(AlignablePos.CENTER)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X_OPEN : FRAME_SHAPE_CENTER_Z_OPEN;
            } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X_OPEN : FRAME_SHAPE_BACK_Z_OPEN;
            } else {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X_OPEN : FRAME_SHAPE_FRONT_Z_OPEN;
            }
        } else {
            if (state.getValue(ALIGN_POS).equals(AlignablePos.CENTER)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_CENTER_X : FRAME_SHAPE_CENTER_Z;
            } else if (state.getValue(ALIGN_POS).equals(AlignablePos.BACK)) {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_BACK_X : FRAME_SHAPE_BACK_Z;
            } else {
                return state.getValue(FACING).getAxis() == Direction.Axis.X ? FRAME_SHAPE_FRONT_X : FRAME_SHAPE_FRONT_Z;
            }
        }
    }

    @Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull PathComputationType pathComputationType) {
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
        Vec3 vectorPos = context.getClickLocation();
        boolean flag = level.hasNeighborSignal(pos);
        Direction direction = context.getHorizontalDirection();
        AlignablePos alignablePos = hitCenter(pos, vectorPos, context);
        return this.defaultBlockState().setValue(FACING, direction).setValue(OPEN, flag).setValue(POWERED, flag).setValue(ALIGN_POS, alignablePos);
    }

    private AlignablePos hitCenter(BlockPos clickedPos, Vec3 clickedLocation, BlockPlaceContext context) {
        /*Direction direction = context.getClickedFace();
        if (direction.getAxis() != Direction.Axis.Y) {
            return AlignablePos.BACK;
        }

        Vec3 vec = clickedLocation.subtract(new Vec3(clickedPos.getX(), clickedPos.getY(), clickedPos.getZ())).subtract(0.5, 0, 0.5);
        if (context.getHorizontalDirection().getAxis() != Direction.Axis.X) {
            double angle = Math.atan2(vec.x, vec.z) * -180 / Math.PI;
            double distance = vec.distanceToSqr(new Vec3(0,0,0));
            if (distance < 0.3) {
                return AlignablePos.CENTER;
            } else {
                Direction placement = Direction.fromYRot(angle).getOpposite();
                if (placement.equals(context.getHorizontalDirection())) {
                    return AlignablePos.FRONT;
                } else {
                    return AlignablePos.BACK;
                }
            }
        }*/
        return AlignablePos.CENTER;

//        return direction.getAxis() == Direction.Axis.X ? (decimalX > 0.3d && decimalX < 0.7d) : decimalZ > 0.3d && decimalZ < 0.7d;
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (state.getValue(OPEN)) {
            state = state.setValue(OPEN, false);
            level.setBlock(pos, state, 10);
        } else {
            Direction direction = player.getDirection();
            if (state.getValue(FACING) == direction.getOpposite()) {
                state = state.setValue(FACING, direction);
            }
            state = state.setValue(OPEN, true);
            level.setBlock(pos, state, 10);
        }

        boolean flag = state.getValue(OPEN);
        level.playSound(player, pos, flag ? this.openSound : this.closeSound, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.1f + 0.9f);
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
                    level.playSound(null, pos, flag ? this.openSound : this.closeSound, SoundSource.BLOCKS, 1.0f, level.getRandom().nextFloat() * 0.1f + 0.9f);
                    level.gameEvent(null, flag ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
                }
            }
        }
    }

    public void setOpen(@Nullable Entity entity, Level level, BlockState state, BlockPos pos, boolean open) {
        if (state.is(this) && state.getValue(OPEN) != open) {
            if (entity != null) {
                Direction direction = entity.getDirection();
                if (state.getValue(FACING) == direction.getOpposite()) {
                    state = state.setValue(FACING, direction);
                }
            }
            level.setBlock(pos, state.setValue(OPEN, open), 10);
            this.playSound(entity, level, pos, open);
            level.gameEvent(entity, open ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }
    }

    private void playSound(@Nullable Entity entity, Level level, BlockPos pos, boolean open) {
        level.playSound(entity, pos, open ? this.openSound : this.closeSound, SoundSource.BLOCKS, 1.0F, level.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(FACING, OPEN, POWERED, ALIGN_POS);
    }

    public static boolean isOpenableByAnimal(Level level, BlockPos pos) {
        return isOpenableByAnimal(level.getBlockState(pos));
    }

    public static boolean isOpenableByAnimal(BlockState state) {
        return state.getBlock() instanceof DogDoorBlock;
    }
}
