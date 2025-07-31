package com.fabbe50.corgimod.world.block;

import com.fabbe50.corgimod.registries.ModRegistries;
import com.fabbe50.corgimod.world.block.state.properties.Color;
import com.fabbe50.corgimod.world.block.state.properties.WoodType;
import com.mojang.serialization.MapCodec;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PetBedBlock extends Block {
    public static final MapCodec<PetBedBlock> CODEC = simpleCodec(PetBedBlock::new);

    protected static final VoxelShape BEDDING_SHAPE = Block.box(1, 0, 1, 16, 1, 16);
    protected static final VoxelShape FRAME_1 = Block.box(0, 0, 0, 16, 2, 1);
    protected static final VoxelShape FRAME_2 = Block.box(0, 0, 15, 16, 2, 16);
    protected static final VoxelShape FRAME_3 = Block.box(0, 0, 1, 1, 2, 15);
    protected static final VoxelShape FRAME_4 = Block.box(15, 0, 1, 16, 2, 15);
    protected static final VoxelShape SHAPE = Shapes.or(BEDDING_SHAPE, FRAME_1, FRAME_2, FRAME_3, FRAME_4);

    public static final EnumProperty<WoodType> WOOD_TYPE = EnumProperty.create("wood_type", WoodType.class);
    public static final EnumProperty<Color> WOOL_COLOR = EnumProperty.create("wool", Color.class);

    public PetBedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(WOOD_TYPE, WoodType.OAK).setValue(WOOL_COLOR, Color.WHITE));
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return SHAPE;
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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WOOD_TYPE, WOOL_COLOR);
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
        String woodType = blockState.getValue(WOOD_TYPE).getSerializedName();
        String woolColor = blockState.getValue(WOOL_COLOR).getSerializedName();
        for (RegistrySupplier<Item> item : ModRegistries.CORGI_BEDS) {
            String path = item.getId().getPath();
            if (path.equals("corgi_bed_" + woodType + "_" + woolColor)) {
                return new ItemStack(item.get());
            }
        }
        return new ItemStack(this);
    }

    @Override
    protected @NotNull MapCodec<PetBedBlock> codec() {
        return CODEC;
    }
}
