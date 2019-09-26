package org.dawnoftimebuilder.blocks.japanese;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import org.dawnoftimebuilder.blocks.general.DoTBBlockBeam;
import org.dawnoftimebuilder.blocks.general.DoTBBlockTileEntity;
import org.dawnoftimebuilder.blocks.general.DoTBBlockWall;
import org.dawnoftimebuilder.enums.EnumsBlock;
import org.dawnoftimebuilder.items.japanese.ItemLittleFlag;
import org.dawnoftimebuilder.blocks.IBlockCustomItem;
import org.dawnoftimebuilder.tileentity.TileEntityLittleFlag;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static org.dawnoftimebuilder.items.DoTBItems.little_flag;

public class BlockLittleFlag extends DoTBBlockTileEntity implements IBlockCustomItem {

    private static final PropertyEnum<EnumsBlock.EnumHorizontalAxis> AXIS = PropertyEnum.create("axis", EnumsBlock.EnumHorizontalAxis.class);
    private static final PropertyBool NORTH = PropertyBool.create("north");
    private static final PropertyBool EAST = PropertyBool.create("east");
    private static final PropertyBool SOUTH = PropertyBool.create("south");
    private static final PropertyBool WEST = PropertyBool.create("west");
    private static final AxisAlignedBB AXIS_X_BB = new AxisAlignedBB(0.00F, 0.0F, 0.4375F , 1.0F, 1.0F, 0.5625F);
    private static final AxisAlignedBB AXIS_Z_BB = new AxisAlignedBB(0.4375F, 0.0F, 0.00F , 0.5625F, 1.0F, 1.0F);

    public BlockLittleFlag() {
        super("little_flag", Material.CLOTH);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_X).withProperty(NORTH, false).withProperty(EAST, false).withProperty(SOUTH, false).withProperty(WEST, false));
        this.setSoundType(SoundType.CLOTH);
        this.setHardness(0.8F);
        this.setBurnable();
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if(state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_X){
            state = this.getDefaultState().withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_X);
            state = state.withProperty(EAST, canConnect(worldIn.getBlockState(pos.east()).getBlock()));
            state = state.withProperty(WEST, canConnect(worldIn.getBlockState(pos.west()).getBlock()));
        }else{
            state = this.getDefaultState().withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_Z);
            state = state.withProperty(NORTH, canConnect(worldIn.getBlockState(pos.north()).getBlock()));
            state = state.withProperty(SOUTH, canConnect(worldIn.getBlockState(pos.south()).getBlock()));
        }
        return state;
    }

    private boolean canConnect(Block block){
        return block instanceof BlockFence || block instanceof BlockWall || block instanceof DoTBBlockWall || block instanceof DoTBBlockBeam;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return little_flag;
    }
    
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
    	TileEntity tileentity = worldIn.getTileEntity(pos);
    	if(tileentity instanceof TileEntityLittleFlag){
            return ((TileEntityLittleFlag)tileentity).getItem();
    	}else{
    		return new ItemStack(little_flag);
    	}
    }
    

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
    	return false;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos p_193383_3_, EnumFacing facing) {
    	return BlockFaceShape.UNDEFINED;
    }
    
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(AXIS, (placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.X) ? EnumsBlock.EnumHorizontalAxis.AXIS_Z : EnumsBlock.EnumHorizontalAxis.AXIS_X);
    }
    
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return (state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_X) ? AXIS_X_BB : AXIS_Z_BB;
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(AXIS, (meta < 8) ? EnumsBlock.EnumHorizontalAxis.AXIS_X : EnumsBlock.EnumHorizontalAxis.AXIS_Z);
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    @Override
    public int getMetaFromState(IBlockState state){
        return (state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_X) ? 0 : 8;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityLittleFlag();
	}
	
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, AXIS, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    public Item getCustomItemBlock() {
        return new ItemLittleFlag()
                .setRegistryName(this.getRegistryName())
                .setTranslationKey(this.getTranslationKey());
    }
}