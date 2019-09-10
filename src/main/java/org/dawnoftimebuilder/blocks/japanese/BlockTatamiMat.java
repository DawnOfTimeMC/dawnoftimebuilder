package org.dawnoftimebuilder.blocks.japanese;

import java.util.Random;

import net.minecraft.block.properties.PropertyDirection;
import org.dawnoftimebuilder.blocks.global.DoTBBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.items.japanese.ItemTatamiMat;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;

import static org.dawnoftimebuilder.items.DoTBItems.tatami_mat;

public class BlockTatamiMat extends DoTBBlock implements IBlockCustomItem {

    private static final AxisAlignedBB CARPET_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
    public static final PropertyEnum<BlockTatamiMat.EnumPartType> PART = PropertyEnum.create("part", BlockTatamiMat.EnumPartType.class);
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    public BlockTatamiMat() {
        super("tatami_mat", Material.CLOTH);
        this.setBurnable();
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(PART, BlockTatamiMat.EnumPartType.FOOT));
    }
    
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    	return CARPET_AABB;
    }
    
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, PART);
    }
	
    /**
     * Get the MapColor for this Block and the given BlockState
     */
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.BROWN;
    }
	
    /**
     * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * blocks, etc.
     */
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        EnumFacing facing = state.getValue(FACING);
        if (state.getValue(PART) == BlockTatamiMat.EnumPartType.FOOT) {
            if (worldIn.getBlockState(pos.offset(facing)).getBlock() != this || worldIn.isAirBlock(pos.down())) worldIn.setBlockToAir(pos);
        } else if (worldIn.getBlockState(pos.offset(facing.getOpposite())).getBlock() != this || worldIn.isAirBlock(pos.down()) ) {
            if (!worldIn.isRemote) this.dropBlockAsItem(worldIn, pos, state, 0);
            worldIn.setBlockToAir(pos);
        }
    }
    
    /**
     * Get the Item that this Block should drop when harvested.
     */
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return state.getValue(PART) == BlockTatamiMat.EnumPartType.FOOT ? Items.AIR : tatami_mat;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state) {
        return EnumPushReaction.DESTROY;
    }
    
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
        return p_193383_4_ == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }
    
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(tatami_mat);
    }
	
    /**
     * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
     * collect this blocks
     */
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && state.getValue(PART) == BlockTatamiMat.EnumPartType.FOOT) {
            pos = pos.offset(state.getValue(FACING));
            if (worldIn.getBlockState(pos).getBlock() == this) worldIn.setBlockToAir(pos);
        }
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);
        return (meta & 4) > 0 ? this.getDefaultState().withProperty(PART, BlockTatamiMat.EnumPartType.HEAD).withProperty(FACING, enumfacing) : this.getDefaultState().withProperty(PART, BlockTatamiMat.EnumPartType.FOOT).withProperty(FACING, enumfacing);
    }
    
    /**
     * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withRotation(IBlockState state, Rotation rot)
    {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
     * blockstate.
     */
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }
    
    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        int i = (state.getValue(FACING)).getHorizontalIndex();
        if (state.getValue(PART) == BlockTatamiMat.EnumPartType.HEAD) i |= 4;
        return i;
    }
	
	@Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public Item getCustomItemBlock() {
        return new ItemTatamiMat()
                .setRegistryName(this.getRegistryName())
                .setTranslationKey(this.getTranslationKey());
    }

    public enum EnumPartType implements IStringSerializable {
        HEAD("head"),
        FOOT("foot");

        private final String name;

        EnumPartType(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        public String getName() {
            return this.name;
        }
    }
}