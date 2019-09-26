package org.dawnoftimebuilder.blocks.japanese;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.dawnoftimebuilder.blocks.general.DoTBBlockFence;

public class BlockSpruceRailing extends DoTBBlockFence {

	private static final PropertyEnum<EnumPillar> PILLAR = PropertyEnum.create("pillar", EnumPillar.class);

	public BlockSpruceRailing() {
		super("spruce_railing", Material.WOOD, 2.0F, SoundType.WOOD);
		this.setBurnable();
		this.setDefaultState(this.blockState.getBaseState().withProperty(EAST, false).withProperty(NORTH, false).withProperty(PILLAR, EnumPillar.NONE).withProperty(SOUTH, false).withProperty(WEST, false));
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, EAST, NORTH, PILLAR, SOUTH, WEST);
    }

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
	    return this.getDefaultState().withProperty(PILLAR, (facing.getAxis().isVertical()) ? EnumPillar.PILLAR_BIG : EnumPillar.NONE);
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing facing) {
		if(facing != EnumFacing.UP && facing != EnumFacing.DOWN) return BlockFaceShape.UNDEFINED;
		return (this.hasNoPillar(state)) ? BlockFaceShape.UNDEFINED : BlockFaceShape.CENTER_BIG;
	}

	@Override
	public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		IBlockState state = worldIn.getBlockState(pos);
		BlockFaceShape shape = state.getBlockFaceShape(worldIn, pos, facing);
		Block block = state.getBlock();
		return !isExcepBlockForAttachWithPiston(block) && shape == BlockFaceShape.SOLID || block instanceof BlockSpruceRailing || (shape == BlockFaceShape.MIDDLE_POLE && block instanceof BlockFenceGate);
	}

    /**
     * Get the actual Block state of this Block at the given position. This applies properties not visible in the
     * metadata, such as fence connections.
     */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		state = super.getActualState(state, worldIn, pos);
		if(this.hasNoPillar(state)) return state;
		else{
			if((state.getValue(EAST) || state.getValue(WEST)) && (state.getValue(NORTH) || state.getValue(SOUTH))){
				if(!worldIn.isAirBlock(pos.up())) return state.withProperty(PILLAR, EnumPillar.PILLAR_BIG);
				else return state.withProperty(PILLAR, EnumPillar.CAP_PILLAR_BIG);
			}else return state.withProperty(PILLAR, EnumPillar.PILLAR_SMALL);
		}
	}

	@Override
    public int getMetaFromState(IBlockState state) {
        return this.hasNoPillar(state) ? 0 : 1;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(PILLAR, (meta == 0) ? EnumPillar.NONE : EnumPillar.PILLAR_BIG);
    }

    private boolean hasNoPillar(IBlockState state){
		return state.getValue(PILLAR) == EnumPillar.NONE;
	}

	public EnumPillar getShape(IBlockState state){
		return state.getValue(PILLAR);
	}

	public enum EnumPillar implements IStringSerializable {
		NONE("none"),
		PILLAR_BIG("pillar_big"),
		PILLAR_SMALL("pillar_small"),
		CAP_PILLAR_BIG("cap_pillar_big");

		private final String name;

		EnumPillar(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.name;
		}

		public String getName()
		{
			return this.name;
		}
	}
}
