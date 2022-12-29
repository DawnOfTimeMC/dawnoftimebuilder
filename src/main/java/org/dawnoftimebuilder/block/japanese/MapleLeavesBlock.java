package org.dawnoftimebuilder.block.japanese;

import org.dawnoftimebuilder.block.ICustomBlockItem;
import org.dawnoftimebuilder.block.templates.BlockDoTB;
import org.dawnoftimebuilder.util.DoTBBlockStateProperties;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MapleLeavesBlock extends BlockDoTB implements ICustomBlockItem
{
    public static final IntegerProperty MULTIBLOCK_X = DoTBBlockStateProperties.MULTIBLOCK_3X;
    public static final IntegerProperty MULTIBLOCK_Y = DoTBBlockStateProperties.MULTIBLOCK_2Y;
    public static final IntegerProperty MULTIBLOCK_Z = DoTBBlockStateProperties.MULTIBLOCK_3Z;

    public MapleLeavesBlock(Properties properties)
    {
        super(properties);

        this.registerDefaultState(this.defaultBlockState()
        		.setValue(MapleTrunkBlock.FACING, Direction.NORTH)
        		.setValue(MULTIBLOCK_X, 0)
        		.setValue(MULTIBLOCK_Y, 0)
        		.setValue(MULTIBLOCK_Z, 0));
    }

    public void playerWillDestroy(World worldIn, BlockPos blockPosIn, BlockState blockStateIn, PlayerEntity playerEntityIn) 
    {
        if(!worldIn.isClientSide)
    	{
        	float currentX = 0-blockStateIn.getValue(MULTIBLOCK_X);
	    	float currentY = 0-blockStateIn.getValue(MULTIBLOCK_Y);
	    	float currentZ = 0-blockStateIn.getValue(MULTIBLOCK_Z);
	        for(int x = 0; x <= 2; x ++)
	        {
	            for(int y = 0; y <= 1; y ++)
	            {
	                for(int z = 0; z <= 2; z ++)
	                {
	        	    	BlockPos baseBlockPos = new BlockPos(blockPosIn.getX() + x + currentX, blockPosIn.getY() + y + currentY, blockPosIn.getZ() + z + currentZ);
	        	    	BlockState state = worldIn.getBlockState(baseBlockPos);
	        			worldIn.setBlock(baseBlockPos, Blocks.AIR.defaultBlockState(), 35);
	        			worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
	                }
	            }
	        }
	        
	        if(!playerEntityIn.isCreative())
	        {
		        BlockPos trunkBlockPos = new BlockPos(blockPosIn.getX() + currentX + 1, blockPosIn.getY() + currentY - 1, blockPosIn.getZ() + currentZ + 1);
		        worldIn.destroyBlock(trunkBlockPos, true);
	        }
	        else
	        {
		        BlockPos trunkBlockPos = new BlockPos(blockPosIn.getX() + currentX + 1, blockPosIn.getY() + currentY - 1, blockPosIn.getZ() + currentZ + 1);
    	    	BlockState state = worldIn.getBlockState(trunkBlockPos);
    			worldIn.setBlock(trunkBlockPos, Blocks.AIR.defaultBlockState(), 35);
    			worldIn.levelEvent(playerEntityIn, 2001, blockPosIn, Block.getId(state));
	        }
	    }
        
        super.playerWillDestroy(worldIn, blockPosIn, blockStateIn, playerEntityIn);
    }
    
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(MapleTrunkBlock.FACING, MULTIBLOCK_X, MULTIBLOCK_Y, MULTIBLOCK_Z);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {	
        return stateIn;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

	@Override
	public Item getCustomBlockItem()
	{
		return null;
	}
}
