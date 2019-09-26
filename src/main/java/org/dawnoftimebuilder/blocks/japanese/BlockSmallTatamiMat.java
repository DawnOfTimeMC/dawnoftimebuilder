package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.DoTBBlocks;
import org.dawnoftimebuilder.blocks.general.DoTBBlock;

public class BlockSmallTatamiMat extends DoTBBlock {
    private static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);

    public BlockSmallTatamiMat() {
        super("small_tatami_mat", Material.CARPET);
        this.setBurnable();
        this.setDefaultState(this.blockState.getBaseState()); 
        this.setSoundType(SoundType.CLOTH);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CARPET_AABB;
    }

    /**
     * Used to determine ambient occlusion and culling when rebuilding chunks for render
     */
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        this.tryMergingWithSprucePlanks(worldIn, pos);
    }

    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)  {
        this.checkForDrop(worldIn, pos, state);
        this.tryMergingWithSprucePlanks(worldIn, pos);
    }

    private void tryMergingWithSprucePlanks(World worldIn, BlockPos pos){
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        Block blockDown = stateDown.getBlock();
        if(blockDown == Blocks.PLANKS){
            if(stateDown.getValue(BlockPlanks.VARIANT) == BlockPlanks.EnumType.SPRUCE){
                worldIn.setBlockToAir(pos);
                worldIn.setBlockState(pos.down(), DoTBBlocks.small_tatami_floor.getDefaultState());
            }
        }
    }

    private void checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos)) {
            this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
        IBlockState stateDown = worldIn.getBlockState(pos.down());
        return stateDown.getBlockFaceShape(worldIn, pos, EnumFacing.UP) != BlockFaceShape.UNDEFINED && stateDown.getBlock() != DoTBBlocks.tatami_floor && stateDown.getBlock() != DoTBBlocks.small_tatami_floor;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing facing) {
        return facing == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
}