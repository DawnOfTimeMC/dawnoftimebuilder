package org.dawnoftimebuilder.blocks.general;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dawnoftimebuilder.enums.EnumsBlock;

import javax.annotation.Nullable;

public class DoTBBlockPortcullis extends DoTBBlock {

	private static final PropertyBool OPEN = PropertyBool.create("open");
	private static final PropertyBool POWERED = PropertyBool.create("powered");
	private static final PropertyEnum<EnumsBlock.EnumHorizontalAxis> AXIS = PropertyEnum.create("axis", EnumsBlock.EnumHorizontalAxis.class);
	private static final PropertyEnum<EnumsBlock.EnumVerticalConnection> VERTICAL_CONNECTION = PropertyEnum.create("vertical_connection", EnumsBlock.EnumVerticalConnection.class);
	private static final AxisAlignedBB X_AXIS_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	private static final AxisAlignedBB Z_AXIS_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
	private static final AxisAlignedBB VOID_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);

	public DoTBBlockPortcullis(String name, Material materialIn, float hardness, SoundType sound) {
		super(name, materialIn, hardness, sound);
		this.setDefaultState(this.blockState.getBaseState().withProperty(OPEN, false).withProperty(POWERED, false).withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_X).withProperty(VERTICAL_CONNECTION, EnumsBlock.EnumVerticalConnection.NONE));
	}

	public DoTBBlockPortcullis(String name, Material materialIn) {
		super(name, materialIn);
		this.setDefaultState(this.blockState.getBaseState().withProperty(OPEN, false).withProperty(POWERED, false).withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_X).withProperty(VERTICAL_CONNECTION, EnumsBlock.EnumVerticalConnection.NONE));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, OPEN, POWERED, AXIS, VERTICAL_CONNECTION);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		if(state.getValue(OPEN) && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.NONE && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.UNDER) {
			return VOID_AABB;
		}else return (state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_X) ? X_AXIS_AABB : Z_AXIS_AABB;
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		state = this.getActualState(state, source, pos);
		return (state.getValue(OPEN) && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.NONE && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.UNDER) ? NULL_AABB : (state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_X) ? X_AXIS_AABB : Z_AXIS_AABB;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
		return state.withProperty(VERTICAL_CONNECTION, this.getShape(worldIn, pos, state.getValue(AXIS)));
	}

	/**
	 * Called when a neighboring blocks was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * blocks, etc.
	 */
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		state = state.getActualState(worldIn, pos);
		//goal : to not have opened portcullis that can't be broken
		if (state.getValue(VERTICAL_CONNECTION) == EnumsBlock.EnumVerticalConnection.UNDER) {
			//update from top blocks : check the shape of whole portcullis to know if it can be or stay open
			EnumsBlock.EnumHorizontalAxis axis = state.getValue(AXIS);
			boolean isNowPowered = worldIn.isBlockPowered(pos);
			if(state.getValue(OPEN)){
				if(isInSamePlane(pos, fromPos, axis) && isNowPowered) setOpenState(worldIn, pos, axis,true);
			}else{
				if(isNowPowered){
					setOpenState(worldIn, pos, axis,true);
					state = state.withProperty(OPEN, true);
				}
			}
			if(!isNowPowered && state.getValue(POWERED)){
				state = state.withProperty(POWERED, false);
				worldIn.setBlockState(pos, state, 2);
				if(!hasAnotherPowerSource(worldIn, pos, axis)) setOpenState(worldIn, pos, axis,false);
			}
			if(isNowPowered != state.getValue(POWERED)) {
				state = state.withProperty(POWERED, isNowPowered);
				worldIn.setBlockState(pos, state, 2);
			}
		} else if(state.getValue(VERTICAL_CONNECTION) == EnumsBlock.EnumVerticalConnection.NONE) {
			if(state.getValue(OPEN)) worldIn.setBlockState(pos, state.withProperty(OPEN, false), 2);
		} else {
			//TODO check if everything is working properly
			//update from the rest in opening state : if it's from a blocks possibly in the portcullis, send a update task to top blocks
			//NB : VerticalConnection.NONE can't be open
			if (state.getValue(OPEN)) {
				EnumsBlock.EnumHorizontalAxis axis = state.getValue(AXIS);
				if(isInSamePlane(pos, fromPos, axis)){
					pos = getTopPortcullisPos(worldIn, pos, axis);
					worldIn.getBlockState(pos).neighborChanged(worldIn, pos, blockIn, fromPos);
				}
			}
		}
	}

	private boolean hasSameAxis(IBlockState state, EnumsBlock.EnumHorizontalAxis axis){
		if(state.getBlock() instanceof DoTBBlockPortcullis){
			return state.getValue(AXIS) == axis;
		}else return false;
	}

	private boolean isInSamePlane(BlockPos pos, BlockPos fromPos, EnumsBlock.EnumHorizontalAxis axis){
		if (axis == EnumsBlock.EnumHorizontalAxis.AXIS_X) return fromPos.getZ() == pos.getZ();
		else return fromPos.getX() == pos.getX();
	}

	private BlockPos getTopPortcullisPos(World worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis){
		IBlockState state;
		for (boolean isStillPortcullis = true; isStillPortcullis; pos = pos.up()) {
			state = worldIn.getBlockState(pos);
			if(state.getBlock() instanceof DoTBBlockPortcullis){
				if(!hasSameAxis(state, axis)) isStillPortcullis = false;
			}else isStillPortcullis = false;
		}
		return pos.down();
	}

	private boolean hasAnotherPowerSource(World worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis){
		boolean isAxisX = axis == EnumsBlock.EnumHorizontalAxis.AXIS_X;
		EnumFacing direction = (isAxisX) ? EnumFacing.WEST : EnumFacing.NORTH;
		int widthLeft = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? EnumFacing.EAST : EnumFacing.SOUTH);
		int widthRight = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? EnumFacing.WEST : EnumFacing.NORTH);
		IBlockState state;
		pos = pos.offset(direction, -widthLeft);
		for(int i = 0; i <= (widthLeft + widthRight); i++){
			state = worldIn.getBlockState(pos.offset(direction, i));
			if(state.getValue(POWERED)) return true;
		}
		return false;
	}

	private void setOpenState(World worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis, boolean openState){
		boolean isAxisX = axis == EnumsBlock.EnumHorizontalAxis.AXIS_X;
		EnumFacing direction = (isAxisX) ? EnumFacing.WEST : EnumFacing.NORTH;
		int height = getPortcullisHeight(worldIn, pos, axis);
		int widthLeft = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? EnumFacing.EAST : EnumFacing.SOUTH);
		int widthRight = getPortcullisWidth(worldIn, pos, axis, (isAxisX) ? EnumFacing.WEST : EnumFacing.NORTH);
		IBlockState state;
		if(height > 16 || (widthLeft + widthRight + 1) > 16) return;
		if(openState){
			for(int horizontal = 1; horizontal <= widthLeft; horizontal++){
				for(int vertical = 0; vertical < height; vertical++){
					state = worldIn.getBlockState(pos.offset((isAxisX) ? EnumFacing.EAST : EnumFacing.SOUTH, horizontal).down(vertical));
					if(state.getBlock() instanceof DoTBBlockPortcullis){
						if(state.getValue(AXIS) != axis) {
							openState = false;
							break;
						}
					}else{
						openState = false;
						break;
					}
				}
				if(!openState) break;
			}
			if(openState) {
				for (int horizontal = 1; horizontal <= widthRight; horizontal++) {
					for (int vertical = 0; vertical < height; vertical++) {
						state = worldIn.getBlockState(pos.offset(direction, horizontal).down(vertical));
						if (state.getBlock() instanceof DoTBBlockPortcullis) {
							if (state.getValue(AXIS) != axis) {
								openState = false;
								break;
							}
						} else {
							openState = false;
							break;
						}
					}
					if (!openState) break;
				}
			}
			if(openState) {
				openState = isCorrectBorder(worldIn, pos.offset(direction, -widthLeft).up(), axis, (isAxisX) ? EnumFacing.WEST : EnumFacing.NORTH, widthLeft + widthRight);
				openState = openState && isCorrectBorder(worldIn, pos.offset(direction, -widthLeft).down(height), axis, (isAxisX) ? EnumFacing.WEST : EnumFacing.NORTH, widthLeft + widthRight);
				openState = openState && isCorrectBorder(worldIn, pos.offset(direction, -widthLeft - 1), axis, EnumFacing.DOWN, height - 1);
				openState = openState && isCorrectBorder(worldIn, pos.offset(direction, widthRight + 1), axis, EnumFacing.DOWN, height - 1);
			}
		}
		pos = pos.offset(direction, -widthLeft);
		BlockPos newPos;
		for(int horizontal = 0; horizontal < widthLeft + widthRight + 1; horizontal++){
			for(int vertical = 0; vertical < height; vertical++){
				newPos = pos.offset(direction, horizontal).down(vertical);
				state = worldIn.getBlockState(newPos);
				if(state.getBlock() instanceof DoTBBlockPortcullis){
					if(state.getValue(AXIS) == axis) {
						worldIn.setBlockState(newPos, state.withProperty(OPEN, openState), 10);
						worldIn.markBlockRangeForRenderUpdate(newPos, newPos);
						if(horizontal == widthLeft){
							if(vertical == 0 && openState) worldIn.playEvent(null, this.getOpenSound(), newPos, 0);
							if(vertical == height - 1 && !openState) worldIn.playEvent(null, this.getCloseSound(), newPos, 0);
						}
					}
				}
			}
		}
	}

	private int getPortcullisHeight(World worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis){
		int height = 0;
		IBlockState state;
		for (boolean isSamePortcullis = true; isSamePortcullis; height++) {
			pos = pos.down();
			state = worldIn.getBlockState(pos);
			if(state.getBlock() instanceof DoTBBlockPortcullis){
				if(state.getValue(AXIS) != axis) isSamePortcullis = false;
			}else isSamePortcullis = false;
		}
		return height;
	}

	private int getPortcullisWidth(World worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis, EnumFacing direction){
		int width = 0;
		IBlockState state;
		for (boolean isSamePortcullis = true; isSamePortcullis; width++) {
			pos = pos.offset(direction);
			state = worldIn.getBlockState(pos);
			if(state.getBlock() instanceof DoTBBlockPortcullis){
				if(state.getValue(AXIS) != axis) isSamePortcullis = false;
			}else isSamePortcullis = false;
		}
		return (width - 1);
	}

	private boolean isCorrectBorder(World worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis, EnumFacing direction, int length){
		IBlockState state;
		for(int i = 0; i <= length; i++){
			state = worldIn.getBlockState(pos.offset(direction, i));
			if(state.getBlock() instanceof DoTBBlockPortcullis){
				if(state.getValue(AXIS) == axis) return false;
			}
		}
		return true;
	}

	private int getCloseSound() {
		return 1011;
	}

	private int getOpenSound()
	{
		return 1005;
	}

	private EnumsBlock.EnumVerticalConnection getShape(IBlockAccess worldIn, BlockPos pos, EnumsBlock.EnumHorizontalAxis axis){
		if(hasSameAxis(worldIn.getBlockState(pos.up()), axis)){
			return (hasSameAxis(worldIn.getBlockState(pos.down()), axis)) ? EnumsBlock.EnumVerticalConnection.BOTH : EnumsBlock.EnumVerticalConnection.ABOVE;
		}else{
			return (hasSameAxis(worldIn.getBlockState(pos.down()), axis)) ? EnumsBlock.EnumVerticalConnection.UNDER : EnumsBlock.EnumVerticalConnection.NONE;
		}
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		return this.getDefaultState().withProperty(AXIS, (placer.getHorizontalFacing().getAxis() == EnumFacing.Axis.Z) ? EnumsBlock.EnumHorizontalAxis.AXIS_X : EnumsBlock.EnumHorizontalAxis.AXIS_Z);
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.MIDDLE_POLE_THIN;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public int getMetaFromState(IBlockState state){
		int meta = 0;
		if(state.getValue(AXIS) == EnumsBlock.EnumHorizontalAxis.AXIS_Z) meta += 4;
		if(state.getValue(OPEN)) meta += 2;
		if(state.getValue(POWERED)) meta += 1;
		return meta;
	}

	@Override
	public IBlockState getStateFromMeta(int meta){
		IBlockState state = this.getDefaultState();
		if(meta >= 4) {
			state = state.withProperty(AXIS, EnumsBlock.EnumHorizontalAxis.AXIS_Z);
			meta -= 4;
		}
		if(meta >= 2) {
			state = state.withProperty(OPEN, true);
			meta -= 2;
		}
		if(meta >= 1) {
			state = state.withProperty(POWERED, true);
		}
		return state;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		IBlockState state = this.getActualState(worldIn.getBlockState(pos), worldIn, pos);
		return state.getValue(OPEN) && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.NONE && state.getValue(VERTICAL_CONNECTION) != EnumsBlock.EnumVerticalConnection.UNDER;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
}